package test.socket.demo.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.socket.demo.controler.GreetingController;
import test.socket.demo.model.RateDto;
import test.socket.demo.model.RateResource;
import test.socket.demo.model.User;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class UserUtil {
    @Autowired
    GreetingController greetingController;

    private static Integer startRate = 5;
    private List<User> users = createUsers();

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Gim", "123"));
        users.add(new User("John", "qwe"));
        users.add(new User("Kate", "1q"));
        return users;
    }

    private boolean isRegistered(String login, String password) {
        return users.contains(new User(login, password));
    }

    private Integer plusToRate(Integer rate) {
        return startRate += rate;
    }

    public RateResource processingReqest(RateDto dto) throws AuthenticationException {
        if (isRegistered(dto.getLogin(), dto.getPassword())) {
            Integer rate = plusToRate(dto.getRate());
            return new RateResource("Rate: " + rate + ". " + "User " + dto.getLogin() +
                    " increased the rate on " + dto.getRate() + ".");
        } else {
            greetingController.sendInfoToPrivateMessage();
            throw new AuthenticationException("User " + dto.getLogin() + " is not authorised");
        }
    }
}
