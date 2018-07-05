package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.CheckListView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
	Button addAudio;

	@FXML
	Button removeAudio;

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

	@FXML
	GridPane grid;

	@FXML
	TitledPane audioPane;

	@FXML
	TitledPane buttonPane;

	boolean isopen = false;

	@FXML
	Accordion accordion;

	ArrayList<AudioFile> list = new ArrayList<AudioFile>();

	File saveFile = null;

	@SuppressWarnings("unchecked")
	@FXML
	void addAudioFile(MouseEvent event)
	{
		if (selectedChannel != null)
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
					if (!selectedChannel.availableFiles.contains(new AudioFile(file)))
					{
						selectedChannel.availableFiles.add(new AudioFile(file));

					}
				}

				int i = 0;
				for (AudioFile file : selectedChannel.availableFiles)
				{
					if (file != null)
					{
						file.index = i;
						i++;
					}
				}

				audiofiles.getItems().clear();
				audiofiles.getItems().addAll(selectedChannel.availableFiles);
				if (Main.controller.selectedChannel != null)
				{
					for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
					{
						Main.controller.audiofiles.getCheckModel().check(file.index);
					}
				}

			}
		}

		// AudioChannel channel = new AudioChannel("Test");
		// channel.playSound();
	}

	@SuppressWarnings("unchecked")
	@FXML
	void removeAudioFile(MouseEvent event)
	{
		if (audiofiles.getSelectionModel().getSelectedItem() != null && selectedChannel != null)
		{
			selectedChannel.ingnoreChange = true;
			audiofiles.getCheckModel().clearCheck(audiofiles.getSelectionModel().getSelectedItem());
			selectedChannel.availableFiles.remove(audiofiles.getSelectionModel().getSelectedItem());
			selectedChannel.getAudioFiles().remove(audiofiles.getSelectionModel().getSelectedItem());
			order.getItems().remove(audiofiles.getSelectionModel().getSelectedItem());

			audiofiles.getItems().remove(audiofiles.getSelectionModel().getSelectedItem());
			int i = 0;
			for (AudioFile file : selectedChannel.availableFiles)
			{
				if (file != null)
				{
					file.index = i;
					i++;
				}
			}
			for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
			{
				if (file != null)
				{
					Main.controller.audiofiles.getCheckModel().check(file.index);
				}
			}
			selectedChannel.ingnoreChange = false;

		}
	}

	@SuppressWarnings("unchecked")
	void refreshAudioChennels()
	{
		channels.getItems().clear();
		channels.getItems().addAll(Main.audioChannels);
	}

	@SuppressWarnings("unchecked")
	void refreshAudioFiles()
	{
		audiofiles.getItems().clear();
		audiofiles.getItems().addAll(selectedChannel.availableFiles);
		for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
		{
			Main.controller.audiofiles.getCheckModel().check(file.index);
		}
	}

	@FXML
	void addChannel(MouseEvent event)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Name Dialog");
		dialog.setHeaderText("Name");
		dialog.setContentText("Please enter a name for this channel:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name ->
		{
			Main.audioChannels.add(new AudioChannel(name));
			Main.controller.refreshAudioChennels();
		});

	}

	@FXML
	void removeChannel(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			Main.audioChannels.remove(selectedChannel);
			refreshAudioChennels();

		}
	}

	@FXML
	void Start(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			if (selectedChannel.isPaused)
			{
				selectedChannel.media.getMediaPlayer().play();
				selectedChannel.isPaused = false;
			}
			else
			{
				selectedChannel.playSound();

			}

		}
	}

	@FXML
	void pause(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			selectedChannel.media.getMediaPlayer().pause();
			selectedChannel.isPaused = true;
		}
	}

	@FXML
	void stop(MouseEvent event)
	{
		if (selectedChannel != null)
		{
			selectedChannel.media.getMediaPlayer().stop();
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

	@SuppressWarnings("unchecked")
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

			AudioFile selectedFile = (AudioFile) Main.controller.audiofiles.getSelectionModel().getSelectedItem();

			Main.controller.audiofiles.getItems().clear();
			Main.controller.audiofiles.getCheckModel().clearChecks();
			Main.controller.audiofiles.getItems().addAll(selectedChannel.availableFiles);
			Main.controller.audiofiles.getCheckModel().clearChecks();

			for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
			{
				Main.controller.audiofiles.getCheckModel().check(file.index);
			}

			Main.controller.repeat.setSelected(Main.controller.selectedChannel.isRepeat());

			Main.controller.order.getItems().setAll(Main.controller.selectedChannel.getAudioFiles());

			Main.controller.order.getSelectionModel().select(indexToMove);

			Main.controller.audiofiles.getSelectionModel().select(selectedFile);

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

	@SuppressWarnings("unchecked")
	void refreshOrder()
	{
		clearOrder();
		order.getItems().setAll(selectedChannel.getAudioFiles());

	}

	void clearOrder()
	{
		order.getItems().clear();
	}

	void refreshButtons()
	{

	}

	void audioFilesDeselect()
	{
		audiofiles.getCheckModel().clearChecks();
	}

	@FXML
	void save(ActionEvent event)
	{
		if (saveFile != null)
		{
			try
			{
				saveFile.createNewFile();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			try
			{
				ConfigHandler handle = new ConfigHandler(saveFile, true);

				handle.save();

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@FXML
	void saveAs(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Open Resource File");

		ExtensionFilter filter = new FileChooser.ExtensionFilter("Properties File", "*.properties");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showSaveDialog(Main.stage);
		try
		{
			file.createNewFile();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		if (file != null)
		{
			try
			{
				ConfigHandler handle = new ConfigHandler(file, true);

				handle.save();

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@FXML
	void close(ActionEvent event)
	{
		Platform.exit();
	}

	@FXML
	void openSave(ActionEvent event)
	{

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Open Resource File");

		ExtensionFilter filter = new FileChooser.ExtensionFilter("Properties File", "*.properties");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(Main.stage);
		if (file != null)
		{
			try
			{
				ConfigHandler handle = new ConfigHandler(file, false);
				handle.load();
				for (Pane audio : Main.audioButtons)
				{

					audio.button.deselect();

				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	@FXML
	void showAbout(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Created By: Cameron Coesens\nVersion: 1.0");

		alert.showAndWait();
	}

}
