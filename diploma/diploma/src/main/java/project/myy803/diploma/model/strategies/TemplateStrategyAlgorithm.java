package project.myy803.diploma.model.strategies;

import java.util.List;
import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.StrategyThreshold;
import project.myy803.diploma.model.Student;

public abstract class TemplateStrategyAlgorithm implements BestApplicantStrategy {
	protected StrategyThreshold strategyThreshold;
	
	public TemplateStrategyAlgorithm() {
		
	}
	
	public TemplateStrategyAlgorithm(StrategyThreshold strategyThreshold) {
		this.strategyThreshold = strategyThreshold;
	}
	
	public Student findBestApplicant(List<Application> applications) {
		if (applications.size() == 0) {
			return null;
		} 
		else if (applications.size() == 1) {
			if(strategyThreshold != null && strategyThreshold.getStrategy().equals("criteria")) {
				int selected = compareApplications(applications.get(0), null);
				if (selected == 0) {
					return applications.get(0).getStudent();
				} else {
					return null;
				}
			} else {
				return applications.get(0).getStudent();
			}
		} 
		else {
			Application application = applications.get(0);
			
			for (int i = 1; i < applications.size(); i++) {
				int selected = compareApplications(application, applications.get(i));
				
				if (selected == 1) {
					application = applications.get(i);
				} else if (selected == 2) {
					application = null;
				}
			}
			
			if (application != null) {
				return application.getStudent();
			} else {
				return null;
			}
		}
	}
	
	public abstract int compareApplications(Application firstApplication, Application secondApplication);
}
