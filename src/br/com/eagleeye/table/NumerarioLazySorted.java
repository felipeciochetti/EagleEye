package br.com.eagleeye.table;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.eagleeye.jpa.Numerario;

public class NumerarioLazySorted implements Comparator<Numerario>  {

	 
	 
	    private String sortField;
	     
	    private SortOrder sortOrder;
	     
	    public NumerarioLazySorted(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }
	 
	    public int compare(Numerario car1, Numerario car2) {
	        try {
	            Object value1 = Numerario.class.getField(this.sortField).get(car1);
	            Object value2 = Numerario.class.getField(this.sortField).get(car2);
	 
	            int value = ((Comparable)value1).compareTo(value2);
	             
	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch(Exception e) {
	            throw new RuntimeException();
	        }
	    }
}
