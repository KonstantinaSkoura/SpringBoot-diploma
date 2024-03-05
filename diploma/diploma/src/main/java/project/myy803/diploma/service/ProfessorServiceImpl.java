package project.myy803.diploma.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myy803.diploma.dao.ApplicationDAO;
import project.myy803.diploma.dao.ProfessorDAO;
import project.myy803.diploma.dao.ThesisDAO;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Professor;
import project.myy803.diploma.model.Thesis;

@Service
public class ProfessorServiceImpl implements ProfessorService {
	@Autowired
	private ProfessorDAO professorRepository;
	
	@Autowired
	private ApplicationDAO applicationRepository;
	
	@Autowired
	private ThesisDAO thesisRepository;
	
	@Override
	@Transactional
	public void save(Professor professor) {
		professorRepository.save(professor);
	}
	
	@Override
	@Transactional
	public List<Application> findApplicationsBySubjectIdAndStudentThesisIsNull(int subjectId) {
		return applicationRepository.findBySubjectIdAndStudentThesisIsNull(subjectId);
	}
	
	@Override
	@Transactional
	public Application findApplicationById(int id) {
		return applicationRepository.findById(id);
	}
	
	@Override
	@Transactional
	public List<Thesis> findAllTheses() {
		return thesisRepository.findAll();
	}
	
	@Override
	@Transactional
	public void saveThesis(Thesis thesis) {
		thesisRepository.save(thesis);
	}
	
	@Override
	@Transactional
	public List<Thesis> findThesesByArchivedAndProfessorId(boolean archived, int professorId) {
		return thesisRepository.findByArchivedAndProfessorId(archived, professorId);
	}
	
	@Override
	@Transactional
	public Thesis findThesisById(int thesisId) {
		return thesisRepository.findById(thesisId);
	}
	
	@Override
	@Transactional
	public void saveGradesOfThesis(Thesis thesis) {
		thesis.setArchived(true);
		
		thesisRepository.save(thesis);
	}
}
