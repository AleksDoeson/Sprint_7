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

/**
 * Тесты на создание курьера.
 */
public class CourierCreateTest {

    private String login;
    private final String password = "qwerty123";
    private final String firstName = "Алекс";

    private CourierSteps courierSteps;
    private boolean isCourierCreated;

    @Before
    public void setUp() {
        login = "doeson_" + System.currentTimeMillis();
        courierSteps = new CourierSteps();
        isCourierCreated = false;
    }

    @After
    public void tearDown() {
        if (isCourierCreated) {
            CourierLoginRequest loginRequest = new CourierLoginRequest(login, password);
            courierSteps.courierDeleteAfterLogin(loginRequest);
        }
    }

    @Test
    @DisplayName("Создание нового курьера с валидными данными")
    @Description("Проверяем, что курьера можно создать с валидными логином, паролем и именем")
    public void shouldCreateCourierWithValidData() {
        CourierCreateRequest request = new CourierCreateRequest(login, password, firstName);

        courierSteps.courierCreate(request)
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);

        isCourierCreated = true;
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Создание двух курьеров с одинаковыми логином и паролем. Второй не должен создаться.")
    public void shouldNotCreateDuplicateCouriers() {
        CourierCreateRequest request = new CourierCreateRequest(login, password, firstName);

        courierSteps.courierCreate(request)
                .assertThat().statusCode(SC_CREATED);
        isCourierCreated = true;

        courierSteps.courierCreate(request)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Если не передан логин — возвращается ошибка 400")
    public void shouldNotCreateCourierWithoutLogin() {
        CourierCreateRequest request = new CourierCreateRequest(null, password, firstName);

        courierSteps.courierCreate(request)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Если не передан пароль — возвращается ошибка 400")
    public void shouldNotCreateCourierWithoutPassword() {
        CourierCreateRequest request = new CourierCreateRequest(login, null, firstName);

        courierSteps.courierCreate(request)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Имя — не обязательное поле, курьер должен быть создан")
    public void shouldCreateCourierWithoutFirstName() {
        CourierCreateRequest request = new CourierCreateRequest(login, password, null);

        courierSteps.courierCreate(request)
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);

        isCourierCreated = true;
    }

    @Test
    @DisplayName("Создание курьера с пустым логином")
    @Description("Если логин пустой строкой — возвращается ошибка 400")
    public void shouldNotCreateCourierWithEmptyLogin() {
        CourierCreateRequest request = new CourierCreateRequest("", password, firstName);

        courierSteps.courierCreate(request)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера с пустым паролем")
    @Description("Если пароль пустой строкой — возвращается ошибка 400")
    public void shouldNotCreateCourierWithEmptyPassword() {
        CourierCreateRequest request = new CourierCreateRequest(login, "", firstName);

        courierSteps.courierCreate(request)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}

