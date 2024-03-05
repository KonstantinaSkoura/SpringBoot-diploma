package project.myy803.diploma.service;

import java.util.List;
import project.myy803.diploma.model.Subject;

public interface SubjectService {
	public Subject findById(int id);
	public List<Subject> findByThesisIsNull();
	public List<Subject> findByProfessorIdAndThesisAvailable(int professorId, boolean available);
	public void save(Subject subject);
	public void deleteById(int id);
}
