package presentacion.incidentes;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReportarFallaTecnicaController implements Handler {

    public ReportarFallaTecnicaController() {
        super();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String idHel = context.queryParam("heladeraId");
        if (idHel == null || idHel.isEmpty()) {
            context.redirect("/visualizarFallasTecnicas?heladeraId=" + idHel);
            return;
        }
        int idHeladera = Integer.parseInt(idHel);

        model.put("heladeraId", idHeladera);
        model.put("nombreUsuario", context.sessionAttribute("nombreUsuario"));
        context.render("templates/reportarFallaTecnica.mustache", model);

    }

}
