package project.myy803.diploma.model.strategies;

import project.myy803.diploma.model.StrategyThreshold;

public class BestApplicantStrategyFactory {
	
	public static BestApplicantStrategy createStrategy(StrategyThreshold strategyThreshold){
		if (strategyThreshold.getStrategy().equals("random")) {
			return new RandomChoiceStrategy();
		}
		else if (strategyThreshold.getStrategy().equals("avgGrade")) {
			return new BestAvgGradeStrategy();
		}
		else if (strategyThreshold.getStrategy().equals("remainingCourses")) {
			return new FewestCoursesStrategy();
		}
		else if (strategyThreshold.getStrategy().equals("criteria")) {
			return new AverageGradeAndRemainingCoursesStrategy(strategyThreshold);
		} else {
			return null;
		}
	}
}
