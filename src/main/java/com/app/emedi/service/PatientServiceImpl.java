package com.app.emedi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.emedi.bean.PersonalInfo;
import com.app.emedi.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public PersonalInfo savePersonalInfo(PersonalInfo personalInfo) {
		return patientRepository.savePersonalInfo(personalInfo);
	}

	@Override
	public List<PersonalInfo> getPersonalInfo(String createdBy) {
		return patientRepository.getPersonalInfo(createdBy);
	}

	@Override
	public String updatePersonalInfo(PersonalInfo personalInfo) {
		return patientRepository.updatePersonalInfo(personalInfo);
	}

	@Override
	public String deletePersonalInfo(String patientId) {
		return patientRepository.deletePersonalInfo(patientId);
	}

	@Override

	public PersonalInfo addPatientWithVisits(PersonalInfo personalInfo) {
		return patientRepository.addPatientWithVisits(personalInfo);
	}

	@Override
	public List<PersonalInfo> getAllPatients() {
		return patientRepository.getAllPatients();
	}

	@Override
	public PersonalInfo getPatientById(String patientId) {
		return patientRepository.getPatientById(patientId);
	}

	@Override
	public List<PersonalInfo> getOnlyPatientsList() {
		return patientRepository.getOnlyPatientsList();
	}

	@Override
	public List<String> getPstientIdByPhoneNumber(String phoneNumber) {
		return patientRepository.getPstientIdByPhoneNumber(phoneNumber);
	}

	@Override
	public String getPstientIdByPhoneAndName(String phoneNumber, String fullName) {
		return patientRepository.getPstientIdByPhoneAndName(phoneNumber,fullName);
	}

}
