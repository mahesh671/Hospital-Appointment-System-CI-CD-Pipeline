package spring.orm.model;
import java.time.LocalDate;

public class PatientSession {
    private Integer id;
    private String username;
    private String name;
    private Integer age;
    private Character gender;
    private Integer accessPatientId;
    private String bloodGroup;
    private LocalDate registrationDate;
    private LocalDate lastVisitDate;
    private Integer lastAppointmentId;
    private String Email;

    // Getter and Setter methods for all the fields

    public PatientSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientSession(Integer id, String username, String name, Integer age, Character gender,
			Integer accessPatientId, String bloodGroup, LocalDate registrationDate, LocalDate lastVisitDate,
			Integer lastAppointmentId, String email) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.accessPatientId = accessPatientId;
		this.bloodGroup = bloodGroup;
		this.registrationDate = registrationDate;
		this.lastVisitDate = lastVisitDate;
		this.lastAppointmentId = lastAppointmentId;
		Email = email;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAccessPatientId() {
        return accessPatientId;
    }

    public void setAccessPatientId(Integer accessPatientId) {
        this.accessPatientId = accessPatientId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Integer getLastAppointmentId() {
        return lastAppointmentId;
    }

    public void setLastAppointmentId(Integer lastAppointmentId) {
        this.lastAppointmentId = lastAppointmentId;
    }
}
