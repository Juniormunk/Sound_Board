package Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
			textField.clear();
		}
	}

	@FXML
	void applyKey(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER)
		{
			if (!textField.getText().equals(""))
			{
				Main.audioChannels.add(new AudioChannel(textField.getText()));
				Main.controller.refreshAudioChennels();
				Main.newAudio.close();
				textField.clear();
				
			}
		}
	}

	@FXML
	void close(MouseEvent event)
	{
		Main.newAudio.close();
		textField.clear();

	}

}
