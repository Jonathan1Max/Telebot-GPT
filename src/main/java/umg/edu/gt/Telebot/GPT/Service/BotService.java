package umg.edu.gt.Telebot.GPT.Service;

import jakarta.websocket.ClientEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import umg.edu.gt.Telebot.GPT.Model.Client;
import umg.edu.gt.Telebot.GPT.Repository.ClientRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BotService {

    private final String BOT_TOKEN = "7822338733:AAH3RJF87rr4QmkqRvjpIlUiZYhHS8zBTaQ"; // Reemplaza con tu token
    private final String TELEGRAM_API_URL = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
    
    // Mapa para almacenar el estado de si se está preguntando el nombre al usuario
    private Map<Long, Boolean> askingName = new HashMap<>();
    // Mapa para almacenar el nombre del usuario por chatId
    private Map<Long, String> userNames = new HashMap<>();

    /*private final RestTemplate restTemplate; // Campo para almacenar el RestTemplate*/

    // Constructor que acepta RestTemplate
    /*public BotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/
    
    // metodo para enviar un mensaje a Telegram
    public void sendTelegramMessage(Long chatId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = TELEGRAM_API_URL + "?chat_id=" + chatId + "&text=" + message;
        restTemplate.getForObject(url, String.class);
    }
    
    // Método para obtener el token por medio de protected, para las pruebas unitarias
    /*public String getBotToken() {
        return BOT_TOKEN;
    }*/

    // metodo para establecer el nombre del usuario
    public void setUserName(Long chatId, String name) {
        userNames.put(chatId, name);
    }

    // metodo para obtener el nombre del usuario
    public String getUserName(Long chatId) {
        return userNames.getOrDefault(chatId, "Aún no me has dicho tu nombre.");
    }

    // metodos para gestionar si el bot está preguntando el nombre del usuario
    public void setAskingName(Long chatId, boolean asking) {
        askingName.put(chatId, asking);
    }

    public boolean isAskingName(Long chatId) {
        return askingName.getOrDefault(chatId, false);
    }

    public void handleUpdate(Map<String, Object> update) throws SQLException {
        if (update.containsKey("message")) {
            Map<String, Object> message = (Map<String, Object>) update.get("message");
            Map<String, Object> chat = (Map<String, Object>) message.get("chat");
            long chatId = ((Number) chat.get("id")).longValue();  // Asegúrate de usar long para chatId
            String text = (String) message.get("text");

            // Primero intentamos buscar si el chatId ya existe en la base de datos
            Client client = ClientRepository.getById(chatId);

            if (client != null) {
                // Si se encuentra el cliente, enviamos el mensaje con su nombre
                sendTelegramMessage(chatId, "¡Hola " + client.getName() + ", en qué te puedo ayudar hoy?");
            } else {
                // Si no se encuentra el chatId, seguimos el proceso para preguntar y guardar el nombre
                if (text.equalsIgnoreCase("/start")) {
                    sendTelegramMessage(chatId, "¡Bienvenido! ¿Cómo te llamas?");
                    setAskingName(chatId, true);  // Indicamos que estamos preguntando por el nombre
                } else if (isAskingName(chatId)) {
                    // Guardamos el nombre proporcionado y lo insertamos en la base de datos
                    setUserName(chatId, text);
                    ClientRepository.add(text, chatId);

                    // Luego de insertar, buscamos nuevamente para confirmar que fue guardado
                    Client newClient = ClientRepository.getById(chatId);

                    // Enviamos el mensaje de confirmación con el nombre recién guardado
                    if (newClient != null) {
                        sendTelegramMessage(chatId, "¡Hola " + newClient.getName() + ", en qué te puedo ayudar hoy?");
                    } else {
                        sendTelegramMessage(chatId, "¡Gracias! Tu nombre ha sido guardado.");
                    }

                    setAskingName(chatId, false);  // Terminamos de preguntar el nombre
                } else {
                    // Si no estamos preguntando el nombre, enviamos la respuesta por defecto
                    String response = getUserName(chatId);
                    sendTelegramMessage(chatId, response);
                }
            }
        } else {
            System.out.println("La actualización no contiene un mensaje válido.");
        }
    }
    
    public void sendTelegramKeyboard(Long chatId, String message) {
    RestTemplate restTemplate = new RestTemplate();
        String url = TELEGRAM_API_URL + "?chat_id=" + chatId + "&text=" + message + 
            "&reply_markup={\"inline_keyboard\":[[{\"text\":\"Opción 1\",\"callback_data\":\"opcion1\"},{\"text\":\"Opción 2\",\"callback_data\":\"opcion2\"}]]]}";
        restTemplate.getForObject(url, String.class);
    }

}