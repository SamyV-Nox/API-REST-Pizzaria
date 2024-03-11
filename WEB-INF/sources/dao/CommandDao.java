package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Commande;
import dto.Ingredient;
import dto.Pizza;

public class CommandDao {
    private Connection con;

    private static final String TABLE_COMMANDES = "commandes";
    private static final String[] ENTETE_COMMANDES = { "cno", "name", "date" };

    private static final String TABLE_COMMANDES_PIZZAS = "contientpizza";
    private static final String[] ENTETE_COMMANDES_PIZZAS = { "cno", "pno" };

    public CommandDao() {
        try {
            con = DataBaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
        }
    }

    public boolean save(Commande command) {
        if (con != null) {
            String queryCommands = "INSERT INTO commandes VALUES ( ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(queryCommands)) {
                ps.setInt(1, command.getId());
                ps.setString(2, command.getName());
                ps.setString(3, command.getOrderDate().toString());
                ps.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean saveCommandees(Commande commande) {
        String query = "INSERT INTO commandes (commande_id, pizza_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (Pizza pizza : commande.getOrderedPizzas()) {
                ps.setInt(1, commande.getId());
                ps.setInt(2, pizza.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Enregistre les pizzas associées à une commande dans la table de liaison
     * contientPizza.
     * 
     * @param commande La commande contenant les pizzas à enregistrer.
     * @return true si l'enregistrement est réussi, sinon false.
     */
    private boolean enregistrerPizzasCommandees(Commande commande) {
        String query = "INSERT INTO contientPizza (commande_id, pizza_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (Pizza pizza : commande.getOrderedPizzas()) {
                ps.setInt(1, commande.getId());
                ps.setInt(2, pizza.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // METHODES PRIVATE =>

    private ResultSet findCommndeById(Integer id) {
        ResultSet rs = null;
        final String QUERY = "SELECT * from " + TABLE_COMMANDES + "WHERE " + ENTETE_COMMANDES[0] + "  = ? ;";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setString(1, id.toString());
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}