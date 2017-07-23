package src;
/**
 * Class holds all data from study(Singleton)
 * data is a hashmap from unique string StudySubjectOID to an array of all events
 */
import java.util.Collection;
import java.util.HashMap;

import org.json.JSONArray;

public class DataSingleton {

	private static DataSingleton instance = null;
	private HashMap<String,StudySubjectsData> data;
	
	/**
	 * Constructor private, used by getInstance only!
	 */
	private DataSingleton() {
		data = new HashMap<String,StudySubjectsData>();
	};
	
	public static DataSingleton getInstance()
	{
		if(instance == null)
		{
			instance = new DataSingleton();	
		}
		return instance;
	}
	
	public void addSubject(String subjectOID, StudySubjectsData fullData)
	{
		data.put(subjectOID, fullData);
	}
	
	public StudySubjectsData getSubject(String key)
	{
		return data.get(key);
	}
	
	public Collection<StudySubjectsData> getAll()
	{
		return data.values();
	}
	
	
	public void printAll()
	{
		data.forEach((key,fullData)->
		System.out.println("Subject: " + key));
	}

}
