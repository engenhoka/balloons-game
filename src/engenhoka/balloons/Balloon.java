package engenhoka.balloons;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Balloon extends Parent {
	private double velocity;
	private boolean alive = true;

	public Balloon(int colorIndex, final int logoIndex, double velocity) {
		this.velocity = velocity;
		
		Image balloon = Resources.balloons[colorIndex];
		Image pow = Resources.pows[colorIndex];
		Image partnerLogo = Resources.logos[logoIndex];
		
		ImageView imageView = new ImageView(balloon);
		ImageView powView = new ImageView(pow);
		ImageView logoView = new ImageView(partnerLogo);
//		powView.setScaleX(0.5);
//		powView.setScaleY(0.5);
//		powView.setTranslateX(pow.getWidth() * -0.5);
		powView.setOpacity(0);
		logoView.setOpacity(0);
		logoView.setBlendMode(BlendMode.MULTIPLY);
		
		getChildren().add(imageView);
		getChildren().add(powView);
		getChildren().add(logoView);
		
		final Timeline timeline = new Timeline();
		KeyFrame kf0 = new KeyFrame(Duration.millis(100), new KeyValue(imageView.opacityProperty(), 0));
		KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(powView.opacityProperty(), 1));
		
		//KeyFrame kf2 = new KeyFrame(Duration.millis(500), new KeyValue(powView.translateXProperty(), powView.translateXProperty().get() - 50));
		
		double difX = pow.getWidth() / 2 - partnerLogo.getWidth() / 2;
		double difY = pow.getHeight() / 2 - partnerLogo.getHeight() / 2;
		
		KeyFrame kf2 = new KeyFrame(Duration.millis(100), 
				new KeyValue(logoView.opacityProperty(), 1)
				,new KeyValue(logoView.translateXProperty(), logoView.translateXProperty().get() + difX)
				,new KeyValue(logoView.translateYProperty(), logoView.translateYProperty().get() + difY)
		);
		
		timeline.getKeyFrames().addAll(kf0, kf1, kf2);//, kf3);
		timeline.setOnFinished(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			alive = false;
		}});
		
		setOnMouseClicked(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent event) {
			if(alive) {
				alive = false;
				timeline.play();
				Resources.pop.play();
				
				BalloonsGame.game.hitBalloon(logoIndex);
			}
		}});
	}
	
	public boolean isAlive() {
		return alive;
	}

	public double getVelocity() {
		return velocity;
	}
}
