package test.socket.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import test.socket.demo.model.*;
import test.socket.demo.service.UserUtil;

@Controller
public class GreetingController {
    @Autowired
    private UserUtil userUtil;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/bidding")
    @SendTo("/field/gold")
    public RateResource rateResource(RateDto dto) {
        RateResource resource = new RateResource();
        if (userUtil.getUsers().contains(new User(dto.getLogin(), dto.getPassword()))) {
            UserUtil.startRate += dto.getRate();
            resource.setMessage("Rate: " + UserUtil.startRate + ". " + "User " + dto.getLogin() +
                    " increased the rate on " + dto.getRate() + ".");
            return resource;
        } else {
            resource.setMessage("User " + dto.getLogin() + " is not authorised");
        }
        return resource;
    }
}
