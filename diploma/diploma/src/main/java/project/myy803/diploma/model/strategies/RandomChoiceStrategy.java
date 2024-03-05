package project.myy803.diploma.model.strategies;

import java.util.Random;
import project.myy803.diploma.model.Application;

public class RandomChoiceStrategy extends TemplateStrategyAlgorithm {

	public int compareApplications(Application firstApplication, Application secondApplication) {
		Random random = new Random();
		return random.nextInt(2);
	}
}
