package spring.orm.test.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import spring.orm.contract.TestDAO;
import spring.orm.model.TestModel;
import spring.orm.model.output.testsCategoriesModel;
import spring.orm.services.TestServices;

public class TestServicesTest {

	@Mock
	private TestDAO testDAO;

	@InjectMocks
	private TestServices testServices;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetTests() {
		// Arrange
		TestModel test1mock = new TestModel();
		test1mock.setTest_id(1);
		test1mock.setTest_name("Blood Test");

		TestModel test2mock = new TestModel();
		test2mock.setTest_id(2);
		test2mock.setTest_name("X-Ray");
		List<TestModel> expectedTests = Arrays.asList(test1mock, test2mock);

		when(testDAO.getTests()).thenReturn(expectedTests);

		// Act
		List<TestModel> actualTests = testServices.getTests();
		System.out.println(actualTests);

		// Assert
		verify(testDAO, times(1)).getTests();
		Assert.assertEquals(actualTests, expectedTests);
	}

	@Test
	public void testGetTestById() {
		// Arrange
		int testId = 1;
		TestModel expectedTest = new TestModel();
		expectedTest.setTest_id(testId);
		expectedTest.setTest_name("Blood Test");

		when(testDAO.getTestById(testId)).thenReturn(expectedTest);

		// Act
		TestModel actualTest = testServices.getTestById(testId);

		// Assert
		verify(testDAO, times(1)).getTestById(testId);
		Assert.assertEquals(actualTest, expectedTest);
	}

	@Test
	public void testUpdateTest() {
		// Arrange
		TestModel test = new TestModel();
		test.setTest_id(1);
		test.setTest_name("Sugar Test");

		// Act
		testServices.updateTest(test);

		// Assert
		verify(testDAO, times(1)).updateTest(test);
	}

	@Test
	public void testDeleteTest() {
		// Arrange
		int testId = 1;

		// Act
		testServices.deleteTest(testId);

		// Assert
		verify(testDAO, times(1)).deleteTest(testId);
	}

	@Test
	public void testGetTestCategory() {

		testsCategoriesModel expectedCategories = new testsCategoriesModel();
		testsCategoriesModel expectedCategories1 = new testsCategoriesModel();
		testsCategoriesModel expectedCategories2 = new testsCategoriesModel();
		expectedCategories.setTest_category("CATG02");
		expectedCategories1.setTest_category("CATG01");
		expectedCategories2.setTest_category("CATG03");
		// Arrange
		List<testsCategoriesModel> expectedCategories3 = Arrays.asList(expectedCategories, expectedCategories1,
				expectedCategories2);
		when(testDAO.getCategories()).thenReturn(expectedCategories3);

		// Act
		List<testsCategoriesModel> actualCategories = testServices.getTestCategory();

		// Assert
		verify(testDAO, times(1)).getCategories();
		assertEquals(actualCategories, expectedCategories3);
	}

	@Test
	public void testGetTestByCategory() {
		// Arrange
		String category = "CATG03";
		List<TestModel> expectedTests = Arrays.asList(new TestModel(), new TestModel());
		when(testDAO.getTestByCategory(category)).thenReturn(expectedTests);

		// Act
		List<TestModel> actualTests = testServices.getTestByCategory(category);

		// Assert
		verify(testDAO, times(1)).getTestByCategory(category);
		assertEquals(actualTests, expectedTests);
	}

	@Test
	public void testGetTestByPrice() {
		// Arrange
		int testId = 1;
		Object expectedPrice = 100;
		when(testDAO.getSelectedTestPrice(testId)).thenReturn(expectedPrice);

		// Act
		Object actualPrice = testServices.getTestByPrice(testId);

		// Assert
		verify(testDAO, times(1)).getSelectedTestPrice(testId);
		assertEquals(actualPrice, expectedPrice);
	}
}
