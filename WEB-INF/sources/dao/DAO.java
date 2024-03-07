package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe abstraite représentant un objet d'accès aux données (DAO).
 * Cette classe fournit une connexion à la base de données et une méthode pour la fermer.
 * 
 * @author samy.vancalster.etu@univ-lille.fr
 */
public abstract class DAO {
    
    Connection con;

    /**
     * Constructeur protégé permettant d'initialiser la connexion à la base de données.
     * Il est protégé afin d'empêcher l'instanciation directe de cette classe.
     */
    protected DAO() {
        try {
            con = DataBaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
        }
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    }
}
