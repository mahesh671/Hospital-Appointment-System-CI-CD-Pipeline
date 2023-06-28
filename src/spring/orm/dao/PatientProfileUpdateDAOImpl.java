package spring.orm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.controller.AppointmentController;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.PrescriptionOutputmodel;

@Component
public class PatientProfileUpdateDAOImpl implements PatientProfileUpdateDAO {

	@PersistenceContext
	private EntityManager entitymanager;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	@Transactional
	@Override
	public void save(PatientMedicalProfile patientMedicalProfile) {
		// TODO Auto-generated method stub
		logger.info("PatientMedicalProfile in dcadmin");
		entitymanager.persist(patientMedicalProfile);
	}

	@Override
	
	@Transactional
	public List<PrescriptionOutputmodel> getallPrescription(int id) {

		String hql = "select new spring.orm.model.output.PrescriptionOutputmodel(a.appn_id,a.appn_sch_date,p.patn_parameter,p.patn_pargroup,p.patn_value,p.patn_prescription) "
				+ "from PatientMedicalProfile p,AppointmentEntity a where (p.id.patn_id in(select f.pfmbPatnId from FamilyMembers f where f.patnAccessPatnId=:p) or p.id.patn_id=:p ) and a.pm.patn_id=p.id.patn_id";
		System.out.println(hql); // to show the following attributes , including family members

		List<PrescriptionOutputmodel> prescriptionOutputList = entitymanager.createQuery(hql, spring.orm.model.output.PrescriptionOutputmodel.class) // this
																														// output
																														// model
																														// consists
																														// of
																														// patientmedicalprofile
																														// and
																														// DiagnosticBill
																														// attributes
				.setParameter("p", id).getResultList();
		logger.info("prescription details in patients screen along with prescription image");
		return prescriptionOutputList;
	}

	@Override
	public List<Integer> getAllAppointmentIds(int patn_id) {
		// TODO Auto-generated method stub
		//List<Integer> lp3 = new ArrayList<>();
		List<Integer> listPointer2 = new ArrayList<>(Collections.nCopies(10000, null)); //10000 null values in ArrayList will be initialized
		listPointer2 = entitymanager.createQuery("select a.appn_id "
				+ "from AppointmentEntity a where a.appn_id not in(select pmp.id.patn_appn_id from PatientMedicalProfile pmp where pmp.id.patn_id =: patn_id ) and a.pm.patn_id=:patn_id")
				.setParameter("patn_id", patn_id)

				.getResultList(); // to get the appointment ids that are unique combination of patient &
									// appointment id in PatientMedicalProfile to insert
		System.out.println(listPointer2);
		logger.info("Appointment IDs of unique combination");

		return listPointer2;
	}
}