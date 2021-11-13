package com.app.emedi.service;

import java.util.List;

import com.app.emedi.bean.PersonalInfo;

public interface PatientService {

	PersonalInfo savePersonalInfo(PersonalInfo personalInfo);

	List<PersonalInfo> getPersonalInfo(String createdBy);

	String updatePersonalInfo(PersonalInfo personalInfo);

	String deletePersonalInfo(String patientId);

	PersonalInfo addPatientWithVisits(PersonalInfo personalInfo);

	List<PersonalInfo> getAllPatients();

	PersonalInfo getPatientById(String patientId);

	List<PersonalInfo> getOnlyPatientsList();

	List<String> getPstientIdByPhoneNumber(String phoneNumber);

	String getPstientIdByPhoneAndName(String phoneNumber, String fullName);

}
