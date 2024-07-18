package uz.app.controller;

import uz.app.entity.User;
import uz.app.payload.Confirmation;
import uz.app.service.AdminService;
import uz.app.service.AuthService;
import uz.app.service.UserService;

import java.sql.SQLException;

import static uz.app.utils.Utill.getInteger;
import static uz.app.utils.Utill.getString;

public class UserController {

    AdminService adminService = AdminService.getInstance();
    UserService userService = UserService.getInstance();
    public void  service(User user) throws SQLException {
        while (true){
            switch (getInteger("""
                    0 exit
                    1 Show Products
                    2 Buy Product
                    3 Show Basket
                    4 Show History
                    5 Show Balance
                    """)){
                case 0->{
                    System.out.println("see you soon!");
                    return;
                }
                case 1->{
                    adminService.showProductsInCategories();
                }
                case 2->{
                    adminService.showProductsInCategories();
                    int product_id = getInteger("choose product id: ");
                    userService.addProductInBasket(user.getId(),product_id);
                }
                case 3->{
                    userService.showBasket(user.getId());
                }
                case 4->{
                    userService.showHistory(user);
                }
                case 5->{
                    userService.showBalance(user);
                }
            }
        }
    }










    private static UserController userController;
    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

}
