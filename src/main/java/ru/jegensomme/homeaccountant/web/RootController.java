package ru.jegensomme.homeaccountant.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.jegensomme.homeaccountant.AuthorizedUser;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
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
    public String getExpenses(Model model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("monthlyLimit", authUser.getUserTo().getMonthlyLimit());
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
