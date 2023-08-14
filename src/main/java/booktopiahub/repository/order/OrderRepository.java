package booktopiahub.repository.order;

import booktopiahub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserId(Long userId);

    Order findByIdAndOrderItemId(Long id, Long orderItemId);
}
