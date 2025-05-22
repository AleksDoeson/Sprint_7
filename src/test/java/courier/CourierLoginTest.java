package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class CourierLoginTest {

    private String login;
    private final String password = "qwerty123";
    private final String firstName = "Aleks";

    private CourierSteps courierSteps;
    private boolean isCourierCreated;

    @Before
    public void setUp() {
        login = "doeson_" + System.currentTimeMillis();
        courierSteps = new CourierSteps();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(login, password, firstName);
        courierSteps.courierCreate(courierCreateRequest);
        isCourierCreated = true;
    }

    @After
    public void tearDown() {
        if (isCourierCreated) {
            CourierLoginRequest loginRequest = new CourierLoginRequest(login, password);
            courierSteps.courierDeleteAfterLogin(loginRequest);
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void loginCourier() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(login, password);
        courierSteps.courierLogin(loginRequest)
                .assertThat().statusCode(SC_OK)
                .and().body("id", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля login")
    public void loginCourierWithoutLogin() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(null, password);
        courierSteps.courierLogin(loginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер НЕ может авторизоваться без передачи поля password")
    public void loginCourierWithoutPassword() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(login, "");
        courierSteps.courierLogin(loginRequest)
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация курьера, используя несуществующие данные")
    @Description("Проверка, что курьер НЕ может авторизоваться, используя несуществующие данные для входа")
    public void loginCourierWithNonExistentCredential() {
        // Для этого теста не создаем курьера, чтобы проверить 404
        isCourierCreated = false;
        CourierLoginRequest loginRequest = new CourierLoginRequest("nonexistent_" + System.currentTimeMillis(), "wrongpass");
        courierSteps.courierLogin(loginRequest)
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
}



