package spring.orm.contract.services;

import java.util.List;

import javax.transaction.Transactional;

import spring.orm.model.input.FamilyMembersInput;

public interface PatientFamilyMembersServices {

	// Method that saves the family member into the database
	int addFamilyMember(FamilyMembersInput familyMember, int patientId);

	// method to fetch all family members for the given ID using the FamilyMembersDao
	List<FamilyMembersInput> getAllFamilyMembers(Integer patientId);

	// method that fetches family member information
	FamilyMembersInput getFamilyMemberInfo(int patientId, int familyMemberid);

	// method that saves the family member details
	void saveFamilyMemberInfo(FamilyMembersInput familyMember);

	// method that deletes the family member
	void deleteFamilyMember(int patientId);

}