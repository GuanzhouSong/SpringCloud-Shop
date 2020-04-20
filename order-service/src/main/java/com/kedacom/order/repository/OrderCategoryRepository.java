package com.kedacom.order.repository;

import com.kedacom.order.model.OrderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCategoryRepository extends JpaRepository<OrderCategory, Long> {

}
