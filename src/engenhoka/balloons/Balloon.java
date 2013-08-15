package engenhoka.balloons;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

public class Balloon extends Parent {
	private static final AudioClip balloonBurst = new AudioClip(Balloon.class.getResource("balloon-burst.wav").toString());

	public Balloon(Image image) {
		ImageView imageView = new ImageView(image);
		getChildren().add(imageView);

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Hit it!");
				balloonBurst.play();
			}
		});
	}

}
