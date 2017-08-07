package src;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//ClinicalData in OC, Found under a study

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class StudySubjectsData { //OC: all data form GET request

	@SerializedName("@StudyOID")
	private String studyOID;
	@SerializedName("@MetaDataVersionOID")
	private Object metaDataVersionOID;
	@SerializedName("SubjectData")
	private subjectStudyEventData subjectData = null;
	//private List<subjectStudyEventData> subjectData = null; //each subjectStudyEventData is (all events + info fields) for a subject (this is OC:SubjectData)

	//@JsonIgnore
	//private Map<String, Object> additionalProperties = new HashMap<String, Object>();



	///////////////////getters////////////////
	@JsonProperty("@StudyOID")
	public String getStudyOID() {
		return studyOID;
	}

	@JsonProperty("@StudyOID")
	public void setStudyOID(String studyOID) {
		this.studyOID = studyOID;
	}

	@JsonProperty("@MetaDataVersionOID")
	public Object getMetaDataVersionOID() {
		return metaDataVersionOID;
	}

	@JsonProperty("@MetaDataVersionOID")
	public void setMetaDataVersionOID(Object metaDataVersionOID) {
		this.metaDataVersionOID = metaDataVersionOID;
	}

	@JsonProperty("SubjectData")
	public subjectStudyEventData getSubjectData() {
		return subjectData;
	}

	@JsonProperty("SubjectData")
	public void setSubjectData(subjectStudyEventData subjectData) {
		this.subjectData = subjectData;
	}

	//private boolean a; //remove?
	public List<SearchResult> searchField(String fieldName)
	{//subjectSingleStudyEvent
		List<SearchResult> results = new ArrayList<SearchResult>(); //sav
		//a = false;
		subjectData.getStudyEventData().forEach(singleStudyEvent->{
			FormData fd = singleStudyEvent.getFormData();
			if(fd!=null){
				List<ItemGroupDatum> igdL = fd.getItemGroupData();
				if(igdL != null){
					igdL.forEach(itemDataLst->{
						List<ItemDatum> idtLst = itemDataLst.getItemData();
						idtLst.forEach(item -> {
							if(fieldName.equals(item.getValue()))
							{
								try { // for URL init
									SearchResult s = new SearchResult(subjectData.getSubjectKey() ,singleStudyEvent.getStudyEventOID() , new URL("http://www.one.co.il") );
									results.add(s);
								} 
								catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//a = true;
							}
						});
					});
				}
			}
		});
		if (results.isEmpty())
		{
			return null;
		}
		return results;
	}
	/*
@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}
	 */
}