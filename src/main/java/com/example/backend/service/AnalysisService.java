package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.OrderItemsRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
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

    @Cacheable("all-top-products")
    public List<TopProduct> getTopProducts() {
        // filter orderItems nach SKU -> count SKU
        List<Product> allProducts = productRepository.findAll();
        List<TopProduct> topProducts = new ArrayList<>();

        for (Product product: allProducts) {
            topProducts.add(new TopProduct(product.getName(), product.getCategory(), product.getSize(), orderItemsRepository.countBySKU(product.getSKU())));
        }
        return topProducts;
    }

    @Cacheable("top-product")
    public TopProduct getTopProduct() {
        // filter orderItems nach SKU -> count SKU
        List<Product> allProducts = productRepository.findAll();

        Map<String, Long> topProductMap = new HashMap<>();
        TreeMap<Long, String> sortedMap = new TreeMap<>(Collections.reverseOrder());
        for (Product product: allProducts) {
            topProductMap.put(product.getSKU(),orderItemsRepository.countBySKU(product.getSKU()));
        }

        for (Map.Entry<String, Long> entry : topProductMap.entrySet()) {
            sortedMap.put(entry.getValue(), entry.getKey());
        }
        Product product = productRepository.findProductBySKU(sortedMap.firstEntry().getValue());

        return new TopProduct(product.getName(), product.getCategory(), product.getSize(), sortedMap.firstKey());
    }

    @Cacheable("all-top-stores")
    public List<TopStore>  getTopStores() {
        List<Store> allStores = storeRepository.findAll();
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
        List<Store> allStores = storeRepository.findAll();
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
        List<Store> stores = storeRepository.findAll();
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

    }

    @Cacheable("all-top-gainer")
    public List<TopGainerStore> getTopGainerStores() {
        List<Store> stores = storeRepository.findAll();
        List<TopGainerStore> topGainerStores = new ArrayList<>();

        for (Store store: stores) {
            topGainerStores.add(new TopGainerStore(store.getCity(), store.getState(), orderRepository.getfFavoriteStores(store.getStoreID())));
        }
        return topGainerStores;
    }

    @Cacheable("top-gainer")
    public TopGainerStore getTopGainerStore() {
        List<Store> stores = storeRepository.findAll();
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