package steps;

import constants.ApiEndpoint;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.OrderCreateRequest;

import static constants.ApiEndpoint.ORDER_GET_LIST;
import static constants.ApiEndpoint.ORDER_POST_CREATE;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiEndpoint.BASE_URL);
    }

    @Step("Создание нового заказа")
    public ValidatableResponse orderCreate(OrderCreateRequest orderCreateRequest) {
        return requestSpecification()
                .body(orderCreateRequest)
                .post(ORDER_POST_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpecification()
                .get(ORDER_GET_LIST)
                .then();
    }
}


