package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderCreateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;
@RunWith(Parameterized.class)
public class OrderCreateTest {
    private List<String> color;
    public OrderCreateTest(List<String> color) {
        this.color = color;
    }
    @Parameterized.Parameters (name = "Цвет самоката - {0}")
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void orderCreate() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setColor(color);
        OrderSteps orderSteps = new OrderSteps();
        orderSteps.orderCreate(orderCreateRequest)
                .assertThat().body("track", instanceOf(Integer.class))
                .and()
                .statusCode(201);
    }

}

