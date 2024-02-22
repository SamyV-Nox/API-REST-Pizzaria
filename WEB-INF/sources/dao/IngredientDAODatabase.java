package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient {

    private Connection con;

    public Ingredient findById(int id) {
        Ingredient ingredient = null;
        String query = "SELECT * FROM ingredients WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                ingredient = new Ingredient(id, name);
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

    public boolean save(Ingredient joueur) {
        String query = "insert into ingredients values (?, '?')";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, joueur.getId());
            ps.setString(2, joueur.getName());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }
/* 
    @Override
    public boolean delete(Ingredient ingredient) {
        String query = "insert into ingredients values (?, '?')";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, joueur.getId());
            ps.setString(2, joueur.getName());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    */

    @Override
    public boolean delete(Ingredient ingredient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
