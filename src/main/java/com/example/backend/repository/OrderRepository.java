package com.example.backend.repository;

import com.example.backend.model.Order;
import com.example.backend.model.StoreStatistics;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    long countByStoreID (String StoreID);

    List<Order> getAllByOrderID(String orderID);

    @Aggregation(pipeline = {
            "{$match: { storeID: ?0}}",
            "{$group: { _id: '', total: {$sum: $total}}}"
    })
    double getfFavoriteStores(String storeId);

    @Aggregation(pipeline = {
            "{$match: { orderID: ?0}}",
            "{$group: { _id: '', total: {$sum: $total}}}"
    })
    double getProductIncomeByOrderID(String orderID);

    @Aggregation(pipeline = {
            "{$match: {orderDate: {$gte: ?0, $lte: ?1}}}",
            "{$group: { _id: '', total: {$sum: $total}, count: {$sum: 1}}}"
    })
    Double getAllBetweenTimeSpan(LocalDate startDate, LocalDate endDate);

    @Aggregation(pipeline = {
            "{$group: { _id: '', total: {$sum: $total}, count: {$sum: 1}}}"
    })
    Double getTotalIncome();

}
