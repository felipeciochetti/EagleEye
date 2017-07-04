package br.com.eagleeye.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.repositorio.Usuarios;

public class UsuarioTable extends LazyDataModel<Usuario>  {    
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Usuario> datasource;
	Usuarios usuario;
	
	public UsuarioTable() {  
    }  
  
    public UsuarioTable(Usuarios usuario) {
    	this.usuario = usuario;
        this.datasource = usuario.getTodosUsuarios();
    } 
    @SuppressWarnings("unchecked")
	@Override  
    public Usuario getRowData(String rowKey) {  
          
        for(Usuario car : datasource) {  
            if(String.valueOf(car.getIdUsuario()).equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
 
	@Override
	public String getRowKey(Usuario arg0) {
		// TODO Auto-generated method stub
		return String.valueOf(arg0.getIdUsuario());
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
	public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		
		 List<Usuario> data = new ArrayList<Usuario>();
	        //filter
	        for(Usuario car : datasource) {
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
	            Collections.sort(data, new UsuarioLazySorted(sortField, sortOrder));
	        }
	 
	        //rowCount
	        int dataSize = data.size();
	        this.setRowCount(dataSize);
	        //paginate
	        if(dataSize > pageSize) {
	            try {
	                return usuario.listar(first, first + pageSize);
	            }
	            catch(IndexOutOfBoundsException e) {
	                return usuario.listar(first, first + (dataSize % pageSize));
	            }
	        }
	        else {
	        		return data;
	        }
		
	}

}  


