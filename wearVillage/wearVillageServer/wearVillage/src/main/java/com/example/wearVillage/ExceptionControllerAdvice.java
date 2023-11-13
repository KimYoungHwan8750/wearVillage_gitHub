//package com.example.wearVillage;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.util.Map;
//import java.util.Objects;
//
//@ControllerAdvice
//public class ExceptionControllerAdvice {
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ModelAndView handleAll404error(HttpServletRequest request, Exception ex){
//        ModelAndView mav = new ModelAndView("404errorPage");
//        mav.addObject("exception", ex);
//        mav.addObject("url", request.getRequestURL());
//        return mav;
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAll500error(Exception ex){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.setViewName("500errorPage");
//        return mav;
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ModelAndView handleAll400error(Exception ex){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", ex);
//        mav.setViewName("400errorPage");
//        return mav;
//    }
//}
