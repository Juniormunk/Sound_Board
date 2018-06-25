package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.CheckListView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

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
	CheckListView audiofiles;

	AudioChannel selectedChannel;

	ArrayList<AudioFile> list = new ArrayList<AudioFile>();

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
		if (channels.getSelectionModel().getSelectedItem() != null)
		{
			selectedChannel = (AudioChannel) channels.getSelectionModel().getSelectedItem();
			deselectAudioFiles();
			if (selectedChannel.getAudioFiles().size() > 0)
			{
				selectAudioFiles(selectedChannel.getAudioFiles());
			}
		}

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

		if (selectedChannel != null && channels.getSelectionModel().getSelectedItem() != null)
		{
			System.out.println("Clicked");
		}
	}

	@SuppressWarnings("unchecked")
	public void selectAudioFiles(ArrayList<AudioFile> list)
	{
		for (AudioFile file : list)
		{
			System.out.println(file);
			audiofiles.getCheckModel().check(file);
		}

	}

	@SuppressWarnings("unchecked")
	public void deselectAudioFiles()
	{
		audiofiles.getItems().clear();
		audiofiles.getItems().addAll(Main.availableFiles);
	}

	@FXML
	void Start(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			selectedChannel.playSound();
		}
	}
}
