package com.kedacom.order.service.impl;

import com.kedacom.commons.vo.OrderVo;
import com.kedacom.order.model.Category;
import com.kedacom.order.model.Order;
import com.kedacom.order.model.OrderCategory;
import com.kedacom.order.repository.CategoryRepository;
import com.kedacom.order.repository.OrderCategoryRepository;
import com.kedacom.order.repository.OrderRepository;
import com.kedacom.order.service.OrderService;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCategoryRepository orderCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean save(OrderVo orderVo) {

        Long categoryId = orderVo.getCategoryId();
        Category category = categoryRepository.findOne(categoryId);
        if (category.getStock() <= 0) {
            return false;
        }
        category.setStock(category.getStock() - orderVo.getNum());
        categoryRepository.save(category);

        Order order = new Order();
        order.setDatetime(new Date());
        order.setState(0);
        order.setUserId(orderVo.getUserId());
        order.setSummoney(category.getPrice().multiply(new BigDecimal(orderVo.getNum())));
        order.setNum(orderVo.getNum());

        Order order1 = orderRepository.save(order);

        OrderCategory orderCategory = new OrderCategory();
        orderCategory.setCategoryId(orderVo.getCategoryId());
        orderCategory.setCreateTime(new Date());
        orderCategory.setOrderId(order1.getId());
        orderCategory.setNum(orderVo.getNum());
        orderCategoryRepository.save(orderCategory);
        return true;
    }

}
