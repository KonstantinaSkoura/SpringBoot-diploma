package project.myy803.diploma.model.strategies;

import project.myy803.diploma.model.Application;

public class FewestCoursesStrategy extends TemplateStrategyAlgorithm {

	public int compareApplications(Application firstApplication, Application secondApplication) {
		if (secondApplication.getStudent().getRemainingCourses() <= firstApplication.getStudent().getRemainingCourses()) {
			return 1;
		} else {
			return 0;
		}
	}
}
