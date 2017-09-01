package test.socket.demo.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import test.socket.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class UserUtil {
    public static Integer startRate = 5;
    private List<User> users = createUsers();

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Gim", "123"));
        users.add(new User("John", "qwe"));
        users.add(new User("Kate", "1q"));
        return users;
    }
}
