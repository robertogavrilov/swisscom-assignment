package com.swisscom.demo.productservice.service.impl;

import com.swisscom.demo.productservice.exceptions.BadRequestException;
import com.swisscom.demo.productservice.exceptions.RecordNotFoundException;
import com.swisscom.demo.productservice.model.Product;
import com.swisscom.demo.productservice.model.ReservedStock;
import com.swisscom.demo.productservice.model.StockItem;
import com.swisscom.demo.productservice.model.enums.OrderState;
import com.swisscom.demo.productservice.model.requests.OrderRequest;
import com.swisscom.demo.productservice.model.response.OrderedProduct;
import com.swisscom.demo.productservice.repository.ReservedStockRepository;
import com.swisscom.demo.productservice.service.ProductService;
import com.swisscom.demo.productservice.service.ReservedStockService;
import com.swisscom.demo.productservice.service.StockItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservedStockServiceImpl implements ReservedStockService {

    private final ReservedStockRepository reservedStockRepository;

    private final StockItemService stockItemService;

    private final ProductService productService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ReservedStockServiceImpl(ReservedStockRepository reservedStockRepository, StockItemService stockItemService, ProductService productService) {
        this.reservedStockRepository = reservedStockRepository;
        this.stockItemService = stockItemService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public List<OrderedProduct> makeOrder(OrderRequest orderRequest) {

        Set<Long> productIds = orderRequest.getOrderingProducts().keySet();

        Map<Long, Product> products = productService.getAllProductsById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        if (products.isEmpty()) {
            logger.error("Products with IDs [{}] does not exist.", productIds);
            throw new BadRequestException("Product/s not found");
        }

        List<ReservedStock> reservedStockList = new ArrayList<>();
        List<StockItem> stockItems = new ArrayList<>();
        List<OrderedProduct> orderedProductList = new ArrayList<>();

        for (Long productId : productIds) {

            Product product = products.get(productId);

            if (product == null) {
                logger.error("Product [{}] not found.", productId);
                throw new RecordNotFoundException(String.format("Can not proceed ordering. Product %s does not exist.", productId));
            }

            StockItem stockItem = product.getStockItem();

            int orderingQuantity = orderRequest.getOrderingProducts().get(productId);
            int stockQuantity = stockItem.getAvailableItems();
            int reservedStockQuantity = stockItem.getReservedStocks().size();

            // Validate stock before make reservation
            if (stockQuantity == 0 || orderingQuantity > stockQuantity - reservedStockQuantity) {
                logger.error("Can not proceed ordering. Product [{}] is not available in stock.", product.getName());
                throw new BadRequestException("Product " + product.getName() + " not available in stock");
            }

            // Create reserved stock items as much as requested ordering quantity
            for (int i = 0; i < orderingQuantity; i++) {
                ReservedStock reservedStock = new ReservedStock();
                reservedStockRepository.save(reservedStock);
                reservedStockList.add(reservedStock);

                OrderedProduct orderedProduct = new OrderedProduct(reservedStock.getId(), product.getId(), product.getName(),
                        product.getType(), product.getDescription());

                orderedProductList.add(orderedProduct);
            }
            stockItem.setReservedStocks(reservedStockList);
            stockItems.add(stockItem);
        }

        stockItemService.saveMultiple(stockItems);
        return orderedProductList;
    }

    @Override
    @Transactional
    public void stockUpdate(List<Long> reservedStockIds, OrderState orderState) {
        logger.info("Updating reserved stock items [{}] triggered by [{}] state.", reservedStockIds, orderState);

        List<ReservedStock> reservedStocks = reservedStockRepository.findAllById(reservedStockIds);

        if (reservedStocks.size() != reservedStockIds.size()) {
            logger.error("Different number of reserved stocks. Trying to delete [{}] , found [{}]", reservedStockIds, reservedStocks);
            throw new BadRequestException("Stock was already updated");
        }

        Set<StockItem> stockItems = stockItemService.findAllByReservedStockItemIds(reservedStockIds);

        try {
            reservedStockRepository.deleteAll(reservedStocks);
        } catch (Exception e) {
            logger.error("An error occurred while deleting reserved stock with stock item ID [{}]. Reason is: [{}]", reservedStockIds, e.getMessage(), e);
            throw new BadRequestException("Unexpected error occurred while deleting reserved stock");
        }

        for (StockItem stockItem : stockItems) {
            if (OrderState.DELIVERED == orderState) {
                stockItem.decreaseStockItems(stockItem.getReservedStocks().size());
            }
            stockItem.setReservedStocks(null);
        }
        stockItemService.saveMultiple(List.copyOf(stockItems));
    }
}
