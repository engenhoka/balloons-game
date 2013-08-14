package engenhoka.balloons;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Balloon extends Parent {

	public Balloon(Image image) {
		ImageView imageView = new ImageView(image);
		
		getChildren().add(imageView);
	}
	
}
