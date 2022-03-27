package com.app.emedi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.emedi.bean.PersonalInfo;
import com.app.emedi.repository.PatientRepositoryImpl;
import com.app.emedi.service.PatientService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/medi/patient/")
@CrossOrigin
public class PatientController {

	@Autowired
	private PatientService patientService;
	@Autowired
	private PatientRepositoryImpl patientRepositoryImpl;

//	@PostMapping("/savedata")
//	public PersonalInfo savePersonalInfo(@RequestBody @Valid PersonalInfo personalInfo) {
//		if (personalInfo.getFirstName()!=null&&personalInfo.getLastName()!=null&&!personalInfo.getFirstName().isEmpty()&&!personalInfo.getLastName().isEmpty()) {
//			
//			personalInfo.setFirstName(fullName(personalInfo.getFirstName(),personalInfo.getLastName()));
//		}
//		return patientService.savePersonalInfo(personalInfo);
//
//	}

	@GetMapping("get-patient-with-createdBy")
	public List<PersonalInfo> getPersonalInfo(@RequestParam String createdBy) {
		return patientService.getPersonalInfo(createdBy);
	}

	@PutMapping("update-patient")
	public String updatePersonalInfo(@RequestBody @Valid PersonalInfo personalInfo) {
		personalInfo.setFullName(fullName(personalInfo.getFirstName(),personalInfo.getLastName()));
		System.out.println(personalInfo.getFullName());
		
		return patientService.updatePersonalInfo(personalInfo);

	}

	@DeleteMapping("delete-patient")
	public String deletePersonalInfo(@RequestParam String patientId) {
		return patientService.deletePersonalInfo(patientId);
	}

	@PostMapping("add-patient")
	public PersonalInfo addPatientWithVisits(@RequestBody @Valid PersonalInfo personalInfo) {
		personalInfo.setFullName(fullName(personalInfo.getFirstName(),personalInfo.getLastName()));
		System.out.println("personal info :----------------------"+personalInfo);
		return patientRepositoryImpl.addPatientWithVisits(personalInfo);

	}

	@GetMapping("get-all-patients")
	public List<PersonalInfo> getAllPatients() {
		System.out.println("all patient");
		return patientService.getAllPatients();

	}

	@GetMapping("get-patient-by-id")
	public PersonalInfo getPatientById(@RequestParam String patientId) {
		System.out.println("patientId +"+patientId);
		return patientService.getPatientById(patientId);

	}

//	@GetMapping("/getonly-patients-list")
//	public List<PersonalInfo> getOnlyPatientsList() {
//		return patientService.getOnlyPatientsList();
//	}
	@ApiOperation(nickname = "find by phone number",
			value = "phonenumber required",notes = "gby-phonenumber",consumes = "onether api",produces = "patient object")
	@ApiResponses(value = {
			@ApiResponse(code =200,message = "succesfull" )
	})
	@GetMapping("get-patientid-by-phonenumber")
	public  List<String> getPstientIdByPhoneNumber(@ApiParam(value = "phone number",required = true,defaultValue = "9441191045",type = "string",name = "phone",example = "73060809614") @RequestParam String phoneNumber) {
		System.out.println("=====================================================");
		return patientService.getPstientIdByPhoneNumber(phoneNumber);
	}
	@GetMapping("get-patientid-by-phone-name")
	public  String getPstientIdByPhoneAndName( @RequestParam String phoneNumber ,@RequestParam String firstName , @RequestParam String lastName  ) {
		String fullName =firstName+lastName;
		System.out.println("fullname===========:"+fullName);
		System.out.println("=====================================================");
		return patientService.getPstientIdByPhoneAndName(phoneNumber,fullName);
	}
	public String fullName(String firstName,String lastName) {
		return firstName+lastName;
	}

}
