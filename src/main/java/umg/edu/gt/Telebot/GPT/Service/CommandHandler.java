package umg.edu.gt.Telebot.GPT.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        
        // Agregar comandos disponibles
        commands.put("/help", (chatId, botService) -> {
            String helpMessage = "Comandos disponibles:\n"
                    + "/start - Comenzar interacción\n"
                    + "/help - Mostrar comandos disponibles\n"
                    + "/settings - Configurar opciones";
            botService.sendTelegramMessage(chatId, helpMessage);
        });
        
        commands.put("/settings", (chatId, botService) -> {
            botService.sendTelegramMessage(chatId, "Configuración no disponible por el momento.");
        });
    }

    public void handleCommand(String command, Long chatId, BotService botService) {
        // Ejecutar el comando si existe, de lo contrario, enviar un mensaje de error
        Command cmd = commands.getOrDefault(command, (id, service) -> {
            service.sendTelegramMessage(id, "Comando no reconocido.");
        });
        cmd.execute(chatId, botService);
    }

    public String handleCommand(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Definir la interfaz funcional para los comandos
    @FunctionalInterface
    public interface Command {
        void execute(Long chatId, BotService botService);
    }
}