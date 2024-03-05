package project.myy803.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.myy803.diploma.model.Professor;

@Repository
public interface ProfessorDAO extends JpaRepository<Professor, Integer> {

}
