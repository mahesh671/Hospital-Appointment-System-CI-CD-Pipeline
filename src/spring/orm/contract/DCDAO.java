package spring.orm.contract;

import java.util.List;

import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

public interface DCDAO {

	List<OutputTestNameProfit> fetchTestNameWiseProfit();

	List<OutputTestMethodProfit> fetchTestMethodWiseProfit();

	List<OutputTestCategoryProfit> fetchTestCategoryWiseProfit();

	List<OutputTestNameProfit> fetchTestNameDateWiseProfit(String from, String to);

	List<OutputTestMethodProfit> fetchTestMethodDateWiseProfit(String from, String to);

	List<OutputTestCategoryProfit> fetchTestCategoryDateWiseProfit(String from, String to);

	public List<OutputReportData> fetchPatientReportsInfo(int pid);

	public String saveReportInfo(int id, byte[] b);

	public String fetchPatientName(int pid);

	public List<Integer> fetchPatientIds();

}
