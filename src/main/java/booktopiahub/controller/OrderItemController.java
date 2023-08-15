package booktopiahub.controller;

import booktopiahub.dto.order.orderitem.OrderItemDto;
import booktopiahub.service.order.orderitem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get orderItem by id", description = "Get avalilable orderItem by id")
    public OrderItemDto getOrderItemById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{bookId}")
    @Operation(summary = "Get orderItem by bookId",
            description = "Get available orderItem by bookId")
    public OrderItemDto getByBookId(@PathVariable Long bookId) {
        return orderItemService.getByBookId(bookId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete orderItem by id",
            description = "Soft delete of orderItem by id from orders")
    public void deleteById(@PathVariable Long id) {
        orderItemService.deleteById(id);
    }
}
