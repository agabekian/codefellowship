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
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    SecretRepo secretRepo;

    @Autowired
    SiteUserRepo siteUserRepo;

    @PostMapping("/messages")
    public RedirectView createNewMessage(String name, String body, String username) {
        SiteUser theUser = siteUserRepo.findByUsername(username);
        Secret newMessage = new Secret(name, body, theUser, new Date());
        secretRepo.save(newMessage);
        return new RedirectView("/myprofile");
    }
}
