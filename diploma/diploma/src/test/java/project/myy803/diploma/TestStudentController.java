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
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.Subject;
import project.myy803.diploma.model.Thesis;
import project.myy803.diploma.model.User;
import project.myy803.diploma.service.StudentService;
import project.myy803.diploma.service.SubjectService;
import project.myy803.diploma.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class TestStudentController {
	private User user;
	private Student student;
	private Professor professor;
	private Subject subject;
	private Application application;
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	StudentService studentService;
	
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
	    user.setUsername("george");
	    user.setPassword("ge0rge!");
	    
	    ArrayList<Application> applications = new ArrayList<Application>();
	    
	    professor = new Professor(1, "Konstantina", "Skoura", "AI Specialty");
	    Thesis thesis = new Thesis();
	    thesis.setProfessor(professor);
	    thesis.setStudent(student);
	    
	    subject = new Subject(1, "Prevent hackers", "Research for prevent hackers", professor, applications, thesis);
	    thesis.setSubject(subject);
	    
	    application = new Application(1, student, subject);
	    
	    student = new Student(1, "Georgios", "Touralias", 6, 7, 11, applications, thesis);
	    user.setStudent(student);
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
	void testStudentServiceIsNotNull() {
		Assertions.assertNotNull(studentService);
	}
	
	@Test
	void testSubjectServiceIsNotNull() {
		Assertions.assertNotNull(subjectService);
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testHomeReturnsPage() throws Exception {
		mockMvc.perform(get("/student/home")).
		andExpect(status().isOk()).
		andExpect(view().name("student/home"));
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testPersonalInformationReturnsPage() throws Exception {
	    Mockito.when(userService.getUserByUsername("george")).thenReturn(user);
	    	    
	    mockMvc.perform(get("/student/personalInformation")).
		andExpect(status().isOk()).
		andExpect(view().name("student/personal-information"));			
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testShowFormForUpdateReturnsPage() throws Exception {
	    Mockito.when(userService.getUserByUsername("george")).thenReturn(user);
	    	    
	    mockMvc.perform(get("/student/showFormForUpdate")).
		andExpect(status().isOk()).
		andExpect(view().name("student/update-form"));			
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testSaveEmployeeReturnsPage() throws Exception {
		Mockito.doNothing().when(studentService).save(user.getStudent());
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("name", student.getName());
	    multiValueMap.add("surname", student.getSurname());
	    multiValueMap.add("yearOfStudies", Integer.toString(student.getYearOfStudies()));
	    multiValueMap.add("avgGrade", Double.toString(student.getAvgGrade()));
	    multiValueMap.add("remainingCourses", Integer.toString(student.getRemainingCourses()));
	    
		mockMvc.perform(
				post("/student/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/student/personalInformation"));			
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testSubjectseReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("george")).thenReturn(user);
		
	    mockMvc.perform(get("/student/subjects")).
		andExpect(status().isOk()).
		andExpect(view().name("student/thesis"));			
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testSubjectDetailsReturnsPage() throws Exception {
		Mockito.when(subjectService.findById(subject.getId())).thenReturn(subject);
		
	    mockMvc.perform(get("/student/subjectDetails")
	    		.param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("student/subject-details"));			
	}
	
	@Test
	@WithMockUser(username = "george", password = "ge0rge!", authorities = { "STUDENT" })
	void testApplyReturnsPage() throws Exception {
		Mockito.when(userService.getUserByUsername("george")).thenReturn(user);
		Mockito.when(studentService.findBySubjectIdAndStudentId(subject.getId(), student.getId())).thenReturn(application);
		
	    mockMvc.perform(get("/student/apply")
	    		.param("subjectId", Integer.toString(subject.getId()))).
		andExpect(status().isOk()).
		andExpect(view().name("student/message"));			
	}
}
