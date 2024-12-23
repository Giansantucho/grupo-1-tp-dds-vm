package presentacion.vistaAdmin;

import accessManagment.Roles;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.util.Map;

public class InicioAdminController implements Handler {


    @Override
    public void handle(@NotNull Context context) throws Exception {

        Map<String, Object> model = GeneradorModel.getModel(context);

        if (context.sessionAttribute("notificacionAdminMail") != null) {
            model.put("notificacionAdminMail", context.sessionAttribute("notificacionAdminMail"));
            context.consumeSessionAttribute("notificacionAdminMail");
        }


        context.render("templates/inicioAdmin.mustache", model);
    }
}
