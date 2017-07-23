package src;
/*
	Copyright 2012 VU Medical Center Amsterdam
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

//import java.util.ArrayList;
import java.util.Date;
//import java.util.GregorianCalendar;


//import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import org.openclinica.ws.beans.StudySubjectWithEventsType;

/**
 * Simple representation of a Study Subject
 *
 * @author Arjan van der Velde (a.vandervelde (at) xs4all.nl) - original
 * @author tomas@skripcak.net - enhanced
 * @since 11 Jun 2013
 */
public class StudySubject {

    //region Members
	
	public StudySubject(StudySubjectsData data, String studySubjectOID,
			String personID , String studySubjectLabel) {
		super();
		this.data = data;
		this.studySubjectOID = studySubjectOID;
		this.personID = personID;
		this.studySubjectLabel = studySubjectLabel;
	}

	//ClinicalData
	private StudySubjectsData data;
	
    /** XML data type factory */
	//private DatatypeFactory dataTypeFactory;
	/** Study Subject OID */
	private String studySubjectOID;
	/** Study Subject Person ID a.k.a. unique identifier */
	private String personID;
	/** Study Subject Label (== study number) */
	private String studySubjectLabel;
    /** Study Subject Secondary Label */
    private String studySubjectSecondaryLabel;
	/** Study Subject gender */
	private String sex;
	/** Study Subject date of birth */
	private XMLGregorianCalendar dateOfBirth;
	/** Study Subject date of registration */
	private XMLGregorianCalendar dateOfRegistration;
	/** Events scheduled for this Study Subject */
	//private ArrayList<ScheduledEvent> scheduledEvents;
	/** The Study this is a subject for */
	//private Study study;

    //endregion

    //region Constructors


    //endregion

    //region Methods

    /**
	 * Update this study subject from a OC StudySubjectWithEventsType object
	 * @param subject the OC StudySubjectWithEventsType to use
	 */


    //endregion

    //region Properties

    /**
	 * Get PersonID
	 * @return personID
	 */
	public String getPersonID() {
		return personID;
	}

	/**
	 * Set personID
	 * @param personID personID
	 */
	public void setPersonID(String personID) {
		this.personID = personID;
	}

	/**
	 * Get studySubjectLabel
	 * @return studySubjectLabel
	 */
	public String getStudySubjectLabel() {
		return studySubjectLabel;
	}

	/**
	 * Set studySubjectLabel
	 * @param studySubjectLabel studySubjectLabel
	 */
	public void setStudySubjectLabel(String studySubjectLabel) {
		this.studySubjectLabel = studySubjectLabel;
	}

    /**
     * Get studySubject Secondary Label
     * @return studySubjectSecondaryLabel
     */
    public String getStudySubjectSecondaryLabel() {
        return studySubjectSecondaryLabel;
    }

    /**
     * Set studySubjectSecondaryLabel
     * @param studySubjectSecondaryLabel studySubjectSecondaryLabel
     */
    public void setStudySubjectSecondaryLabel(String studySubjectSecondaryLabel) {
        this.studySubjectSecondaryLabel = studySubjectSecondaryLabel;
    }

	/**
	 * Get sex!
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Set sex
	 * @param sex sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Get date of birth
	 * @return dateOfBirth
	 */
	public XMLGregorianCalendar getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Set dateOfBirth
	 * @param dateOfBirth dateOfBirth
	 */
	public void setDateOfBirth(XMLGregorianCalendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		this.dateOfBirth.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Set dateOfBirth from String
	 * @param dateOfBirth dateOfBirth as String (yyyy-mm-dd)
	 */
	//public void setDateOfBirth(String dateOfBirth) {
	//	setDateOfBirth(dataTypeFactory.newXMLGregorianCalendar(dateOfBirth));
	//}

	//region DateOfRegistration

	/**
	 * Get registration date
	 * @return dateOfRegistration
	 */
	public XMLGregorianCalendar getDateOfRegistration() {
		return this.dateOfRegistration;
	}

	/**
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
	public Date getComparableDateOfRegistration(){
		return this.dateOfRegistration != null ? this.dateOfRegistration.toGregorianCalendar().getTime() : null;
	}

	/**
	 * Set registration date
	 * @param dateOfRegistration dateOfRegistration
	 */
	public void setDateOfRegistration(XMLGregorianCalendar dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
		this.dateOfRegistration.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	}

	/**
	 * Set registration date from String
	 * @param dateOfRegistration dateOfRegistration as String (yyyy-mm-dd)
	 */
	//public void setDateOfRegistration(String dateOfRegistration) {
	//	setDateOfRegistration(dataTypeFactory.newXMLGregorianCalendar(dateOfRegistration));
	//}

	//endregion

	/**
	 * Get events scheduled for this subject
	 * @return list of events
	 */

	/**
	 * Set schedules events list
	 * @param scheduledEvents Scheduled events
	 */


	/**
	 * Get subject OID
	 * @return the studySubjectOID
	 */
	public String getStudySubjectOID() {
		return studySubjectOID;
	}

	/**
	 * Set subject OID
	 * @param studySubjectOID the studySubjectOID to set
	 */
	public void setStudySubjectOID(String studySubjectOID) {
		this.studySubjectOID = studySubjectOID;
	}

	/**
	 * Get study
	 * @return the study
*/

	/**
	 * Set study
	 * @param study the study to set
	 */


    //endregion

    //region Overrides

    @Override
	public String toString() {
		return "StudySubject: OID: " + studySubjectOID + ", personID: " + personID + ", studySubjectLabel: "
				+ studySubjectLabel + ", studySubjectSecondaryLabel: " + studySubjectSecondaryLabel + ", sex: "
                + sex + ", dateOfBirth: " + dateOfBirth + ", dateOfRegistration: "
				+ dateOfRegistration + ", StudySubject Events: " + "taken out manually (Giladhos@post.bgu.ac.il)";
	}

	@Override
	public int hashCode() {
		return (personID + studySubjectLabel).hashCode();
	}

    //endregion

}
