package uz.app.service;

import uz.app.entity.Basket;
import uz.app.entity.Product;
import uz.app.entity.User;
import uz.app.repository.AuthRepository;
import uz.app.repository.UserRepositary;
import static uz.app.utils.Utill.*;


import java.util.List;
import java.util.Optional;

public class UserService {

UserRepositary userRepositary = UserRepositary.getInstance();




    public void addProductInBasket(int user_id, int productId) {
        boolean b;
        int basketActive_id = userRepositary.isBasketActive(user_id);
        if (basketActive_id ==0) {
            Basket basket = new Basket();
            basket.setUser_id(user_id);
            basket.setTime("2024-06-06");
           b = userRepositary.addProductInNewBasket(basket,productId);
        }
        else {
            b = userRepositary.addProductActiveBasket(basketActive_id, productId);
        }
        if (b){
            System.out.println("basketga qo'shildi 👌");
        }else System.out.println("something wrong");
    }


    public void showBasket(int user_id) {
//        List<Product> allProduct = adminService.getAllProduct();
//        if (allProduct.isEmpty()) {
//            System.out.println("product is not yet");
//            return;
//        }
        int basketActive = userRepositary.isBasketActive(user_id);
        if (basketActive==0){
            System.out.println("you didn't buy anything");
            return;
        }
        List<Product> productsInBasket = userRepositary.getProductsInBasket( user_id);
        if (productsInBasket.isEmpty()){
            System.out.println("sizda product mavjud emas!!!");
            return;
        }
        Double overAllsumma = 0.0;
        for (Product product : productsInBasket) {
            System.out.println(product);
            overAllsumma+=product.getPrice();
        }
        System.out.println("Jami hisob : " + overAllsumma);
        int userWantToBuy = getInteger("do you want buy your products yes->1//no->0");
        if (userWantToBuy == 1) {
            Optional<User> userById = userRepositary.getUserById(user_id);
            if (userById.isEmpty()){
                return;
            }
            User user = userById.get();
            if (user.getBalance() < overAllsumma) {
                System.out.println("Mablag' yetarli emas !!!");
                System.out.println("sizning hisobingiz : " +user.getBalance());
                return;
            }
            userRepositary.createHistory(basketActive,overAllsumma);
            userRepositary.minusUserBalanse(user_id,user.getBalance()-overAllsumma);
        }

    }




    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void showHistory(User user) {
        List<Product> products = userRepositary.getProductsInHistory(user);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void showBalance(User user) {
        while (true){
            System.out.println("""
                    1 show myBalance
                    2 fill balance
                    0 exit
                    """);
            switch (getInteger("choose : ")){
                case 0->{
                    return;
                }
                case 1->{
                    Optional<User> userById = userRepositary.getUserById(user.getId());
                    userById.ifPresent(user1 -> {
                        System.out.println(user1.getBalance());
                    });
                }
                case 2->{
                    userRepositary.plusUserBalanse(user.getId(),user.getBalance()+getDouble("enter amount: "));
                }
            }
        }

    }
}
