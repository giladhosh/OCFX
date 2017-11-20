package src;
/*
 * This file is part of RadPlanBio
 * Copyright (C) 2013-2016 Tomas Skripcak
 */

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.log4j.Logger;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;
/**
 * OpenClinica service implements client calls to SOAP and REST based web-service endpoints provided by the EDC system
 * It provides the possibility to cache the ws responses for situation where the amount of data that is within a response
 * causes big delays from usability perspective (e.g. study-0 subject list)
 */
public class OpenClinicaService {

	//region Enums
	/**
	 * OpenClinica REST full URLs casebook formats
	 */
	public enum CasebookFormat {
		JSON("json"), XML("xml"), HTML("html");

		private String value;

		CasebookFormat(String value) {
			this.value = value;
		}
	}

	/**
	 * OpenClinica REST full URLs casebook methods
	 */
	public enum CasebookMethod {
		VIEW("view"), PRINT("print");

		private String value;

		CasebookMethod(String value) {
			this.value = value;
		}
	}

	/**
	 * OpenClinica REST full URLs casebook data type filter
	 */
	public enum CasebookDataType {
		CLINICALDATA("clinicaldata");

		private String value;

		CasebookDataType(String value) {
			this.value = value;
		}
	}
	//endregion enums

	//region Finals
	private static final Logger log = Logger.getLogger(OpenClinicaService.class);
	//endregion Finals

	//region Members
	private String ocUsername;
	private String ocPassword;
	private String restBaseUrl;
	private Boolean cacheIsEnabled;
	private Gson gson;
	//endregion members

	//region Constructors
	public OpenClinicaService() {
		gson = new GsonBuilder().serializeNulls().create();
	}
	//endregion constructors


	//region Properties

	public void printServiceInfo() { //used for debugging

		System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><>");
		System.out.println("Service print info:");
		System.out.println("User: " + ocUsername);
		System.out.println("Password: " + ocPassword);
		System.out.println("Rest: " + restBaseUrl);
	}

	public Boolean getCacheIsEnabled() {
		return this.cacheIsEnabled;
	}

	public void setCacheIsEnabled(Boolean value) {
		this.cacheIsEnabled = value;
	}
	//endregion properties

	//region Methods
	//region Connect
	/**
	 * {@inheritDoc}
	 */
	public void connect(String username, String password, String wsBaseUrl, String restBaseUrl) {
		this.cacheIsEnabled = Boolean.FALSE;
		this.ocUsername = username;
		this.ocPassword = password;
		this.restBaseUrl = restBaseUrl;
	}

	//region REST

	//region EDC User Account

	public UserAccount loginUserAccount(String username, String password) {
		UserAccount account = null;

		String method = "pages/accounts/login";

		if (!this.restBaseUrl.endsWith("/")) {
			this.restBaseUrl += "/";
		}

		Client client = Client.create();
		WebResource webResource = client.resource(this.restBaseUrl + method);

		String body = "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, body);

		int status = response.getStatus();
		if (status == 200) {
			String output = response.getEntity(String.class);

			try {
				JSONObject jsonObject = new JSONObject(output);

				account = new UserAccount();
				account.setUserName(jsonObject.getString("username"));
				account.setFirstName(jsonObject.getString("firstName"));
				account.setLastName(jsonObject.getString("lastName"));
				account.setPassword(jsonObject.getString("password"));
				account.setApiKey(jsonObject.optString("apiKey", ""));

				// TODO: implement parsing later when roles are needed
				//                JSONArray rolesArray = jsonObject.getJSONArray("roles");
				//                for (int i = 0; i < rolesArray.length(); i++) {
				//                    JSONObject role = rolesArray.getJSONObject(i);
				//    }
			}
			catch (JSONException err) {
				log.error(err);
			}
		}
		// 401 Bad Credentials
		else if (status == 401) {
			log.info("Bed credentials.");
		}
		return account;
	}

