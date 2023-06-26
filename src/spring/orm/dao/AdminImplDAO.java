package spring.orm.dao;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import spring.orm.contract.AdminDao;
import spring.orm.model.output.OutputDoctorProfit;
import spring.orm.model.output.OutputSpecializationProfit;
import spring.orm.model.output.OutputTestNameProfit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Component
public class AdminImplDAO implements AdminDao {
	@PersistenceContext
	EntityManager em;
	@Override
	public List<OutputDoctorProfit> fetchDoctorProfit() {
		 String hql = "SELECT new spring.orm.model.output.OutputDoctorProfit(d.doctName, SUM(a.appn_payamount) AS Double) FROM DoctorTemp d ,AppointmentEntity a where a.doctor.doctId=d.doctId GROUP BY d.doctName";
	   

	    List<OutputDoctorProfit> data = em.createQuery(hql, OutputDoctorProfit.class).getResultList();

	    return data;
	}


	@Override
	public List<OutputSpecializationProfit> fetchSpecializationProfit() {
		String hql = "SELECT new spring.orm.model.output.OutputSpecializationProfit(s.id, s.title, SUM(a.appn_payamount)) FROM DoctorTemp d,Specialization s,AppointmentEntity a where s.id=d.specialization.id and a.doctor.doctId=d.doctId group by s.id, s.title";
	             
	             

	List<OutputSpecializationProfit> data = em.createQuery(hql, OutputSpecializationProfit.class).getResultList();

	return data;

	}
	@Override
	public List<OutputDoctorProfit> fetchDoctorProfit(String from, String to) {
		 String hql = "SELECT new spring.orm.model.output.OutputDoctorProfit(d.doctName, SUM(a.appn_payamount) AS Double) FROM DoctorTemp d ,AppointmentEntity a where a.doctor.doctId=d.doctId and  a.appn_sch_date >= :fromDate AND a.appn_sch_date <= :toDate GROUP BY d.doctName";
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date fromDate = null;
		    Date toDate = null;

		    try {
		        fromDate = dateFormat.parse(from);
		        toDate = dateFormat.parse(to);
		    } catch (ParseException e) {
		        // Handle the exception if the date strings are not in the correct format
		    }

	List<OutputDoctorProfit> data = em.createQuery(hql, OutputDoctorProfit.class)
	        .setParameter("fromDate", fromDate)
	        .setParameter("toDate", toDate)
	        .getResultList();

	return data;

	}

	@Override
	public List<OutputSpecializationProfit> fetchSpecializationProfit(String from, String to) {
		String hql = "SELECT new spring.orm.model.output.OutputSpecializationProfit(s.id, s.title, SUM(a.appn_payamount)) FROM DoctorTemp d,Specialization s,AppointmentEntity a where s.id=d.specialization.id and a.doctor.doctId=d.doctId and a.appn_sch_date >= :fromDate AND a.appn_sch_date <= :toDate group by s.id, s.title";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date fromDate = null;
	    Date toDate = null;
		

		    try {
		        fromDate = dateFormat.parse(from);
		        toDate = dateFormat.parse(to);
		    } catch (ParseException e) {
		        // Handle the exception if the date strings are not in the correct format
		    }
	List<OutputSpecializationProfit> data = em.createQuery(hql, OutputSpecializationProfit.class)
	        .setParameter("fromDate", fromDate)
	        .setParameter("toDate", toDate)
	        .getResultList();

	return data;

	}

	

}
