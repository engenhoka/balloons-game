package engenhoka.balloons;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Credits extends Parent {

	public Credits() {
		Font font = Font.font("Verdana", FontWeight.BOLD, 25);
		
		ImageView engenhoka = new ImageView(Resources.engenhoka);
		
		double factorX = 0.7;
		engenhoka.setTranslateX(Resources.engenhoka.getWidth() * -0.5 * (1 - factorX));
		engenhoka.setScaleX(factorX);
		engenhoka.setScaleY(factorX);
		
		getChildren().add(engenhoka);
		
		Text emails = new Text("neris.marrony@gmail.com\ndudu.email@gmail.com");
		emails.setFont(font);
		emails.setFill(Color.WHITE);
		emails.setStroke(Color.BLACK);
		emails.setTranslateY(115);
		getChildren().add(emails);
	}
	
}
