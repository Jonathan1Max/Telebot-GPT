package umg.edu.gt.Telebot.GPT.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    // metodo para enviar un mensaje a Telegram
    public void sendTelegramMessage(Long chatId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = TELEGRAM_API_URL + "?chat_id=" + chatId + "&text=" + message;
        restTemplate.getForObject(url, String.class);
    }

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
}