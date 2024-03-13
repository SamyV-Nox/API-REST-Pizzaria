package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dto.Ingredient;
import dto.Pate;
import dto.Pizza;

public class PizzaDao extends Dao {

    public List<Pizza> findAll() {
        return null;
    }

    public Pizza findById(int id) {
        Pizza pizza = null;
        final String QUERY = "SELECT * FROM pizzas WHERE dno = " + id;

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            int dno;
            String d_nom;

            int pno = id;
            String p_nom;
            Double p_prix;

            int ino;
            String i_nom;
            Double i_prix;

            if (resultSet.next()) {

                dno = resultSet.getInt("dno");
                d_nom = resultSet.getString("d_nom");
                Pate pate = new Pate(dno, d_nom);

                p_nom = resultSet.getString("p_nom");
                p_prix = resultSet.getDouble("p_prix");
                pizza = new Pizza(pno, p_nom, pate, p_prix);

                    ino = resultSet.getInt("ino");
                    i_nom = resultSet.getString("ii_nomno");
                    i_prix = resultSet.getDouble("i_prix");
                    Ingredient ingredient = new Ingredient(ino, i_nom, i_prix);
                    pizza.add(ingredient);
                
                while (resultSet.next()) {
                    ino = resultSet.getInt("ino");
                    i_nom = resultSet.getString("ii_nomno");
                    i_prix = resultSet.getDouble("i_prix");
                    ingredient = new Ingredient(ino, i_nom, i_prix);

                    pizza.add(ingredient);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizza;
    }

    public static void main(String[] args) {
        PizzaDao pd = new PizzaDao();
        System.out.println(pd.findById(1));
    }
}