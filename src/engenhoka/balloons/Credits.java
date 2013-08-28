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
		
		double scale = 0.5;
		engenhoka.setTranslateX(Resources.engenhoka.getWidth() * -0.5 * (1 - scale));
		engenhoka.setScaleX(scale);
		engenhoka.setScaleY(scale);
		
		getChildren().add(engenhoka);
		
		Text emails = new Text("neris.marrony@gmail.com\neduardo@bruno.etc.br");
		emails.setFont(font);
		emails.setFill(Color.WHITE);
		emails.setStroke(Color.BLACK);
		emails.setTranslateY(115);
		getChildren().add(emails);
	}
	
}
