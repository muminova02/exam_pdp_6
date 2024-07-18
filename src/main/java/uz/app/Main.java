package uz.app;

import uz.app.controller.AuthController;
import uz.app.service.AuthService;
import uz.app.utils.TestConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new AuthController().service();
    }
}
