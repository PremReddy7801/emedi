package com.app.emedi.bean;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

public class Visits {
        Long visitId; 
        String patientId; 
        Long status; 
        Timestamp createdDate;
        String createdBy; 
        Timestamp updatedDate; 
        String updatedBy;
		public Long getVisitId() {
			return visitId;
		}
		public void setVisitId(Long visitId) {
			this.visitId = visitId;
		}
		public String getPatientId() {
			return patientId;
		}
		public void setPatientId(String patientId) {
			this.patientId = patientId;
		}
		public Long getStatus() {
			return status;
		}
		public void setStatus(Long status) {
			this.status = status;
		}
		public Timestamp getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Timestamp createdDate) {
			this.createdDate = createdDate;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Timestamp getUpdatedDate() {
			return updatedDate;
		}
		public void setUpdatedDate(Timestamp updatedDate) {
			this.updatedDate = updatedDate;
		}
		public String getUpdatedBy() {
			return updatedBy;
		}
		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}
		@Override
		public String toString() {
			return "Visits [visitId=" + visitId + ", patientId=" + patientId + ", status=" + status + ", createdDate="
					+ createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate + ", updatedBy="
					+ updatedBy + "]";
		}
		
        
        
}
