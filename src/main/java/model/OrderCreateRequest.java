package model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private String firstName = "Алекс";
    private String lastName = "Воропай";
    private String address = "Краснодар, ул. Ленина, д.5, кв.75";
    private int metroStation = 1;
    private String phone = "8 900 000 11 11";
    private String rentTime = "1";
    private String deliveryDate = "2026-01-01";
    private String comment = "Пожалуйста, доставьте чистый самокат";
    private List<String> color;
}



