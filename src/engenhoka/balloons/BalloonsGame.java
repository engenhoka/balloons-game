package engenhoka.balloons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BalloonsGame extends Application {
	private static final int INITIAL_VELOCITY = 350;
	private static final int LOGO_HEIGHT = 114;
	private static final int LOGO_WIDTH = 261;
	private static final int MAX_TIME = 30;
	private static final double BALLOON_SCALE_Y = 0.5;
	private static final double BALLOON_SCALE_X = 0.5;

	private static final double DELTA_TIME = 0.03333;
	
	public static BalloonsGame game;
	
	private List<Balloon> balloons = new ArrayList<Balloon>();
	
	private int score;
	private GameState state;
	private Group currentScene;

	private Group root;
	private double timeSpan;

	private Random random = new Random(System.currentTimeMillis());
	
	private Timeline balloonsTimeline = new Timeline();
	private Timeline clockTimeline = new Timeline();

	private double velocity = INITIAL_VELOCITY;

	private int countDown;
	
	private Scene scene;

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		game = this;
		
		stage.setTitle("Balloons Game");
		
		stage.setWidth(1024);
		stage.setHeight(768);
		stage.setResizable(false);
		
		//stage.setFullScreen(true);

		root = new Group();
		scene = new Scene(root);
		scene.setFill(Color.BLUE);
		stage.setScene(scene);
		
		Background background = new Background();
		background.widthProperty().bind(scene.widthProperty());
		background.heightProperty().bind(scene.heightProperty());
		root.getChildren().add(background);
		
		final ImageView logo = new ImageView(Resources.logo);
		logo.setScaleX(0.4);
		logo.setScaleY(0.4);
		logo.setTranslateX(340);
		logo.setTranslateY(590);
//		logo.translateXProperty().bind(scene.widthProperty().subtract(200));
//		logo.translateYProperty().bind(scene.heightProperty().subtract(100));
		
		root.getChildren().add(logo);
		
		balloonsTimeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.millis(33.33), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			update();
			logo.toFront();
		}});
		
		balloonsTimeline.getKeyFrames().add(kf);
		
		changeState(GameState.MENU);
		
		stage.show();
	}
	
	private void changeState(GameState newState) {
		root.getChildren().remove(currentScene);
		
		switch(newState) {
		case MENU:
			currentScene = createMenu();
			break;
			
		case PLAYING:
			currentScene = startGame();
			break;
			
		case WINNING:
			currentScene = winningGame();
			break;
			
		case LOSE:
			currentScene = loseGame();
			break;
			
		default:
			break;
		}
		
		state = newState;
		root.getChildren().add(currentScene);
	}

	private Group loseGame() {
		Group group = new Group();
		
		Text text = new Text("N�o foi desta vez!!");
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 90));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.translateXProperty().bind(scene.widthProperty().divide(2).subtract(text.getLayoutBounds().getWidth() / 2));
		text.translateYProperty().bind(scene.heightProperty().divide(2).subtract(text.getLayoutBounds().getHeight() / 2));
		group.getChildren().add(text);
		
		final Button playButton = new Button("Jogar novamente");
		playButton.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		playButton.translateXProperty().bind(scene.widthProperty().divide(2).subtract(playButton.widthProperty().divide(2)));
		playButton.translateYProperty().bind(scene.heightProperty().divide(2));
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent e) {
			changeState(GameState.PLAYING);
		}});
		group.getChildren().add(playButton);
		
		return group;
	}

	private Group winningGame() {
		Resources.applause.play();
		
		Group group = new Group();
		
		Text text = new Text("Voc� ganhou!!");
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 110));
		text.setFill(Color.WHITE);
		text.setStroke(Color.BLACK);
		text.translateXProperty().bind(scene.widthProperty().divide(2).subtract(text.getLayoutBounds().getWidth() / 2));
		text.translateYProperty().bind(scene.heightProperty().divide(2).subtract(text.getLayoutBounds().getHeight() / 2));
		group.getChildren().add(text);
		
		final Button playButton = new Button("Jogar novamente");
		playButton.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		playButton.translateXProperty().bind(scene.widthProperty().divide(2).subtract(playButton.widthProperty().divide(2)));
		playButton.translateYProperty().bind(scene.heightProperty().divide(2));
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent e) {
			changeState(GameState.PLAYING);
		}});
		group.getChildren().add(playButton);
		
		//TODO colocar o iwatinha neste momento
		
		return group;
	}

	private Group startGame() {
		Group group = new Group();
		
		countDown = MAX_TIME;
		velocity = INITIAL_VELOCITY;
		score = 0;
		
		final Text clockText = new Text("30");
		clockText.translateXProperty().bind(scene.widthProperty().subtract(150));
		clockText.setTranslateY(100);

		InnerShadow is = new InnerShadow();
		is.setOffsetX(3.0f);
		is.setOffsetY(3.0f);
		
		clockText.setFont(Font.font("Verdana", FontWeight.BOLD, 100));
		clockText.setFill(Color.WHITE);
		clockText.setStroke(Color.BLACK);
//		clockText.setEffect(is);
		
		group.getChildren().add(clockText);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			clockText.toFront();
			clockText.setText(String.valueOf(countDown));
			countDown--;
			
			if (countDown < 0) {
				clockTimeline.stop();
				balloonsTimeline.stop();
				
				changeState(GameState.LOSE);
			}
		}});
		
		clockTimeline.setCycleCount(Timeline.INDEFINITE);
		clockTimeline.getKeyFrames().add(kf);
		
		clockTimeline.play();
		balloonsTimeline.play();
		
		return group;
	}

	private Group createMenu() {
		Group menu = new Group();
		
		final Button playButton = new Button("Jogar");
		playButton.translateXProperty().bind(scene.widthProperty().divide(2).subtract(playButton.widthProperty().divide(2)));
		playButton.translateYProperty().bind(scene.heightProperty().divide(2).subtract(playButton.heightProperty().divide(2)));
		playButton.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		
		playButton.setOnMousePressed(new EventHandler<MouseEvent>() { @Override public void handle(MouseEvent e) {
			changeState(GameState.PLAYING);
		}});
		
		menu.getChildren().add(playButton);
		
		return menu;
	}

	private void createBalloon() {
		int colorIndex = random.nextInt(Resources.balloons.length);
		int logoIndex = random.nextInt(Resources.logos.length);
		
		Balloon balloon = new Balloon(colorIndex, logoIndex, velocity, state != GameState.WINNING);
		
		balloon.setScaleX(BALLOON_SCALE_X);
		balloon.setScaleY(BALLOON_SCALE_Y);
		balloon.setTranslateY(root.getScene().getHeight());
		balloon.setTranslateX(random.nextDouble() * (root.getScene().getWidth() - Resources.balloons[colorIndex].getWidth()));
		currentScene.getChildren().add(balloon);
		balloons.add(balloon);
	}

	private void update() {
		timeSpan += DELTA_TIME;
		
		if(timeSpan > 1.0) {
			timeSpan = 0;
			velocity += 2;
			
			createBalloon();
		}
		
		Iterator<Balloon> iterator = balloons.iterator();
		while(iterator.hasNext()) {
			Balloon balloon = iterator.next();
			
			if (!balloon.isAlive())
				continue;
			
			balloon.setTranslateY(balloon.getTranslateY() - balloon.getVelocity()*DELTA_TIME);

			if(balloon.getTranslateY()+balloon.getBoundsInLocal().getHeight() < 0) {
				root.getChildren().remove(balloon);
				iterator.remove();
			}
		}
	}

	public void hitBalloon(final int logoIndex, final ImageView logoView, Transform transform) {
		if(state != GameState.PLAYING) return;
		
		double sx = transform.getMxx();
		double sy = transform.getMyy();
		double tx = transform.getTx();
		double ty = transform.getTy();
		
		currentScene.getChildren().add(logoView);
		logoView.setScaleX(sx);
		logoView.setScaleY(sy);
		logoView.setTranslateX(tx);
		logoView.setTranslateY(ty);
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		
		int x = logoIndex % 6;
		int y = logoIndex / 6;
		
		double offsetX = x*LOGO_WIDTH*BALLOON_SCALE_X + x*15 - 50;
		double offsetY = y*LOGO_HEIGHT*BALLOON_SCALE_Y + y*15 - 10;
		
		KeyFrame kf0 = new KeyFrame(Duration.millis(1000)
				, new KeyValue(logoView.translateXProperty(), offsetX)
				, new KeyValue(logoView.translateYProperty(), offsetY)
		);
		
		timeline.getKeyFrames().addAll(kf0);
		timeline.play();
		
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent event) {
			final int bits = 1 << logoIndex;
			
			if((score & bits) == 0) {
				score |= bits;
			} else {
				currentScene.getChildren().remove(logoView);
			}
			
			if(score == 255) {
				changeState(GameState.WINNING);
			}
		}});
	}
}
