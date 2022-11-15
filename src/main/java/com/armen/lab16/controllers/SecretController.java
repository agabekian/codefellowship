package com.armen.lab16.controllers;

import com.armen.lab16.models.SiteUser;
import com.armen.lab16.repositories.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SecretController {
    @Autowired
    SiteUserRepo siteUserRepo;

    @GetMapping("/recipe")
    public String getRecipe(HttpServletRequest req, Model m) {
        HttpSession session = req.getSession();
        String userName = session.getAttribute("userName").toString();
        // authenticate the user
        if (userName != null) {
            m.addAttribute("userName", userName);
            SiteUser siteUser = siteUserRepo.findByUsername(userName);
            m.addAttribute("secrets", siteUser.getFirstName());
            return "recipes";
        }
        return "login.html";
    }
}
