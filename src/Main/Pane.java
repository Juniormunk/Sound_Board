package Main;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

public class Pane extends BorderPane
{
	AudioButton button;
	Slider volume;
	String name;

	public Pane(AudioButton button, Slider volume, String name)
	{
		this.name = name;
		this.button = button;
		this.volume = volume;
		this.volume.setValue(100);
		this.volume.setShowTickLabels(true);
		this.volume.majorTickUnitProperty().set(25);
		this.volume.setPrefWidth(120);
		this.volume.setMaxWidth(120);
		this.volume.setMinWidth(120);
		this.volume.valueProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				if (button.buttonPlayer != null)
				{
					button.setVolume(new_val.doubleValue() / 100.0);
				}
			}
		});
		BorderPane.setAlignment(this.button, Pos.CENTER);
		BorderPane.setAlignment(this.volume, Pos.CENTER);
		this.setCenter(this.button);
		this.setBottom(this.volume);
	}

	public Pane(String data, String nothing)
	{
		System.out.println("FIRST : " + data);
		data = data.substring(6, data.length() - 1);
		System.out.println("SECOND : " + data);
		ArrayList<String> arr = new ArrayList<String>();
		arr.addAll(Arrays.asList(data.split("\\|\\|\\|\\|")));

		System.out.println("THIRD : " + arr.get(0).split("=")[1]);
		
		button = new AudioButton(arr.get(0).split("=")[1], null);

	}

	public String toSaveString()
	{
		return "Pane [Button=" + this.button.toSaveString() + "|||| Name=" + name + "]";
	}
}
