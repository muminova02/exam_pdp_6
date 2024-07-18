package uz.app.controller;


import uz.app.entity.User;
import uz.app.payload.Confirmation;
import uz.app.service.AuthService;
import uz.app.service.UserService;

import java.sql.SQLException;

import static uz.app.utils.Utill.getInteger;
import static uz.app.utils.Utill.getString;

public class AuthController {
    AuthService authService=AuthService.getInstance();
    public void  service() throws SQLException {
        while (true){
            switch (getInteger("""
                    0 exit
                    1 sign in
                    2 sign up
                    3 confirm sms
                    """)){
                case 0->{
                    System.out.println("see you soon!");
                    return;
                }
                case 1->{
                    String email = getString("enter email");
                    String password = getString("enter password");
                    authService.signIn(email,password);
                }
                case 2->{
                    String name = getString("enter name");
                    String email = getString("enter email");
                    String password = getString("enter password");
                    User user =new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    authService.signUp(user);
                }
                case 3->{
                    String email = getString("enter email");
                    String code = getString("enter code");
                    authService.checkSmsConfirmation(new Confirmation(email,code));
                }
            }
        }
    }
}
