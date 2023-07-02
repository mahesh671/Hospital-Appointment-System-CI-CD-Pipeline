package spring.orm.contract.DAO;

import java.util.List;

import spring.orm.model.DiagnosticBillModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.OutputPatientTestReports;

public interface DiagnosticBillDAO {

	public void bookDcTest(BillInputModel bi);

	public List<DiagnosticBillModel> getdbilldetails();

	public List<Object> getTotalBills(int patient);

	public int storeToDatabase(int patient);

	public List<OutputPatientTestReports> gettestdate(String date1, String date2, int i);

}