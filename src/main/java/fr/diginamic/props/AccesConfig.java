package fr.diginamic.props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccesConfig {

    public static void main(String[] args) {
        ResourceBundle config = ResourceBundle.getBundle("config");
        String url = config.getString("database.url");
        String user = config.getString("database.user");
        String pwd = config.getString("database.password");
        System.out.println(url);

        try {
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            Connection maConnexion = DriverManager.getConnection(url, user, pwd);

            // Exécuter un update
            Statement stat = maConnexion.createStatement();
            int nbLignesModif = stat.executeUpdate("UPDATE fournisseur SET nom='leroy&merlan' WHERE id=4");

            // Supprimer l'entrée existante avant d'insérer
            stat.executeUpdate("DELETE FROM fournisseur WHERE id=4");
            stat.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES (4, 'nanan')");

            ArrayList<Fournisseur> listeFournisseur = new ArrayList<>();
            ResultSet resultat = stat.executeQuery("SELECT id, nom FROM fournisseur");

            while (resultat.next()) {
                Fournisseur f = new Fournisseur(resultat.getInt("id"), resultat.getString("nom"));
                listeFournisseur.add(f);
            }
            resultat.close();
            stat.close();
            maConnexion.close();

            // Afficher la liste des fournisseurs
            for (Fournisseur fournisseur : listeFournisseur) {
                System.out.println(fournisseur);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
