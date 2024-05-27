package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {	

	public static void main(String[] args) {
		String url="jdbc:mysql://localhost:3306/magazzino";	//stringa di connessione (in questo caso per MySql, ma potrebbe essere diversa per altre tipologie di DBMS)
		
		//significato parti della stringa:
		// jdbc: Java Data Base Connectivity (API da utilizzare per connettersi al DB)
		// mysql: tipologia di DBMS (Data Base Management System)
		// localhost: nome della macchina (oppure indirizzo IP) sulla quale è in esecuzione il DBMS (nel nostro caso localhost indica la stessa macchina sulla quale viene eseguito il codice Java)
		// 3306: porta sulla quale è in ascolto il DMBS (la porta individua l'applicazione che utilizza la rete a parità di indirizzo IP)
		// magazzino: nome del database da utilizzare
		
		System.out.println("Tentativo di connessione al db...");
		try (Connection conn=DriverManager.getConnection(url, "root", "jaita101")) {	//provo a connettermi
			//la connessione è andata a buon fine
			System.out.println("Connesso correttamente al database");
			String sql="INSERT INTO movimenti(id, data, codProdotto, codMovimento, quantità) "
					+ "VALUES(1, '2020-02-01', 'P01', 'E02', 12)";
			
			System.out.println("Tentativo di esecuzione INSERT");
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		//provo a eseguire l'sql
				int righeInteressate=ps.executeUpdate();	//eseguo l'istruzione
				System.out.println("Righe inserite: "+righeInteressate);
			}
			
		} catch (Exception e) {
			//si è verificato un problema. L'oggetto e (di tipo Exception) contiene informazioni sull'errore verificatosi
			System.err.println("Si è verificato un errore: "+e.getMessage());
		}
	}

}
