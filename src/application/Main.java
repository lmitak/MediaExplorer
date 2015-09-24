package application;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.controller.DBConnector;
import application.model.AudioFormats;
import application.model.ImageFormats;
import application.model.VideoFormats;
import application.view.FirstScreenController;
import application.view.PlayerScreenController;
import application.view.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;



public class Main extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    private StackPane playerLayout;
    private Scene dbScene, playerScene;
    private FirstScreenController fsc;
    //private DBConnector connector;
    
    private FileChooser fileChooser;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		initRootLayout();
		initBodyLayout();
		fileChooser = new FileChooser();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			RootLayoutController rlc = loader.getController();
			rlc.setMainApp(this);
			
			dbScene = new Scene(rootLayout, 800, 600);
			primaryStage.setScene(dbScene);
			primaryStage.setTitle("MediaExlporer");
			rlc.resizeRootLayoutButtons();
			primaryStage.show();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initBodyLayout(){
		BorderPane bodyPane = null;	
		try {
			FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("view/FirstScreen.fxml"));
			bodyPane = loader2.load();
			
			rootLayout.setCenter(bodyPane);
			
			//give the controller access to main
			fsc = loader2.getController();
			fsc.setMain(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void pozoviPlayScenu(String path){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PlayerScreen.fxml"));
			
			playerLayout = (StackPane) loader.load();
			PlayerScreenController playerController = loader.getController();
			
			playerController.setMain(Main.this);
			
			playerController.setMedia(path);
			playerController.createPlayer();
			
			playerScene = new Scene(playerLayout);
			
			primaryStage.setScene(playerScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startExplorerScene(){
		primaryStage.setScene(dbScene);
	}
	
	public Stage getMainStage(){
		return primaryStage;
	}
	
	public File startFileChooser(){
		List<String> audioTypes = new ArrayList<>();
		List<String> videoTypes = new ArrayList<>();
		ArrayList<String> imageTypes = new ArrayList<>();
		StringBuffer audioDesc = new StringBuffer("Audio files (");
		StringBuffer videoDesc = new StringBuffer("Video files (");
		StringBuffer imageDesc = new StringBuffer("Image files (");
		
		
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("All files (*.*)", new String[]{"*.*"}));
		
		for(VideoFormats vf:VideoFormats.values()){
			videoTypes.add("*" + vf.getExt());
			videoDesc.append("*" + vf.getExt() + ";");
		}
		videoDesc.append(")");
		FileChooser.ExtensionFilter videoExtFilter = 
				new FileChooser.ExtensionFilter(videoDesc.toString(), videoTypes);
		fileChooser.getExtensionFilters().add(videoExtFilter);
		for(AudioFormats af: AudioFormats.values()){
			audioTypes.add("*" + af.getExt());
			audioDesc.append("*" + af.getExt() + ";");
		}
		audioDesc.append(")");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(audioDesc.toString(), audioTypes));
		for(ImageFormats imgf: ImageFormats.values()){
			imageTypes.add("*" + imgf.getExt());
			imageDesc.append("*" + imgf.getExt() + ";");
		}
		imageDesc.append(")");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(imageDesc.toString(), imageTypes));
		
		return fileChooser.showOpenDialog(primaryStage);
		
	}
	
	public void addMediaHandler(String uri){
		fsc.AddMediaToLayout(uri);
	}
	
	public FirstScreenController getFSC(){
		return fsc;
	}

	/*public DBConnector getConnector() {
		return connector;
	}*/
}
