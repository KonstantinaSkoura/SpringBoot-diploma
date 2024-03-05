package project.myy803.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myy803.diploma.dao.ApplicationDAO;
import project.myy803.diploma.dao.StudentDAO;
import project.myy803.diploma.dao.SubjectDAO;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Student;
import project.myy803.diploma.model.Subject;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDAO studentRepository;
	
	@Autowired
	private ApplicationDAO applicationRepository;
	
	@Autowired
	private SubjectDAO subjectRepository;

	@Override
	@Transactional
	public void save(Student student) {
		studentRepository.save(student);
	}
	
	@Override
	@Transactional
	public Application findBySubjectIdAndStudentId(int subjectId, int studentId) {
		return applicationRepository.findBySubjectIdAndStudentId(subjectId, studentId);
	}
	
	@Override
	@Transactional
	public void applyToSubject(Student student, int subjectId) {
		Subject subject = subjectRepository.findById(subjectId);
		Application application = new Application(student, subject);
		applicationRepository.save(application);
	}
}
