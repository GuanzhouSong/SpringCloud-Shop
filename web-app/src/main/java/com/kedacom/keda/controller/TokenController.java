package com.kedacom.keda.controller;


import com.kedacom.commons.api.Result;
import com.kedacom.commons.util.ResultUtil;
import com.kedacom.keda.annotation.Authorization;
import com.kedacom.keda.annotation.CurrentUser;
import com.kedacom.keda.manager.TokenManager;
import com.kedacom.keda.model.TokenModel;
import com.kedacom.keda.service.UserService;
import com.kedacom.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Use ACCESS TOKEN for access authentication check
 * Get the delete the request address of token，which corresponds to resource projecting of
 * login and logout in Restful design
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping("/login")
    public Result login (User user) {
        Assert.notNull (user.getName(), "username can not be empty");
        Assert.notNull (user.getPassword(), "password can not be empty");


        User u = userService.login(user);
        if (u == null || !u.getPassword ().equals (user.getPassword())) { // password error
            return ResultUtil.error(2,"User name or password error");
        }
        // create a token to store user login status
        TokenModel model = tokenManager.createToken (u.getId ());
        return ResultUtil.success(model);
    }

    @RequestMapping (method = RequestMethod.DELETE)
    @Authorization
    public Result logout (@CurrentUser User user) {
        tokenManager.deleteToken (user.getId ());
        return ResultUtil.success();
    }

    /**
     * 测试
     */
    @RequestMapping("/test")
    public String test() {
        return "Success";
    }

}
