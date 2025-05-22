package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void orderGetList() {
        orderSteps.getOrderList()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}


