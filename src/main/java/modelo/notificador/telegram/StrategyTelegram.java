package modelo.notificador.telegram;

import modelo.notificador.StrategyNotificacion;
import modelo.personas.Colaborador;

public class StrategyTelegram implements StrategyNotificacion {

    @Override
    public void enviarNotificacion(String mensaje, Colaborador persona, String asunto) {

        TelegramBot bot = new TelegramBot();
        bot.enviarMensaje(mensaje, "123456"); //Aca deberia ir el numero de chat de la persona

    }

}