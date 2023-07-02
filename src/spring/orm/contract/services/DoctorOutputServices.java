package spring.orm.contract.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.model.input.DoctorInput;
import spring.orm.model.input.DoctorUpdateModel;
import spring.orm.model.output.DoctorList;
import spring.orm.model.output.DoctorOutPutModel;

public interface DoctorOutputServices {

	/*
	 * Retrieve a list of doctors with their schedules based on specialization and
	 * appointment date
	 */
	List<DoctorList> getAllDocBySpecDate(String specialization, Date appointmentDated);

	// Retrieve doctor information based on ID
	DoctorOutPutModel getDocbyID(int id);

	// Get available time slots for a doctor on a specific date
	List<String> getDocTimeSlots(int id, String date);

	// Generate time slots based on the doctor's schedule and availability
	List<String> generateTimeSlots(String fromTime, String toTime, int avgTime, String date, int id);

	// Add a new doctor with the provided information
	int addDoc(DoctorInput d, CommonsMultipartFile pic);

	// Update doctor information based on the provided data
	int updateDoctor(DoctorUpdateModel d, CommonsMultipartFile docphoto);

}