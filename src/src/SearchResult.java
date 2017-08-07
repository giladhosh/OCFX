package src;

import java.net.URL;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class represents all data returned from the different searches used.
 * The relevant data will be moved into the relevant DataTableRow
 * @author Gilad
 *
 */
public class SearchResult {
	private final SimpleStringProperty eventName;
	private final SimpleStringProperty subjectOID; 
	private URL link; //maybe get this through GET POST 
	
	public SearchResult(String subjectOID, String eventName, URL link) {
		this.subjectOID = new SimpleStringProperty(subjectOID);
		this.eventName = new SimpleStringProperty(eventName);
		this.link = link;
	}
	
	public SimpleStringProperty subjectOIDProperty() {
		return subjectOID;
	}
	public void setSubjectOID(String subcjectOID) {
		this.subjectOID.set(subcjectOID);
	}
	public SimpleStringProperty eventNameProperty() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName.set(eventName);
	}
	public URL getLink() {
		return link;
	}
	public void setLink(URL link) {
		this.link = link;
	}
	
}