	public String loadEditableUrl(String ocUrl, String returnUrl) {
		String result = "";

		//TODO: this is stupid check to fix the production, try to find better solution
		// If the editable is actually new Enketo form (data entry was started in EDC)
		if (ocUrl.indexOf(":9005/::") == -1) {

			Client client = Client.create();
			WebResource webResource = client.resource(ocUrl);
			ClientResponse response = webResource.get(ClientResponse.class);

			// Accepted
			if (response.getStatus() == 202) {
				result = response.getEntity(String.class);
				result = result.replace("\"", "");

				// Adapt return URL to be URL of my participate
				// &returnUrl=http%3A%2F%2Fstudy1.mystudy.me%3A8080%2F%23%2Fevent%2FSS_SUB001%2Fdashboard&ecid=
				int startIndex = result.indexOf("&returnUrl");
				startIndex = startIndex + 11;
				int endIndex = result.indexOf("&ecid");
				String oldReturnUrlParameter = result.substring(startIndex, endIndex);
				result = result.replace(oldReturnUrlParameter, returnUrl);
			}
		}
		else {
			result = ocUrl;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private ClientResponse getOcRestfulUrl(String ocUsername, String ocClearPassword, String format, String method) {
		Client client;
		client= Client.create();
		client.setFollowRedirects(true);

		MultivaluedMap loginFormData = new MultivaluedMapImpl();
		if (ocUsername != null && !ocUsername.equals("")) {
			loginFormData.add(Constants.OC_UNAMEPARAM, ocUsername);
		}
		else {
			loginFormData.add(Constants.OC_UNAMEPARAM, UserContext.getUsername());
		}
		if (ocClearPassword != null && !ocClearPassword.equals("")) {
			loginFormData.add(Constants.OC_PASSWDPARAM, ocClearPassword);
		}
		else {
			loginFormData.add(Constants.OC_PASSWDPARAM, UserContext.getClearPassword());
		}

		WebResource webResource = client.resource(this.restBaseUrl + Constants.OC_LOGINMETHOD);
		ClientResponse response = webResource
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.MULTIPART_FORM_DATA_TYPE)
				.post(ClientResponse.class, loginFormData);

		//TODO: very ugly find out other way of reading returned jsessionid
		String jsessionid = response.toString().replace("GET " + this.restBaseUrl + "MainMenu;jsessionid=", "").replace(" returned a response status of 200 OK", "");
		Cookie cookie = new Cookie("JSESSIONID", jsessionid);

		//System.out.print("Full REST is: " + this.restBaseUrl + "rest/" + CasebookDataType.CLINICALDATA.value + "/" + format + "/view/" + method +"\n <><><><><><><><><><><><><><><><><><><><><><><><> \n"); //debug

		webResource = client.resource(this.restBaseUrl + "rest/" + CasebookDataType.CLINICALDATA.value + "/" + format + "/view/" + method);
		if (format.equals(CasebookFormat.JSON.value)) {
			response = webResource
					.accept(MediaType.APPLICATION_JSON_TYPE)
					.cookie(cookie)
					.get(ClientResponse.class);
		}
		else if (format.equals(CasebookFormat.XML.value)) {
			response = webResource
					.type(MediaType.APPLICATION_XML_TYPE)
					.cookie(cookie)
					.get(ClientResponse.class);
		}
		return response;
	}
	//endregion connection

	//region get data

	/**
	 * @author Gilad Hoshmand
	 * 	Log all subjects and events into the data singleton
	 *  Wrapper for private void getEventsbyStudySubject(String studyOid, String studySubjectOID) {
	 * @param studyOID
	 */
	public void updateAllData(String studyOID)
	{
		List <String> studySubjectOIDs = this.getAllSubjectsOIDs(studyOID); //Get all Study Subjects OIDs
		System.out.println("All Study Subject OIDs(string) saved @ studySubjectOIDs"); //debug
		System.out.println("Attempting to retrieve all subject events \nPlease wait..."); //debug
		studySubjectOIDs.forEach (studySubjectOIDString ->{
			this.getEventsbyStudySubject(studyOID, studySubjectOIDString); //loop later
		});
		System.out.println("All Data saved!");//\ncompleted in: " + (stopTime - functionStartTime)/1000 + " seconds");
	}

	/**
	 * Exclusion Strategy: Parse all except studyEventData field
	 * @author Gilad Hoshmand
	 * It returns true for StudySubjectsData.subjectData, which is what you want to exclude.
	 */
	public class exclStratSubjectData implements ExclusionStrategy {
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}
		public boolean shouldSkipField(FieldAttributes f) {
			return (f.getDeclaringClass() == StudySubjectsData.class && f.getName().equals("subjectData"));
		}
	}

