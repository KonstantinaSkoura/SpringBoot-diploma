package project.myy803.diploma.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.Subject;
import project.myy803.diploma.model.User;
import project.myy803.diploma.service.StudentService;
import project.myy803.diploma.service.SubjectService;
import project.myy803.diploma.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping("/home")
    public String getUserHome(){		
        return "student/home";
    }
	
	@RequestMapping("/personalInformation")
	public String getPersonalInformation(Model model){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		model.addAttribute("student", user.getStudent());
		
		return "student/personal-information";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		model.addAttribute("student", user.getStudent());
		return "student/update-form";
	}
	
	@RequestMapping("/save")
	public String saveStudent(@ModelAttribute("student") Student student){
		studentService.save(student);
		
		return "redirect:/student/personalInformation";
	}
	
	@RequestMapping("/subjects")
	public String getSubjects(Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		if(user.getStudent().getThesis() == null) {
			List<Subject> subjects = subjectService.findByThesisIsNull();
			model.addAttribute("subjects", subjects);
			
			return "student/list-subjects";
		}else {
			model.addAttribute("thesis", user.getStudent().getThesis());
			return "student/thesis";
		}
	}
	
	@RequestMapping("/subjectDetails")
	public String getSubjectDetails(@RequestParam("subjectId") int subjectId, Model model){
		Subject subject = subjectService.findById(subjectId);
		
		model.addAttribute("subject", subject);
		return "student/subject-details";
	}
	
	@RequestMapping("/apply")
	public String applyForSubject(@RequestParam("subjectId") int subjectId, Model model){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		Application application = studentService.findBySubjectIdAndStudentId(subjectId, user.getStudent().getId());
		
		if (application != null) {
			model.addAttribute("dangerMessage", "You have already applied to this diploma thesis subject");
			return "student/message";
		} else {
			studentService.applyToSubject(user.getStudent(), subjectId);
			
			model.addAttribute("primaryMessage", "You have successfully applied to the diploma thesis subject");
			return "student/message";
		}		
	}
}
