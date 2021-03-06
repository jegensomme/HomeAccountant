package ru.jegensomme.homeaccountant.web.ui;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.jegensomme.homeaccountant.AuthorizedUser;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.web.AbstractUserController;
import ru.jegensomme.homeaccountant.web.validators.UniqueMailValidator;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Objects;

@ApiIgnore
@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    public ProfileUIController(UserService service, UniqueMailValidator emailValidator) {
        super(service, emailValidator);
    }

    @GetMapping
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("user", authUser.getUserTo());
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute("user") UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            clearFieldsIfXss(userTo, result);
            return "profile";
        }
        super.update(userTo, authUser.getId());
        authUser.update(userTo);
        status.setComplete();
        return "redirect:/expenses";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("user", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid @ModelAttribute("user") UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            clearFieldsIfXss(userTo, result);
            return "profile";
        }
        super.create(userTo);
        status.setComplete();
        return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
    }

    private void clearFieldsIfXss(UserTo userTo, BindingResult result) {
        if (result.getFieldErrors().stream().anyMatch(fe -> Objects.equals(fe.getCode(), "SafeHtml"))) {
            userTo.setName("");
            userTo.setEmail("");
        }
    }
}