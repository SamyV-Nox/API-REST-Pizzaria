package dao;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DS {

    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties p = new Properties();
        String wd = new File(".").getAbsolutePath();
        System.out.println("WDir : " + wd);
        try(InputStream in = DS.class.getClassLoader().getResourceAsStream("config.prop")) {
            p.load(in);
        }
        Class.forName(p.getProperty("driver"));
        String url = p.getProperty("url");
        String nom = p.getProperty("login");
        String mdp = p.getProperty("password");
        return DriverManager.getConnection(url, nom, mdp);
    }


}
