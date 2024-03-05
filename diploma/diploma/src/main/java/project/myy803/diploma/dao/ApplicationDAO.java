package project.myy803.diploma.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.myy803.diploma.model.Application;

@Repository
public interface ApplicationDAO extends JpaRepository<Application, Integer> {
	public Application findBySubjectIdAndStudentId(int subjectId, int studentId);
	public Application findById(int id);
	public List<Application> findBySubjectIdAndStudentThesisIsNull(int subjectId);
}
