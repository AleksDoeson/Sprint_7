package model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CourierCreateRequest {
    private final String login;
    private final String password;
    private final String firstName;
}



