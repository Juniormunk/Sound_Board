package Main;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller
{

	@SuppressWarnings("rawtypes")
	@FXML
	ListView channels;

	@FXML
	Button addChannel;

	@FXML
	Button removeChannel;

	@FXML
	CheckBox repeat;

	@FXML
	Spinner delay;

	@SuppressWarnings("rawtypes")
	@FXML
	ListView audiofiles;

	
	@SuppressWarnings("unchecked")
	@FXML
	void addAudioFile(MouseEvent event)
	{

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Open Resource File");

		ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac");
		fileChooser.getExtensionFilters().add(filter);
		List<File> files = fileChooser.showOpenMultipleDialog(Main.stage);
		if (files != null)
		{
			for (File file : files)
			{
				if (!Main.audioFiles.contains(new AudioFile(file)))
				{
					Main.audioFiles.add(new AudioFile(file));

				}
			}
			audiofiles.getItems().addAll(Main.audioFiles);
		}
		// AudioChannel channel = new AudioChannel("Test");
		// channel.playSound();
	}
	@FXML
	void selectChannel(MouseEvent event)
	{
		
	}

}
