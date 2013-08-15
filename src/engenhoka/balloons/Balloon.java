package engenhoka.balloons;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Balloon extends Parent {
	private static final AudioClip balloonBurst = new AudioClip(Balloon.class.getResource("balloon-burst.wav").toString());

	private double velocity;

	public Balloon(Image image, double velocity) {
		this.velocity = velocity;
		
		ImageView imageView = new ImageView(image);
		ImageView powView = new ImageView(Resources.pow);
//		powView.setScaleX(0.5);
//		powView.setScaleY(0.5);
//		powView.setTranslateX(pow.getWidth() * -0.5);
		powView.setOpacity(0);
		
		getChildren().add(imageView);
		getChildren().add(powView);
		
		final Timeline timeline = new Timeline();
		KeyFrame kf0 = new KeyFrame(Duration.millis(100), new KeyValue(imageView.opacityProperty(), 0));
		KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(powView.opacityProperty(), 1));
		KeyFrame kf2 = new KeyFrame(Duration.millis(500), new KeyValue(powView.opacityProperty(), 1));
		KeyFrame kf3 = new KeyFrame(Duration.millis(600), new KeyValue(powView.opacityProperty(), 0));
		timeline.getKeyFrames().addAll(kf0, kf1, kf2, kf3);
		
		setOnMouseClicked(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent event) {
			timeline.play();
			
			Resources.pop.play();
		}});
	}

	public double getVelocity() {
		return velocity;
	}
}
