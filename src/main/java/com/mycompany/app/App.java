package com.mycompany.app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class App 
{
static Connection conn = null;
	static PreparedStatement statement = null;

	private static void makeJDBCConnection() {
 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Le driver JDBC pour MySQL est disponible.");
		} catch (ClassNotFoundException e) {
			log("Le driver JDBC pour MySQL n'est pas disponible! Verifier que vous avez bien ajouté votre dependance Maven dans le POM!");
			e.printStackTrace();
			return;
		}
 
		try {
			// Charger le JDBC driver pour MYSQL.
			conn = DriverManager.getConnection("jdbc:mysql://localhost/training", "root", "");
			if (conn != null) {
				log("Connexion à la base de données a été établie avec succès.");
			} else {
				log("Problème de connexion à la base!");
			}
		} catch (SQLException e) {
			log("Connexion au MySQL n'est pas établie!");
			e.printStackTrace();
			return;
		}
 
	}
 
	private static void getDataFromDB() {
 
		try {
			// Requete Select MySQL
			String getQueryStatement = "SELECT * FROM session";
 
			statement = conn.prepareStatement(getQueryStatement);
 
			// Executer la requete
			ResultSet rs = statement.executeQuery();
 
			// Parser le resultat
			while (rs.next()) {
				String name = rs.getString("name");
				String track = rs.getString("track");
				String address = rs.getString("adress");
				String date = rs.getString("date_session");
				int nb_participants = rs.getInt("nb_participants");
				int iscompleted = rs.getInt("iscomplete");
				
 
				// Afficher le résultat
				System.out.format("%s, %s, %s, %s, %d, %d\n", name, track, address, date, nb_participants, iscompleted);
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
 
	private static void log(String string) {
		System.out.println(string);
 
	}

	public static void main(String[] argv) {
 
		try {
			log("-------- Connexion au serveur de données MYSQL ------------");
			makeJDBCConnection();
			log("-------- Afficher toutes les sessions de formations ------------");
			getDataFromDB();

			statement.close();
			conn.close(); // Fermer la connexion
 
		} catch (SQLException e) {
 
			e.printStackTrace();
		}
	}
}
