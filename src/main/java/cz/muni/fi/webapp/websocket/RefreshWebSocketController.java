package cz.muni.fi.webapp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RefreshWebSocketController {

    private final SimpMessagingTemplate template;

    @Autowired
    public RefreshWebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    @SendTo("/server/alive")
    public String alive(String message) throws Exception {
        System.out.println(message);
        return "hi";
    }

    public void sendRefreshMessage() {
        System.out.println("REFRESH MSG SENT");
        this.template.convertAndSend("/server/refresh", "New data available");
    }
}
