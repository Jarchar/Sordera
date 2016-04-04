package controllers;

import static play.data.Form.form;

import javax.inject.Inject;

import models.Palabra;
import views.html.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class PalabraController extends Controller {
    @Inject 
    public static FormFactory formFactory;

	public static Result GO_HOME = redirect(
            routes.PalabraController.list(0, "nombre", "asc", "")
        );
   
   /**
    * Handle default path requests, redirect to computers list
    */
   public static Result index() {
       return GO_HOME;
   }
   
   public static Result list(int page, String sortBy, String order, String filter) {
       return ok(
           list.render(
               Palabra.page(page, 10, sortBy, order, filter),
               sortBy, order, filter
           )
       );
   }
   public static Result edit(Long id) {
       Form<Palabra> computerForm = formFactory.form(Palabra.class).fill(
           Palabra.find.byId(id)
       );
       return ok(
           editForm.render(id, computerForm)
       );
   }
   public static Result update(Long id) {
       Form<Palabra> palabraForm = formFactory.form(Palabra.class).bindFromRequest();
       if(palabraForm.hasErrors()) {
           return badRequest(editForm.render(id, palabraForm));
       }
       palabraForm.get().update(id);
       flash("success", "Palabra " + palabraForm.get().nombre + " has been updated");
       return GO_HOME;
   }
   
   public static Result create() {
       Form<Palabra> palabraForm = formFactory.form(Palabra.class);
       return ok(
           createForm.render(palabraForm)
       );
   }
   
   /**
    * Handle the 'new computer form' submission 
    */
   public static Result save() {
       Form<Palabra> computerForm = formFactory.form(Palabra.class).bindFromRequest();
       if(computerForm.hasErrors()) {
           return badRequest(createForm.render(computerForm));
       }
       computerForm.get().save();
       flash("success", "Computer " + computerForm.get().name + " has been created");
       return GO_HOME;
   }
   
   /**
    * Handle computer deletion
    */
   public static Result delete(Long id) {
       Palabra.find.ref(id).delete();
       flash("success", "Computer has been deleted");
       return GO_HOME;
   }
   
   
   

}
