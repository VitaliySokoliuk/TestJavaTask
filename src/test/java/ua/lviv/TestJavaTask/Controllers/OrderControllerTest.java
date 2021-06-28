package ua.lviv.TestJavaTask.Controllers;

import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.lviv.TestJavaTask.Entities.Order;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createOrder() throws Exception {
        Order order = new Order();
        order.setQuantity(3);
        order.setItem("Candy");
        order.setPrice(23.1);

        Gson gson = new Gson();
        this.mvc.perform(post("/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(order)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Order was created"));
    }

    @Test
    @Sql(value = {"/create_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getItem() throws Exception {
        this.mvc.perform(get("/getItem?item=Bread&quantity=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":3,\"item\":\"Bread\",\"quantity\":3,\"price\":3.75")))
                .andExpect(content().string(containsString("\"id\":1,\"item\":\"Bread\",\"quantity\":5,\"price\":4.25")))
                .andExpect(content().string(containsString("\"id\":4,\"item\":\"Bread\",\"quantity\":2,\"price\":4.5")));
    }

    @Test
    @Sql(value = {"/delete_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getItem2() throws Exception {
        this.mvc.perform(get("/getItem?item=Bread&quantity=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    @Sql(value = {"/create_one_order.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getItem3() throws Exception {
        this.mvc.perform(get("/getItem?item=Bread&quantity=12"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1,\"item\":\"Bread\",\"quantity\":5,\"price\":4.25")));
    }

}