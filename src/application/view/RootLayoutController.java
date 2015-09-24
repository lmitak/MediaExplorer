package application.view;

import java.io.File;
import java.net.MalformedURLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.FileChooser;

public class RootLayoutController {
	
	/*
	@FXML
	private MenuBar bar;
	@FXML
	private Menu menu_file;
	@FXML
	private Menu menu_edit;
	@FXML
	private Menu menu_help;
	*/
	@FXML
	private SplitMenuButton menu_media;
	@FXML
	private SplitMenuButton menu_album;
	@FXML
	private SplitMenuButton menu_artist;
	@FXML
	private SplitMenuButton menu_genre;
	@FXML
	private Button btn_about;
	//menus of edit
	/*
	@FXML
	private Menu menu_media;
	@FXML
	private Menu menu_album;
	@FXML
	private Menu menu_artist;
	@FXML
	private Menu menu_genre;
	*/
	//menu items of media menu
	@FXML
	private MenuItem mediaAdd;
	@FXML
	private MenuItem mediaFolderAdd;
	@FXML
	private MenuItem deleteMedia;
	
	//menu items of album menu
	@FXML
	private MenuItem albumCreate;
	@FXML
	private MenuItem albumEdit;
	@FXML
	private MenuItem albumDelete;
	
	//menu items of genre menu
	@FXML
	private MenuItem genreCreate;
	@FXML
	private MenuItem genreDelete;
	
	//menu items of artist menu
	@FXML
	private MenuItem artistCreate;
	@FXML
	private MenuItem artistEdit;
	@FXML
	private MenuItem artistDelete;
	
	private Main mainApp;
	

	public RootLayoutController(){}
	
	public void initialize(){}
	
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
	
	public void addMediaAction(){
		File file = mainApp.startFileChooser();
		if(file != null){
			try {
				mainApp.addMediaHandler(file.toURI().toURL().toExternalForm().toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteMediaAction(){
		mainApp.getFSC().deleteMedia();
	}
	
	public void resizeRootLayoutButtons(){
		double screenWidth = mainApp.getMainStage().getScene().getWidth();
		double aboutBtnWidth = btn_about.getMinWidth();
		menu_media.setPrefWidth((screenWidth - aboutBtnWidth) / 4);
		menu_album.setPrefWidth((screenWidth - aboutBtnWidth) / 4);
		menu_artist.setPrefWidth((screenWidth - aboutBtnWidth) / 4);
		menu_genre.setPrefWidth((screenWidth - aboutBtnWidth) / 4);
		
		
		for(MenuItem item:menu_media.getItems()){
			System.out.println(item.getParentMenu());
			Label lbl = (Label) item.getGraphic();
			lbl.setPrefWidth(((screenWidth - aboutBtnWidth) / 4)-25);
		}
		for(MenuItem item:menu_album.getItems()){
			Label lbl = (Label) item.getGraphic();
			lbl.setPrefWidth(((screenWidth - aboutBtnWidth) / 4)-25);
		}
		for(MenuItem item:menu_artist.getItems()){
			Label lbl = (Label) item.getGraphic();
			lbl.setPrefWidth(((screenWidth - aboutBtnWidth) / 4)-25);
		}
		for(MenuItem item:menu_genre.getItems()){
			Label lbl = (Label) item.getGraphic();
			lbl.setPrefWidth(((screenWidth - aboutBtnWidth) / 4)-25);
		}
	}

}
