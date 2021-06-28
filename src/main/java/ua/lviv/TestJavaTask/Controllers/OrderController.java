package ua.lviv.TestJavaTask.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.TestJavaTask.Entities.Order;
import ua.lviv.TestJavaTask.Services.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        orderService.save(order);
        return new ResponseEntity<>("Order was created", HttpStatus.OK);
    }

    @GetMapping("/getItem")
    public ResponseEntity<List<Order>> getItem(@RequestParam String item, @RequestParam int quantity){
        List<Order> returnedOrders = orderService.getItems(item, quantity);
        return new ResponseEntity<>(returnedOrders, HttpStatus.OK);
    }

}
