package spring.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import spring.orm.contract.PatientDao;
import spring.orm.contract.PatientProfileUpdateDAO;
import spring.orm.model.PatientSession;
import spring.orm.model.entity.PatientMedicalProfile;
import spring.orm.model.output.PrescriptionOutputmodel;
import spring.orm.model.output.patientPrescriptionOutputmodel;

@Controller
public class PostConsultUpdateController {

	@Autowired
	private PatientDao pdao;

	@Autowired
	private PatientProfileUpdateDAO pcudao;

	@RequestMapping(value = "patient/getParaGroup", method = RequestMethod.GET)
	public ResponseEntity<String> getParaGroup(@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<PatientMedicalProfile> lo = pdao.getParaGroup(p);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
		// return "patient/myprofile";
	}

	@RequestMapping(value = "patient/getPrescription", method = RequestMethod.GET)
	public ResponseEntity<String> getPrescription(@SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<patientPrescriptionOutputmodel> lo = pdao.getPrescription(p);
		return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(lo));
		// return "patient/myprofile";
	}

	@RequestMapping(value = "patient/getallPrescription", method = RequestMethod.GET)
	public String getallPrescription(Model model, @SessionAttribute("patientSession") PatientSession patientSession) {
		int p = patientSession.getId();
		List<PrescriptionOutputmodel> lm = pcudao.getallPrescription(p);
		model.addAttribute("pres", lm);
		System.out.println(lm.toString());
		return "patient/patpresdisplay";
	}

}
