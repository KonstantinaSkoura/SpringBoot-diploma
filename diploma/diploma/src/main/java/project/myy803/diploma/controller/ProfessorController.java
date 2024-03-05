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
import project.myy803.diploma.model.Professor;
import project.myy803.diploma.model.StrategyThreshold;
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.Subject;
import project.myy803.diploma.model.Thesis;
import project.myy803.diploma.model.User;
import project.myy803.diploma.model.strategies.BestApplicantStrategy;
import project.myy803.diploma.model.strategies.BestApplicantStrategyFactory;
import project.myy803.diploma.service.ProfessorService;
import project.myy803.diploma.service.SubjectService;
import project.myy803.diploma.service.UserService;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping("/home")
    public String getProfessorHome() {		
        return "professor/home";
    }
	
	@RequestMapping("/personalInformation")
	public String getPersonalInformation(Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		model.addAttribute("professor", user.getProfessor());
		
		return "professor/personal-information";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		model.addAttribute("professor", user.getProfessor());
		
		return "professor/update-form";
	}
	
	@RequestMapping("/save")
	public String saveProfessor(@ModelAttribute("professor") Professor professor){
		professorService.save(professor);
		
		return "redirect:/professor/personalInformation";
	}
	
	@RequestMapping("/subjects")
    public String getSubjects(@RequestParam("available") boolean available, Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		List<Subject> subjects = subjectService.findByProfessorIdAndThesisAvailable(user.getProfessor().getId(), available);
		
		model.addAttribute("available", available);
		model.addAttribute("subjects", subjects);
        return "professor/list-subjects";
    }
	
	@RequestMapping("/showFormForAddSubject")
	public String showFormForAddSubject(Model model) {
		Subject subject = new Subject();
		
		model.addAttribute("add", true);
		model.addAttribute("subject", subject);
		return "professor/subject-form";
	}
	
	@RequestMapping("/showFormForUpdateSubject")
	public String showFormForUpdateSubject(@RequestParam("subjectId") int id, Model model) {
		Subject subject = subjectService.findById(id);
		
		model.addAttribute("add", false);
		model.addAttribute("subject", subject);
		return "professor/subject-form";			
	}
	
	@RequestMapping("/saveSubject")
	public String saveSubject(@ModelAttribute("subject") Subject subject){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		subject.setProfessor(user.getProfessor());
		
		subjectService.save(subject);
		
		return "redirect:/professor/subjects?available=true";
	}
	
	@RequestMapping("/deleteSubject")
	public String deleteSubject(@RequestParam("subjectId") int id) {
		subjectService.deleteById(id);

		return "redirect:/professor/subjects?available=true";
	}
	
	@RequestMapping("/getApplications")
	public String getApplications(@RequestParam("subjectId") int subjectId, Model model) {
		List<Application> applications = professorService.findApplicationsBySubjectIdAndStudentThesisIsNull(subjectId);
		
		model.addAttribute("applications", applications);
		model.addAttribute("subjectId", subjectId);

		return "professor/list-applications";
	}
	
	@RequestMapping("/studentPersonalInformation")
	public String getStudentPersonalInformation(@RequestParam("applicationId") int applicationId, Model model) {
		Application application = professorService.findApplicationById(applicationId);
		
		model.addAttribute("student", application.getStudent());
		model.addAttribute("subjectId", application.getSubject().getId());
		
		return "professor/personal-information-student";
	}
	
	@RequestMapping("/assignDirectDiplomaThesisSubject")
	public String assignDirectDiplomaThesisSubject(@RequestParam("applicationId") int applicationId, Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		Application application = professorService.findApplicationById(applicationId);
		
		Thesis thesis = new Thesis(application.getStudent(), application.getSubject(), user.getProfessor(), false);
		
		professorService.saveThesis(thesis);
		
		model.addAttribute("primaryMessage", "You have assigned the diploma thesis subject to " + application.getStudent().getName() + " " + application.getStudent().getSurname());
		model.addAttribute("path", "/professor/theses?archived=false");
		
		return "professor/message";
	}
	
	@RequestMapping("/showFormForSelectStrategy")
	public String showFormForSelectStrategy(@RequestParam("subjectId") int subjectId, Model model) {
		StrategyThreshold strategyThreshold = new StrategyThreshold();
		strategyThreshold.setSubjectId(subjectId);
		
		model.addAttribute("strategyThreshold", strategyThreshold);

		return "professor/select-strategy";
	}
	
	@RequestMapping("/assignDiplomaThesisSubject")
	public String assignDiplomaThesisSubject(@ModelAttribute("strategyThreshold") StrategyThreshold strategyThreshold, Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		BestApplicantStrategy strategy = BestApplicantStrategyFactory.createStrategy(strategyThreshold);
		
		List<Application> applications = professorService.findApplicationsBySubjectIdAndStudentThesisIsNull(strategyThreshold.getSubjectId());
		Student student = strategy.findBestApplicant(applications);
		Subject subject = subjectService.findById(strategyThreshold.getSubjectId());
		
		if (student != null) {
			Thesis thesis = new Thesis(student, subject, user.getProfessor(), false);
			professorService.saveThesis(thesis);
			
			model.addAttribute("primaryMessage", "You have assigned the diploma thesis subject to " + student.getName() + " " + student.getSurname());
			model.addAttribute("path", "/professor/theses?archived=false");
			
			return "professor/message";
		} else {
			model.addAttribute("dangerMessage", "There are no students to assign this diploma thesis subject");
			model.addAttribute("path", "/professor/subjects?available=true");
			
			return "professor/message";
		}
	}

	@RequestMapping("/theses")
	public String assignDiplomaThesisSubject(@RequestParam("archived") boolean archived, Model model) {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUsername(currentPrincipalName);
		
		List<Thesis> theses = professorService.findThesesByArchivedAndProfessorId(archived, user.getProfessor().getId());
		model.addAttribute("theses", theses);
		
		if (archived == false) {
			return "professor/list-theses";
		} else {
			return "professor/list-theses-archived";
		}
	}
	
	@RequestMapping("/showFormForThesisGrades")
	public String showFormForThesisGrades(@RequestParam("thesisId") int thesisId, Model model) {
		Thesis thesis = professorService.findThesisById(thesisId);
		
		model.addAttribute("thesis", thesis);
		return "professor/thesis-form";			
	}

	@RequestMapping("/SaveGradesOfThesis")
	public String SaveGradesOfThesis(@ModelAttribute("thesis") Thesis thesis, Model model){
		professorService.saveGradesOfThesis(thesis);		
		
		model.addAttribute("primaryMessage", "You have set the grades of the thesis");
		model.addAttribute("path", "/professor/theses?archived=true");
		
		return "professor/message";
	}
}
