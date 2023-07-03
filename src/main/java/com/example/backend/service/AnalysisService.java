package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.OrderItemsRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    public final OrderItemsRepository orderItemsRepository;
    public final ProductRepository productRepository;
    public final StoreRepository storeRepository;
    public final OrderRepository orderRepository;
    public final CacheService cacheService;

    @Cacheable("all-top-products")
    public List<TopProduct> getTopProducts() {
        // filter orderItems nach SKU -> count SKU
        List<Product> allProducts = getAllProducts();
        List<TopProduct> topProducts = getAllTopProductsOrdered();

        return topProducts;
    }

    @Cacheable("all-products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable("all-stores")
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }



    public void dynamicPricing() {
        // check after every analysis call
        // get top products in relation to their orders
        // calculate the average order of the most and least sold product
        // Make a list of every product that is over 75% over the double the average of products order count
        // Make a list of every product that is lower than 25% under the average of products order count
        // If a product is over 75% of the average order count then increase the price to 10%
        // If a product is lower than the average order count then decrease the price to 10%

        List<TopProduct> topProducts = getTopProducts();
        List<Product> setHigerList = new ArrayList<>();
        List<Product> setLowerList = new ArrayList<>();

        Long getHighestOrder = topProducts.get(0).getAmountOfOrder();
        Long getLowestOrder = topProducts.get(topProducts.size() - 1).getAmountOfOrder();
        Long averageOrder = (getHighestOrder + getLowestOrder) / 2;
        Double highGate = (averageOrder * 1.25);
        Double lowGate = (averageOrder * 0.75);
        System.out.println("highest: " + highGate);
        System.out.println("lowest: " + lowGate);

        // iterate through sortedMap and add Products that need to be changed
        for (TopProduct entry:  topProducts) {
            if (entry.getAmountOfOrder() > highGate) {
                //setHigerList.add(productRepository.findProductBySKU(""));
            }

            if (entry.getAmountOfOrder() < lowGate) {
                //setLowerList.add(productRepository.findProductBySKU("");
            }
        }

        System.out.println(setHigerList);
        System.out.println(setLowerList);


    }

    @Cacheable("top-products-ordered")
    public List<TopProduct> getAllTopProductsOrdered() {
        // filter orderItems nach SKU -> count SKU
        List<Product> allProducts = getAllProducts();
        List<TopProduct> topProducts = new ArrayList<>();

        Map<String, Long> topProductMap = new HashMap<>();
        TreeMap<Long, String> sortedMap = new TreeMap<>(Collections.reverseOrder());
        for (Product product: allProducts) {
            topProductMap.put(product.getSKU(),orderItemsRepository.countBySKU(product.getSKU()));
        }

        for (Map.Entry<String, Long> entry : topProductMap.entrySet()) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<Long, String> entry : sortedMap.entrySet()) {
            Product product = productRepository.findProductBySKU(entry.getValue());
            topProducts.add(new TopProduct(product.getName(), product.getCategory(), product.getSize(), entry.getKey()));
        }

        return topProducts;
    }

    public void test() {
        List<Product> allProducts = getAllProducts();
        for (Product item: allProducts) {
            orderItemsRepository.countBySKU(item.getSKU());

        }
    }

    @Cacheable("top-product")
    public TopProduct getTopProduct() {
        List<TopProduct> topProducts = getAllTopProductsOrdered();

        return topProducts.get(0);
    }

    @Cacheable("all-top-stores")
    public List<TopStore>  getTopStores() {
        List<Store> allStores = getAllStores();
        List<TopStore> topStores = new ArrayList<>();


        Map<String, Long> topStoreMap = new HashMap<>();

        for (Store store: allStores) {
            topStoreMap.put(store.getStoreID(),orderRepository.countByStoreID(store.getStoreID()));
            topStores.add(new TopStore(store.getCity(),store.getState(), orderRepository.countByStoreID(store.getStoreID())));
        }
        return topStores;
    }

    @Cacheable("top-store")
    public TopStore getTopStore() {
        List<Store> allStores = getAllStores();
        Map<String, Long> topStoreMap = new HashMap<>();
        TreeMap<Long, String> sortedMap = new TreeMap<>(Collections.reverseOrder());
        for (Store store: allStores) {
            topStoreMap.put(store.getStoreID(),orderRepository.countByStoreID(store.getStoreID()));
        }

        for (Map.Entry<String, Long> entry : topStoreMap.entrySet()) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }

        Store store = storeRepository.findByStoreID(sortedMap.firstEntry().getValue());

        return new TopStore(store.getCity(), store.getState(), sortedMap.firstKey());
    }

    @Cacheable("top-seasons")
    public List<TopSeason> getTopSeasons() {
        int[] years = {2020, 2021, 2022};
        List<TopSeason> topSeasons = new ArrayList<>();
        for (int year: years) {
            for (Month month: Month.values()) {
                LocalDate startDate = LocalDate.of(year, month, 1);
                LocalDate endDate;
                if (Year.isLeap(year)) endDate = LocalDate.of(year, month, month.maxLength());
                else
                    endDate = LocalDate.of(year, month, month.maxLength() - 1);
                topSeasons.add(new TopSeason(year, month, orderRepository.getAllBetweenTimeSpan(startDate, endDate)));
            }
        }

        return topSeasons;
    }

    public void makeOrder(OrderDTO orderDTO) {
        // create Order:
        // set totalAmount to total
        // create OrderItem:
        // for every Item and save its SKU and the given orderID

        Random random = new Random();
        int zahl = random.nextInt(1000000);
        String newCustomerID = "C" + zahl;
        List<Store> stores = getAllStores();
        String newOrderID = "" + (orderRepository.count());
        String randomStoreID = stores.get(random.nextInt(stores.size())).getStoreID();
        int nItems = 0;
        for (OrderItemDTO item: orderDTO.getOrderList()) {
            nItems += item.getAmount();
            // get sku and add as many orderItems as amount
            for (int i = 0; i < item.getAmount(); i++) {
                orderItemsRepository.save(OrderItem.builder().orderID(newOrderID).SKU(item.getSku()).build());
                System.out.println(OrderItem.builder().orderID(newOrderID).SKU(item.getSku()).build());
            }
        }

        Order newOrder = Order.builder()
                .orderID(newOrderID)
                .customerID(newCustomerID)
                .storeID(randomStoreID)
                .orderDate(LocalDate.now().toString())
                .nItems(nItems)
                .total(Double.parseDouble(orderDTO.getTotalAmount().substring(0,orderDTO.getTotalAmount().length() - 1)))
                .build();

        System.out.println(newOrder);
        orderRepository.save(newOrder);
        cacheService.clearCache();

    }


    @Cacheable("all-top-gainer")
    public List<TopGainerStore> getTopGainerStores() {
        List<Store> stores = getAllStores();
        List<TopGainerStore> topGainerStores = new ArrayList<>();

        for (Store store: stores) {
            topGainerStores.add(new TopGainerStore(store.getCity(), store.getState(), orderRepository.getfFavoriteStores(store.getStoreID())));
        }
        return topGainerStores;
    }

    @Cacheable("top-gainer")
    public TopGainerStore getTopGainerStore() {
        List<Store> stores = getAllStores();
        TreeMap<Double, String> totalMap = new TreeMap<>(Collections.reverseOrder());

        for (Store store: stores) {
            totalMap.put(orderRepository.getfFavoriteStores(store.getStoreID()), store.getStoreID());
        }

        Store store = storeRepository.findByStoreID(totalMap.firstEntry().getValue());

        return new TopGainerStore(store.getCity(), store.getState(), totalMap.firstKey());
    }



    @Cacheable("total-income")
    public double getTotalIncome() {

        return orderRepository.getTotalIncome();
    }


}
