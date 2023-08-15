package booktopiahub.controller;

import booktopiahub.dto.order.CreateOrderRequestDto;
import booktopiahub.dto.order.OrderDto;
import booktopiahub.dto.order.UpdateOrderRequestDto;
import booktopiahub.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get a list of all available orders")
    public List<OrderDto> findAll(@ParameterObject Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id", description = "Get available order by id")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get order by userId", description = "Get available order by userId")
    public OrderDto getByUserId(@PathVariable Long userId) {
        return orderService.getByUserId(userId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by id", description = "Update available order by id")
    public OrderDto update(@PathVariable Long id,
                           @RequestBody @Valid UpdateOrderRequestDto requestDto) {
        return orderService.update(id, requestDto);
    }

    @PostMapping
    @Operation(summary = "Add order to repository", description = "Add valid order to repository")
    public OrderDto addOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        return orderService.addOrder(requestDto);
    }

    @GetMapping("/{id}/items/{orderItemId}")
    @Operation(summary = "Get orderitem by order id and item id",
            description = "Get available orderitem by order id and item id")
    public OrderDto getOrderItemByOrderIdAndItemId(
            @PathVariable Long orderId,
            @PathVariable Long orderItemId
    ) {
        return orderService.getByOrderIdAndOrderItemId(orderId, orderItemId);
    }
}
