package com.app.emedi.repository;

import java.util.List;

import com.app.emedi.bean.PersonalInfo;

public interface PatientRepository {

	PersonalInfo savePersonalInfo(PersonalInfo personalInfo);

	String updatePersonalInfo(PersonalInfo personalInfo);

	List<PersonalInfo> getPersonalInfo(String createdBy);

	String deletePersonalInfo(String patientId);

	PersonalInfo addPatientWithVisits(PersonalInfo personalInfo);

	List<PersonalInfo> getAllPatients();

	PersonalInfo getPatientById(String patientId);

	List<PersonalInfo> getOnlyPatientsList();

	List<String> getPstientIdByPhoneNumber(String phoneNumber);

	String getPstientIdByPhoneAndName(String phoneNumber, String fullName);

}
