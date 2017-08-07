/*
 * Copyright (c) 2012 , 2013 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 *  NOT USED AS OF NOW
 */
package src;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/*
 * TODO:
 * GUI:
 * 1. (2nd) Add Preloader while all data gets retreived (after adding threads).									
 * V	2. 	2.1 Add Pane for data.																					
 * V   		2.2 Add parse results																				
 * V	3. 	3.1 Add search text and labels in new pane.															
 * V	  	3.2 Add button that runs search method.																
 * V   		3.3 add search funtion
 * V	4. display results.
 * 
 * Data:
 * V	1. (2nd) move search inside - create a searchAll.
 * 2. (2nd)add link to each result || add link field to all when retrieved (data).
 * 
 * THREADS: (inside service?)
 * 1. (1st) Add Thread to get all strings.
 * 2. (1st) Add Thread for data retrieval (Thread has to wait until 'get strings' thread is finished).
 * 3. (1st) Add Thread to run GUI.
 * 
 * GENERAL:
 * 1. (3rd) Check usage of constants and update class to use current values.
 * 2. (3rd) check need - split data from GUI.
 * 3. V (3rd) keep runner? - yes! for debugging.
 * 4. (3rd) StudySubject class not used? 
 * 5. OpenClinica service cleanup:
 *    (3rd) 5.1 Method loginUserAccount(String username, String password) not used?
 *    (3rd) 5.2 Clean commented out code.
 * (3rd) 6. UserWithId used?
 */


/**
 * The Main class is an extension of the javafx.application.Application class. 
 */
public class Main extends Application {
	 
	@FXML
	private TableView<SearchResult> tbl_data;
	@FXML
	private TableColumn<SearchResult,String> tbl_col_subjectOID;
	@FXML
	private TableColumn<SearchResult,String> tbl_col_event;
	@FXML
	private TableColumn<SearchResult,URL> tbl_col_link;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(Main.class, args);
	}

	/**
	 * start method is overridden and receives a Stage object (a top-level GUI container) as its only parameter.
	 * The primaryStage is the top-level container.
	 * The root node is loaded form FXML file and then passed to the scene's constructor, along with the scene's width and height.
	 * The stage's title, scene, and visibility are all set.
	 */
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/OCFX.fxml"));
		try {
			System.out.println(loader.getLocation());
			Parent root = loader.load(); //FXML document which gets loaded into a Parent object
			Scene scene = new Scene(root, 300, 275);
			primaryStage.setTitle("OpenClinica Search Utility");
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) { //loader.load() throws
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
