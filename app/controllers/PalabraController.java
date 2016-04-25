package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.inject.Inject;


import models.Palabra;
import views.html.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class PalabraController extends Controller {
    @Inject 
    public FormFactory formFactory;

	public static Result GO_HOME = redirect(
            routes.PalabraController.list(0, "nombre", "asc", "")
        );
   
   public  Result index() {
       return GO_HOME;
   }
   
   public Result list(int page, String sortBy, String order, String filter) {
       return ok(
           list.render(
               Palabra.page(page, 10, sortBy, order, filter),
               sortBy, order, filter
           )
       );
   }
   public Result edit(Long id) {
       Form<Palabra> computerForm = formFactory.form(Palabra.class).fill(
           Palabra.find.byId(id)
       );
       return ok(
           editForm.render(id, computerForm)
       );
   }
   public Result update(Long id) {
       Form<Palabra> palabraForm = formFactory.form(Palabra.class).bindFromRequest();
       if(palabraForm.hasErrors()) {
           return badRequest(editForm.render(id, palabraForm));
       }
       palabraForm.get().update();
       flash("success", "Palabra " + palabraForm.get().nombre + " has been updated");
       return GO_HOME;
   }
   
   public Result create() {
       Form<Palabra> palabraForm = formFactory.form(Palabra.class);
       return ok(
           createForm.render(palabraForm)
       );
   }
   
   /**
    * Handle the 'new computer form' submission 
    */
   public Result save() {
       play.mvc.Http.MultipartFormData<File> body = request().body().asMultipartFormData();
       play.mvc.Http.MultipartFormData.FilePart<File> picture = body.getFile("video");
       if (picture != null) {
           String fileName = picture.getFilename();
           String contentType = picture.getContentType();
           java.io.File file = picture.getFile();
           Palabra pal= new Palabra();
           Map<String,String[]> datos= body.asFormUrlEncoded();
           pal.descripcion=datos.get("descripcion")[0];
           pal.categoria=datos.get("categoria")[0];
           pal.nombre=datos.get("nombre")[0];
           pal.save();
           InputStream inStream = null;
       	OutputStream outStream = null;
       		
           	try{
           		
           	    File bfile =new File("/home/bruno/"+fileName);
           		
           	    inStream = new FileInputStream(file);
           	    outStream = new FileOutputStream(bfile);
               	
           	    byte[] buffer = new byte[1024];
           		
           	    int length;
           	    //copy the file content in bytes 
           	    while ((length = inStream.read(buffer)) > 0){
           	  
           	    	outStream.write(buffer, 0, length);
           	 
           	    }
           	 
           	    inStream.close();
           	    outStream.close();
           	    
           	    //delete the original file
           	    file.delete();
           	    
           	    System.out.println("File is copied successful!");
           	    
           	}catch(IOException e){
           	    e.printStackTrace();
           	}
           
           return GO_HOME;
       } else {
           flash("error", "Missing file");
           return badRequest();
       }

     }
   
   /**
    * Handle computer deletion
    */
   public Result delete(Long id) {
       Palabra.find.ref(id).delete();
       flash("exito", "Se ha borrado la palabra");
       return GO_HOME;
   }
   
   
   

}
