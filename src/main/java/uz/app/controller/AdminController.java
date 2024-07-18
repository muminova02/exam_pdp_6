package uz.app.controller;

import uz.app.entity.Category;
import uz.app.entity.Product;
import uz.app.entity.User;
import uz.app.repository.AuthRepository;
import uz.app.service.AdminService;
import uz.app.service.AuthService;

import java.sql.SQLException;

import static uz.app.utils.Utill.*;

public class AdminController {

    AdminService adminService = AdminService.getInstance();

    public void  service(User user) throws SQLException {
        while (true){
            switch (getInteger("""
                    0 exit
                    1 show Category
                    2 Show Product
                    3 Create Category
                    4 Add Product
                    5 showProductsInCategories
                    6 users
                    """)){
                case 0->{
                    System.out.println("see you soon!");
                    return;
                }
                case 1->{
                    adminService.showCategory();
                }
                case 2->{
                    adminService.showProduct();
                }
                case 4->{
                    adminService.showCategory();
                    int category_id = getInteger("enter category_id: ");
                    String productName = getString("enter Product name: ");
                    String productDescription = getString("enter Description: ");
                    Double productPrice = getDouble("enter Price: ");
                    Product product = new Product();
                    product.setName(productName);
                    product.setDescription(productDescription);
                    product.setPrice(productPrice);
                    product.setCategory_id(category_id);
                    adminService.addProduct(product);
                }
                case 3->{
                    String categoryName =getString("enter Category name: ");
                    Category category = new Category();
                    category.setName(categoryName);
                    adminService.addCategory(category);
                }
                case 5->{
                    adminService.showProductsInCategories();
                }
                case 6->{
                    AuthRepository authRepository = AuthRepository.getInstance();
                    authRepository.getAllUsers().forEach(System.out::println);
                }
                case 7->{
                    adminService.showProductsInCategories();
                    int product_id = getInteger("choose Product id");
                    adminService.disableProduct(product_id);
                }
            }
        }
    }




    private static AdminController adminController;
    public static AdminController getInstance() {
        if (adminController == null) {
            adminController = new AdminController();
        }
        return adminController;
    }

}
