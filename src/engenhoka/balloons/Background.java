package engenhoka.balloons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background extends Parent {
	
	private static final String BACKGROUND = "background-full.jpg";
	private Image image = new Image(Background.class.getResourceAsStream(BACKGROUND));
	private DoubleProperty width;
	private DoubleProperty height;
	private ImageView background;
	
	public DoubleProperty ssX() {
		return background.scaleXProperty();
	}
	
	public DoubleProperty ssY() {
		return background.scaleYProperty();
	}
	
	public DoubleProperty widthProperty() {
		if(width == null) {
			width = new SimpleDoubleProperty();
			width.addListener(new ChangeListener<Number>() { @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double factorX = newValue.doubleValue() / image.getWidth();
				background.setTranslateX(image.getWidth() * -0.5 * (1 - factorX));
				background.setScaleX(factorX);
			}});
		}
		
		return width;
	}
	
	public DoubleProperty heightProperty() {
		if(height == null) {
			height = new SimpleDoubleProperty();
			height.addListener(new ChangeListener<Number>() { @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double factorY = newValue.doubleValue() / image.getHeight();
				background.setTranslateY(image.getHeight() * -0.5 * (1 - factorY));
				background.setScaleY(factorY);
			}});
		}
		
		return height;
	}

	public Background() {
		background = new ImageView(image);
		
		getChildren().add(background);
		
	}
	
}
