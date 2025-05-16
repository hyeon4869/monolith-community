package community.community.controller.frontController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    private String main(){
    return "posts";
    }


    @GetMapping("/detail/{id}")
    private String postDetail(@PathVariable("id") Long id){
        return "postDetail";
    }

    @GetMapping("/login")
    private String login(){
        return "login";
    }

    @GetMapping("/signUp")
    private String signUpPage(){
        return "signUp";
    }

    @GetMapping("/postWrite")
    private String writePost() {
        return "postWrite";
    }

    @GetMapping("/posts/recent")
    private String recentPost(){
        return "recent";
    }
}
