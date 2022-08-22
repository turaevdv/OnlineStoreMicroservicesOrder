package ru.turaev.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.turaev.order.model.Order;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByIsCanceledIsFalse();

    @Query("select o from Order o where o.pickupPointId = :id and o.orderDate between :begin and :end and o.isCanceled = false ORDER BY o.orderDate")
    List<Order> getAllNonCancelledOrdersOfPickupPointByPeriod(long id, LocalDate begin, LocalDate end);
}
