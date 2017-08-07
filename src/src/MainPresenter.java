package src;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Gilad
 * Fields marked with the @FXML annotation are injected by the JavaFX runtime 
 */
public class MainPresenter implements Initializable {
	
	//@Inject
   // @Named("OCService")
	// Use inject ????
    private OpenClinicaService service;
	
	/*
	 * fxml controls 
	 */
    @FXML
    private JFXButton b_home;
    @FXML
    private JFXButton b_support;
    @FXML
    private JFXButton b_update;
    @FXML
    private JFXButton b_about;
    @FXML
    private JFXButton b_exit;
    @FXML
    private BorderPane root;
    @FXML
    private TableView<SearchResult> tbl_data;
    @FXML
    private TextField txt_search_all;
    
    
    @FXML
    TableColumn<SearchResult, String> tbl_col_subjectOID;
    @FXML
    TableColumn<SearchResult, String> tbl_col_event;
    @FXML
    TableColumn<SearchResult, URL> tbl_col_link;

    
    public static BorderPane rootP;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new OpenClinicaService();
		rootP = root;
		
	}
	
    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch(btn.getText()) //change to any or use id?
        {
            case "Color 1":MainPresenter.rootP.setStyle("-fx-background-color:#00FF00");
                break;
            case "Color 2":MainPresenter.rootP.setStyle("-fx-background-color:#0000FF");
                break;
            case "Color 3":MainPresenter.rootP.setStyle("-fx-background-color:#FF0000");
                break;
        }
    }

    @FXML
    private void updateDataClick(ActionEvent event) {
				//Runner run = new Runner();
				service.connect("Gilad", "gilad123", "http://openc1.bgu.ac.il:8080/OpenClinica/rest/", "http://openc1.bgu.ac.il:8080/OpenClinica/");
				service.updateAllData("S_ZFAT3");
    }
    

    /*
     * Search region
     */

    @FXML  
    private void searchAll(ActionEvent event) {
    	String searchStr = txt_search_all.getText();
    	//run service.searchAllRaw(searchStr);
    	
    	ObservableList<SearchResult> data = service.searchAll(searchStr);
    	if(data!=null && !data.isEmpty()) //TODO: remove redundant
    	{
    		System.out.println("got  to Presenter.searchAll");
    		PropertyValueFactory p = new PropertyValueFactory<SearchResult,String>("subjectOID");
    		PropertyValueFactory p1 = new PropertyValueFactory<SearchResult,String>("eventName");
    		
    		tbl_col_event.setCellValueFactory(new PropertyValueFactory<SearchResult,String>("eventName"));
    		tbl_col_subjectOID.setCellValueFactory(new PropertyValueFactory<SearchResult,String>("subjectOID"));
			tbl_col_link.setCellValueFactory(new PropertyValueFactory<SearchResult,URL>("link"));
    		//tbl_data.setEditable(true);
    		//tbl_data.setEditable(true);
    		tbl_data.setItems(data);
    		//data.add(data.get(0));
    		//tbl_data.setEditable(false);
    	}
    	System.out.println();
}
    
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}

/*
 * results.layoutXProperty().bind(pane.widthProperty().subtract(results.widthProperty()).divide(2));
 */
