package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.controlsfx.control.CheckListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
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
	CheckListView audiofiles;

	@FXML
	Slider volume;

	@SuppressWarnings("rawtypes")
	@FXML
	Spinner minRanDel;

	@SuppressWarnings("rawtypes")
	@FXML
	Spinner maxRanDel;

	@FXML
	CheckBox random;

	@SuppressWarnings("rawtypes")
	@FXML
	ListView order;

	@FXML
	Button orderUp;

	@FXML
	Button orderDown;

	AudioChannel selectedChannel;

	AudioChannel lastSelectedChannel;

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
			int i = 0;
			for (AudioFile file : Main.availableFiles)
			{
				file.index = i;
				i++;
				// We need not to use this....
			}

			audiofiles.getItems().clear();
			audiofiles.getItems().addAll(Main.availableFiles);
		}
		// AudioChannel channel = new AudioChannel("Test");
		// channel.playSound();
	}

	@SuppressWarnings("unchecked")
	@FXML
	void selectChannel(MouseEvent event)
	{

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

	}

	@SuppressWarnings("unchecked")
	public void deselectAudioFiles()
	{
		audiofiles.getItems().clear();
	}

	@FXML
	void Start(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			selectedChannel.playSound();
		}
	}

	@FXML
	void orderDown(ActionEvent event)
	{
		move(selectedChannel.getAudioFiles(), (AudioFile) order.getSelectionModel().getSelectedItem(), 1);
	}

	@FXML
	void orderUp(ActionEvent event)
	{
		move(selectedChannel.getAudioFiles(), (AudioFile) order.getSelectionModel().getSelectedItem(), -1);
	}

	void move(ArrayList<AudioFile> list, AudioFile toMove, int indexdist)
	{
		int index = list.indexOf(toMove);
		list.remove(index);
		int indexToMove;
		if (index + indexdist < 0)
			indexToMove = list.size();
		else if (index + indexdist > list.size())
			indexToMove = 0;
		else
			indexToMove = index + indexdist;

		list.add(indexToMove, toMove);
		if (Main.controller.lastSelectedChannel != Main.controller.selectedChannel)
		{
			Main.controller.selectedChannel.ingnoreChange = true;
			Main.controller.selectedChannel.setAudioFiles(list);
			Main.controller.audiofiles.getItems().clear();
			Main.controller.audiofiles.getCheckModel().clearChecks();
			Main.controller.audiofiles.getItems().addAll(Main.availableFiles);
			Main.controller.audiofiles.getCheckModel().clearChecks();

			for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
			{
				Main.controller.audiofiles.getCheckModel().check(file.index);
			}

			Main.controller.repeat.setSelected(Main.controller.selectedChannel.isRepeat());

			Main.controller.order.getItems().setAll(Main.controller.selectedChannel.getAudioFiles());

			Main.controller.selectedChannel.ingnoreChange = false;
		}

	}

	@FXML
	void selectRepeat(ActionEvent event)
	{
		if (selectedChannel != null)
		{
			selectedChannel.setRepeat(repeat.isSelected());
		}
	}
}
