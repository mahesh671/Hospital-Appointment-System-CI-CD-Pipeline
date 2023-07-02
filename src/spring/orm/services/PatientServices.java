package spring.orm.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.orm.contract.DAO.PatientDAO;
import spring.orm.contract.services.PatientService;
import spring.orm.model.output.ParaGroupOutput;
import spring.orm.model.output.PatientlastvisitOutput;

@Service
public class PatientServices implements PatientService {
	@Autowired
	PatientDAO patientDAO;
	private static final Logger logger = LoggerFactory.getLogger(PatientServices.class);

	/**
	 * Retrieves the appointment tests for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointment tests.
	 */
	@Override
	public List<Object> getTestsDetails(int p) {
		// TODO: Implement the logic to retrieve appointment tests
		logger.info("Services Method to display tests in dashboard is called");
		return patientDAO.getPatientTestsById(p);
	}

	/**
	 * Retrieves the appointments for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointments.
	 */
	@Override
	public List<Object> getAppointmentsDetails(int p) {
		// TODO: Implement the logic to retrieve appointments using the patient ID
		logger.info("Services Method to display appointments in dashboard is called");
		return patientDAO.getAppointmentsById(p);
	}

	/**
	 * Retrieves the parameter groups with their corresponding parameters.
	 *
	 * @param p The patient ID.
	 * @return A list of ParaGroupOutput objects representing parameter groups.
	 */
	@Override
	public List<ParaGroupOutput> getParamterValues(int p) {
		// TODO: Implement the logic to retrieve parameter groups with their parameters
		logger.info("Services Method to display parameter values for chart in dashboard is called");
		return patientDAO.getParameterValues(p);
	}

	/**
	 * Retrieves the last visit information for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of PatientlastvisitOutput objects representing last visit information.
	 */
	@Override
	public List<PatientlastvisitOutput> getPatientLastVisit(int p) {
		// TODO: Implement the logic to retrieve last visit information using the patient ID
		logger.info("Services Method to display patient Last Visit details in dashboard is called");
		return patientDAO.getLastAppointmentInfoById(p);
	}

	/**
	 * Retrieves the appointment test cards for the specified patient.
	 *
	 * @param p The patient ID.
	 * @return A list of appointment test cards.
	 */
	@Override
	public List<Object> getAppointmentTestCounts(int p) {
		// TODO: Implement the logic to retrieve appointment test cards using the patient ID
		logger.info("Services Method to display appointments and tests counts  in dashboard is called");
		return patientDAO.getAppointmentTestsCount(p);
	}

}