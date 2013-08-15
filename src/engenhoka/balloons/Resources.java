package engenhoka.balloons;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Resources {
	public final static Image balloonBlue = new Image(Resources.class.getResourceAsStream("balloon-blue.png"));
	public final static Image balloonGreen = new Image(Resources.class.getResourceAsStream("balloon-green.png"));
	public final static Image balloonPink = new Image(Resources.class.getResourceAsStream("balloon-pink.png"));
	public final static Image balloonRed = new Image(Resources.class.getResourceAsStream("balloon-red.png"));
	public final static Image balloonYellow = new Image(Resources.class.getResourceAsStream("balloon-yellow.png"));
	public final static Image balloonOrange = new Image(Resources.class.getResourceAsStream("balloon-orange.png"));
	
	public final static Image logo = new Image(Resources.class.getResourceAsStream("logo.png"));
	
	public final static Image pow = new Image(Resources.class.getResourceAsStream("pow2.png"));
	
	public final static AudioClip pop = new AudioClip(Resources.class.getResource("pop.mp3").toString());
}
