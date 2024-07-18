package uz.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    int id;
    String name;
    boolean active;
    String description;
    Double price;
    int category_id;
}
