package spring.orm.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.contract.AppointmentDao;
import spring.orm.contract.DocScheduleDao;
import spring.orm.contract.DoctorsDaoTemp;
import spring.orm.contract.SpecializationDao;
import spring.orm.model.DoctorSchedule;
import spring.orm.model.Specialization;
import spring.orm.model.entity.DoctorTemp;
import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;

@Component
public class DoctorOutputService {

	@Autowired
	private DocScheduleDao docschedao;

	@Autowired
	private SpecializationDao specdao;

	@Autowired
	private DoctorsDaoTemp doctdao;

	@Autowired
	private AppointmentDao appdao;

	public List<DoctorList> getAllDocBySpecDate(String specialization, Date appointmentDated) {
		// TODO Auto-generated method stub
		List<DoctorList> docspec = new ArrayList<>();
		Map<Integer, String> daymap = new HashMap<>();
		daymap.put(1, "M");
		daymap.put(2, "T");
		daymap.put(3, "W");
		daymap.put(4, "H");

		daymap.put(5, "F");
		daymap.put(6, "S");
		daymap.put(0, "U");
		System.out.println("function called" + specialization + appointmentDated + docspec);
		String like = daymap.get(appointmentDated.getDay());
		for (DoctorList d : doctdao.getallDocScheduleBySpec(specialization, like)) {

			docspec.add(d);

		}

		return docspec;
	}

	public DoctorOutPutModel getDocbyID(int id) {
		// TODO Auto-generated method stub
		return doctdao.getDocById(id);
	}

	public List<String> getDocTimeSlots(int id, String date) {
		DoctorSchedule s = docschedao.getSchedulebyId(id);
		List<String> slots = generateTimeSlots(s.getTimeFrom(), s.getTimeTo(), s.getAverageAppointmentTime(), date, id);
		return slots;
	}

	public List<String> generateTimeSlots(String fromTime, String toTime, int avgTime, String date, int id) {
		List<String> timeSlots = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		DateTimeFormatter sqlformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter outformat = DateTimeFormatter.ofPattern("hh:mm a");
		LocalTime startTime = LocalTime.parse(fromTime, formatter);
		LocalTime endTime = LocalTime.parse(toTime, formatter);

		while (startTime.isBefore(endTime)) {
			if (!appdao.isSlotBooked(id, date, startTime.format(sqlformat))) {
				timeSlots.add(startTime.format(outformat));
			}
			startTime = startTime.plusMinutes(avgTime);
		}

		return timeSlots;
	}

	public int addDoc(DoctorInput d, CommonsMultipartFile pic) {
		DoctorTemp dt = new DoctorTemp();
		Specialization s = specdao.getSpecialization(d.getDocspec());
		dt.setDoctCfee(d.getDocfee());
		dt.setDoctExp(d.getDocexp());
		dt.setDoctPhoto(pic.getBytes());
		dt.setDoctQual(d.getDocqual());
		dt.setDoctName(d.getDocname());
		dt.setSpecialization(s);
		doctdao.saveDoc(dt);
		return dt.getDoctId();

	}

	@Transactional
	public int updateDoc(DoctorUpdateModel d, CommonsMultipartFile docphoto) {
		// TODO Auto-generated method stub
		DoctorTemp dt = doctdao.getdoc(d.getDoc_id());
		if (d.getDocspec() != null) {
			Specialization s = specdao.getSpecialization(d.getDocspec());
			dt.setSpecialization(s);

		}
		if (d.getDocfee() != null) {
			dt.setDoctCfee(d.getDocfee());
		}
		if (d.getDocexp() != null) {
			dt.setDoctExp(d.getDocexp());
		}
		if (docphoto.getBytes() != null) {
			dt.setDoctPhoto(docphoto.getBytes());
		}
		if (d.getDocqual() != null) {
			dt.setDoctQual(d.getDocqual());

		}
		if (d.getDocname() != null) {
			dt.setDoctName(d.getDocname());

		}
		// ds.setTimeFrom();
		doctdao.updatedoc(dt);
		docschedao.updateSchedule(d);
		return dt.getDoctId();
	}
}
