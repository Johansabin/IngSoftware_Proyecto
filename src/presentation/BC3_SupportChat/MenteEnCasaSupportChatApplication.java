package presentation.BC3_SupportChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "presentation.BC3_SupportChat"
})
public class MenteEnCasaSupportChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenteEnCasaSupportChatApplication.class, args);
    }
}
