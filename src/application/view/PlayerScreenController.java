package application.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import application.Main;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class PlayerScreenController {

	@FXML
	private Button btnPlay;
	@FXML
	private Button btnPrev;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnRepeat;
	@FXML
	private Button btnSwitchScreen;
	@FXML
	private Button btnSpeaker;
	@FXML
	private Slider durationSlider;
	@FXML
	private Slider volumeSlider;
	@FXML
	private MediaView view;
	@FXML
	private Label labelCurrentTime;
	@FXML
	private Label labelEndTime;

	private MediaPlayer player;
	private Media media;
	
	private Main mainApp;
	
	private double volumeAmount;
	
	//for speaker mute
	private boolean isSoundMuted;
	//for repeat
	private boolean repeating = false;
	
	private Background playBtnBg, pauseBtnBg, prevBtnBg, nextBtnBg, repeatBtnBg, speakerBtnBg, ssBtnBg;
	
	public PlayerScreenController(){
		
		playBtnBg = createBackground("resources/whit_play.png");
		pauseBtnBg = createBackground("resources/pause.png");
		repeatBtnBg = createBackground("resources/repeat.png");
		speakerBtnBg = createBackground("resources/speaker_volume.png");
		ssBtnBg = createBackground("resources/switch_screen_icon.png");
		
	}
	
	private Background createBackground(String path){
		
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(path));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			BackgroundSize bgSize = new BackgroundSize(
					BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
			BackgroundImage bgImg = new BackgroundImage(
					image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
			return new Background(new BackgroundImage[]{bgImg});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(path);
		}
		return null;
	}
	
	public void initilaize(){
		isSoundMuted = false;
	}
	
	public void setMain(Main m){
		mainApp = m;
	}
	
	public void setMedia(String uri){
		media = new Media(uri);
	}
	
	public Media getMedia() {
		return media;
	}

	public void createPlayer(){
		player = new MediaPlayer(media);
		view.setMediaPlayer(player);
		
		player.setOnReady(() -> {
			System.out.println(media.getWidth() + " width, " + media.getHeight() + " height");
			mainApp.getMainStage().setHeight(media.getHeight()+30);
			mainApp.getMainStage().setWidth(media.getWidth()+16);
			setMediaViewDimensions();
			labelEndTime.setText(stringRepresentationOfDuration(media.getDuration().toMillis()));	
		});
		player.setOnEndOfMedia(() -> {
			player.seek(new Duration(0));
			if(repeating == false){ //pause player is he doesnt need to repeat
				player.pause();
				btnPlay.setBackground(playBtnBg);
			}
			
		});
		
		player.currentTimeProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				updateDurationSeeker();	
				labelCurrentTime.setText(stringRepresentationOfDuration(player.getCurrentTime().toMillis()));		
			}
		});
		
		durationSlider.valueProperty().addListener((observable)-> {
			if(durationSlider.isPressed())
				player.seek(media.getDuration().multiply(durationSlider.getValue()/durationSlider.getMax()));
		});
		
		volumeSlider.valueProperty().addListener((observable)-> {
			if(volumeSlider.isPressed()){
				player.setVolume(volumeSlider.getValue()/volumeSlider.getMax());
				volumeAmount = volumeSlider.getValue()/volumeSlider.getMax();
			}
			
		});
		
		btnPlay.setBackground(playBtnBg);
		btnSpeaker.setBackground(speakerBtnBg);
		btnRepeat.setBackground(repeatBtnBg);
		btnSwitchScreen.setBackground(ssBtnBg);
	}
	
	public void setMediaViewDimensions(){
		view.setFitWidth(media.getWidth());
		view.setFitHeight(media.getHeight());
	}

	public MediaView getMediaView() {
		return view;
	}

	public MediaPlayer getPlayer() {
		return player;
	}
	
	private void updateDurationSeeker(){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				durationSlider.setValue( (player.getCurrentTime().toMillis()
						/player.getTotalDuration().toMillis() ) * 100);
				
			}
		});
	}
	
	public void playBtnHandle(){
		Status mediaStatus = player.getStatus();
		if(mediaStatus == Status.PLAYING){
			player.pause();
			btnPlay.setBackground(playBtnBg);
			
		}else if((mediaStatus == Status.HALTED) || (mediaStatus == Status.PAUSED) 
				|| (mediaStatus == Status.STOPPED)){
			player.play();
			btnPlay.setBackground(pauseBtnBg);
		}else if(mediaStatus == Status.READY){
			player.play();
			btnPlay.setBackground(pauseBtnBg);
		}
	}
	
	public void speakerImageHandler(){
		if(isSoundMuted == true){
			player.setVolume(volumeAmount);
			isSoundMuted = false;
		}else{
			player.setVolume(0);
			isSoundMuted = true;
		}
	}
	
	
	public double getVolumeAmount() {
		return volumeAmount;
	}

	public void setVolumeAmount(double volumeAmount) {
		this.volumeAmount = volumeAmount;
	}
	
	public void switchScreenHandle(){
		mainApp.startExplorerScene();
	}
	
	public void handleRepeatBtn(){
		repeating = repeating ? false : true;
	}
	
	private String stringRepresentationOfDuration(double d){
		
		long time = Math.round(d);
		DateFormat df = new SimpleDateFormat("mm:ss");
		Date date = new Date(time);
		return df.format(date);
	}
	
}
