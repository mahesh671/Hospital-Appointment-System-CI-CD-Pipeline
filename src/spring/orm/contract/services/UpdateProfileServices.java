package spring.orm.contract.services;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import spring.orm.model.input.ProfileUpdateForm;

public interface UpdateProfileServices {

	void updateProfile(ProfileUpdateForm profileUpdateForm, CommonsMultipartFile reportsInput);

}