package project.myy803.diploma.model;

public class StrategyThreshold {
	private String strategy;
	
	private double Th1;
	
	private double Th2;
	
	private int subjectId;
	
	public StrategyThreshold() {
		
	}
	
	public StrategyThreshold(String strategy, double Th1, double Th2, int subjectId) {
		this.strategy = strategy;
		this.Th1 = Th1;
		this.Th2 = Th2;
		this.subjectId = subjectId;
	}
	
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public double getTh1() {
		return Th1;
	}

	public void setTh1(double Th1) {
		this.Th1 = Th1;
	}

	public double getTh2() {
		return Th2;
	}

	public void setTh2(double Th2) {
		this.Th2 = Th2;
	}
	
	public int getSubjectId() {
		return subjectId;
	}
	
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	@Override
	public String toString() {
		return "Strategy [strategy=" + strategy + ", Th1=" + Th1 + ", Th2=" + Th2 +  ", SubjectId=" + subjectId + "]";
	}
}
