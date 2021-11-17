package ru.jegensomme.homeaccountant.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:expenses";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping("/expenses")
    public String getExpenses() {
        return "expenses";
    }

    @GetMapping("/categories")
    public String getCategories() {
        return "categories";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
