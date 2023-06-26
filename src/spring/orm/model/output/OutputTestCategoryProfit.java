

package spring.orm.model.output;

public class OutputTestCategoryProfit {
	
	private String test_category;
	
	private long profits;
	
	public OutputTestCategoryProfit() {
		
	}

	public OutputTestCategoryProfit(String test_category, long profits) {
		super();
		this.test_category = test_category;
		this.profits = profits;
	}

	public String getTest_category() {
		return test_category;
	}

	public void setTest_category(String test_category) {
		this.test_category = test_category;
	}

	public float getProfits() {
		return profits;
	}

	public void setProfits(long profits) {
		this.profits = profits;
	}
	
	

}
