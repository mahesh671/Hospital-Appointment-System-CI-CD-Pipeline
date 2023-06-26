package spring.orm.model.input;

import java.time.LocalTime;
import java.util.List;

public class DoctorInput {
	private String docname;
	private String docspec;
	private String docqual;
	private int docexp;
	private List<String> weekday;
	private LocalTime docfrom;
	private LocalTime docto;
	private int docavgtime;
	private double docfee;
	// private byte[] docphoto;
	//
	// public byte[] getDocphoto() {
	// return docphoto;
	// }
	//
	// public void setDocphoto(byte[] docphoto) {
	// this.docphoto = docphoto;
	// }

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getDocspec() {
		return docspec;
	}

	public void setDocspec(String docspec) {
		this.docspec = docspec;
	}

	public String getDocqual() {
		return docqual;
	}

	public void setDocqual(String docqual) {
		this.docqual = docqual;
	}

	public int getDocexp() {
		return docexp;
	}

	public void setDocexp(int docexp) {
		this.docexp = docexp;
	}

	public List<String> getWeekday() {
		return weekday;
	}

	public void setWeekday(List<String> weekday) {
		this.weekday = weekday;
	}

	public LocalTime getDocfrom() {
		return docfrom;
	}

	public void setDocfrom(String docfrom) {
		this.docfrom = LocalTime.parse(docfrom);
		;
	}

	public LocalTime getDocto() {
		return docto;
	}

	public void setDocto(String docto) {
		this.docto = LocalTime.parse(docto);
		;
	}

	public int getDocavgtime() {
		return docavgtime;
	}

	public void setDocavgtime(int docavgtime) {
		this.docavgtime = docavgtime;
	}

	public double getDocfee() {
		return docfee;
	}

	public void setDocfee(double docfee) {
		this.docfee = docfee;
	}

	public DoctorInput() {
		// TODO Auto-generated constructor stub
	}

	public DoctorInput(String docname, String docspec, String docqual, int docexp, List<String> weekday, String docfrom,
			String docto, int docavgtime, double docfee) {
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
				+ docavgtime + ", docfee=" + docfee + "]";
	}

}
