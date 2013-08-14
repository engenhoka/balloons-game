package engenhoka.balloons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BalloonsGame extends Application {

	private static final double DELTA_TIME = 0.03333;
	
	private List<Balloon> balloons = new ArrayList<Balloon>();

	private Group root;
	private double time;

	private Image balloonBlue;
	private Image balloonGreen;
	private Image balloonPink;
	private Image balloonRed;
	private Image balloonYellow;
	private Image balloonOrange;
	
	private Image[] images;

	private Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Balloons Game");
		stage.setWidth(500);
		stage.setHeight(700);
		//stage.setFullScreen(true);
		
		root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.BLUE);
		
		balloonBlue = new Image(BalloonsGame.class.getResourceAsStream("balloon-blue.png"));
		balloonGreen = new Image(BalloonsGame.class.getResourceAsStream("balloon-green.png"));
		balloonPink = new Image(BalloonsGame.class.getResourceAsStream("balloon-pink.png"));
		balloonRed = new Image(BalloonsGame.class.getResourceAsStream("balloon-red.png"));
		balloonYellow = new Image(BalloonsGame.class.getResourceAsStream("balloon-yellow.png"));
		balloonOrange = new Image(BalloonsGame.class.getResourceAsStream("balloon-orange.png"));
		
		images = new Image[] {
				balloonBlue,
				balloonGreen,
				balloonPink,
				balloonRed,
				balloonYellow,
				balloonOrange
		};
		
		createBalloon();
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.millis(33.33), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			update();
		}});
		
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		stage.setScene(scene);
		stage.show();
	}

	private void createBalloon() {
		int index = random.nextInt(images.length);
		Image image = images[index];
		Balloon balloon = new Balloon(image);
		
		balloon.setTranslateY(root.getScene().getHeight());
		balloon.setTranslateX(random.nextDouble() * (root.getScene().getWidth() - image.getWidth()));
		root.getChildren().add(balloon);
		balloons.add(balloon);
	}

	private void update() {
		time += DELTA_TIME;
		if(time > 1.0) {
			time = 0;
			createBalloon();
		}
		
		Iterator<Balloon> iterator = balloons.iterator();
		while(iterator.hasNext()) {
			Balloon balloon = iterator.next();
			
			//balloon.setRotate(balloon.getRotate() + 20*DELTA_TIME);
			balloon.setTranslateY(balloon.getTranslateY() - 350*DELTA_TIME);

			if(balloon.getTranslateY()+balloon.getBoundsInLocal().getHeight() < 0) {
				root.getChildren().remove(balloon);
				iterator.remove();
			}
		}
	}

}
