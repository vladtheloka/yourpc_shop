package com.yourpc_shop.controller;

import com.yourpc_shop.entity.Billable;
import com.yourpc_shop.service.MailSenderService;
import com.yourpc_shop.validator.Validator;
import com.yourpc_shop.validator.user.UserValidationMessages;
import com.yourpc_shop.validator.userLoginValidation.UserLoginValidationMessages;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.yourpc_shop.entity.User;
import com.yourpc_shop.service.UserService;

import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Controller
@Transactional
public class UserController
{
    private final UserService userService;

    private final MailSenderService mailSenderService;

    private final Validator validator;

    @Autowired
    public UserController(UserService userService, MailSenderService mailSenderService, @Qualifier("userLoginValidator") Validator validator)
    {
        this.userService = userService;
        this.mailSenderService = mailSenderService;
        this.validator = validator;
    }

    @GetMapping("/signUp")
    public String signUp(Model model)
    {
        model.addAttribute("user", new User());
        return "views-user-signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute User user, Model model)
    {
        String uuid = UUID.randomUUID().toString();

        user.setUuId(uuid);

        try
        {
            userService.add(user);
        }
        catch (Exception e)
        {
            if(e.getMessage().equals(UserValidationMessages.EMPTY_USERNAME_FIELD) ||
                    e.getMessage().equals(UserValidationMessages.USERNAME_ALREADY_EXIST))
            {
                model.addAttribute("usernameException", e.getMessage());
            }
            else if(e.getMessage().equals(UserValidationMessages.EMPTY_EMAIL_FIELD)||
                    e.getMessage().equals(UserValidationMessages.EMAIL_ALREADY_EXIST)||
                    e.getMessage().equals(UserValidationMessages.WRONG_EMAIL))
            {
                model.addAttribute("emailException", e.getMessage());
            }
            else if(e.getMessage().equals(UserValidationMessages.EMPTY_PASSWORD_FIELD)||
                    e.getMessage().equals(UserValidationMessages.TOO_SHORT_PASSWORD))
            {
                model.addAttribute("passwordException", e.getMessage());
            }
            else if(e.getMessage().equals(UserValidationMessages.EMPTY_ADDRESS_FIELD))
            {
                model.addAttribute("addressException", e.getMessage());
            }
            return "views-user-signUp";
        }

        String theme = "thank's for registration";
        String mailBody =
                "gl & hf       http://localhost:8080/confirm/" + uuid;

        mailSenderService.sendMail(theme, mailBody, user.getEmail());
        return "redirect:/";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id)
    {
        userService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable int id, Model model)
    {
        model.addAttribute("userAttribute", userService.getOne(id));
        return "views-admin-updateUser";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@ModelAttribute("userAttribute") User user, @PathVariable int id)
    {
        user.setId(id);
        userService.update(user);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model)
    {
        User user = userService.userWithBillables(Integer.parseInt(principal.getName()));
        Set<Billable> billable = user.getBillable();
        for (Billable billable1: billable)
        {
            Hibernate.initialize(billable1.getItem());
        }
        model.addAttribute("user", user);
        return "views-user-profile";
    }

    @GetMapping("/cart")
    public String cart(Principal principal, Model model)
    {
        model.addAttribute("userCart", userService.userWithItems(Integer.parseInt(principal.getName())));
        return "views-user-cart";
    }

    @GetMapping("/listOfUsers")
    public String allItems(Model model, @PageableDefault Pageable pageable)
    {
        model.addAttribute("users", userService.findAllPages(pageable));
        return "views-admin-listOfUsers";
    }

    @GetMapping("/confirm/{uuid}")
    public String confirm(@PathVariable String uuid)
    {
        User user = userService.findByUuid(uuid);
        user.setEnable(true);

        userService.update(user);
        return "redirect:/";
    }

    @PostMapping("/failureLogin")
    public String failureLogin(Model model, @RequestParam String username,
                               @RequestParam String password){

        try
        {
            validator.validate(new User(username, password));
        }
        catch (Exception e)
        {
            if(e.getMessage().equals(UserLoginValidationMessages.EMPTY_USERNAME_FIELD)||
                    e.getMessage().equals(UserLoginValidationMessages.EMPTY_PASSWORD_FIELD)||
                    e.getMessage().equals(UserLoginValidationMessages.WRONG_USERNAME_OR_PASSWORD))
            {
                model.addAttribute("exception", e.getMessage());
            }
        }
        model.addAttribute("user", new User());

        return "views-user-signUp";
    }
}
