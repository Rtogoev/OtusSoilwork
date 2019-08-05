package ru.otus.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.homework.model.UserForm;
import ru.otus.homework.service.UserFormService;

import java.util.List;

@Controller
public class UserController {

    private final UserFormService userFormService;

    public UserController(UserFormService userFormService) {
        this.userFormService = userFormService;
    }

    @GetMapping({"/", "/user/list"})
    public String userList(Model model) {
        List<UserForm> userForms = userFormService.getAll();
        model.addAttribute("users", userForms);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("user", new UserForm());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute UserForm userForm) {
        userFormService.saveAsUser(userForm);
        return new RedirectView("/user/list", true);
    }

}
