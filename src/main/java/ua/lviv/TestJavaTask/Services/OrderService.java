package ua.lviv.TestJavaTask.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.TestJavaTask.Entities.Order;
import ua.lviv.TestJavaTask.Repositories.OrderRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void save(Order order) {
        order.setCreationDate(LocalDateTime.now());
        orderRepo.save(order);
    }


    public List<Order> getItems(String item, int quantity) {
        List<Order> resultList = new ArrayList<>();
        LocalDateTime timeDiff = LocalDateTime.now().minusMinutes(10);
        List<Order> foundedOrders = orderRepo.findAllByItemAndCreationDateGreaterThanOrderByPrice(item, timeDiff);

        int index = 0;
        while (quantity != 0 && index < foundedOrders.size()){
            Order elem = foundedOrders.get(index);
            if(elem.getQuantity() > quantity){
                elem.setQuantity(elem.getQuantity() - quantity);
                orderRepo.save(elem);
                elem.setQuantity(quantity);
                resultList.add(elem);
                return resultList;
            }
            resultList.add(elem);
            quantity -= elem.getQuantity();
            orderRepo.delete(elem);
            index++;
        }
        return resultList;
    }
}
