package spring.orm.model.input;

import java.time.LocalTime;
import java.util.List;

public class DoctorUpdateModel {
	private String docname;
	private String docspec;
	private String docqual;
	private Integer docexp;
	private List<String> weekday;
	private LocalTime docfrom;
	private LocalTime docto;
	private Integer docavgtime;
	private Double docfee;
	private Integer doc_id;
	// private byte[] docphoto;
	//
	// public byte[] getDocphoto() {
	// return docphoto;
	// }
	//
	// public void setDocphoto(byte[] docphoto) {
	// this.docphoto = docphoto;
	// }

	public Integer getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Integer doc_id) {
		this.doc_id = doc_id;
	}

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {

		this.docname = docname;
		System.out.println("in dn");
	}

	public String getDocspec() {
		return docspec;
	}

	public void setDocspec(String docspec) {

		this.docspec = docspec;

		System.out.println("in ds");
	}

	public String getDocqual() {
		return docqual;
	}

	public void setDocqual(String docqual) {
		this.docqual = docqual;

		System.out.println("in dq");
	}

	public Integer getDocexp() {
		return docexp;
	}

	public void setDocexp(Integer docexp) {
		this.docexp = docexp;

		System.out.println("in de");
	}

	public List<String> getWeekday() {
		return weekday;
	}

	public void setWeekday(List<String> weekday) {
		this.weekday = weekday;

		System.out.println("in dsw");
	}

	public LocalTime getDocfrom() {
		return docfrom;
	}

	public void setDocfrom(String docfrom) {
		this.docfrom = LocalTime.parse(docfrom);

		System.out.println("in ddf");
	}

	public LocalTime getDocto() {
		return docto;
	}

	public void setDocto(String docto) {
		this.docto = LocalTime.parse(docto);

		System.out.println("in dto");
	}

	public Integer getDocavgtime() {
		return docavgtime;
	}

	public void setDocavgtime(Integer docavgtime) {
		this.docavgtime = docavgtime;

		System.out.println("in davg");
	}

	public Double getDocfee() {
		return docfee;
	}

	public void setDocfee(Double docfee) {
		this.docfee = docfee;

		System.out.println("in dfee");
	}

	public DoctorUpdateModel() {
		// TODO Auto-generated constructor stub
	}

	public DoctorUpdateModel(String docname, String docspec, String docqual, int docexp, List<String> weekday,
			String docfrom, String docto, int docavgtime, double docfee) {
		super();
		this.docname = docname;
		this.docspec = docspec;
		this.docqual = docqual;
		this.docexp = docexp;
		this.weekday = weekday;
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
		this.docfrom = LocalTime.parse(docfrom);
		this.docto = LocalTime.parse(docto);
		this.docavgtime = docavgtime;
		this.docfee = docfee;
	}

	@Override
	public String toString() {
		return "DoctorInput [docname=" + docname + ", docspec=" + docspec + ", docqual=" + docqual + ", docexp="
				+ docexp + ", weekday=" + weekday + ", docfrom=" + docfrom + ", docto=" + docto + ", docavgtime="
				+ docavgtime + ", docfee=" + docfee + " " + doc_id + "]";
	}

}