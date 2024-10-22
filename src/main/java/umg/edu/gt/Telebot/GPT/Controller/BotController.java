/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.edu.gt.Telebot.GPT.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author J MAX
 */
@RestController
public class BotController {
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Hola, bienvenido al bot de Telegram con la API de ChatGPT!";
    }
}
