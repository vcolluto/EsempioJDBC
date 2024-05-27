package org.generation.italy.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movimento {
	public int id;
	public LocalDate data;
	public String codiceProdotto, codiceMovimento, riferimento;
	public int quantità;
	@Override
	public String toString() {
		DateTimeFormatter df=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return "\n[data=" + data.format(df) + "\ncodiceProdotto=" + codiceProdotto + "\ncodiceMovimento=" + codiceMovimento
				+ "\nquantità=" + quantità + "]\n";
	}
	
	

}
