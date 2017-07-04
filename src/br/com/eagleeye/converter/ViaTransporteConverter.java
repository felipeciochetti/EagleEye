package br.com.eagleeye.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.eagleeye.jpa.ViaTransporte;


@FacesConverter(value = "viaTransporteConverter")
public class ViaTransporteConverter implements Converter {

	
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

        if ( !"".equals(value) ) {

            ViaTransporte pais = (ViaTransporte) value;

            this.addAttribute(component, pais);

            String id= String.valueOf(pais.getIdViaTransporte());

            if (id != null) {
                return id;
            }
        }

        return (String) value;
    }

    protected void addAttribute(UIComponent component, ViaTransporte o) {
        String key = String.valueOf(o.getIdViaTransporte()); 
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
