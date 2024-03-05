package project.myy803.diploma.model.strategies;

import project.myy803.diploma.model.Application;

public class BestAvgGradeStrategy extends TemplateStrategyAlgorithm {

	public int compareApplications(Application firstApplication, Application secondApplication) {
		if (secondApplication.getStudent().getAvgGrade() >= firstApplication.getStudent().getAvgGrade()) {
			return 1;
		} else {
			return 0;
		}
	}
}
