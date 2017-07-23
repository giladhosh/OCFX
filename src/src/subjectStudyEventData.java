package src;

// FormData = subjectSingleStudyEvent
// this = SubjectDatum (scheme maker)

import java.util.HashMap;
import java.util.List;
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
	"StudyEventData",
	"@OpenClinica:Status",
	"@OpenClinica:EnrollmentDate",
	"@OpenClinica:StudySubjectID",
	"@OpenClinica:DateOfBirth",
	"@xmlns:OpenClinica",
	"@OpenClinica:UniqueIdentifier",
	"@OpenClinica:Sex",
	"@SubjectKey"
})
/**
 * This class represents an object in OpenClinica
 *  OC: this is one item in Study SubjectData(array), 
 *  this object contains info about the subject and ALL EVENTS
 * @author Gilad
 *
 */
public class subjectStudyEventData { //OC: SubjectData

	@SerializedName("StudyEventData")
	private List<subjectSingleStudyEvent> studyEventData = null;  //each subjectSingleStudyEvent is a study event for a subject
	@SerializedName("@OpenClinica:Status")
	private String openClinicaStatus;
	@SerializedName("@OpenClinica:EnrollmentDate")
	private String openClinicaEnrollmentDate;
	@SerializedName("@OpenClinica:StudySubjectID")
	private String openClinicaStudySubjectID;
	@SerializedName("@OpenClinica:DateOfBirth")
	private String openClinicaDateOfBirth;
	@SerializedName("@xmlns:OpenClinica")
	private String xmlnsOpenClinica;
	@SerializedName("@OpenClinica:UniqueIdentifier")
	private String openClinicaUniqueIdentifier;
	@SerializedName("@OpenClinica:Sex")
	private String openClinicaSex;
	@SerializedName("@SubjectKey")
	private String subjectKey;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("StudyEventData")
	public List<subjectSingleStudyEvent> getStudyEventData() {
		return studyEventData;
	}

	@JsonProperty("StudyEventData")
	public void setStudyEventData(List<subjectSingleStudyEvent> studyEventData) {
		this.studyEventData = studyEventData;
	}

	@JsonProperty("@OpenClinica:Status")
	public String getOpenClinicaStatus() {
		return openClinicaStatus;
	}

	@JsonProperty("@OpenClinica:Status")
	public void setOpenClinicaStatus(String openClinicaStatus) {
		this.openClinicaStatus = openClinicaStatus;
	}

	@JsonProperty("@OpenClinica:EnrollmentDate")
	public String getOpenClinicaEnrollmentDate() {
		return openClinicaEnrollmentDate;
	}

	@JsonProperty("@OpenClinica:EnrollmentDate")
	public void setOpenClinicaEnrollmentDate(String openClinicaEnrollmentDate) {
		this.openClinicaEnrollmentDate = openClinicaEnrollmentDate;
	}

	@JsonProperty("@OpenClinica:StudySubjectID")
	public String getOpenClinicaStudySubjectID() {
		return openClinicaStudySubjectID;
	}

	@JsonProperty("@OpenClinica:StudySubjectID")
	public void setOpenClinicaStudySubjectID(String openClinicaStudySubjectID) {
		this.openClinicaStudySubjectID = openClinicaStudySubjectID;
	}

	@JsonProperty("@OpenClinica:DateOfBirth")
	public String getOpenClinicaDateOfBirth() {
		return openClinicaDateOfBirth;
	}

	@JsonProperty("@OpenClinica:DateOfBirth")
	public void setOpenClinicaDateOfBirth(String openClinicaDateOfBirth) {
		this.openClinicaDateOfBirth = openClinicaDateOfBirth;
	}

	@JsonProperty("@xmlns:OpenClinica")
	public String getXmlnsOpenClinica() {
		return xmlnsOpenClinica;
	}

	@JsonProperty("@xmlns:OpenClinica")
	public void setXmlnsOpenClinica(String xmlnsOpenClinica) {
		this.xmlnsOpenClinica = xmlnsOpenClinica;
	}

	@JsonProperty("@OpenClinica:UniqueIdentifier")
	public String getOpenClinicaUniqueIdentifier() {
		return openClinicaUniqueIdentifier;
	}

	@JsonProperty("@OpenClinica:UniqueIdentifier")
	public void setOpenClinicaUniqueIdentifier(String openClinicaUniqueIdentifier) {
		this.openClinicaUniqueIdentifier = openClinicaUniqueIdentifier;
	}

	@JsonProperty("@OpenClinica:Sex")
	public String getOpenClinicaSex() {
		return openClinicaSex;
	}

	@JsonProperty("@OpenClinica:Sex")
	public void setOpenClinicaSex(String openClinicaSex) {
		this.openClinicaSex = openClinicaSex;
	}

	@JsonProperty("@SubjectKey")
	public String getSubjectKey() {
		return subjectKey;
	}

	@JsonProperty("@SubjectKey")
	public void setSubjectKey(String subjectKey) {
		this.subjectKey = subjectKey;
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
