
package umg.edu.gt.Telebot.GPT.Config;


public class UserConfiguration {
    
    private Long chatId;
    private boolean notificationsEnabled;

    public UserConfiguration(Long chatId) {
        this.chatId = chatId;
        this.notificationsEnabled = true; // Por defecto, modificable de ser necesario
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