	/**
	 * Exclusion Strategy: Parse all except studyEventData field
	 * @author Gilad Hoshmand
	 *
	 */
	public class exclStratStudyEventData implements ExclusionStrategy {
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}
		public boolean shouldSkipField(FieldAttributes f) {
			return (f.getDeclaringClass() == subjectStudyEventData.class && f.getName().equals("studyEventData"));
		}
	}


	/**
	 * @author Gilad Hoshmand
	 * Get a list of events according to studyOID sent
	 */
	private void getEventsbyStudySubject(String studyOid, String studySubjectOID) {
		String method = studyOid + "/" + studySubjectOID + "/*/*";
		String format = "json";
		//JSONArray studyEventData = null;

		if (!this.restBaseUrl.endsWith("/")) {
			this.restBaseUrl += "/";
		}

		ClientResponse response = this.getOcRestfulUrl(
				this.ocUsername,
				this.ocPassword,
				format,
				method
				);

		// Success
		if (response.getStatus() == 200) {
			String output = response.getEntity(String.class);
			try {
				/* Json Objects */
				JSONObject jsonAllOutput;
				JSONObject jsonClinicalData;
				JSONObject jsonSubjectData;
				JSONArray jsonArrStudyEventData;
				JSONObject jsonsubjectSingleStudyEvent;
				JSONObject jsonFormData;
				JSONObject jsonItemGroupData = null;
				JSONArray jsonArrItemGroupData = null;
				JSONObject jsonStudyEventData;
				//JSONArray jsonArrItemData;
				/* End Json Objects */

				/* Objects parsed manually because of gson issues */
				StudySubjectsData final_ans;
				List<ItemGroupDatum> itemGroupDatumList; //itemGroupDatumList will be the item set into FormData
				List<ItemDatum> itemDataList;

				/*inits*/
				jsonAllOutput = new JSONObject(output);
				jsonClinicalData = jsonAllOutput.getJSONObject("ClinicalData"); //get clinical data 
				jsonSubjectData = jsonClinicalData.getJSONObject("SubjectData");
				/*end inits*/
				//System.out.println(studySubjectOID); //debug

				/*StudyEventData can be array or not*/
				if(jsonClinicalData.getJSONObject("SubjectData").optJSONArray("StudyEventData") != null){ //StudyEventData is an array
					final_ans = gson.fromJson(jsonClinicalData.toString() , StudySubjectsData.class);
					jsonArrStudyEventData = jsonSubjectData.getJSONArray("StudyEventData");
					//System.out.println("StudyEventData not an array, testing");
				}
				else if(jsonClinicalData.getJSONObject("SubjectData").optJSONObject("StudyEventData") != null)
				{
					jsonSubjectData = jsonClinicalData.getJSONObject("SubjectData");
					Gson gson_special1 = new GsonBuilder().setExclusionStrategies(new exclStratSubjectData()).create(); //this means parse without touching "subjectData"
					final_ans = gson_special1.fromJson(jsonClinicalData.toString(), StudySubjectsData.class); //this works
					Gson gson_special2 = new GsonBuilder().setExclusionStrategies(new exclStratStudyEventData()).create(); //this means parse without touching "studyEventData"
					subjectStudyEventData subjectSingleStudyEvent = gson_special2.fromJson(jsonSubjectData.toString(), subjectStudyEventData.class); //this works

					jsonStudyEventData = jsonSubjectData.getJSONObject("StudyEventData"); //this exists - if checks that
					subjectSingleStudyEvent t = gson.fromJson(jsonStudyEventData.toString(), subjectSingleStudyEvent.class);
					List<subjectSingleStudyEvent> lst = new ArrayList<subjectSingleStudyEvent>();
					lst.add(t);

					jsonArrStudyEventData = new JSONArray();
					jsonArrStudyEventData.put(jsonStudyEventData);

					subjectSingleStudyEvent.setStudyEventData(lst);
					final_ans.setSubjectData(subjectSingleStudyEvent);
				}
				else
				{
					System.out.println(studySubjectOID + " StudyEventData doesn't exist");
					return;
				}
				/*manually parse *some* inner objects because of FormData*/
				for(int i=0; i < jsonArrStudyEventData.length() ;i++)
				{
					jsonsubjectSingleStudyEvent = jsonArrStudyEventData.getJSONObject(i); 	//one event

					//when a event is scheduled but no data entry has started => formdata == null (field does not exist)
					jsonFormData = jsonsubjectSingleStudyEvent.optJSONObject("FormData"); //needed to parse "ItemGroupData"
					//fromData = gson.fromJson(jsonFormData.toString(), FormData.class);
					if(jsonFormData != null){

						itemGroupDatumList = getItemGroupDatumList_from_JSONObjFormData(jsonFormData); ////ItemGroupData can be an array or an object, this function handles it. itemGroupDatumList null if ItemGroupData doesn't exist.
						if(itemGroupDatumList != null){ //FormData has  List<ItemGroupDatum> itemGroupData - *itemGroupDatumList!=null (events exist) - here we parse it by loop
							jsonItemGroupData = jsonFormData.optJSONObject("ItemGroupData"); //parsed for a check - ItemGroupData can be array or object
							if(jsonItemGroupData!=null){ //if not null -> only one item in itemGroupDatumList
								itemDataList = getItemDataList_from_jsonItemGroupData(jsonItemGroupData) ;
								itemGroupDatumList.get(0).setItemData(itemDataList); //0 because there is only 1 element in the list
							}
							else{ /*ItemGroupData is an array */ 
								jsonArrItemGroupData = jsonFormData.optJSONArray("ItemGroupData"); //get this to parse the events
								for(int z=0; z < jsonArrItemGroupData.length() ;z++){
									itemDataList = getItemDataList_from_jsonItemGroupData(jsonArrItemGroupData.getJSONObject(z));
									itemGroupDatumList.get(z).setItemData(itemDataList);
								}
							}
							final_ans.getSubjectData().getStudyEventData().get(i).getFormData().setItemGroupData(itemGroupDatumList);
						}
					}
				}
				//final_ans is all data parsed into StudySubjectsData class!!!! Yes!!
				DataSingleton data = DataSingleton.getInstance();
				data.addSubject(studySubjectOID, final_ans); //add to singleton
			} //end of try
			catch (JSONException err) {
				log.error(err);
			}
		}
	}
	/**
	 * 
	 * @author Gilad Hoshmand

	 *  Helper Method: Returns the list of ItemData elements from jsonItemGroupData
	 * @param jsonItemGroupData
	 * @return
	 */
	private List<ItemDatum> getItemDataList_from_jsonItemGroupData(JSONObject jsonItemGroupData) 
	{
		List<ItemDatum> itemDataList = new ArrayList<ItemDatum>();
		ItemDatum singleItem;
		Type listType = new TypeToken<ArrayList<ItemDatum>>() {}.getType();;

		JSONArray jsonArrItemData;
		JSONObject jsonSingleItemData;

		jsonArrItemData = jsonItemGroupData.optJSONArray("ItemData");
		if(jsonArrItemData != null){
			itemDataList = gson.fromJson(jsonArrItemData.toString(), listType);

		}
		else{ //ItemData was a JSON object, not an array!
			jsonSingleItemData =  jsonItemGroupData.optJSONObject("ItemData");
			singleItem = gson.fromJson(jsonSingleItemData.toString(), ItemDatum.class);
			itemDataList.add(singleItem);
		}
		return itemDataList;
	}

	/**
	 * Helper Method: Returns the list of ItemGroupData from FormData or null if 
	 */
	private List<ItemGroupDatum> getItemGroupDatumList_from_JSONObjFormData(JSONObject jsonFormData)
	{
		JSONArray jsonArrItemGroupData;
		List<ItemGroupDatum> itemGroupDataList = new ArrayList<ItemGroupDatum>();;
		JSONObject jsonItemGroupData;
		ItemGroupDatum itemGroupDatum;
		Type listType;

		jsonItemGroupData = jsonFormData.optJSONObject("ItemGroupData"); //optional, returns null if not found
		if(jsonItemGroupData!=null)// List<ItemGroupDatum> is an item -> we make it a list
		{
			itemGroupDatum = gson.fromJson(jsonItemGroupData.toString(), ItemGroupDatum.class);
			itemGroupDataList.add(itemGroupDatum);
			return itemGroupDataList;
		}
		//else ItemGroupData is an array
		jsonArrItemGroupData = jsonFormData.optJSONArray("ItemGroupData"); //optional, returns null if not found.
		if(jsonArrItemGroupData != null){
			listType = new TypeToken<ArrayList<ItemGroupDatum>>() {}.getType();

			itemGroupDataList = gson.fromJson(jsonArrItemGroupData.toString(), listType);
			return itemGroupDataList; //done!!!
		}
		//null == itemGroupDataList
		/* DEBUG ONLY NOT NEEDED
	System.out.println("getItemGroupData_asJSONArray method: ItemGroupData does not exist - deal with this?");
		 */ 
		return null;
	}

	//endregion

	//search region:

	/**
	 * Function that searches for a mutation in all subjects
	 * StudySubjectsData = all data for a subject
	 */
	public ObservableList<SearchResult> searchAll(String searchStr)
	{
		//TODO: Cleanup
		//List<DataTableRow> results = new ArrayList<DataTableRow>();
		DataSingleton dataSingleton = DataSingleton.getInstance();
		Collection<StudySubjectsData> allSubjects = dataSingleton.getAll();

		List<SearchResult> results_study_events = new ArrayList<SearchResult>();

		allSubjects.forEach(subject -> { //search all subjects
			List<SearchResult> acc ;
			if((acc = subject.searchField(searchStr)) != null ) { //if found anything
				results_study_events.addAll(acc); //append results
				/*results_study_events.forEach(res -> {  //each result will be a match in a specific subject event
					DataTableRow row = new DataTableRow(res.getSubcjectOID() , res.getEventName() , res.getLink());
					results.add(row); //add key to results
				});*/
			}
		});
		ObservableList<SearchResult> ans =  FXCollections.observableArrayList(results_study_events);
		//ObservableList<DataTableRow> ans =  FXCollections.observableArrayList(results);
		return ans;
	}

	/**
	 * @author Gilad Hoshmand
	 * @param searchStr - search term
	 * @return 
	 */
	public List<String> searchAllRaw(String searchStr)
	{
		List<String> results = new ArrayList<String>();
		DataSingleton dataSingleton = DataSingleton.getInstance();
		Collection<StudySubjectsData> allSubjects = dataSingleton.getAll();

		allSubjects.forEach(subject -> {
			if(subject.searchField(searchStr) != null)
				results.add(subject.getSubjectData().getSubjectKey()); //add key to results
		});

		return results;
	}

	/**
	 * @author Gilad Hoshmand
	 * Get all Study Subject OIDs, can be a private for getEventsbyStudySubject
	 * @param studyOid
	 * @return List<String> subjectOIDs
	 */
	private List<String> getAllSubjectsOIDs(String studyOid) {

		String method = studyOid + "/*/*/*";
		String format = "json";

		List<String> subjectOIDs = new ArrayList<String>(); ; //returned

		if (!this.restBaseUrl.endsWith("/")) {
			this.restBaseUrl += "/";
		}

		ClientResponse response = this.getOcRestfulUrl(this.ocUsername, this.ocPassword, format, method);

		// Success
		if (response.getStatus() == 200) {
			String output = response.getEntity(String.class);

			try {
				JSONObject everything = new JSONObject(output);
				JSONObject clinicalData  = everything.getJSONObject("ClinicalData");
				JSONArray subjectData  = clinicalData.getJSONArray("SubjectData");

				if(subjectData != null) //
				{
					for (int i = 0; i < subjectData.length(); i++) {
						subjectOIDs.add(subjectData.getJSONObject(i).getString("@SubjectKey"));
					}
				}
				else // Only one event for current study subject
				{
					System.out.println("only 1 subject - should not happen, check OpenClinicaService.getAllSubjectsOIDs(String studyOid)");
				}
			}
			catch (JSONException err) {
				log.error(err);
			}
		}
		return subjectOIDs;
	}
}

