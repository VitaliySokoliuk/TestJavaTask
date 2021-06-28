package ua.lviv.TestJavaTask.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.TestJavaTask.Entities.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findAllByItemAndCreationDateGreaterThanOrderByPrice(String item, LocalDateTime creationDate);

}
