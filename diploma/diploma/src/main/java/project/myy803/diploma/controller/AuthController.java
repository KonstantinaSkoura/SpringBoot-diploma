package project.myy803.diploma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import project.myy803.diploma.model.Professor;
import project.myy803.diploma.model.Role;
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.User;
import project.myy803.diploma.service.UserService;

@Controller
public class AuthController {
	
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
                
        if (user.getRole() == Role.STUDENT) {
        	Student student = new Student();
        	user.setStudent(student);
        } else if(user.getRole() == Role.PROFESSOR) {
        	Professor professor = new Professor();
        	user.setProfessor(professor);
        }
        
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}
