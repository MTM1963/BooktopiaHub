package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.order.orderitem.OrderItemDto;
import booktopiahub.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
}
