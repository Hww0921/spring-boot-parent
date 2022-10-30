package it.artisan.controller;

import it.artisan.response.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author dkp
 * @create 2022-10-30 11:41
 */
@RestController
public class HelloWorldController {

   @GetMapping("/hello")
   public RestResponse get(){
       return RestResponse.ok("你好！");
   }
}
