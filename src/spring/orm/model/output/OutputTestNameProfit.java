package spring.orm.model.output;
public class OutputTestNameProfit {
	
	private String testname;
	private long profits;
	
	public OutputTestNameProfit() {
		
	}
	
	
	public OutputTestNameProfit(String testname, long profits) {
		super();
		this.testname = testname;
		this.profits = profits;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public float getProfits() {
		return profits;
	}

	public void setProfits(long profits) {
		this.profits = profits;
	}
}