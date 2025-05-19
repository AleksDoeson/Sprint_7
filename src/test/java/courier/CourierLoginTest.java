package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import org.junit.Test;
import steps.CourierSteps;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class CourierLoginTest {
    public static String login = "doeson_" + System.currentTimeMillis();
    public static String password = "qwerty123";
    public static String firstName = "Aleks";
    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void loginCourier() {
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(login, password, firstName);
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courierCreateRequest);

        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().statusCode(200)
                .and().body("id", instanceOf(Integer.class));

        courierSteps.courierDeleteAfterLogin(courierLoginRequest);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля login")
    public void loginCourierWithoutLogin() {
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(login, password, firstName);
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(null, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courierCreateRequest);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        CourierLoginRequest validCourierLoginRequest = new CourierLoginRequest(login, password);
        courierSteps.courierDeleteAfterLogin(validCourierLoginRequest);
    }
    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля password")
    public void loginCourierWithoutPassword() {
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(login, password, firstName);
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, "");
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierCreate(courierCreateRequest);
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        CourierLoginRequest validCourierLoginRequest = new CourierLoginRequest(login, password);
        courierSteps.courierDeleteAfterLogin(validCourierLoginRequest);
    }
    @Test
    @DisplayName("Авторизация курьера, используя несуществующие данные")
    @Description("Проверка, что курьер НЕ может авторизоваться, используя несуществующие данные для входа")
    public void loginCourierWithNonExistentCredential () {
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest(login, password);
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.courierLogin(courierLoginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }
}

