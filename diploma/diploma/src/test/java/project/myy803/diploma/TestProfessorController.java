package project.myy803.diploma;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Professor;
import project.myy803.diploma.model.StrategyThreshold;
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.Subject;
import project.myy803.diploma.model.Thesis;
import project.myy803.diploma.model.User;
import project.myy803.diploma.service.ProfessorService;
import project.myy803.diploma.service.SubjectService;
import project.myy803.diploma.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class TestProfessorController {
	private User user;
	private Student student;
	private Professor professor;
	private Subject subject;
	private Application application;
	private Thesis thesis;
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	ProfessorService professorService;
	
	@MockBean
	SubjectService subjectService;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		createUser();
    }
	
	private void createUser() {
		user = new User();
	    user.setId(1);
	    user.setUsername("konstantina");
	    user.setPassword("k0nstrantInaa!");
	    
	    student = new Student(1, "Georgios", "Touralias", 6, 7, 11, null, null);
	    
	    ArrayList<Application> applications = new ArrayList<Application>();
	    
	    professor = new Professor(1, "Konstantina", "Skoura", "AI Specialty");
	    thesis = new Thesis();
	    thesis.setProfessor(professor);
	    thesis.setStudent(student);
	    thesis.setArchived(false);
	    student.setThesis(thesis);
	    
	    subject = new Subject(1, "Prevent hackers", "Research for prevent hackers", professor, applications, thesis);
	    thesis.setSubject(subject);
	    
	    application = new Application(1, student, subject);
	    applications.add(application);
	    
	    student.setApplications(applications);
	    
	    user.setProfessor(professor);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@Test
	void testUserServiceIsNotNull() {
		Assertions.assertNotNull(userService);
	}
	
	@Test
	void testProfessorServiceIsNotNull() {
		Assertions.assertNotNull(professorService);
	}
	
	@Test
	void testSubjectServiceIsNotNull() {
		Assertions.assertNotNull(subjectService);
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testHomeReturnsPage() throws Exception {
		mockMvc.perform(get("/professor/home")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/home"));
	}

	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testPersonalInformationReturnsPage() throws Exception {
	    Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
	    	    
	    mockMvc.perform(get("/professor/personalInformation")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/personal-information"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testShowFormForUpdateReturnsPage() throws Exception {
	    Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
	    	    
	    mockMvc.perform(get("/professor/showFormForUpdate")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/update-form"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testSaveReturnsPage() throws Exception {
		Mockito.doNothing().when(professorService).save(professor);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(professor.getId()));
	    multiValueMap.add("name", professor.getName());
	    multiValueMap.add("surname", professor.getSurname());
	    multiValueMap.add("specialty", professor.getSpecialty());
	    
		mockMvc.perform(
				post("/professor/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/personalInformation"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testSubjectsReturnsPage() throws Exception {
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		subjects.add(subject);
		Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
		Mockito.when(subjectService.findByProfessorIdAndThesisAvailable(professor.getId(), false)).thenReturn(subjects);
		
	    mockMvc.perform(get("/professor/subjects")
	    		.param("available", "false")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/list-subjects"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testShowFormForAddSubjectReturnsPage() throws Exception {    	    
	    mockMvc.perform(get("/professor/showFormForAddSubject")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/subject-form"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testShowFormForUpdateSubjectReturnsPage() throws Exception {
		Mockito.when(subjectService.findById(subject.getId())).thenReturn(subject);
	    	    
	    mockMvc.perform(get("/professor/showFormForUpdateSubject").param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/subject-form"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testSaveSubjectReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
		Mockito.doNothing().when(subjectService).save(subject);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(subject.getId()));
	    multiValueMap.add("title", subject.getTitle());
	    multiValueMap.add("objectives", subject.getObjectives());
	    
		mockMvc.perform(
				post("/professor/saveSubject")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/professor/subjects?available=true"));			
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testDeleteSubjectReturnsPage() throws Exception {
		Mockito.doNothing().when(subjectService).deleteById(subject.getId());
	    	    
	    mockMvc.perform(get("/professor/deleteSubject").param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isFound()).
		andExpect(view().name("redirect:/professor/subjects?available=true"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testGetApplicationsReturnsPage() throws Exception {
		ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(application);
		Mockito.when(professorService.findApplicationsBySubjectIdAndStudentThesisIsNull(subject.getId())).thenReturn(applications);
	    	    
	    mockMvc.perform(get("/professor/getApplications").param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/list-applications"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testStudentPersonalInformationReturnsPage() throws Exception {
		Mockito.when(professorService.findApplicationById(application.getId())).thenReturn(application);
	    	    
	    mockMvc.perform(get("/professor/studentPersonalInformation").param("applicationId", Integer.toString(application.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/personal-information-student"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testAssignDirectDiplomaThesisSubjectReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
		Mockito.when(professorService.findApplicationById(application.getId())).thenReturn(application);
		Mockito.doNothing().when(professorService).saveThesis(thesis);
	    	    
	    mockMvc.perform(get("/professor/assignDirectDiplomaThesisSubject").param("applicationId", Integer.toString(application.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/message"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testShowFormForSelectStrategyReturnsPage() throws Exception {	    	    
	    mockMvc.perform(get("/professor/showFormForSelectStrategy").param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/select-strategy"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testAssignDiplomaThesisSubjectReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
		StrategyThreshold strategyThreshold = new StrategyThreshold("random", 8, 5, subject.getId());
		ArrayList<Application> applications = new ArrayList<Application>();
		applications.add(application);
		Mockito.when(professorService.findApplicationsBySubjectIdAndStudentThesisIsNull(subject.getId())).thenReturn(applications);
		Mockito.when(subjectService.findById(subject.getId())).thenReturn(subject);
		Mockito.doNothing().when(professorService).saveThesis(thesis);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("strategy", strategyThreshold.getStrategy());
	    multiValueMap.add("Th1", Double.toString(strategyThreshold.getTh1()));
	    multiValueMap.add("Th2", Double.toString(strategyThreshold.getTh2()));
	    multiValueMap.add("subjectId", Integer.toString(strategyThreshold.getSubjectId()));
	    
		mockMvc.perform(
				post("/professor/assignDiplomaThesisSubject")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/message"));	
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testThesesReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("konstantina")).thenReturn(user);
		ArrayList<Thesis> theses = new ArrayList<Thesis>();
		theses.add(thesis);
		Mockito.when(professorService.findThesesByArchivedAndProfessorId(true, professor.getId())).thenReturn(theses);
	    	    
	    mockMvc.perform(get("/professor/theses").param("archived", "true")).
		andExpect(status().isOk()).
		andExpect(view().name("professor/list-theses-archived"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testShowFormForThesisGradesReturnsPage() throws Exception {
		Mockito.when(professorService.findThesisById(thesis.getId())).thenReturn(thesis);
	    	    
	    mockMvc.perform(get("/professor/showFormForThesisGrades").param("thesisId", Integer.toString(thesis.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("professor/thesis-form"));
	}
	
	@Test
	@WithMockUser(username = "konstantina", password = "k0nstrantInaa!", authorities = { "PROFESSOR" })
	void testSaveGradesOfThesisReturnsPage() throws Exception {
		Mockito.doNothing().when(professorService).saveGradesOfThesis(thesis);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(thesis.getId()));
	    multiValueMap.add("implementationGrade", Double.toString(thesis.getImplementationGrade()));
	    multiValueMap.add("reportGrade", Double.toString(thesis.getReportGrade()));
	    multiValueMap.add("presentationGrade", Double.toString(thesis.getPresentationGrade()));
	    multiValueMap.add("archived", Boolean.toString(thesis.getArchived()));
	    
		mockMvc.perform(
				post("/professor/SaveGradesOfThesis")
			    .params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("professor/message"));	
	}
}
