package booktopiahub.repository.order;

import booktopiahub.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND EXISTS (SELECT oi FROM o.orderItems oi WHERE oi.id = :orderItemsId)")
    Optional<Order> findByOrderIdAndOrderItemId(@Param("orderId") Long orderId, @Param("orderItemsId") Long orderItemsId);
}
