package engenhoka.balloons;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BalloonsGame extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Balloons Game");
		stage.setWidth(500);
		stage.setHeight(700);
		
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.BLUE);
		
		ImageView image = new ImageView(new Image(BalloonsGame.class.getResourceAsStream("balloon-blue.png")));
		root.getChildren().add(image);
		
		stage.setScene(scene);
		stage.show();
	}

}
