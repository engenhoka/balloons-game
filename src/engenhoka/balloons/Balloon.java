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
import javafx.scene.transform.Transform;
import javafx.util.Duration;

public class Balloon extends Parent {
	private double velocity;
	private boolean alive;

	public Balloon(int colorIndex, final int logoIndex, double velocity, boolean alive0, final long gameTimestamp) {
		this.velocity = velocity;
		this.alive = alive0;
		
		final Image balloon = Resources.balloons[colorIndex];
		final Image pow = Resources.pows[colorIndex];
		final Image partnerLogo = Resources.logos[logoIndex];
		
		final ImageView ballonView = new ImageView(balloon);
		final ImageView powView = new ImageView(pow);
		final ImageView logoView = new ImageView(partnerLogo);
		
		powView.setOpacity(0);
		logoView.setOpacity(0);
		
		powView.setTranslateX(pow.getWidth() * -0.25);
		logoView.setTranslateX(pow.getWidth() * -0.25);
		
		logoView.setBlendMode(BlendMode.MULTIPLY);
		
		getChildren().add(ballonView);
		getChildren().add(powView);
		getChildren().add(logoView);
		
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		
		KeyFrame kf0 = new KeyFrame(Duration.millis(100), new KeyValue(ballonView.opacityProperty(), 0));
		KeyFrame kf1 = new KeyFrame(Duration.millis(200), new KeyValue(powView.opacityProperty(), 1));
		
		double difX = pow.getWidth() / 2 - partnerLogo.getWidth() / 2;
		double difY = pow.getHeight() / 2 - partnerLogo.getHeight() / 2;
		
		KeyFrame kf2 = new KeyFrame(Duration.millis(100)
				, new KeyValue(logoView.opacityProperty(), 1)
				, new KeyValue(logoView.translateXProperty(), logoView.translateXProperty().get() + difX)
				, new KeyValue(logoView.translateYProperty(), logoView.translateYProperty().get() + difY)
		);
		
		KeyFrame kf3 = new KeyFrame(Duration.millis(2500), new KeyValue(powView.opacityProperty(), 0));
		
		timeline.getKeyFrames().addAll(kf0, kf1, kf2, kf3);
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			Transform transform = logoView.getLocalToSceneTransform();
			
			getChildren().remove(logoView);
			
			BalloonsGame.game.animateBalloon(logoIndex, logoView, transform, gameTimestamp);
		}});
		
		setOnMouseClicked(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent event) {
			if(alive) {
				alive = false;
				timeline.play();
				Resources.pop.play();
				
				Transform transform = logoView.getLocalToSceneTransform();
				BalloonsGame.game.incrementScore(logoIndex, transform, gameTimestamp);
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
