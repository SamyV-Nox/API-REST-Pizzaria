package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Pate;
import dto.Pizza;

public class PizzaDao extends Dao {

    public List<Pizza> findAll() {
        List<Pizza> pates = new ArrayList<>();
        final String QUERY = "SELECT * FROM pizzas";
        
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            int pno;
            String name;
            Pate pate;
            double price;

            while (resultSet.next()) {
                pno = resultSet.getInt("pno");
                name = resultSet.getString("d_name");
                pates.add(new Pizza(pno, name, pate, price));
            }
        }
        return pates;
    }

    public Pizza findById(int id) {

    }
}