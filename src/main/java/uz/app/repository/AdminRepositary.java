package uz.app.repository;

import uz.app.entity.Category;
import uz.app.entity.Product;
import uz.app.entity.User;
import uz.app.service.UserService;
import uz.app.utils.TestConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositary {

    TestConnection testConnection = TestConnection.getInstance();

    public <T> List<T> getAllThings(Class<T> clazz, String tableName, RowMapper<T> rowMapper) {
        try {
            Statement statement = testConnection.getStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s;", tableName));
            return mapResults(resultSet, rowMapper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private <T> List<T> mapResults(ResultSet resultSet, RowMapper<T> rowMapper) throws SQLException {
        List<T> results = new ArrayList<>();
        while (resultSet.next()) {
            T rowObject = rowMapper.mapRow(resultSet);
            results.add(rowObject);
        }
        return results;
    }



//    public List<Category> getAllCategory() {
//        try {
//            Statement statement = testConnection.getStatement();
//            return getCategoryies(statement.executeQuery(String.format("select * from users;")));
//        } catch (
//        SQLException e) {
//        e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//
//
//    private List<Category> getCategoryies(ResultSet resultSet) {
//        List<Category> categories = new ArrayList<>();
//        try {
//            while (true) {
//                if (!resultSet.next()) break;
//                Category category = makeCategory(resultSet);
//                categories.add(category);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return categories;
//    }


    public void saveProduct(Product product) {
        Statement statement = testConnection.getStatement();
        try {
            String query = String.format("insert into product(name,description,price,category_id) values('%s','%s','%f','%d')",
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory_id()
            );
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveCategory(Category category) {
        Statement statement = testConnection.getStatement();
        try {
            String query = String.format("insert into category(name) values('%s')",
                    category.getName()
            );
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void disableProduct(Integer product_id) {
        Statement statement = testConnection.getStatement();
        try {
            String query = String.format("Update product set active = false where id = %d",
                    product_id
            );
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private static AdminRepositary adminRepositary;

    public static AdminRepositary getInstance() {
        if (adminRepositary == null) {
            adminRepositary = new AdminRepositary();
        }
        return adminRepositary;
    }
}
