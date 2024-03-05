package project.myy803.diploma.model.strategies;

import java.util.List;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Student;

public interface BestApplicantStrategy {
	public Student findBestApplicant(List<Application> applications);
}
