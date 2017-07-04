package br.com.eagleeye.table;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.eagleeye.jpa.Transportadora;

public class TransportadoraLazySorted implements Comparator<Transportadora>  {

	 
	 
	    private String sortField;
	     
	    private SortOrder sortOrder;
	     
	    public TransportadoraLazySorted(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Transportadora car1, Transportadora car2) {
	        try {
	            Object value1 = Transportadora.class.getField(this.sortField).get(car1);
	            Object value2 = Transportadora.class.getField(this.sortField).get(car2);
	 
	            int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }
}
