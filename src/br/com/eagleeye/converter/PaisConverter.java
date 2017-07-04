package br.com.eagleeye.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.eagleeye.jpa.Pais;


@FacesConverter(value = "paisConverter")
public class PaisConverter implements Converter {

	
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

        if ( !"".equals(value) ) {

            Pais pais = (Pais) value;

            this.addAttribute(component, pais);

            String id= String.valueOf(pais.getIdPais());

            if (id != null) {
                return id;
            }
        }

        return (String) value;
    }

    protected void addAttribute(UIComponent component, Pais o) {
        String key = String.valueOf(o.getIdPais()); 
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
