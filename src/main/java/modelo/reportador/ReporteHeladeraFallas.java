package modelo.reportador;

import lombok.NoArgsConstructor;
import modelo.elementos.Heladera;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Entity
@DiscriminatorValue("HELADERA_FALLAS")
@NoArgsConstructor
public class ReporteHeladeraFallas extends Reporte {
    @Transient
    private Map<Heladera, Integer> datos;

    public ReporteHeladeraFallas(Map<Heladera, Integer> datos){
        this.datos = datos;
        try {
            this.path = saveToCSV("/archivos/reportes/");
        } catch (IOException e){
            this.path = "";
            e.printStackTrace();
        }
    }

    public String saveToCSV(String directory) throws IOException {
        String createdPath = directory + "reporte_heladera_fallas_"+this.getFecha()+".csv";
        String savePath = "/app/static" + createdPath;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            writer.write("ID_HELADERA;NOMBRE_HELADERA;ESTADO;CANTIDAD_FALLAS\n");
            for (Map.Entry<Heladera, Integer> entry : datos.entrySet()) {
                Heladera heladera = entry.getKey();
                Integer contador = entry.getValue();
                writer.write(heladera.getId() + ";" + heladera.getNombre() + ";" + heladera.getActiva() + ";" + contador + "\n");
            }
        }
        return createdPath;
    }

}
