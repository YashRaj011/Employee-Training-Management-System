package com.example.Employee.Training.Management.System.Controller;

 import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

 import java.util.Arrays;

@ControllerAdvice
 @Slf4j
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) throws Exception {
        var mv = new ModelAndView();
        mv.setViewName("Error");
        mv.addObject("error_message", e.getMessage());
        return mv;
    }
}
