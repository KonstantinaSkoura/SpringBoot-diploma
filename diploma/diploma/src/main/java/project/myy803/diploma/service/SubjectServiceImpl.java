package project.myy803.diploma.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.myy803.diploma.dao.SubjectDAO;
import project.myy803.diploma.model.Subject;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectDAO subjectRepository;
	
	@Override
	@Transactional
	public Subject findById(int id) {
		Subject subject = subjectRepository.findById(id);
				
		if (subject != null ) {
			return subject;
		}
		else {
			// we didn't find the subject
			throw new RuntimeException("Did not find subject id - " + id);
		}
	}
	
	@Override
	@Transactional
	public List<Subject> findByThesisIsNull() {
		return subjectRepository.findByThesisIsNull();
	}
	
	@Override
	@Transactional
	public List<Subject> findByProfessorIdAndThesisAvailable(int professorId, boolean available) {
		if(available==true) {
			return subjectRepository.findByProfessorIdAndThesisIsNull(professorId);
		}else {
			return subjectRepository.findByProfessorIdAndThesisNotNull(professorId);
		}
	}
	
	@Override
	@Transactional
	public void save(Subject subject) {
		subjectRepository.save(subject);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		subjectRepository.deleteById(id);
	}
}
