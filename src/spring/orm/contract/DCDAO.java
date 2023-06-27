package spring.orm.contract;

import java.util.List;

import spring.orm.model.output.OutputReportData;
import spring.orm.model.output.OutputTestCategoryProfit;
import spring.orm.model.output.OutputTestMethodProfit;
import spring.orm.model.output.OutputTestNameProfit;

public interface DCDAO {

	List<OutputTestNameProfit> fetchTestNameProfit();

	List<OutputTestMethodProfit> fetchTestMethodProfit();

	List<OutputTestCategoryProfit> fetchTestCategoryProfit();

	List<OutputTestNameProfit> fetchTestNameProfitDate(String from, String to);

	List<OutputTestMethodProfit> fetchTestMethodProfitDate(String from, String to);

	List<OutputTestCategoryProfit> fetchTestCategoryProfitDate(String from, String to);

	public List<OutputReportData> fetchPatientInfo(int pid);

	public String saveReportInfo(int id, byte[] b);

	public String fetchPatientName(int pid);

	public List<Integer> fetchPatientReports();

}
