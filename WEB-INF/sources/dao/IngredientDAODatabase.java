package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient {

    private Connection con;

    public Ingredient findById(int pno) {
        Ingredient ingredient = null;
        String query = "SELECT * FROM ingredients WHERE ino = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                ingredient = new Ingredient(pno, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredient;
    }

    public IngredientDAODatabase() {
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

    public boolean save(Ingredient ingredient) {
        String query = "INSERT INTO ingredients VALUES ( ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            ps.setString(2, ingredient.getName());
            ps.setInt(3, ingredient.getPrice());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Ingredient> findAll() {
        Collection<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ino = rs.getInt("ino");
                String name = rs.getString("name");
                ingredients.add(new Ingredient(ino, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    @Override
    public boolean delete(Ingredient ingredient) {
        String query = "DELETE FROM ingredients WHERE ino = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ingredient.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
