package spring.orm.model.input;

public class BillInputModel {
	
	private String cat;
	private int test;
	private int price;
	private int patient;
	public BillInputModel( String cat, int test, int price, int patient) {
	
		this.cat = cat;
		this.test = test;
		this.price = price;
		
		this.patient = patient;
		
	}

	public int getPatient() {
		return patient;
	}

	public void setPatient(int patient) {
		this.patient = patient;
	}

	public BillInputModel() {
		// Default constructor
	}

	

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	

	

	@Override
	public String toString() {
		return "BillInputModel [cat=" + cat + ", test=" + test + ", price=" + price + ", patient=" + patient + "]";
	}

	
}