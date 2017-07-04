package br.com.eagleeye.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import br.com.eagleeye.jpa.Processo;
import br.com.eagleeye.repositorio.Processos;

public class ProcessoTable extends LazyDataModel<Processo>  {    
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Processo> datasource;
	Processos repositorio;
	
	public ProcessoTable() {  
    }  
  
    public ProcessoTable(Processos processo , String valorPesquisa) {
    	this.repositorio = processo;
    	if(valorPesquisa != null){
    		this.datasource = processo.getProcessosPorCampo("referenciaMvw", valorPesquisa);
    	}else{
    		this.datasource = processo.getTodosProcessosNaoCancelados();
    	}
    	
    } 
    public ProcessoTable(Processos processo , String where , List<Serializable> param ) {
    		this.repositorio = processo;
    		this.datasource = processo.getProcessosPorCampos(where, param);
    	
    } 
    @SuppressWarnings("unchecked")
	@Override  
    public Processo getRowData(String rowKey) {  
          
        for(Processo car : datasource) {  
            if(String.valueOf(car.getIdProcesso()).equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
 
	@Override
	public String getRowKey(Processo arg0) {
		// TODO Auto-generated method stub
		return String.valueOf(arg0.getIdProcesso());
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
	@Override
	public List<Processo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		
		 List<Processo> data = new ArrayList<Processo>();
	        //filter
	        for(Processo car : datasource) {
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
	            Collections.sort(data, new ProcessoLazySorted(sortField, sortOrder));
	        }
	 
	        //rowCount
	        int dataSize = data.size();
	        this.setRowCount(dataSize);
	        //paginate
	        if(dataSize > pageSize) {
	            try {
	                return repositorio.listar(first, first + pageSize);
	            }
	            catch(IndexOutOfBoundsException e) {
	                return repositorio.listar(first, first + (dataSize % pageSize));
	            }
	        }
	        else {
	        		return data;
	        }
		
	}

}  


