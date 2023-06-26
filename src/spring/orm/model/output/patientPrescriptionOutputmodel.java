package spring.orm.model.output;



public class patientPrescriptionOutputmodel {

	private String lastConsultationDate;

	private String prescriptionImage;

	private String nextAppointmentDate;

	private String doc_name;

	public patientPrescriptionOutputmodel(String lastConsultationDate, String prescriptionImage,
			String nextAppointmentDate, String doc_name) {

		this.lastConsultationDate = lastConsultationDate;
		this.prescriptionImage = prescriptionImage;
		this.nextAppointmentDate = nextAppointmentDate;
		this.doc_name = doc_name;
	}

	public String getLastConsultationDate() {
		return lastConsultationDate;
	}

	public void setLastConsultationDate(String lastConsultationDate) {
		this.lastConsultationDate = lastConsultationDate;
	}

	public String getPrescriptionImage() {
		return prescriptionImage;
	}

	public void setPrescriptionImage(String prescriptionImage) {
		this.prescriptionImage = prescriptionImage;
	}

	public String getNextAppointmentDate() {
		return nextAppointmentDate;
	}

	public void setNextAppointmentDate(String nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public patientPrescriptionOutputmodel() {
		super();
	}

}
