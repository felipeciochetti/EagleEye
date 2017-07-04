package br.com.eagleeye.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.eagleeye.jpa.Usuario;
import br.com.eagleeye.repositorio.Usuarios;
import br.com.eagleeye.service.ExcluirUsuario;
import br.com.eagleeye.table.UsuarioTable;
import br.com.eagleeye.util.FormatUtils;

@ManagedBean
@ViewScoped
public class UsuarioListaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	Usuarios usuario = new Usuarios();
	private Usuario selectUsuario  ;
	private Usuario usuarioExcluir;
	ExcluirUsuario excluir = new ExcluirUsuario();
	List<Usuario> lista;
	UsuarioTable list;
	
	public UsuarioListaBean() {
	}

	@PostConstruct
	public void init(){
		list = new UsuarioTable(usuario);
	}
	
	public List<Usuario> getLista() {
		return usuario.getTodosUsuarios();
	}
	public UsuarioTable getList() {
		return list ;//= new UsuarioTable(getLista());
	}
	
	public String preparaAlterar(Usuario usuario) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put("usuarioAlterar", String.valueOf(usuario.getIdUsuario()));
		return "CadastroUsuario.xhtml?faces-redirect=true";
	}
	public void preparaExcluir(Usuario usuario) {
		this.usuarioExcluir = usuario;
	}

	public void excluir() {
		excluir.excluir(usuarioExcluir);
		usuarioExcluir = null;
		list = new UsuarioTable(usuario);
	}
	
	public void setList(UsuarioTable list) {
		this.list = list;
	}

	public Usuario getSelectUsuario() {
		return selectUsuario;
	}

	public void setSelectUsuario(Usuario selectUsuario) {
		this.selectUsuario = selectUsuario;
	}

	public Usuario getUsuarioExcluir() {
		return usuarioExcluir;
	}

	public void setUsuarioExcluir(Usuario usuarioExcluir) {
		this.usuarioExcluir = usuarioExcluir;
	}
	
	public String formatarNivel(String nivel){
		 return FormatUtils.getInstance().formatarNivelUsuario(nivel);
	}

	

}
