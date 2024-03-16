package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import dto.Pate;
import dto.Pizza;

public class PizzaDao extends Dao {

    public Pizza findById(int id) throws SQLException {
        Pizza pizza = null;
        final String QUERY = "SELECT * FROM pizzas LEFT JOIN pates USING(dno) LEFT JOIN recettes USING(pno) LEFT JOIN ingredients USING(ino) WHERE pno = "
                + id + ";";

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
                i_nom = resultSet.getString("i_nom");
                i_prix = resultSet.getDouble("i_prix");
                Ingredient ingredient = new Ingredient(ino, i_nom, i_prix);
                pizza.add(ingredient);

                while (resultSet.next()) {
                    ino = resultSet.getInt("ino");
                    i_nom = resultSet.getString("i_nom");
                    i_prix = resultSet.getDouble("i_prix");
                    ingredient = new Ingredient(ino, i_nom, i_prix);

                    pizza.add(ingredient);
                }
            }
        }
        return pizza;
    }

    public List<Pizza> findAll() throws SQLException {
        List<Pizza> pizzas = new ArrayList<>();

        final String QUERY = "SELECT * FROM pizzas LEFT JOIN pates USING(dno) LEFT JOIN recettes USING(pno) LEFT JOIN ingredients USING(ino);";

        try (Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                // Pates
                int dno = resultSet.getInt("dno");
                String d_nom = resultSet.getString("d_nom");
                Pate pate = new Pate(dno, d_nom);
                // Pizzas
                int pno = resultSet.getInt("pno");
                String p_nom = resultSet.getString("p_nom");
                Double p_prix = resultSet.getDouble("p_prix");
                Pizza pizza = new Pizza(pno, p_nom, pate, p_prix);
                // Ingredients
                int ino = resultSet.getInt("ino");
                String i_nom = resultSet.getString("i_nom");
                Double i_prix = resultSet.getDouble("i_prix");
                Ingredient ingredient = new Ingredient(ino, i_nom, i_prix);
                pizza.add(ingredient);

                while (resultSet.next() && resultSet.getInt("pno") == pno) {
                    ino = resultSet.getInt("ino");
                    i_nom = resultSet.getString("i_nom");
                    i_prix = resultSet.getDouble("i_prix");
                    ingredient = new Ingredient(ino, i_nom, i_prix);
                    pizza.add(ingredient);
                }
                resultSet.previous();
                pizzas.add(pizza);
            }
        }
        return pizzas;
    }

    public int findIdByName(String name) throws SQLException {
        final String QUERY = "SELECT pno FROM pizzas WHERE p_nom = ?;";
        int pno = -1;
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                pno = resultSet.getInt("pno");
            }
        }
        return pno;
    }

    public static void main(String[] args) throws SQLException {
        PizzaDao pd = new PizzaDao();
        System.out.println(pd.findIdByName(pd.findById(1).getName()));
    }

    public int update(Pizza pizza) {
        final String QUERY = "UPDATE pizzas SET p_nom = ?, dno = ?, p_prix = ? WHERE pno = ?";

        try (PreparedStatement updateStatement = con.prepareStatement(QUERY)) {

            con.setAutoCommit(false); // Commencer une transaction

            deleteIngredient(pizza);
            saveIngredient(pizza);

            updateStatement.setString(1, pizza.getName());
            updateStatement.setInt(2, pizza.getPate().getId());
            updateStatement.setDouble(3, pizza.getPrice());
            updateStatement.setInt(4, pizza.getId());
            int rowsAffected = updateStatement.executeUpdate();

            con.commit(); // Valider la transaction

            con.setAutoCommit(true); // Retourner à l'autocommit par défaut

            return rowsAffected;
        } catch (SQLException e) {
            try {
                con.rollback(); // Annuler la transaction en cas d'erreur
                return -1;
            } catch (SQLException rollbackException) {
                return -1;
            }        
        }
    }

    private int deleteIngredient(Pizza pizza) throws SQLException {
        final String QUERY = "DELETE FROM recettes WHERE pno = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(QUERY)) {
            preparedStatement.setInt(1, pizza.getId());
            return preparedStatement.executeUpdate();
        }
    }

    private void saveIngredient(Pizza pizza) throws SQLException {
        final String QUERY = "INSERT INTO recettes(pno, ino) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            for (Ingredient ingredient : pizza.getIngredients()) {
                ps.setInt(1, pizza.getId());
                ps.setInt(2, ingredient.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void updateIngredient(Pizza pizza) throws SQLException {
        deleteIngredient(pizza);
        saveIngredient(pizza);
    }

    public void save(Pizza ingredient) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public int delete(int id) throws SQLException {
        final String QUERY = "DELETE FROM pizzas WHERE pno = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(QUERY)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }

    public int deleteIngredient(int pno, int ino) throws SQLException {
        final String QUERY = "DELETE FROM recettes WHERE pno = ? AND ino = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(QUERY)) {
            preparedStatement.setInt(1, pno);
            preparedStatement.setInt(2, ino);
            return preparedStatement.executeUpdate();
        }
    }
}