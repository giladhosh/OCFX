package src;

import java.util.List;
import org.apache.log4j.BasicConfigurator;

/**
 * This is a runner to test features
 * @author Gilad
 *
 */
public class Runner {
	
	/*
	 * TODO: this is just a temp runner to immitate a GUI, now that a GUI exists -> 
	 * move all functionality inside Service and Main class:
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
		List<String> searchResults = service.searchAllRaw("FGFR2");
		midTime = System.currentTimeMillis();
		//System.out.println("Search ended! \n time: " + (midTime - startTime)/1000 + " secs");
		System.out.println("Subjects that have the mutation:");
		searchResults.forEach(System.out::println);
		
		stopTime = System.currentTimeMillis();
		runTime = stopTime - startTime;
		System.out.println("End of Runner :)");
		System.out.println("Total Run time: " + runTime/1000 + " seconds");
		/*launching GUI*/
		 new Main().main(args);
		 //GUIFX.main(args);
	}
}
