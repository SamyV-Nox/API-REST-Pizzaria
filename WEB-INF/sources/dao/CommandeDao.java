package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dto.Commande;
import dto.Ingredient;
import dto.Pate;
import dto.Pizza;

public class CommandeDao extends Dao {

    public Commande findById(int id) throws SQLException {
        Commande commande = null;

        final String QUERY = "SELECT * " +
                "FROM commandes " +
                "JOIN panier USING (cno) " +
                "JOIN pizzas USING (pno) " +
                "JOIN pates USING (dno) " +
                "JOIN recettes USING (pno) " +
                "JOIN ingredients USING (ino) " +
                "WHERE commandes.cno = ?";

        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            Map<Integer, Pizza> pizzaMap = new HashMap<>();

            while (resultSet.next()) {
                if (commande == null) {
                    String userName = resultSet.getString("c_name");
                    LocalDate orderDate = resultSet.getDate("c_orderDate").toLocalDate();
                    commande = new Commande(id, userName, orderDate, new ArrayList<>());
                }

                int pno = resultSet.getInt("pno");
                Pizza pizza;
                if (!pizzaMap.containsKey(pno)) {
                    String pizzaName = resultSet.getString("p_nom");
                    Double pizzaPrice = resultSet.getDouble("p_prix");
                    int dno = resultSet.getInt("dno");
                    String pateName = resultSet.getString("d_nom");
                    Pate pate = new Pate(dno, pateName);
                    pizza = new Pizza(pno, pizzaName, pate, pizzaPrice);
                    pizza.setIngredients(new ArrayList<>());
                    pizzaMap.put(pno, pizza);
                    commande.getPanier().add(pizza);
                } else {
                    pizza = pizzaMap.get(pno);
                }

                int ingredientId = resultSet.getInt("ino");
                String ingredientName = resultSet.getString("i_nom");
                Double ingredientPrice = resultSet.getDouble("i_prix");
                Ingredient ingredient = new Ingredient(ingredientId, ingredientName, ingredientPrice);
                pizza.getIngredients().add(ingredient);
            }
        }
        return commande;
    }

    public static void main(String[] args) throws SQLException {
        
        System.out.println(new CommandeDao().findById(1));
    }
}
