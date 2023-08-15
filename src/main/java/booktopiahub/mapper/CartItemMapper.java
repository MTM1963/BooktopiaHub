package booktopiahub.mapper;

import booktopiahub.config.MapperConfig;
import booktopiahub.dto.shoppingcart.cartitem.CartItemDto;
import booktopiahub.dto.shoppingcart.cartitem.CreateCartItemRequestDto;
import booktopiahub.dto.shoppingcart.cartitem.UpdateCartItemDto;
import booktopiahub.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    CartItem toModel(CreateCartItemRequestDto cartItemRequestDto);

    @Mapping(target = "id", ignore = true)
    CartItem toModel(UpdateCartItemDto updateCartItemDto);
}
