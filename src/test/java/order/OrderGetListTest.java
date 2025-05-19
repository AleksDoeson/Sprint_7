package order;

import constants.ApiEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import org.junit.Test;

import static constants.ApiEndpoint.ORDER_GET_LIST;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
public class OrderGetListTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void orderGetList() {
        given().log().all()
                .baseUri(ApiEndpoint.BASE_URL)
                .get(ORDER_GET_LIST)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}

