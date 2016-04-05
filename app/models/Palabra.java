package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
@Entity
public class Palabra extends Model {
	@Id
	public Long id;
    public String url;
    
    public String nombre;
 
    public Date submittedOn = new Date();
 
    public String descripcion;
    public String categoria;
	public static final Model.Find<Long,Palabra> find = new Model.Find<Long,Palabra>(){};
	
	public Palabra(){
		
	}
	public Palabra(String url, String nombre){
		this.url=url;
		this.nombre=nombre;
	}
	public Palabra(String url, String nombre, String descripcion, String categoria, String video) {
		super();
		this.url = url;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
	}
	public static PagedList<Palabra>page (int page, int pageSize, String sortBy, String order, String filter){
		return
				find.where()
				    .ilike("nombre", "%" + filter + "%")
	                .orderBy(sortBy + " " + order)
	                .findPagedList(0,pageSize)
	                ;
	}



}
