package spring.orm.controller;

enum BookingType {
	NEW_PATIENT("NEW_PATIENT"), REGULAR("Regular"), SELF("SELF"), existing_Patient("existing_Patient");
	;

	private final String value;

	BookingType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
