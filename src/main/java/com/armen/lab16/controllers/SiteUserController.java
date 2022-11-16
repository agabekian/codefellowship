package com.armen.lab16.controllers;

import com.armen.lab16.models.SiteUser;
import com.armen.lab16.repositories.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@Controller
public class SiteUserController {
    @Autowired
    SiteUserRepo siteUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;
    // Principal == Http session
    @GetMapping("/myprofile")
    public String getHomePage(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            SiteUser foundUser = siteUserRepo.findByUsername(username);
            m.addAttribute("username", username);
            m.addAttribute("fName", foundUser.getFirstName());
            m.addAttribute("lName", foundUser.getLastName());
            m.addAttribute("role", foundUser.getRole());
            m.addAttribute("id", foundUser.getId());
        }
        return "profile";
    }
    @GetMapping("/")
    public String greet(Model m, Principal p){
        if (p != null) {
            String username = p.getName();
            SiteUser foundUser = siteUserRepo.findByUsername(username);
            m.addAttribute("username", username);
        }
        return "dashboard";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String role) {
        // hash the PW
        String hashedPW = passwordEncoder.encode(password);
        // create new user
        SiteUser newUser = new SiteUser(username, hashedPW, firstName, lastName, role);
        // save the user
        siteUserRepo.save(newUser);
        // auto login -> httpServletRequest
        authWithHttpServletRequest(username, password); //automagic security below
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) { //alex wrote it
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    // if secret -> authenticated then GET -> /sauce return html
    @GetMapping("/secret")
    public String getSauce() {
        return "secret";
    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        SiteUser loggedUser = siteUserRepo.findByUsername(p.getName());
        m.addAttribute("loggedUsername", loggedUser.getUsername());

        SiteUser targetUser = siteUserRepo.findById(id).orElseThrow(); //chain "orElseThrow() since find by id is optional and may NOT yield a result, spring yells
        m.addAttribute( "targetUsername",targetUser.getUsername());
        m.addAttribute("targetUserID",targetUser.getId());
        m.addAttribute("targetFirstName",targetUser.getFirstName());
        m.addAttribute("targetRole",targetUser.getRole());
        m.addAttribute("targetLastName",targetUser.getLastName());

        return "user-info";
    }
    @PutMapping("/users/{id}")
    public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username, String firstName, String lastName, String role, RedirectAttributes redir) throws ServletException {
        SiteUser userToBeEdited = siteUserRepo.findById(id).orElseThrow();
        if(p.getName().equals(userToBeEdited.getUsername())){
            userToBeEdited.setUsername(username);
            userToBeEdited.setFirstName(firstName);
            userToBeEdited.setLastName(lastName);
            userToBeEdited.setRole(role);
            siteUserRepo.save(userToBeEdited);
//            request.logout();
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another user's info");
        }
        return new RedirectView("/users/" + id);
    }


}
