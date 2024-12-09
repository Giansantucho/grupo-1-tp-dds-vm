import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Application {
    private static Javalin app = null;
    private static final String UPLOAD_DIR = "src/main/resources/uploads";

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void main(String[] args) {

        app = Javalin.create(javalinConfig -> {
            javalinConfig.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });

            // Configurar recursos estáticos
            javalinConfig.staticFiles.add("/", Location.CLASSPATH);

        }).start(8080);

        // Crear el directorio de imágenes si no existe
        initializeUploadDirectory();

        // Configurar rutas
        Router.init(getEntityManager());
        configureImageRoutes(app);
    }

    private static EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        return emf.createEntityManager();
    }

    private static void initializeUploadDirectory() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    private static void configureImageRoutes(Javalin app) {
        app.get("/images/{filename}", ctx -> {
            String filePath = "src/main/resources/uploads/ofertas/" + ctx.pathParam("filename");
            try {
                // Leer el archivo completo como un arreglo de bytes
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

                if (fileBytes.length > 0) {
                    ctx.contentType("image/*");
                    ctx.result(fileBytes); // Enviar los bytes directamente
                } else {
                    ctx.status(404).result("Imagen vacía");
                }
            } catch (IOException e) {
                e.printStackTrace();
                ctx.status(500).result("Error al cargar la imagen");
            }
        });
    }
}

