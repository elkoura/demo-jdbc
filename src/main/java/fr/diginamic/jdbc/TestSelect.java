package fr.diginamic.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TestSelect {

    public static void main(String[] args) {
        ResourceBundle config = ResourceBundle.getBundle("config");
        String url = config.getString("database.url");
        String user = config.getString("database.user");
        String pwd = config.getString("database.password");

        Connection conn = null;
        Statement stat = null;
        ResultSet resultSet = null;

        try {
            // Charger le driver MariaDB
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            // Établir la connexion
            conn = DriverManager.getConnection(url, user, pwd);
            
            // Créer le statement
            stat = conn.createStatement();
            
            // Exécuter la sélection
            String sql = "SELECT id, nom FROM fournisseur";
            resultSet = stat.executeQuery(sql);

            ArrayList<Fournisseur> fournisseurs = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                fournisseurs.add(new Fournisseur(id, nom));
            }

            // Afficher les fournisseurs
            for (Fournisseur fournisseur : fournisseurs) {
                System.out.println(fournisseur);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
