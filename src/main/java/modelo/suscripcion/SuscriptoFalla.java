package modelo.suscripcion;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import modelo.personas.TipoMedioDeContacto;

public class SuscriptoFalla extends ColaboradorSuscripto {

    public SuscriptoFalla(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, TipoMedioDeContacto medio) {
        super(heladera, colaborador, tipo, medio);
    }


}