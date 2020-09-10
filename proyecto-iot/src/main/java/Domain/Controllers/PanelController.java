package Domain.Controllers;


import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PanelController {
        public ModelAndView mostrar(Request request, Response response){
            return new ModelAndView(null,"panel.hbs");
        }
}
