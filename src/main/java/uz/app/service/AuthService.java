package uz.app.service;

import lombok.SneakyThrows;
import uz.app.controller.AdminController;
import uz.app.controller.UserController;
import uz.app.entity.User;
import uz.app.payload.Confirmation;
import uz.app.repository.AuthRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;


public class AuthService {
    SimpleMailSender simpleMailSender=new SimpleMailSender();
    AuthRepository authRepository =AuthRepository.getInstance();
    UserController userController=UserController.getInstance();
    AdminController adminController=AdminController.getInstance();


    public void signUp(User user) {
        Optional<User> optionalUser = authRepository.getByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            System.out.println("this email is already in use");
            return;
        }
        user.setSmsCode(generateCode());
        try {
            simpleMailSender.sendSmsToUser(user.getEmail(),user.getSmsCode());
            System.out.println("success, please confirm sms!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        authRepository.save(user);




    }
    public void signIn(String email, String password) throws SQLException {
        Optional<User> optionalUser = authRepository.getByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                if (!user.getEnabled()){
                    System.out.println("Check your sms code in email");
                    return;
                }
                System.out.println("welcome to system");
                if (user.getRole().equals("USER")){
                  userController.service(user);
                }else adminController.service(user);
            }else {
                System.out.println("wrong password");
            }
            return;
        }else {
            System.out.println("no such email");
        }
    }
    public void checkSmsConfirmation(Confirmation confirmation) throws SQLException {
        Optional<User> optionalUser = authRepository.getByEmail(confirmation.email());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (user.getSmsCode().equals(confirmation.code())){
                authRepository.setActive(user.getId());
                return;
            }
        }
        System.out.println("wrong email");
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(0, 10));
        }
        return sb.toString();
    }
    private static AuthService authService;
    public static AuthService getInstance() {
        if (authService == null) {
            authService = new AuthService();
        }
        return authService;
    }

    public void allUsers() {
        authRepository.getAllUsers().forEach(System.out::println);
    }
}
