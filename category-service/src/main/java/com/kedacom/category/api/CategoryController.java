package com.kedacom.category.api;

import com.kedacom.category.model.Category;
import com.kedacom.category.service.CategoryService;
import com.kedacom.commons.api.Error;
import com.kedacom.commons.api.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private Registration registration;


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Category get(@PathVariable Long id) {
        String serviceId = registration.getServiceId();
        ServiceInstance instance = client.getInstances(serviceId).get(0);
        Category category = categoryService.findOne(id);
        logger.info("/category, host:" + instance.getHost() + ", serviceId: " + instance.getServiceId() + ",category id: " + category.getId() + ",category name: " + category.getName());
        return category;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> save(@RequestBody Category category, UriComponentsBuilder ucb) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/category, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId() + ",category id:" + category.getId() + ",category name:" + category.getName());

        Category saved = categoryService.save(category);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/category/")
                .path(String.valueOf(saved.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);

        return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Category> categories(@RequestParam(value = "name") String name) {
        ServiceInstance instance = client.getLocalServiceInstance();
        List<Category> categories = categoryService.findByName(name);
        logger.info("/category, host:" + instance.getHost() + ", serviceId: " + instance.getServiceId() + ",  category name: " + name);
        return categories;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error resourceNotFound(ResourceNotFoundException e) {
        Long resourceId = e.getResourceId();
        return new Error(4, "Category [" + resourceId + "] not found");
    }
}
