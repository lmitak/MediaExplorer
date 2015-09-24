package application.view;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import application.Main;
import application.controller.DBConnector;
import application.model.MediaStavka;
import application.model.MyStackPane;
import application.model.Type;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class FirstScreenController {
	
	@FXML
	private TextField searchTF;
	@FXML
	private ComboBox<String> tipCB;
	@FXML
	private TilePane tilePane;
	@FXML
	private ImageView imageView;
	@FXML
	private Label labelName;
	@FXML
	private Label labelSize;
	@FXML
	private Label labelLength;
	@FXML
	private Label labelRate;
	@FXML
	private FlowPane flowPane;
	
	//reference to the main app
	private Main mainApp;
	private DBConnector connector;
	//image for play btn
	private Image playBtn;
	
	//list of all media items
	private ArrayList<MediaStavka> mediaList;
	private ArrayList<MyStackPane> mediaViewList;
	
	//dimensions
	private static final double MEDIA_FIT_WIDTH = 200;
	private static final double PLAYER_BUTTON_SIZE = 40;
	
	/*constructor
	 * constructor is called before initialize() method*/
	public FirstScreenController(){
		mediaList = new ArrayList<>();
		mediaViewList = new ArrayList<>();
	}
	
	/*Initializes controller class. 
	 * This method is called automatically after fxml file has been loaded*/
	@FXML
	private void initialize(){
		initializeTipFilter();
		
		connector = new DBConnector();
		new Thread(() -> {
			synchronized (connector) {
				if(connector.openConnection()){
					System.out.println("Database connection succeeded");
				}else{
					System.out.println("Database connection failed");
				}
			}
		}).start();
		
		initializeVideoList();
	}
	
	public void setMain(Main m){
		mainApp = m;
	}
	
	private void initializeTipFilter(){
		ObservableList<String> filterFileOpt = FXCollections.observableArrayList(
				"All Media", "Music", "Image", "Video");
		ObservableList<String> filterAlbumOpt = FXCollections.observableArrayList(
				"All albums", "Image Album", "Music Album", "Video Album");
		ObservableList<String> allFilterOpt = FXCollections.observableArrayList();
		allFilterOpt.add("All");
		allFilterOpt.addAll(filterFileOpt);
		allFilterOpt.addAll(filterAlbumOpt);
		
		tipCB.getItems().addAll(allFilterOpt);
		tipCB.setValue("All");
	}
	
	private void initializeVideoList(){
	
		//retrieving media and putting it in views
		Thread t1 = new Thread(()-> {
			synchronized (connector) {
				mediaList = connector.giveAllMediaItems();
				if(mediaList == null){
					connector.createDatabase();
				}else{
					Platform.runLater(()-> {
						int br = 0;
						for(MediaStavka ms: mediaList){
							MyStackPane s = new MyStackPane(ms);
							s.setId("sp" + br++);
							Media media = new Media(ms.getPath().getValue());
							MediaPlayer player = new MediaPlayer(media);
							MediaView view = new MediaView(player);
							view.setFitWidth(MEDIA_FIT_WIDTH);
							s.getChildren().add(view);
							s.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

								@Override
								public void handle(MouseEvent event) {
									if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
										displayInfoAboutItem(ms);
										s.select();
										deselectOthers(s.getId());
									}
									if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
										Path path = Paths.get("resources/white_play.png");
										try(FileInputStream fis = new FileInputStream(path.toFile());){
											playBtn = new Image(fis);
											ImageView iv = new ImageView(playBtn);
											iv.setFitWidth(PLAYER_BUTTON_SIZE);
											iv.setFitHeight(PLAYER_BUTTON_SIZE);
											iv.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
												@Override
												public void handle(MouseEvent e) {
													if(e.getEventType() == MouseEvent.MOUSE_ENTERED){
														
													}
													if(e.getEventType() == MouseEvent.MOUSE_EXITED){
														
													}
													if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
														mainApp.pozoviPlayScenu(ms.getPath().getValue());
													}
												}
											});
											//treba se postaviti da iv ima svoj handler
											s.getChildren().add(iv);
										} catch (IOException e) {
											e.printStackTrace();
										}
										
									}
									if(event.getEventType() == MouseEvent.MOUSE_EXITED){
										s.getChildren().remove(1);
									}
									
								}

								
			
							});
							tilePane.getChildren().add(s);
							TilePane.setMargin(s, new Insets(0, 10, 0, 0));
							mediaViewList.add(s);
						}
					});
				}
			}
		});
		t1.start();
	}
	
	public void AddMediaToLayout(String path){
		Media media = new Media(path);
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		view.setFitWidth(MEDIA_FIT_WIDTH);
		player.setOnReady(() -> {
			MediaStavka ms = createMediaStavka(media);
			storeMediaInformation(ms);
			MyStackPane sp = new MyStackPane(ms);
			ms.setId(findMaxID());
			sp.getChildren().add(view);
			
			//ovo se treba sredit
			sp.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
						mainApp.pozoviPlayScenu(path);
					}
					if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
						Path path = Paths.get("resources/white_play.png");
						try(FileInputStream fis = new FileInputStream(path.toFile());){
							playBtn = new Image(fis);
							ImageView iv = new ImageView(playBtn);
							iv.setFitWidth(view.getFitWidth()/5);
							iv.setFitHeight(view.getFitWidth()/5);
							
							ImageView iv2 = new ImageView();
							sp.getChildren().add(iv);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
					if(event.getEventType() == MouseEvent.MOUSE_EXITED){
						sp.getChildren().remove(1);
					}
					
				}
			});
			tilePane.getChildren().add(sp);
			TilePane.setMargin(sp, new Insets(0, 10, 0, 0));
			sp.setId("sp" + mediaList.size());
			mediaViewList.add(sp);
			deselectOthers(sp.getId());
			sp.select();		
			});
			
	}

	private int findMaxID() {
		
		MediaStavka firstMs = mediaList.get(0);
		
		for(MediaStavka ms:mediaList){
			if(ms.getId().getValue() > firstMs.getId().getValue()){
				firstMs = ms;
			}
		}
		return firstMs.getId().getValue();
	}

	private void storeMediaInformation(MediaStavka mediaS) {
		new Thread(()-> connector.insertMedia(mediaS)).start();
	}
	
	private MediaStavka createMediaStavka(Media media){
		File file = new File(media.getSource());
		String fileName = file.getName().replace("%20", " ");
		String ext = fileName.substring(fileName.indexOf("."));
		String name = fileName.substring(0, fileName.indexOf("."));
		MediaStavka ms = new MediaStavka(
				name, media.getSource(), media.getDuration().toMillis(), ext, Type.resolveId(ext));
		mediaList.add(ms);
		
		displayInfoAboutItem(ms);
		return ms;
	}
	
	public void deleteMedia(){
		for(MyStackPane mySP : mediaViewList){
			if(mySP.isSelected()){
				new Thread(() -> connector.deleteMedia(mySP.getMedia().getId().getValue())).start();
			}
		}
		for(int i = 0; i < mediaViewList.size(); i++){
			if(mediaViewList.get(i).isSelected()){
				tilePane.getChildren().remove(i);
				mediaViewList.remove(i);
				mediaList.remove(i);
			}
		}
		deselectAll();
	}
	
	
	private String stringRepresentationOfDuration(double d){
		Duration duration = new Duration(d);
		long time = Math.round(d);
		DateFormat df = new SimpleDateFormat("mm:ss");
		Date date = new Date(time);
		
		return df.format(date);
	}
	
	//deselect other containers except last one selected
	private void deselectOthers(String id) {
		for(MyStackPane mySP : mediaViewList){
			if(mySP.getId() != id){
				mySP.deselect();
			}
		}
	}
	
	private void deselectAll(){
		for(MyStackPane mySP : mediaViewList){
			mySP.deselect();
		}
	}

	private void displayInfoAboutItem(MediaStavka ms) {
		labelName.setText(ms.getIme().getValue());
		labelLength.setText(stringRepresentationOfDuration(ms.getDuration()));	
		labelRate.setText(Double.toString(ms.getRate()));
	}
	
	
}
