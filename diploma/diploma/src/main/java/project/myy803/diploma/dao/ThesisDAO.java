package project.myy803.diploma.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.myy803.diploma.model.Thesis;

@Repository
public interface ThesisDAO extends JpaRepository<Thesis, Integer> {
	public List<Thesis> findByArchivedAndProfessorId(boolean archived, int professorId);
	public Thesis findById(int thesisId);
}
