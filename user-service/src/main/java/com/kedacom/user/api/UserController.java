package com.kedacom.user.api;

import com.kedacom.user.model.User;
import com.kedacom.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DiscoveryClient client;

    @PostMapping(value = "/login")
    public User login(@RequestBody User user) {
        return userService.findByName(user.getName());
    }

    @PostMapping(value = "/insertUser")
    public boolean insertUser(@RequestBody User user) {
        try {
            userService.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> save(User user, UriComponentsBuilder ucb) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/user, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId() + ",user id:" + user.getId() + ",user name:" + user.getName());

        User saved = userService.save(user);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/login/")
                .path(String.valueOf(saved.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);

        ResponseEntity<User> responseEntity = new ResponseEntity<>(saved, headers, HttpStatus.CREATED);

        return responseEntity;

    }

    /**
     * Test Spring Session
     *
     * @param session
     * @return
     */
    @RequestMapping("/test/cookie")
    public String cookie(HttpSession session) {
        //取出session中的browser
        Object sessionBrowser = session.getAttribute("browser");
        if (sessionBrowser == null) {
            System.out.println("不存在sessionBrowser");
        } else {
            System.out.println("存在session，browser=" + sessionBrowser.toString());
        }
        return "成功";
    }

    /**
     * Test Transactions
     *
     * @return
     */
    @RequestMapping("/test/testTransactional")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public String testTransactional() {
        userService.save(new User("AAA", "123456"));
        //name no longer than 5, will rollback
        userService.save(new User("TESTTESTTEST", "123456"));
        userService.save(new User("BBB", "123456"));
        return "成功";
    }
}
