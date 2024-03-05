package project.myy803.diploma.model.strategies;

import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.StrategyThreshold;

public class AverageGradeAndRemainingCoursesStrategy extends TemplateStrategyAlgorithm {

	public AverageGradeAndRemainingCoursesStrategy(StrategyThreshold strategyThreshold) {
		super(strategyThreshold);
	}
	
	public int compareApplications(Application firstApplication, Application secondApplication) {
		boolean foundFirst = false;
		boolean foundSecond = false;
		
		if (firstApplication != null) {
			if (firstApplication.getStudent().getAvgGrade() > strategyThreshold.getTh1() && firstApplication.getStudent().getRemainingCourses() < strategyThreshold.getTh2()) {
				foundFirst = true;
			}
		}
		
		if (secondApplication != null) {
			if (secondApplication.getStudent().getAvgGrade() > strategyThreshold.getTh1() && secondApplication.getStudent().getRemainingCourses() < strategyThreshold.getTh2()) {
				foundSecond = true;
			}
		}
		
		if (foundSecond == true) {
			return 1;
		} else if(foundFirst == true) {
			return 0;
		} else {
			return 2;
		}
	}
}
