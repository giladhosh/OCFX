package src;

import java.io.IOException;

//Imports are listed in full to show what's being used
//could just import javafx.*
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.jfoenix.controls.*;

//<BorderPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" >

//fx:controller="naivgationdrawer.SidePanelContentController">
/*
 * This code uses a BorderPane as a container for two FlowPanes and a Button. 
 * The first FlowPane contains a Label and ChoiceBox, the second FlowPane a Label and a ListView. 
 * The Button switches the visibility of each FlowPane.
 */
public class GUIFX extends Application {
	OpenClinicaService service;
	
	public GUIFX() {
		super();
		this.service = new OpenClinicaService();
	}

	//JavaFX applicatoin still use the main method.
	//It should only ever contain the call to the launch method
	public static void main(String[] args) {
		launch(args);
	}

	//starting point for the application
	//this is where we put the code for the user interface
	@Override
	public void start(Stage primaryStage) {
		//The primaryStage is the top-level container
		primaryStage.setTitle("OpenClinica Search Utility");
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/OCFX.fxml"));
		 try {
			 System.out.println(loader.getLocation());
			 
			Parent root = loader.load();
			Scene scene = new Scene(root, 300, 275);
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//move out to function
		Button updateButton = new Button("Update Data");
		updateButton.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				//Runner run = new Runner();
				service.connect("Gilad", "gilad123", "http://openc1.bgu.ac.il:8080/OpenClinica/rest/", "http://openc1.bgu.ac.il:8080/OpenClinica/");
				service.updateAllData("S_ZFAT3");
			}
		});
		

	}
}