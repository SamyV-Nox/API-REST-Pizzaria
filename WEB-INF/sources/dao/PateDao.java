package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.Pate;

public class PateDao {
    Connection con;

    public PateDao() {
        try {
            con = DataBaseConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
        }
    }

    public Pate findById(int id) throws SQLException {
        Pate res = null;
        final String QUERY = "SELECT * FROM pates WHERE dno = " + id;
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            if (resultSet.next()) {
                res = new Pate(id, resultSet.getString("d_name"));
            }
        }
        return res;
    }

    public List<Pate> findAll() throws SQLException {
        List<Pate> res = new ArrayList<>();
        final String QUERY = "SELECT * FROM pates";
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                res.add(new Pate(resultSet.getInt("dno"), resultSet.getString("d_name")));
            }
        }
        return res;
    }

    public boolean save(Pate pate) {
        final String QUERY = "INSERT INTO pates (d_name) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setString(1, pate.getName());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int update(Pate pate) {
        final String QUERY = "UPDATE pates SET d_name = ? WHERE dno = ?";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setString(1, pate.getName());
            ps.setInt(2, pate.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int delete(int id) {
        final String QUERY = "DELETE FROM pates WHERE dno = ?";
        try (PreparedStatement ps = con.prepareStatement(QUERY)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean close() {
        try {
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
