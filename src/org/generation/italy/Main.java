package org.generation.italy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.generation.italy.model.Movimento;

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
			
			Movimento m=new Movimento();
			
			//leggo i dati del movimento
			System.out.print("Id: ");
			m.id=sc.nextInt();
			sc.nextLine();
			
			boolean dataValida=false;
			do {
				System.out.print("Data: ");
				try {
					m.data=LocalDate.parse(sc.nextLine(),df);
					dataValida=true;
				} catch (Exception e) {
					//se la data non è valida gestisco l'eccezione
					System.out.println("Hai inserito una data non valida, riprova!");					
				}
			} while (!dataValida); 	//torno indietro se la data non è valida
			
			
			System.out.print("Cod prodotto: ");
			m.codiceProdotto=sc.nextLine();
			
			System.out.print("Cod movimento: ");
			m.codiceMovimento=sc.nextLine();
			
			System.out.print("Quantità: ");
			m.quantità=sc.nextInt();
			sc.nextLine();
			
			
			String sql="INSERT INTO movimenti(id, data, codProdotto, codMovimento, quantità) "
					+ "VALUES(?, ?, ?, ?, ?)";		//il ? indica un parametro (segnaposto)
			
			System.out.println("Tentativo di esecuzione INSERT");
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		//provo a creare l'istruzione sql
				
				//imposto i valori dei parametri				
				ps.setInt(1, m.id);		//il primo parametro è l'id. NB: si parte dalla posizione 1
				ps.setDate(2, Date.valueOf(m.data));	//il secondo parametro è la data
				ps.setString(3, m.codiceProdotto);
				ps.setString(4, m.codiceMovimento);
				ps.setInt(5, m.quantità);
				
				
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
