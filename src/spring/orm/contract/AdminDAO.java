package spring.orm.contract;

import java.util.List;

import spring.orm.model.output.OutputDoctorProfit;
import spring.orm.model.output.OutputSpecializationProfit;

public interface AdminDAO {
	List<OutputDoctorProfit> fetchDoctorProfit(); 
	List<OutputSpecializationProfit> fetchSpecializationProfit();
	

	List<OutputDoctorProfit> fetchDoctorProfit(String from, String to); 
	List<OutputSpecializationProfit> fetchSpecializationProfit(String from, String to);
	
	
	

}
