package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
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

	@SuppressWarnings("rawtypes")
	@FXML
	Spinner delay;

	@SuppressWarnings("rawtypes")
	@FXML
	ListView audiofiles;

	@SuppressWarnings("unchecked")

	AudioChannel selectedChannel;

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
				if (!Main.availableFiles.contains(new AudioFile(file)))
				{
					Main.availableFiles.add(new AudioFile(file));

				}
			}
			audiofiles.getItems().clear();
			audiofiles.getItems().addAll(Main.availableFiles);
		}
		// AudioChannel channel = new AudioChannel("Test");
		// channel.playSound();
	}

	@FXML
	void selectChannel(MouseEvent event)
	{
		selectedChannel = (AudioChannel) channels.getSelectionModel().getSelectedItem();
		System.out.println("Test");
		deselectAudioFiles();
	}

	@SuppressWarnings("unchecked")
	void refreshAudioChennels()
	{
		channels.getItems().clear();
		channels.getItems().addAll(Main.audioChannels);
	}

	@FXML
	void addChannel(MouseEvent event)
	{
		Main.newAudio.show();
	}

	public void selectAudioFiles(MouseEvent event)
	{
		if (channels.getSelectionModel().getSelectedItem() != null
				&& channels.getSelectionModel().getSelectedIndices().size() > 0)
		{
			selectedChannel.setAudioFiles((ArrayList<File>) audiofiles.getSelectionModel().getSelectedItems());
			System.out.println(selectedChannel.getAudioFiles().toString());
		}
	}

	public void deselectAudioFiles()
	{
		audiofiles.getItems().clear();
		audiofiles.getItems().addAll(Main.availableFiles);
	}

}
