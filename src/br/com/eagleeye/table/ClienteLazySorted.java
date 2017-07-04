package br.com.eagleeye.table;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.eagleeye.jpa.Cliente;

public class ClienteLazySorted implements Comparator<Cliente>  {

	 
	 
	    private String sortField;
	     
	    private SortOrder sortOrder;
	     
	    public ClienteLazySorted(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Cliente car1, Cliente car2) {
	        try {
	            Object value1 = Cliente.class.getField(this.sortField).get(car1);
	            Object value2 = Cliente.class.getField(this.sortField).get(car2);
	 
	            int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }
}