//-------------------------------------------------------------------Junk Yard----------------------------------------------------------------------------------------//

/**
 * Giladhos
 * Write to file function used for debugging purposes.
 */
//public List<StudySubject> getStudyCasebookSubjectsWriteOut(String studyOid) {
//
//	List<StudySubject> results = new ArrayList<>();
//
//	String method = studyOid + "/*/*/*";
//	String format = "json";
//	FileWriter file ;
//
//	if (!this.restBaseUrl.endsWith("/")) {
//		this.restBaseUrl += "/";
//	}
//
//	ClientResponse response = this.getOcRestfulUrl(
//			this.ocUsername,
//			this.ocPassword,
//			format,
//			method
//			);
//
//	// Success
//	if (response.getStatus() == 200) {
//		String output = response.getEntity(String.class);
//
//		try {
//			JSONObject obj = new JSONObject(output);
//
//			JSONObject clinicalData = obj.getJSONObject("ClinicalData");
//			file = new FileWriter("/Users/giladhos/workspace/OC_API/everything.txt") ;
//			file.write(clinicalData.toString());
//			file.close();
//			// Multiple subjects (for one subject it will be json object not an array)
//			//JSONArray subjectData = clinicalData.optJSONArray("SubjectData");
//			JSONObject subjectData = clinicalData.getJSONObject("SubjectData");
//			//String ans = subjectData.toString();
//			String keyOID="";
//			String label="";
//			String unique_id=""; //
//			/*if (subjectData != null) {
//				for (int i = 0; i < subjectData.length(); i++) {
//					// StudySubject(StudySubjectsData data, String studySubjectOID, String personID, label)
//
//					keyOID = (subjectData.getJSONObject(i).getString("@SubjectKey"));
//					label = (subjectData.getJSONObject(i).getString("@OpenClinica:StudySubjectID"));
//					unique_id = (subjectData.getJSONObject(i).optString("@OpenClinica:UniqueIdentifier", ""));
//					StudySubject subject = new StudySubject(null,keyOID, unique_id, label );
//
//					results.add(subject);
//				}
//			}
//			// Only one subject => subjectData is null
//			else {*/
//			JSONObject s = clinicalData.getJSONObject("SubjectData");
//
//			StudySubject subject = new StudySubject();
//			subject.setStudySubjectOID(s.getString("@SubjectKey"));
//			subject.setStudySubjectLabel(s.getString("@OpenClinica:StudySubjectID"));
//			subject.setPersonID(s.optString("@OpenClinica:UniqueIdentifier", ""));
//
//			results.add(subject);
//			//}
//		}
//		catch (JSONException | IOException err) {
//			log.error(err);
//		}
//	}
//
//	return results;
//}