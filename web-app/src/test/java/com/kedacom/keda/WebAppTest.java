package com.kedacom.keda;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * API Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
@ContextConfiguration
public class WebAppTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * Unit test for user service -> login function
     * @throws Exception
     */
    @Test
    public void testUserLogin() throws Exception {
        RequestBuilder request = null;
        //path
        request = post("/users/login")
                //parameters
                .param("name", "admin")
                .param("password","123456")
                //accepted data type
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                //check status code
                .andExpect(status().isOk())
                //Check whether obtained information matches particular fields
                .andExpect(content().string("{\"code\":0,\"msg\":\"成功\",\"data\":null}"))
                //print info
                .andDo(print());

        /**
         * output after executing：
         * MockHttpServletRequest:
         *       HTTP Method = POST
         *       Request URI = /users/login
         *        Parameters = {name=[admin], password=[123456]}
         *           Headers = {}
         *
         * Handler:
         *              Type = com.kedacom.keda.controller.UserController
         *            Method = public com.kedacom.commons.api.Result com.kedacom.keda.controller.UserController.login(com.kedacom.user.model.User,javax.servlet.http.HttpSession)
         *
         * Async:
         *     Async started = false
         *      Async result = null
         *
         * Resolved Exception:
         *              Type = null
         *
         * ModelAndView:
         *         View name = null
         *              View = null
         *             Model = null
         *
         * FlashMap:
         *        Attributes = null
         *
         * MockHttpServletResponse:
         *            Status = 200
         *     Error message = null
         *           Headers = {Content-Type=[application/json;charset=UTF-8]}
         *      Content type = application/json;charset=UTF-8
         *              Body = {"code":0,"msg":"成功","data":null}
         *     Forwarded URL = null
         *    Redirected URL = null
         *           Cookies = []
         */
    }

    /**
     * Test for category service--> Get category
     * @throws Exception
     */
    @Test
    public void testGetCategoryById() throws Exception {
        RequestBuilder request = null;
        //path
        request = get("/category/introduction/1");
        mvc.perform(request)
                //check status code
                .andExpect(status().isOk())
                //print info
                .andDo(print());
        return;
    }

}
