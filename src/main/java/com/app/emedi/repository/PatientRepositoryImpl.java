package com.app.emedi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.app.emedi.bean.PersonalInfo;
import com.app.emedi.bean.Visits;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public PersonalInfo savePersonalInfo(PersonalInfo personalInfo) {
		String savedata = "insert into personal_info(first_name, last_name, age, gender, status, created_date, created_by, updated_date, updated_by)"
				+ "values(:firstName,:lastName,:age,:gender,:status,:createdDate,:createdBy,:updatedDate,:updatedBy)";
		SqlParameterSource param =  new MapSqlParameterSource()
				.addValue("firstName",personalInfo.getFirstName())
				.addValue("lastName",personalInfo.getLastName())
				.addValue("age",personalInfo.getAge())
				.addValue("gender",personalInfo.getGender())
				.addValue("status",personalInfo.getStatus())
				.addValue("createdDate",OffsetDateTime.now())
				.addValue("createdBy",personalInfo.getCreatedBy())
				.addValue("updatedDate",OffsetDateTime.now())
				.addValue("updatedBy",personalInfo.getUpdatedBy());
				
	
		
		//BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(personalInfo);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(savedata, param, keyHolder, new String[] { "patient_id" });
		return personalInfo;
	}

	@Override
	public String updatePersonalInfo(PersonalInfo personalInfo) {
		String parientId= getPstientIdByPhoneAndName(personalInfo.getPhoneNumber(),personalInfo.getFullName());
		if(parientId!=null) {
		String updateQuery = "update personal_info set first_name=:firstName,last_name=:LastName,full_name =:fullName, age=:age,gender=:gender where patient_id=:patientId";
	    SqlParameterSource parameter = new MapSqlParameterSource()
	    		.addValue("firstName", personalInfo.getFirstName())
	    		.addValue("LastName", personalInfo.getLastName())
	    		.addValue("fullName", personalInfo.getFullName())
	    		.addValue("age", personalInfo.getAge())
	    		.addValue("gender", personalInfo.getGender())
	    		.addValue("patientId", parientId);
	    		
	    
		Integer result= namedParameterJdbcTemplate.update(updateQuery, parameter);
		if(result != 0) {
			return "updated sucessfully";
		}else {
		return "not updated";
		}
		}else {
			throw new RuntimeException("patient id is empty "+personalInfo.getFullName());
		}
	}

	@Override
	public List<PersonalInfo> getPersonalInfo(String createdBy) {
		String selectQuery ="select patient_id, first_name, last_name, age, gender, status, created_date, created_by, updated_date, updated_by from personal_info where created_by=:createdBy";
        Map<String , Object> querydetails= new HashMap<String, Object>();
        querydetails.put("createdBy", createdBy);
		List<Map<String, Object>> PersonalInfo = namedParameterJdbcTemplate.queryForList(selectQuery, querydetails);
          List<PersonalInfo> patients = new ArrayList<>();
          PersonalInfo.forEach(data->{
        	  PersonalInfo personalInfo = new PersonalInfo();
        	  personalInfo.setAge(data.get("age")==null? 0:convertToLong(data.get("age")));
        	  personalInfo.setCreatedBy((String) data.get("created_by"));
        	  personalInfo.setCreatedDate( (Timestamp) data.get("created_date"));
        	  personalInfo.setFirstName((String) data.get("first_name"));
        	  personalInfo.setGender((String) data.get("gender"));
        	  personalInfo.setLastName((String) data.get("last_name"));
        	  personalInfo.setPatientId((String) data.get("patient_id"));
        	  personalInfo.setStatus(convertToLong(data.get("status")));
        	  personalInfo.setUpdatedBy((String) data.get("updated_by"));
        	  personalInfo.setUpdatedDate((Timestamp) data.get("updated_date"));
        	  patients.add(personalInfo);
        	  
          });
		return patients;
	}

	@Override
	public String deletePersonalInfo(String patientId) {
		String deleteQuery = "delete from personal_info where  patient_id =:patientId";
		SqlParameterSource source = new MapSqlParameterSource().addValue("patientId", patientId);
		Integer result= namedParameterJdbcTemplate.update(deleteQuery, source);
		System.out.println(result+"=========================================");
		if(result != 0) {
			return "updated sucessfully";
		}else {
		return "not updated";
		}
		
	}

	@Override
	public PersonalInfo addPatientWithVisits(PersonalInfo personalInfo) {
	if (personalInfo.getPhoneNumber()!=null&&personalInfo.getFullName()!=null&&!personalInfo.getPhoneNumber().isEmpty()&&!personalInfo.getFullName().isEmpty()) {
		
		String patientId=getPstientIdByPhoneAndName(personalInfo.getPhoneNumber(),personalInfo.getFullName());
			
		
		if(Objects.isNull(patientId)) {
		String savedata = "insert into personal_info( phone_number,first_name, last_name, age, gender, status, created_date, created_by, updated_date, updated_by,full_name)"
				+ "values(:phoneNumber,:firstName,:lastName,:age,:gender,:status,:createdDate,:createdBy,:updatedDate,:updatedBy,:fullName)";
		SqlParameterSource param =  new MapSqlParameterSource()
				.addValue("phoneNumber", personalInfo.getPhoneNumber())
				.addValue("firstName",personalInfo.getFirstName())
				.addValue("lastName",personalInfo.getLastName())
				.addValue("age",personalInfo.getAge())
				.addValue("gender",personalInfo.getGender())
				.addValue("status",personalInfo.getStatus())
				.addValue("createdDate",OffsetDateTime.now())
				.addValue("createdBy",personalInfo.getCreatedBy())
				.addValue("updatedDate",OffsetDateTime.now())
				.addValue("updatedBy",personalInfo.getUpdatedBy())
				.addValue("fullName", personalInfo.getFullName());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(savedata, param, keyHolder, new String[] { "patient_id" });
		
		personalInfo.getVisits().forEach(visit ->{
			String creditId = (String) keyHolder.getKeys().get("patient_id");
			System.out.println("------------------------"+creditId);
			visit.setPatientId(creditId);
		});
		
		personalInfo.setVisits(visits(personalInfo.getVisits()));
		return personalInfo;
		}else {
			personalInfo.getVisits().forEach(visit ->{
				
				visit.setPatientId(patientId);
				personalInfo.setVisits(visits(personalInfo.getVisits()));

			});
			
			return personalInfo;
		}
	}else {
		throw new RuntimeException("invalid data");
	}
	}
	private List<Visits> visits(List<Visits> visits) {
		String insert ="insert into visits	(patient_id,status, created_date, created_by, updated_date, updated_by)values(:patientId,:status,:createdDate,:createdBy,:updatedDate,:updatedBy)";
		visits.forEach(visit->{

		SqlParameterSource param =  new MapSqlParameterSource()
				.addValue("patientId", visit.getPatientId())
				
				.addValue("status", visit.getStatus())
				.addValue("createdDate", OffsetDateTime.now())
				.addValue("createdBy", visit.getCreatedBy())
				.addValue("updatedDate",OffsetDateTime.now())
				.addValue("updatedBy",visit.getUpdatedBy() );
				
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(insert, param, keyHolder, new String[] { "visit_id" });
		visit.setVisitId(keyHolder.getKey().longValue());
		});
		return visits;
		
	}

	@Override
	public List<PersonalInfo> getAllPatients() {
		System.out.println("all patient  in repo" );

		String query = "select p.patient_id,p.phone_number,p.first_name,p.last_name,p.full_name,p.age,p.gender,p.status,p.created_date,p.created_by,p.updated_date,p.updated_by, v.visit_id,v.created_date,v.created_by,v.updated_date,v.updated_by,v.status from personal_info as p inner join visits as v on  (p.patient_id=v.patient_id)";
		List<Map<String, Object>> data =namedParameterJdbcTemplate.queryForList(query, Collections.emptyMap());
		 List<PersonalInfo> patients = new ArrayList<PersonalInfo>();
		 Set<String> noDublicats = new HashSet<String>();
		 System.out.println(data);
		 
		data.forEach(patient->{
			if(!noDublicats.contains(patient.get("patient_id"))) {
			PersonalInfo personalInfo = new PersonalInfo();
			personalInfo.setCreatedDate( (Timestamp) patient.get("created_date"));
			personalInfo.setAge( convertToLong(patient.get("age")));
			personalInfo.setFirstName( (String) patient.get("first_name"));
			personalInfo.setLastName( (String) patient.get("last_name"));
			personalInfo.setFullName( (String) patient.get("full_name"));
			personalInfo.setFullName(personalInfo.getFirstName()+" "+personalInfo.getLastName());
			personalInfo.setPatientId( (String) patient.get("patient_id"));
			personalInfo.setGender( (String) patient.get("gender"));
			personalInfo.setStatus(convertToLong( patient.get("status")));
			personalInfo.setPhoneNumber((String) patient.get("phone_number"));
			personalInfo.setCreatedBy((String) patient.get("created_by"));
			personalInfo.setUpdatedDate((Timestamp) patient.get("updated_date"));
			
			
			
			List<Visits> visits = new ArrayList<>();
			Visits visit = new Visits();
			visit.setVisitId(convertToLong(patient.get("visit_id")));
			visit.setPatientId((String)(patient.get("patient_id")));
			visit.setCreatedBy((String) patient.get("created_by"));
			visit.setCreatedDate( (Timestamp) patient.get("created_date"));
			visit.setUpdatedBy((String) patient.get("updated_by"));
			visit.setUpdatedDate((Timestamp) patient.get("updated_date"));
			visit.setStatus(convertToLong(patient.get("status")));
			
			
			
			visits.add(visit);
			
			personalInfo.setVisits(visits);
			patients.add(personalInfo);
			noDublicats.add((String) patient.get("patient_id"));
		}
			else {
				patients.forEach(visit->{
					
					if(visit.getPatientId().equalsIgnoreCase((String) patient.get("patient_id"))) {
						List<Visits> visits = new ArrayList<>();
						Visits visitt = new Visits();
						visitt.setVisitId(convertToLong(patient.get("visit_id")));
						visitt.setPatientId((String)(patient.get("patient_id")));
						visitt.setCreatedBy((String) patient.get("created_by"));
						visitt.setCreatedDate( (Timestamp) patient.get("created_date"));
						visitt.setUpdatedBy((String) patient.get("updated_by"));
						visitt.setUpdatedDate((Timestamp) patient.get("updated_date"));
						visitt.setStatus(convertToLong(patient.get("status")));
						visits.add(visitt);
						
						visit.getVisits().add(visitt);
					}
					
				});
			
				
				
			}
		});
		System.out.println(patients+"-------------------------");
		return patients;
		
		
		
	}

	@Override
	public PersonalInfo getPatientById(String patientId) {
		String query = "select p.patient_id,p.first_name,p.last_name,p.age,p.gender,p.status,p.created_date,p.phone_number,p.created_by,p.updated_date,p.updated_by, v.visit_id,v.created_date,v.created_by,v.updated_date,v.updated_by from personal_info as p inner join visits as v on  (p.patient_id=v.patient_id) where p.patient_id =:patientId";
		Map<String, String> map = new HashMap<String, String>();
		map.put("patientId", patientId);
		List<Map<String, Object>> data =namedParameterJdbcTemplate.queryForList(query, map);
		 List<PersonalInfo> patients = new ArrayList<PersonalInfo>();
		 Set<String> noDublicats = new HashSet<String>();
		 
		data.forEach(patient->{
			if(!noDublicats.contains(patient.get("patient_id"))) {
				PersonalInfo personalInfo = new PersonalInfo();
				personalInfo.setCreatedDate( (Timestamp) patient.get("created_date"));
				personalInfo.setAge( convertToLong(patient.get("age")));
				personalInfo.setFirstName( (String) patient.get("first_name"));
				personalInfo.setLastName( (String) patient.get("last_name"));
				personalInfo.setFullName(personalInfo.getFirstName()+" "+personalInfo.getLastName());
				personalInfo.setPatientId( (String) patient.get("patient_id"));
				personalInfo.setGender( (String) patient.get("gender"));
				personalInfo.setStatus(convertToLong( patient.get("status")));
				personalInfo.setPhoneNumber((String) patient.get("phone_number"));
				personalInfo.setCreatedBy((String) patient.get("created_by"));
				personalInfo.setUpdatedDate((Timestamp) patient.get("updated_date"));
				
				
				
				List<Visits> visits = new ArrayList<>();
				Visits visit = new Visits();
				visit.setVisitId(convertToLong(patient.get("visit_id")));
				visit.setPatientId((String)(patient.get("patient_id")));
				visit.setCreatedBy((String) patient.get("created_by"));
				visit.setCreatedDate( (Timestamp) patient.get("created_date"));
				visit.setUpdatedBy((String) patient.get("updated_by"));
				visit.setUpdatedDate((Timestamp) patient.get("updated_date"));
				visit.setStatus(convertToLong(patient.get("status")));
				
				
				
				visits.add(visit);
				
				personalInfo.setVisits(visits);
				patients.add(personalInfo);
				noDublicats.add((String) patient.get("patient_id"));
			}
				else {
					patients.forEach(visit->{
						
						if(visit.getPatientId().equalsIgnoreCase((String) patient.get("patient_id"))) {
							List<Visits> visits = new ArrayList<>();
							Visits visitt = new Visits();
							visitt.setVisitId(convertToLong(patient.get("visit_id")));
							visitt.setPatientId((String)(patient.get("patient_id")));
							visitt.setCreatedBy((String) patient.get("created_by"));
							visitt.setCreatedDate( (Timestamp) patient.get("created_date"));
							visitt.setUpdatedBy((String) patient.get("updated_by"));
							visitt.setUpdatedDate((Timestamp) patient.get("updated_date"));
							visitt.setStatus(convertToLong(patient.get("status")));
							visits.add(visitt);
							
							visit.getVisits().add(visitt);
						}
						
					});
				
					
					
				}
			});
		PersonalInfo findFirst = patients.stream().findFirst().get();
		return findFirst;
		
		
		
	}

	@Override
	public List<PersonalInfo> getOnlyPatientsList() {
		String selectQuery ="select patient_id, first_name, last_name, age, gender, status, created_date, created_by, updated_date, updated_by from personal_info ";
        
		List<Map<String, Object>> PersonalInfo = namedParameterJdbcTemplate.queryForList(selectQuery, Collections.emptyMap());
        if (!PersonalInfo.isEmpty()) {
        	List<PersonalInfo> patients = new ArrayList<>();
        	PersonalInfo.forEach(data->{
        		PersonalInfo personalInfo = new PersonalInfo();
        		personalInfo.setAge(convertToLong(data.get("age")));
        		personalInfo.setCreatedBy((String) data.get("created_by"));
        		personalInfo.setCreatedDate((Timestamp) data.get("created_date"));
        		personalInfo.setFirstName((String) data.get("first_name"));
        		personalInfo.setGender((String) data.get("gender"));
        		personalInfo.setLastName((String) data.get("last_name"));
        		personalInfo.setPatientId((String) data.get("patient_id"));
        		personalInfo.setStatus(convertToLong(data.get("status")) );
        		personalInfo.setUpdatedBy((String) data.get("updated_by"));
        		personalInfo.setUpdatedDate((Timestamp) data.get("updated_date"));
        		patients.add(personalInfo);
        		
        	});
        	return patients;
		} else {
                   throw new RuntimeException("patients table is empty");
		}
	}
	public Long convertToLong(Object obj) {
		return Objects.nonNull(obj) ? Long.valueOf(obj.toString()) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPstientIdByPhoneNumber(String phoneNumber) {
		String query = "select patient_id from personal_info where phone_number =:phoneNumber";
		SqlParameterSource param = new MapSqlParameterSource("phoneNumber", phoneNumber);
		
		 
		List<String> result = namedParameterJdbcTemplate.query(query, param, new RowMapper<String>() {
		 
		    @Override
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException  {
	        	String patientId=rs.getString("patient_id");
	        	return patientId;
	        	

		    }
		     
		});
		if (Objects.nonNull(result)&&!result.isEmpty()) {
			
			return result;
			
		} else {

			return null;
		}
	}

	@Override
	public String getPstientIdByPhoneAndName(String phoneNumber, String fullName) {
		String query = "select patient_id from personal_info where phone_number =:phoneNumber or full_name =:fullName";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("phoneNumber",phoneNumber )
				.addValue("fullName", fullName);
		 
		PersonalInfo result = namedParameterJdbcTemplate.query(query, param, new ResultSetExtractor<PersonalInfo>() {
		 
		    @Override
		    public PersonalInfo extractData(ResultSet rs) throws SQLException, DataAccessException {
		        if (rs.next()) {
		        	PersonalInfo patient = new PersonalInfo();
		        	patient.setPatientId(rs.getString("patient_id"));
		           
		             
		            return patient;
		        }
		        return null;
		    }
		     
		});
		if(result!=null) {
			
			return result.getPatientId();
		}else {
			return null;
		}
	}
	
}
