package project.myy803.diploma.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.myy803.diploma.model.Subject;

@Repository
public interface SubjectDAO extends JpaRepository<Subject, Integer>{
	public Subject findById(int id);
	public List<Subject> findByThesisIsNull();
	public List<Subject> findByProfessorIdAndThesisNotNull(int professorId);
	public List<Subject> findByProfessorIdAndThesisIsNull(int professorId);
}
