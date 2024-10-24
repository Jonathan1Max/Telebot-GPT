/*package umg.edu.gt.Telebot.GPT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import umg.edu.gt.Telebot.GPT.Service.BotService;


@ExtendWith(MockitoExtension.class)
public class BotServiceTest {

    @InjectMocks
    private BotService botService;

    @Mock
    private RestTemplate restTemplateMock;

    @Test
    public void testSendTelegramMessage() {
        Long chatId = 123456789L;
        String message = "Hello!";
        String expectedUrl = "https://api.telegram.org/bot" + botService.getBotToken() + "/sendMessage?chat_id=" + chatId + "&text=" + message;

        // Mockear la llamada a la API
        when(restTemplateMock.getForObject(expectedUrl, String.class)).thenReturn("Mocked Response");

        // Llamar al método que queremos probar
        botService.sendTelegramMessage(chatId, message);

        // Verificar que el mock fue llamado correctamente
        verify(restTemplateMock).getForObject(expectedUrl, String.class);
    }
}*/
