package spring.orm.contract;

import java.util.List;

import spring.orm.model.DiagnosticBillModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.OutputPatientTestReports;

public interface DiagnosticBillDAO {

	public void booktestt(BillInputModel bi);

	public List<DiagnosticBillModel> getdbilldetails();

	public List<Object> gettotalbills(int patient);

	public int storedb(int patient);

	public List<OutputPatientTestReports> gettestdate(String date1, String date2, int i);

}