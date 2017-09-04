package test.socket.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import test.socket.demo.model.Greeting;
import test.socket.demo.model.HelloMessage;
import test.socket.demo.model.RateDto;
import test.socket.demo.model.RateResource;
import test.socket.demo.service.UserUtil;

import javax.naming.AuthenticationException;

@Controller
public class GreetingController {
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/bidding")
    @SendTo("/field/gold")
    public RateResource rateResource(RateDto dto) throws AuthenticationException {
        return userUtil.processingReqest(dto);
    }

    public void sendInfoToPrivateMessage() {
        simpMessagingTemplate.convertAndSend("/private/message",
                new RateResource("Unregistered user tried to connect!"));
    }

    @MessageMapping("/private_message")
    @SendTo("/private/message")
    public RateResource privateMessage(HelloMessage message) throws Exception {
        return new RateResource("Hello, " + message.getName() + "!");
    }
}
