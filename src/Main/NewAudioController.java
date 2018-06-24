package Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NewAudioController
{
	@FXML
	private TextField textField;

	@FXML
	private Button apply;

	@FXML
	private Button close;

	@FXML
	void apply(MouseEvent event)
	{
		if (!textField.getText().equals(""))
		{
			Main.audioChannels.add(new AudioChannel(textField.getText()));
			Main.controller.refreshAudioChennels();
			Main.newAudio.close();
		}
	}

	@FXML
	void close(MouseEvent event)
	{
		Main.newAudio.close();
	}

}
