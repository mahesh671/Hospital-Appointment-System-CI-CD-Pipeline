package spring.orm.model.output;

public class OutputTestMethodProfit {
	
	private String testmethod;
	
	private long profit;
	
	public OutputTestMethodProfit() {
		
	}

	public OutputTestMethodProfit(String testmethod, long profit) {
		super();
		this.testmethod = testmethod;
		this.profit = profit;
	}

	public String getTestmethod() {
		return testmethod;
	}

	public void setTestmethod(String testmethod) {
		this.testmethod = testmethod;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}
	
	
	

}
