package presentacion.vistaTecnico;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import modelo.authService.AuthServiceTecnico;
import modelo.excepciones.ExcepcionValidacion;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import utils.GeneradorModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegistroCompletadoVisitaController implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = GeneradorModel.getModel(context);

        Integer idTecnico = context.sessionAttribute("idTecnico");
        String idHel = context.formParam("id_heladera");
        String idInc = context.formParam("id_incidente");
        String checkBoxResolvioProblema = context.formParam("checkBoxResolvioProblema");
        String descripcion = context.formParam("descripcion");
        List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

        UploadedFile file = uploadedFiles.get(0);
        File archivo = null;
        String fileName = "";
        if (  file.size() != 0 ) {
            fileName = file.filename();
            System.out.println("Received file: " + fileName);

            archivo = new File("src/main/resources/uploads/visitasImagenes/" + file.filename());
        }

        Integer idHeladera = Integer.parseInt(idHel);
        Integer idIncidente = Integer.parseInt(idInc);

        Boolean problemaResuelto = Boolean.FALSE;
        if (checkBoxResolvioProblema.equals("on")) {
            problemaResuelto = Boolean.TRUE;;
        }

        try {
            AuthServiceTecnico.registrarVisita(idTecnico, idHeladera, idIncidente, descripcion, fileName, problemaResuelto);

            if ( file.size() != 0 ){
                FileUtils.copyInputStreamToFile(file.content(), archivo); }

        } catch (ExcepcionValidacion | IOException e) {
            context.sessionAttribute("notificacionVisita", e.getMessage());
            context.redirect("/registrarVisita");
        }

        context.sessionAttribute("notificacionVisita", "Tu visita ha sido registrada con exito!");
        context.redirect("/registrarVisita");

    }

}

