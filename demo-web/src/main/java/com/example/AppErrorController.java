package com.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppErrorController implements ErrorController {
    private final static String ERROR_PATH = "/error";
    @Autowired
    private ErrorAttributes errorAttributes; // Error Attributes in Application

    @RequestMapping(path = ERROR_PATH, produces = "text/html")
    public ModelAndView errorHtml(WebRequest request) {
        return new ModelAndView("/errorPage", errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults()));
    }
    @ResponseBody
    @RequestMapping(path = ERROR_PATH, produces = "application/json")
    public Map<String, Object> errorJson(WebRequest request) {
        return errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    }
}
