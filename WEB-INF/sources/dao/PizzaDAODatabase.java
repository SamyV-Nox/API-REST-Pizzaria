package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAODatabase implements DAOPizza {

    private Connection con;

    public PizzaDAODatabase() {
        try {
            con = DS.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("sql error");
        } catch (ClassNotFoundException e) {
            System.err.println("Jar non trouvé : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("file not found : " + e.getMessage());
        }
    }

    public Pizza findById(int pno) {
        Pizza pizza = null;
        String query = "SELECT i.ino, i.name as \"Iname\", i.price as \"Iprice\", p.name as \"Pname\", p.dough as \"Pdough\", p.price as \"Pprice\" FROM pizzas as \"p\" JOIN contient USING (pno) JOIN ingredients as \"i\" USING (ino) WHERE p.pno = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();

            Map<Integer, Ingredient> ingredientsMap = new HashMap<>();
            if (rs.next()) {
                String namePizza = rs.getString("Pname");
                String doughPizza = rs.getString("Pdough");
                double pricePizza = rs.getFloat("Pprice");

                int idIngredients = rs.getInt("ino");
                String nameIngredients = rs.getString("Iname");
                int priceIngredients = rs.getInt("Iprice");

                pizza = new Pizza(pno, namePizza, doughPizza, pricePizza);
                ingredientsMap.put(idIngredients, new Ingredient(idIngredients, nameIngredients, priceIngredients));
                pizza.getIngredients().add(ingredientsMap.get(idIngredients));
            }

            while (rs.next()) {
                int ino = rs.getInt("ino");
                if (!ingredientsMap.containsKey(ino)) {
                    String name = rs.getString("Iname");
                    int price = rs.getInt("Iprice");
                    ingredientsMap.put(ino, new Ingredient(ino, name, price));
                }
                pizza.getIngredients().add(ingredientsMap.get(ino));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizza;
    }

    public boolean save(Pizza pizza) {
        String query = "insert into pizza values ('?', '?', ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, pizza.getName());
            ps.setString(2, pizza.getDough());
            ps.setDouble(3, pizza.getPrice());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Pizza> findAll() {
        Map<Integer, Pizza> pizzasMap = new HashMap<>();
        Map<Integer, Ingredient> ingredientsMap = new HashMap<>();
        String query = "SELECT i.ino, i.name as \"Iname\", i.price as \"Iprice\", p.pno, p.name as \"Pname\", p.dough as \"Pdough\", p.price as \"Pprice\" FROM pizzas as \"p\" JOIN contient USING (pno) JOIN ingredients as \"i\" USING (ino)";

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);

            // Parcourt les résultats de la requête
            while (rs.next()) {
                int ino = rs.getInt("ino");
                if (!ingredientsMap.containsKey(ino)) { // Vérifie si l'ingrédient n'a pas déjà été ajouté à la map
                    String name = rs.getString("Iname");
                    int price = rs.getInt("Iprice");
                    ingredientsMap.put(ino, new Ingredient(ino, name, price));
                }

                int pno = rs.getInt("pno");
                if (!pizzasMap.containsKey(pno)) { // Vérifie si la pizza n'a pas déjà été ajoutée à la map
                    String name = rs.getString("Pname");
                    String dough = rs.getString("Pdough");
                    double price = rs.getFloat("Pprice");

                    pizzasMap.put(pno, new Pizza(pno, name, dough, price));
                }
                pizzasMap.get(pno).getIngredients().add(ingredientsMap.get(ino)); // Ajoute l'ingrédient à la liste des
                                                                                  // ingrédients de la pizza
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzasMap.values();
    }

    @Override
    public boolean delete(Pizza pizza) {
        String query = "DELETE FROM pizzas WHERE pno = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pizza.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PizzaDAODatabase pizzaDAODatabase = new PizzaDAODatabase();
        System.out.println(pizzaDAODatabase.findAll());
    }
}
