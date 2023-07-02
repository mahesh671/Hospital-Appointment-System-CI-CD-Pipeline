package spring.orm.test.controllers;

import org.testng.annotations.Test;
import spring.orm.model.entity.*;
import spring.orm.model.*;
import spring.orm.contract.DAO.*;
import spring.orm.controller.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SpecializationControllerTest {

	@Test
	public void testGetSpecialization() {
		// Create a mock specialization object
		Specialization specialization = new Specialization();
		specialization.setId("123");
		specialization.setTitle("Specialization Name");
		specialization.setDeleted(false); // Set the "isDeleted" field to false

		// Mock the specializationDAO
		SpecializationDAO specializationDAO = mock(SpecializationDAO.class);
		when(specializationDAO.getSpecialization("123")).thenReturn(specialization);
		DoctorsDAO doctorDAO = mock(DoctorsDAO.class);

		// Create an instance of the controller
		SpecializationController controller = new SpecializationController(specializationDAO, doctorDAO);

		// Call the method to get the specialization
		ResponseEntity<String> response = controller.getSpecialization("123");

		// Verify the result
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"id\":\"123\",\"title\":\"Specialization Name\",\"isDeleted\":false}", response.getBody());
	}

	@Test
	public void testDeleteSpecialization() {
		// Mock the specializationDAO
		SpecializationDAO specializationDAO = mock(SpecializationDAO.class);
		DoctorsDAO doctorDAO = mock(DoctorsDAO.class);
		// Create an instance of the controller
		SpecializationController controller = new SpecializationController(specializationDAO,doctorDAO);

		// Call the method to delete the specialization
		ResponseEntity<?> response = controller.deleteSpecialization("123");

		// Verify the result
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("success", response.getBody());
		verify(specializationDAO, times(1)).deleteSpecialization("123");
	}

}
