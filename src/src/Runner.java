package src;
import java.util.List;

import javafx.application.Application;

import org.apache.log4j.BasicConfigurator;

public class Runner {
	
	/*
	 * TODO: this is just a temp runner to immitate a GUI, now that a GUI exists -> move all functionality inside Service class
	 * 1. move search inside - create a searchAll
	 * THREADS:
	 * 1. 	1.1 Add Thread to take care of data retreival
	 * 		1.2 Thread has to wait until thread 2 is finished
	 * 2. Add Thread to get all strings
	 * 3. Add Thread to run GUI
	 */
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		long stopTime;
		long midTime;
		long runTime;
		BasicConfigurator.configure();
		
		String studyOID = "S_ZFAT3";
		System.out.println("Start Run");

		OpenClinicaService service = new OpenClinicaService();
		service.connect("Gilad", "gilad123", "http://openc1.bgu.ac.il:8080/OpenClinica/rest/", "http://openc1.bgu.ac.il:8080/OpenClinica/");
		//service.connect("root", "clinica15", "http://openc1.bgu.ac.il:8080/OpenClinica/rest/", "http://openc1.bgu.ac.il:8080/OpenClinica/");
		service.printServiceInfo();
		
		/*update all data into singleton*/
		service.updateAllData(studyOID);
		
		System.out.println("Beginning search:");
		List<String> searchResults = service.searchMutation("FGFR2");
		midTime = System.currentTimeMillis();
		//System.out.println("Search ended! \n time: " + (midTime - startTime)/1000 + " secs");
		System.out.println("Subjects that have the mutation:");
		searchResults.forEach(System.out::println);
		
		stopTime = System.currentTimeMillis();
		runTime = stopTime - startTime;
		System.out.println("End of Runner :)");
		System.out.println("Total Run time: " + runTime/1000 + " seconds");
		/*launching GUI*/
		 new GUIFX().main(args);
		 //GUIFX.main(args);
	}
}
