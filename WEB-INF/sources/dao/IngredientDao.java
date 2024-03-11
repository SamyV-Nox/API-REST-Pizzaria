package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDao {

    private Connection con;

    public Ingredient findById(int pno) throws SQLException {
        Ingredient res = null;
        final String QUERY = "SELECT * FROM ingredients WHERE ino = ?";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("i_nom");
                Double price = rs.getDouble("i_prix");
                res = new Ingredient(pno, name, price);
            }
        }
        return res;
    }

    public IngredientDao() {
        try {
            con = DataBaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
        }
    }

    public boolean save(Ingredient ingredient) {
        String query = "INSERT INTO ingredients(i_name, i_prix) VALUES ( ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPrice());
            return 0 < ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Ingredient> findAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        final String QUERY = "SELECT * FROM ingredients";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(QUERY);
            
            int ino;
            String name;
            double price;
            
            while (rs.next()) {
                ino = rs.getInt("ino");
                name = rs.getString("i_nom");
                price = rs.getDouble("i_prix");
                ingredients.add(new Ingredient(ino, name, price));
            }
        }
        return ingredients;
    }

    public int delete(Ingredient ingredient) {
        String query = "DELETE FROM ingredients WHERE ino = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Ingredient ingredient) {
        final String QUERY = "UPDATE ingredients SET i_name = ?, i_prix = ? WHERE dno = ?";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getPrice());
            ps.setInt(3, ingredient.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
