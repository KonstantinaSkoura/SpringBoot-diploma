package project.myy803.diploma.service;

import java.util.List;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Professor;
import project.myy803.diploma.model.Thesis;

public interface ProfessorService {
	public void save(Professor professor);
	public List<Application> findApplicationsBySubjectIdAndStudentThesisIsNull(int subjectId);
	public Application findApplicationById(int id);
	public List<Thesis> findAllTheses();
	public void saveThesis(Thesis thesis);
	public List<Thesis> findThesesByArchivedAndProfessorId(boolean archived, int professorId);
	public Thesis findThesisById(int thesisId);
	public void saveGradesOfThesis(Thesis thesis);
}
