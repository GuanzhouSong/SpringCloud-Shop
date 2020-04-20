package com.kedacom.apigateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Zuul filter. After implementing self-defined filter, it won't work directly. We need to
 * construct a Bean to activate it. We can define some general logic that's irrelevant to the
 * business to implement filter for requests. Example: Signature check, permission check, etc.
 *
 */
public class AccessFilter extends ZuulFilter{

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    private static final String[] IGNORE_URI = {"/login","/css/","/js/","/img/"};

    //Type of filter, which decides filter execute in which part of the request's life cycle
    @Override public String filterType() {
        //pre，will execute before requesting router
        return "pre";
    }

    //execution order of filter
    @Override public int filterOrder() {
        return 0;
    }

    //Decide whether need the filter
    @Override public boolean shouldFilter() {
        return true;
    }

    @Override public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        /** Default: User not login */
        boolean flag = false;
        /** Get request's ServletPath */
        String servletPath = request.getServletPath();
        /**  Check whether to filter the request */
        for (String s : IGNORE_URI) {
            if (servletPath.contains(s)) {
                flag = true;
                break;
            }
        }

        if(!flag){
            Object accessToken = request.getParameter("accessToken");
            accessToken = "test";

            if(accessToken == null) {
                logger.warn("access token is empty");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                return null;
            }

            logger.info("accessToken ok");
            //resent by router
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            return null;
        }else {
            return null;
        }
    }
}
