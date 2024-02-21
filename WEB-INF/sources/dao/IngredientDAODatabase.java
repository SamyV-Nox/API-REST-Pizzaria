package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient {

    private static final String url = "jdbc:postgresql://psqlserv:5432/but2";
    private static final String nom = "samyvancalsteretu";
    private static final String mdp = "moi";
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
            con = DriverManager.getConnection(url, nom, mdp);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("sql error");
        }
    }

    public void save(Ingredient joueur) {
        String query = "insert into ingredients values (?, '?')";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, joueur.getId());
            ps.setString(2, joueur.getName());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ingredients.add(new Ingredient(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }
}
