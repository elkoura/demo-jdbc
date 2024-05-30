package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TestDelete {

    public static void main(String[] args) {
        ResourceBundle config = ResourceBundle.getBundle("config");
        String url = config.getString("database.url");
        String user = config.getString("database.user");
        String pwd = config.getString("database.password");

        Connection conn = null;
        Statement stat = null;

        try {
            // Charger le driver MariaDB
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            // Établir la connexion
            conn = DriverManager.getConnection(url, user, pwd);
            
            // Créer le statement
            stat = conn.createStatement();
            
            // Exécuter la suppression
            String sql = "DELETE FROM fournisseur WHERE nom='La Maison des Peintures'";
            int nb = stat.executeUpdate(sql);
            
            if (nb > 0) {
                System.out.println("Le fournisseur a été supprimé avec succès.");
            } else {
                System.err.println("La suppression du fournisseur n'a pas fonctionné.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
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
