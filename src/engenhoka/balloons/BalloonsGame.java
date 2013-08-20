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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	private GameState state;
	private Node currentScene;

	private Group root;
	private double time;

	private Image[] images;

	private Random random = new Random(System.currentTimeMillis());
	
	private Timeline balloonsTimeline = new Timeline();
	private Timeline clockTimeline = new Timeline();

	private double velocity = 350;

	private int countDown;
	
	private boolean running;

	private Scene scene;
	
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
		scene = new Scene(root);
		scene.setFill(Color.BLUE);
		stage.setScene(scene);
		
		Background background = new Background();
		background.widthProperty().bind(scene.widthProperty());
		background.heightProperty().bind(scene.heightProperty());
		root.getChildren().add(background);
		
		ImageView logo = new ImageView(Resources.logo);
		logo.translateXProperty().bind(scene.widthProperty().subtract(Resources.logo.getWidth()));
		logo.translateYProperty().bind(scene.heightProperty().subtract(Resources.logo.getHeight()));
		root.getChildren().add(logo);
		
		images = new Image[] {
				Resources.balloonBlue,
				Resources.balloonGreen,
				Resources.balloonPink,
				Resources.balloonRed,
				Resources.balloonYellow,
				Resources.balloonOrange
		};
		
		balloonsTimeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.millis(33.33), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			update();
		}});
		
		balloonsTimeline.getKeyFrames().add(kf);
		
		changeState(GameState.MENU);
		
		stage.show();
	}
	
	private void changeState(GameState newState) {
		switch(newState) {
		case MENU:
			root.getChildren().remove(currentScene);
			currentScene = createMenu();
			root.getChildren().add(currentScene);
			break;
		}
		
		state = newState;
	}

	private Node createMenu() {
		Group menu = new Group();
		
		final Button playButton = new Button("Play / Pause");
		playButton.translateXProperty().bind(scene.widthProperty().subtract(100));
		playButton.setTranslateY(100);
		
		final Text clockText = new Text("30");
		clockText.translateXProperty().bind(scene.widthProperty().subtract(100));
		clockText.setTranslateY(80);
		
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent e) {
			running = !running;
			countDown = 30;
			clockText.setText("30");
			
			if (running) {
				clockTimeline.play();
				balloonsTimeline.play();
			} else {
				clockTimeline.stop();
				balloonsTimeline.stop();
			}
		}});
		
		clockTimeline.setCycleCount(Timeline.INDEFINITE);
		
		clockText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		clockText.setFill(Color.WHITESMOKE);
		
		InnerShadow is = new InnerShadow();
		is.setOffsetX(3.0f);
		is.setOffsetY(3.0f);
		
		clockText.setEffect(is);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			if (running) {
				clockText.setText(String.valueOf(countDown));
				countDown--;
				
				if (countDown == 0) {
					running = false;
					clockTimeline.stop();
					balloonsTimeline.stop();
					clockText.setText("..");
				}
			}
		}});
		
		clockTimeline.getKeyFrames().add(kf);
		
		menu.getChildren().add(playButton);
		menu.getChildren().add(clockText);
		
		return menu;
	}

	private void createBalloon() {
		int index = random.nextInt(images.length);
		Image image = images[index];
		
		Balloon balloon = new Balloon(image, velocity);
		
		balloon.setTranslateY(root.getScene().getHeight());
		balloon.setTranslateX(random.nextDouble() * (root.getScene().getWidth() - image.getWidth()));
		root.getChildren().add(balloon);
		balloons.add(balloon);
	}

	private void update() {
		time += DELTA_TIME;
		
		if(time > 1.0) {
			time = 0;
			velocity += 2;
			
			createBalloon();
		}
		
		Iterator<Balloon> iterator = balloons.iterator();
		while(iterator.hasNext()) {
			Balloon balloon = iterator.next();
			
			//balloon.setRotate(balloon.getRotate() + 20*DELTA_TIME);
			balloon.setTranslateY(balloon.getTranslateY() - balloon.getVelocity()*DELTA_TIME);

			if(balloon.getTranslateY()+balloon.getBoundsInLocal().getHeight() < 0) {
				root.getChildren().remove(balloon);
				iterator.remove();
			}
		}
	}
}
