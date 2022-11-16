package com.armen.lab16.controllers;

import com.armen.lab16.models.Secret;
import com.armen.lab16.models.SiteUser;
import com.armen.lab16.repositories.SecretRepo;
import com.armen.lab16.repositories.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    SecretRepo secretRepo;

    @Autowired
    SiteUserRepo siteUserRepo;

    @GetMapping("/messages")
    public String getMessage(HttpServletRequest req, Model m) {
        HttpSession session = req.getSession();
        String userName = session.getAttribute("userName").toString();
        // authenticate the user
        if (userName != null) {
            List<Secret> messages = secretRepo.findAll();
            m.addAttribute("messages",messages);
//            m.addAttribute("userName", userName);
//            SiteUser siteUser = siteUserRepo.findByUsername(userName);
//            m.addAttribute("secrets", siteUser.getFirstName());
            return "messages";
        }
        return "login.html";
    }

    @PostMapping("/messages")
    public RedirectView createNewMessage(String name, String body, String username) {
        SiteUser theUser = siteUserRepo.findByUsername(username);
        Secret newMessage = new Secret(name, body, theUser);
        secretRepo.save(newMessage);
        return new RedirectView("/");
    }
}
