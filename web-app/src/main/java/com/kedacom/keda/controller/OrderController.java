package com.kedacom.keda.controller;

import com.kedacom.commons.api.Result;
import com.kedacom.commons.util.ResultUtil;
import com.kedacom.commons.vo.OrderVo;
import com.kedacom.keda.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController{

    @Autowired OrderService orderService;

    @PostMapping("/add")
    public Result addOrder(OrderVo orderVo,HttpSession session) {
//        Long userId= (Long) session.getAttribute("userId");
        Long userId= 1L;

        if(userId == null){
            return ResultUtil.error(3,"Please login!");
        }else{
            orderVo.setUserId(userId);
            orderService.add(orderVo);
            return ResultUtil.success();
        }
    }

}
