package src;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"FormData",
"@StudyEventOID",
"@StudyEventRepeatKey",
"@OpenClinica:Status",
"@OpenClinica:SubjectAgeAtEvent",
"@OpenClinica:StartDate"
})

public class subjectSingleStudyEvent { //in OC: one item in StudyEventData List
	
@SerializedName("FormData")
private FormData formData; //holds all data for the event
@SerializedName("@StudyEventOID")
private String studyEventOID;
@SerializedName("@StudyEventRepeatKey")
private String studyEventRepeatKey;
@SerializedName("@OpenClinica:Status")
private String openClinicaStatus;
@SerializedName("@OpenClinica:SubjectAgeAtEvent")
private String openClinicaSubjectAgeAtEvent;
@SerializedName("@OpenClinica:StartDate")
private String openClinicaStartDate;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("FormData")
public FormData getFormData() {
return formData;
}

@JsonProperty("FormData")
public void setFormData(FormData formData) {
this.formData = formData;
}

@JsonProperty("@StudyEventOID")
public String getStudyEventOID() {
return studyEventOID;
}

@JsonProperty("@StudyEventOID")
public void setStudyEventOID(String studyEventOID) {
this.studyEventOID = studyEventOID;
}

@JsonProperty("@StudyEventRepeatKey")
public String getStudyEventRepeatKey() {
return studyEventRepeatKey;
}

@JsonProperty("@StudyEventRepeatKey")
public void setStudyEventRepeatKey(String studyEventRepeatKey) {
this.studyEventRepeatKey = studyEventRepeatKey;
}

@JsonProperty("@OpenClinica:Status")
public String getOpenClinicaStatus() {
return openClinicaStatus;
}

@JsonProperty("@OpenClinica:Status")
public void setOpenClinicaStatus(String openClinicaStatus) {
this.openClinicaStatus = openClinicaStatus;
}

@JsonProperty("@OpenClinica:SubjectAgeAtEvent")
public String getOpenClinicaSubjectAgeAtEvent() {
return openClinicaSubjectAgeAtEvent;
}

@JsonProperty("@OpenClinica:SubjectAgeAtEvent")
public void setOpenClinicaSubjectAgeAtEvent(String openClinicaSubjectAgeAtEvent) {
this.openClinicaSubjectAgeAtEvent = openClinicaSubjectAgeAtEvent;
}

@JsonProperty("@OpenClinica:StartDate")
public String getOpenClinicaStartDate() {
return openClinicaStartDate;
}

@JsonProperty("@OpenClinica:StartDate")
public void setOpenClinicaStartDate(String openClinicaStartDate) {
this.openClinicaStartDate = openClinicaStartDate;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}

