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
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
	
	private Timeline balloonsTimeline = new Timeline();

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
		
		balloonsTimeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.millis(33.33), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			update();
		}});
		
		balloonsTimeline.getKeyFrames().add(kf);
		//balloonsTimeline.play();
		
		controls(root);
		clock(root);
		
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

	private void controls(Group root) {
		Button playButton = new Button("Play / Pause");
		playButton.setTranslateX(370);
		playButton.setTranslateY(100);
		
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Play / Pause");
				running = !running;
				countDown = 30;
				clockText.setText("30");
				
				if (running) {
					clockTimeline.play();
					balloonsTimeline.play();
				}
				else {
					clockTimeline.stop();
					balloonsTimeline.stop();
				}
			}
		});
		
		root.getChildren().add(playButton);
	}
	
	private int countDown;
	
	private boolean running;
	
	private Timeline clockTimeline = new Timeline();
	
	private final Text clockText = new Text(380, 80, "30");
	
	private void clock(Group root) {
		clockTimeline.setCycleCount(Timeline.INDEFINITE);
		
		clockText.setFont(Font.font ("Verdana",FontWeight.BOLD,  40));
		clockText.setFill(Color.WHITESMOKE);
		
		InnerShadow is = new InnerShadow();
		is.setOffsetX(3.0f);
		is.setOffsetY(3.0f);
		
		clockText.setEffect(is);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			if (running) {
				clockText.setText(String.valueOf(countDown));
				countDown--;
				if (countDown == 0)
					timeout();
			}
		}});
		
		clockTimeline.getKeyFrames().add(kf);
		
		root.getChildren().add(clockText);
	}

	protected void timeout() {
		running = false;
		clockTimeline.stop();
		balloonsTimeline.stop();
		clockText.setText("..");
	}
}
