package spring.orm.contract.services;

import java.util.List;

import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientlastvisitOutput;

public interface PatientService {

	/**
	 * Retrieves the appointment tests for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointment tests.
	 */
	List<Object> getTestsDetails(int p);

	/**
	 * Retrieves the appointments for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointments.
	 */
	List<Object> getAppointmentsDetails(int p);

	/**
	 * Retrieves the parameter groups with their corresponding parameters.
	 *
	 * @param p The patient ID.
	 * @return A list of ParaGroupOutput objects representing parameter groups.
	 */
	List<ParaGroupOutput> getParamterValues(int p);

	/**
	 * Retrieves the last visit information for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of PatientlastvisitOutput objects representing last visit information.
	 */
	List<PatientlastvisitOutput> getPatientLastVisit(int p);

	/**
	 * Retrieves the appointment test cards for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointment test cards.
	 */
	List<Object> getAppointmentTestCounts(int p);

}