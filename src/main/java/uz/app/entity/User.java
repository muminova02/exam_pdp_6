package uz.app.entity;


import lombok.*;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    int id;
    String name;
    String email;
    String password;
    String phone;
    String role;
    Boolean enabled ;
    Double balance;
    String smsCode;

}
