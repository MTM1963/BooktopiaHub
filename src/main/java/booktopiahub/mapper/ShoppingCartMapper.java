package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.shoppingcart.ShoppingCartDto;
import booktopiahub.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
