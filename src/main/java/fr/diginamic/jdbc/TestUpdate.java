package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TestUpdate {

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
            
            // Exécuter la mise à jour
            String sql = "UPDATE fournisseur SET nom='La Maison des Peintures' WHERE nom='La Maison de la Peinture'";
            int nb = stat.executeUpdate(sql);
            
            if (nb > 0) {
                System.out.println("Le nom du fournisseur a été mis à jour avec succès.");
            } else {
                System.err.println("La mise à jour du fournisseur n'a pas fonctionné.");
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
