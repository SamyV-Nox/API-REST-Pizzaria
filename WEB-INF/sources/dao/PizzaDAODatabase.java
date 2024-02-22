package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Pizza findById(int id) {
        Pizza pizza = null;
        String query = "SELECT * FROM pizza WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                pizza = new Pizza(id, name, null, 0, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pizza;
    }


    public boolean save(Pizza joueur) {
        String query = "insert into pizza values (?, '?')";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, joueur.getID());
            ps.setString(2, joueur.getName());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Pizza> findAll() {
        List<Pizza> pizza = new ArrayList<>();
        String query = "SELECT * FROM pizza";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                pizza.add(new Pizza(id, name, null, 0, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pizza;
    }
}
