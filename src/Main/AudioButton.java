package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AudioButton extends ToggleButton
{
	ContextMenu context = new ContextMenu();
	MenuItem linkAudio = new MenuItem("Link Audio");
	MenuItem rename = new MenuItem("Rename");

	String name;

	Media sound;
	MediaPlayer buttonPlayer;

	AudioFile file;
	boolean isPlaying;
	double volume = 100;

	AudioButton thisbutton = this;

	public AudioButton()
	{
		this.setPrefSize(120, 120);
		this.setAlignment(Pos.CENTER);
		this.setOnAction(this::Clicked);
		linkAudio.setOnAction(this::LinkAudio);
		rename.setOnAction(this::Rename);
		context.getItems().addAll(linkAudio, rename);
		this.setContextMenu(context);
	}

	public AudioButton(String data, String nothing)
	{

		data = data.substring(12, data.length() - 1);
		ArrayList<String> arr = new ArrayList<String>();
		arr.addAll(Arrays.asList(data.split("\\|\\|\\|")));

		name = arr.get(0).split("~")[1];
		if (!arr.get(1).split("~")[1].equals("null"))
		{
			file = new AudioFile(arr.get(1).split("~")[1], null);
		}
		else
			file = null;

		volume = Double.parseDouble(arr.get(2).split("~")[1]);

	}

	// TODO: ADD END FEATURE

	public void LinkAudio(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Open Resource File");

		ExtensionFilter filter = new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(Main.stage);
		if (file != null)
		{

			this.file = new AudioFile(file);
			if (name == null)
			{
				name = file.getName();
				this.setText(name);
			}

		}
	}

	public void Rename(ActionEvent event)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Rename Dialog");
		dialog.setHeaderText("Rename");
		dialog.setContentText("Please enter a name for this button:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name ->
		{
			this.setText(name);
			this.name = name;
		});
	}

	public void deselect()
	{
		this.setSelected(false);
		this.isPlaying = false;
		if (buttonPlayer != null)
		{
			buttonPlayer.stop();
		}
	}

	public void Clicked(ActionEvent event)
	{
		if (file != null && !isPlaying)
		{
			if (buttonPlayer != null)
			{
				buttonPlayer.stop();
			}
			for (Pane audio : Main.audioButtons)
			{
				if (this != audio.button)
				{
					audio.button.deselect();

				}
			}
			sound = new Media(file.getPath().toString().replace('\\', '/'));
			buttonPlayer = new MediaPlayer(sound);
			buttonPlayer.setOnEndOfMedia(new Runnable()
			{

				@Override
				public void run()
				{
					thisbutton.setSelected(false);
					buttonPlayer.stop();
					isPlaying = false;
				}

			});
			buttonPlayer.setVolume(volume);

			buttonPlayer.play();
			this.setSelected(true);
			isPlaying = true;
		}
		else if (file != null && isPlaying)
		{
			buttonPlayer.stop();
			isPlaying = false;
			this.setSelected(false);
		}
		else if (file == null)
		{
			isPlaying = false;
			this.setSelected(false);
		}
	}

	public String toSaveString()
	{
		String audioFileString;
		if (file != null)
		{
			audioFileString = file.toSaveString();
		}
		else
		{
			audioFileString = "null";
		}

		return "AudioButton [Name~" + this.name + "||| AudioFile~" + audioFileString + "||| Volume~" + this.volume + "]";
	}

	void setVolume(double volume)
	{
		if (buttonPlayer != null)
		{
			buttonPlayer.setVolume(volume);
		}

		this.volume = volume;
	}

}
