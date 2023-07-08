package spring.orm.contract.DAO;

import java.util.List;

import org.postgresql.util.PSQLException;

import spring.orm.model.DiagnosticBillModel;
import spring.orm.model.input.BillInputModel;
import spring.orm.model.output.OutputPatientTestReports;

public interface DiagnosticBillDAO {

	public void bookDcTest(BillInputModel bi) throws Exception;

	public List<DiagnosticBillModel> getDiagnosticBillDetails();

	public List<Object> getTotalBills(int patient);

	public int storeToDatabase(int patient);

	public List<OutputPatientTestReports> gettestdate(String date1, String date2, int i);

	public void cancelTest(int patient, String test);

}