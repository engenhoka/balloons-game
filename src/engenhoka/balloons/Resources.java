package engenhoka.balloons;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Resources {
	public final static Image logo = new Image(Resources.class.getResourceAsStream("logo.png"));
	
//	public final static Image pow = new Image(Resources.class.getResourceAsStream("pow.png"));
	public final static AudioClip pop = new AudioClip(Resources.class.getResource("pop.mp3").toString());
	
	private static final String colors[] = { "blue", "green", "pink", "red", "yellow", "orange" };
	
	public final static List<Image> balloons = new ArrayList<Image>(colors.length);
	public final static List<Image> pows = new ArrayList<Image>(colors.length);

	private static final int LOGO_COUNT = 8;
	public final static List<Image> logos = new ArrayList<Image>(LOGO_COUNT);
	
	static {
		for(String color: colors) {
			System.out.println("Resources initing color " + color);
			balloons.add(new Image(Resources.class.getResourceAsStream("balloon-" + color + ".png")));
			pows.add(new Image(Resources.class.getResourceAsStream("splashes/splash-" + color + ".png")));
		}
		
		for(int i = 1; i <= LOGO_COUNT; i++)
			logos.add(new Image(Resources.class.getResourceAsStream(String.format("logos/logo-%02d.jpg", i))));
	}
}
