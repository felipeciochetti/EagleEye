package br.com.eagleeye.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.eagleeye.jpa.Transportadora;
import br.com.eagleeye.repositorio.Transportadoras;

public class TransportadoraTable extends LazyDataModel<Transportadora>  {    
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Transportadora> datasource;
	Transportadoras transportadora;
	
	public TransportadoraTable() {  
    }  
  
    public TransportadoraTable(Transportadoras transportadora) {
    	this.transportadora = transportadora;
        this.datasource = transportadora.getTodosTransportadoras();
    } 
    @SuppressWarnings("unchecked")
	@Override  
    public Transportadora getRowData(String rowKey) {  
          
        for(Transportadora car : datasource) {  
            if(String.valueOf(car.getIdTransportadora()).equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
 
	@Override
	public String getRowKey(Transportadora arg0) {
		// TODO Auto-generated method stub
		return String.valueOf(arg0.getIdTransportadora());
	}
//	public void setRowIndex(int rowIndex) {
//	    /*
//	     * The following is in ancestor (LazyDataModel):
//	     * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
//	     */
//	    if (rowIndex == -1 || getPageSize() == 0) {
//	        super.setRowIndex(-1);
//	    }
//	    else
//	        super.setRowIndex(rowIndex % getPageSize());
//	}
	public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		
		 List<Transportadora> data = new ArrayList<Transportadora>();
	        //filter
	        for(Transportadora car : datasource) {
	            boolean match = true;
	 
	            if (filters != null) {
	                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
	                    try {
	                        String filterProperty = it.next();
	                        Object filterValue = filters.get(filterProperty);
	                        String fieldValue = String.valueOf(car.getClass().getDeclaredField(filterProperty).get(car));
	 
	                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
	                            match = true;
	                    }
	                    else {
	                            match = false;
	                            break;
	                        }
	                    } catch(Exception e) {
	                        match = false;
	                    }
	                }
	            }
	 
	            if(match) {
	                data.add(car);
	            }
	        }
	 
	        //sort
	        if(sortField != null) {
	            Collections.sort(data, new TransportadoraLazySorted(sortField, sortOrder));
	        }
	 
	        //rowCount
	        int dataSize = data.size();
	        this.setRowCount(dataSize);
	        //paginate
	        if(dataSize > pageSize) {
	            try {
	                return transportadora.listar(first, first + pageSize);
	            }
	            catch(IndexOutOfBoundsException e) {
	                return transportadora.listar(first, first + (dataSize % pageSize));
	            }
	        }
	        else {
	        		return data;
	        }
		
	}

}  


