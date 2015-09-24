package application.model;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;

public class MyStackPane extends StackPane {

	private boolean isSelected;
	private Paint paint;
	private Border border;
	private MediaStavka media;
	
	public MyStackPane(MediaStavka ms) {
		super();
		isSelected = false;
		//Stop[] stops = new Stop[] { new Stop(0, Color.rgb(15, 15, 15, 0.8)), new Stop(1, Color.rgb(15, 15, 15, 0)) };
		//LinearGradient linearGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		BorderWidths bw = new BorderWidths(2, 2, 2, 2);
		CornerRadii radius = new CornerRadii(5);
		paint = Paint.valueOf(Color.AQUA.toString());
		BorderStroke[] stroke = {new BorderStroke(paint, BorderStrokeStyle.SOLID, radius, bw)};
		border = new Border(stroke);
		media = ms;
	}
	
	public void select(){
		isSelected = true;
		this.setBorder(border);
	}
	
	public void deselect(){
		isSelected = false;
		this.setBorder(null);
	}
	
	public boolean isSelected(){
		return isSelected;
	}

	public MediaStavka getMedia() {
		return media;
	}
	
	
	
	
}
