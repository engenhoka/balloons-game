package engenhoka.balloons;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Balloon extends Parent {
	private double velocity;
	
	private boolean alive = true;

	public Balloon(Image balloon, Image pow, double velocity) {
		this.velocity = velocity;
		
		ImageView imageView = new ImageView(balloon);
		ImageView powView = new ImageView(pow);
		//ImageView powView = new ImageView(Resources.pow);
//		powView.setScaleX(0.5);
//		powView.setScaleY(0.5);
//		powView.setTranslateX(pow.getWidth() * -0.5);
		powView.setOpacity(0);
		
		getChildren().add(imageView);
		getChildren().add(powView);
		
		final Timeline timeline = new Timeline();
		KeyFrame kf0 = new KeyFrame(Duration.millis(100), new KeyValue(imageView.opacityProperty(), 0));
		KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(powView.opacityProperty(), 1));
		KeyFrame kf2 = new KeyFrame(Duration.millis(500), new KeyValue(powView.translateXProperty(), powView.translateXProperty().get() - 50));
		//KeyFrame kf3 = new KeyFrame(Duration.millis(600), new KeyValue(powView.opacityProperty(), 0));
		timeline.getKeyFrames().addAll(kf0, kf1, kf2);
		timeline.setOnFinished(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			alive = false;
		}});
		
		setOnMouseClicked(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent event) {
			timeline.play();
			
			Resources.pop.play();
		}});
	}
	
	public boolean isAlive() {
		return alive;
	}

	public double getVelocity() {
		return velocity;
	}
}
