package uz.app.service;

import uz.app.entity.Category;
import uz.app.entity.Product;
import uz.app.repository.AdminRepositary;
import uz.app.repository.CategoryRowMapper;
import uz.app.repository.ProductRowMapper;

import java.util.List;

public class AdminService {

    AdminRepositary adminRepositary = AdminRepositary.getInstance();



    public void showCategory() {
       List<Category> categories = adminRepositary.getAllThings(Category.class, "category", new CategoryRowMapper());
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    public void showProduct() {
        List<Product> products = adminRepositary.getAllThings(Product.class, "product", new ProductRowMapper());
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void showProductsInCategories(){
        List<Category> categories = adminRepositary.getAllThings(Category.class, "category", new CategoryRowMapper());
        List<Product> products = adminRepositary.getAllThings(Product.class, "product", new ProductRowMapper());
        for (Category category : categories) {
            System.out.println("\nid: "+category.getId() + "\t->> " + category.getName());
            for (Product product : products) {
                if (category.getId() == product.getCategory_id()){
                    System.out.println("           >>> " + product);
                }
            }
            System.out.println("==========================");
        }
    }



    public void addProduct(Product product) {
        List<Category> allCategory = getAllCategory();
        if (allCategory.isEmpty()) {
            System.out.println("category mavjud emas");
            return;
        }
        for (Category category : allCategory) {
            if (product.getCategory_id()==category.getId()){
                adminRepositary.saveProduct(product);
                System.out.println("added product 👌 ");
                return;
            }
        }
        System.out.println("uzr qo'shib bo'lmadi, category id nato'g'ri kiritilgan");
    }


    public void addCategory(Category category) {
        adminRepositary.saveCategory(category);
        System.out.println("saved category 👌 ");
    }


    public void disableProduct(int productId) {
        adminRepositary.disableProduct(productId);
    }

    public List<Category> getAllCategory(){
        return adminRepositary.getAllThings(Category.class, "category", new CategoryRowMapper());
    }
    public List<Product> getAllProduct(){
        return adminRepositary.getAllThings(Product.class, "product", new ProductRowMapper());
    }

    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null) {
            adminService = new AdminService();
        }
        return adminService;
    }
}
