package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {	

	public static void main(String[] args) {
		String url="jdbc:mysql://localhost:3306/magazzino";	//stringa di connessione (in questo caso per MySql, ma potrebbe essere diversa per altre tipologie di DBMS)
		Scanner sc=new Scanner(System.in);
		DateTimeFormatter df=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
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
			
			System.out.println("*** INSERIMENTO MOVIMENTO ***");
			
			
			//leggo i dati del movimento
			System.out.print("Id: ");
			int id=sc.nextInt();
			sc.nextLine();
			
			System.out.print("Data: ");
			LocalDate data=LocalDate.parse(sc.nextLine(),df);
			
			System.out.print("Cod prodotto: ");
			String codProd=sc.nextLine();
			
			System.out.print("Cod movimento: ");
			String codMov=sc.nextLine();
			
			System.out.print("Quantità: ");
			int quantità=sc.nextInt();
			sc.nextLine();
			
			
			String sql="INSERT INTO movimenti(id, data, codProdotto, codMovimento, quantità) "
					+ "VALUES(?, ?, ?, ?, ?)";		//il ? indica un parametro
			
			System.out.println("Tentativo di esecuzione INSERT");
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		//provo a creare l'istruzione sql
				
				//imposto i valori dei parametri				
				ps.setInt(1, id);		//il primo parametro è l'id. NB: si parte dalla posizione 1
				ps.setObject(2, data);	//il secondo parametro è la data
				ps.setString(3, codProd);
				ps.setString(4, codMov);
				ps.setInt(5, quantità);
				
				
				int righeInteressate=ps.executeUpdate();	//eseguo l'istruzione
				System.out.println("Righe inserite: "+righeInteressate);
			}
			
		} catch (Exception e) {
			//si è verificato un problema. L'oggetto e (di tipo Exception) contiene informazioni sull'errore verificatosi
			System.err.println("Si è verificato un errore: "+e.getMessage());
		}
		sc.close();
	}

}
