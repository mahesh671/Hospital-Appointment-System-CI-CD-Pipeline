package spring.orm.model.output;

public class OutputDoctorProfit {
    private String doctorName;
    private double totalProfit;

    public OutputDoctorProfit(String doctorName, double totalProfit) {
        this.doctorName = doctorName;
        this.totalProfit = totalProfit;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public OutputDoctorProfit() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OutputDoctorProfit [doctorName=" + doctorName + ", totalProfit=" + totalProfit + "]";
	}

	public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    // Optional: Override toString() method if needed
    
    // Optional: Add any additional methods or fields as needed
}

