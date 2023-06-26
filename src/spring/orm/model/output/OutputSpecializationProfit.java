package spring.orm.model.output;

public class OutputSpecializationProfit {
	private String id;
    private String specializationTitle;
    private double totalProfit;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecializationTitle() {
		return specializationTitle;
	}
	public void setSpecializationTitle(String specializationTitle) {
		this.specializationTitle = specializationTitle;
	}
	public double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public OutputSpecializationProfit(String id, String specializationTitle, double totalProfit) {
		super();
		this.id = id;
		this.specializationTitle = specializationTitle;
		this.totalProfit = totalProfit;
	}
	public OutputSpecializationProfit() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OutputSpecializationProfit [id=" + id + ", specializationTitle=" + specializationTitle
				+ ", totalProfit=" + totalProfit + "]";
	}

    }

