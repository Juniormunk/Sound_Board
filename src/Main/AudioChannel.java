package Main;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class AudioChannel
{
	private boolean isPlaying;
	private boolean repeat;
	private String name;
	private double volume;
	private double delay;
	private ArrayList<AudioFile> audioFiles = new ArrayList<AudioFile>();
	public ArrayList<AudioFile> availableFiles = new ArrayList<AudioFile>();
	public MediaView media;
	ArrayList<MediaPlayer> players;
	boolean ingnoreChange;
	boolean isPaused;

	public AudioChannel(String name)
	{
		this.name = name;
	}

	// plays the sound once
	public void playSound()
	{
		Platform.runLater(new Runnable()
		{

			@Override
			public void run()
			{
				players = new ArrayList<MediaPlayer>();
				for (File file : audioFiles)
				{

					Media soundFile1 = new Media(file.getPath().toString().replace('\\', '/'));
					MediaPlayer player = new MediaPlayer(soundFile1);
					players.add(player);

				}

				media = new MediaView(players.get(0));
				// mediaview1 = new MediaView(players.get(0));
				for (int j = 0; j < players.size(); j++)
				{
					final MediaPlayer player = players.get(j);
					final MediaPlayer nextPlayer = players.get((j + 1) % players.size());

					player.setOnEndOfMedia(new Runnable()
					{
						@Override
						public void run()
						{
							if (repeat)
							{
								nextPlayer.seek(Duration.ZERO);
							}
							media.setMediaPlayer(nextPlayer);
							nextPlayer.play();

						}
					});

				}
				media.setMediaPlayer(players.get(0));
				media.getMediaPlayer().play();

			}
		});
	}

	public boolean isPlaying()
	{
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying)
	{
		this.isPlaying = isPlaying;
	}

	public boolean isRepeat()
	{
		return repeat;
	}

	public void setRepeat(boolean repeat)
	{
		this.repeat = repeat;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
		if (media != null && players.size() > 0)
		{
			for (MediaPlayer player : players)
			{
				player.setVolume(volume);
			}
			media.getMediaPlayer().setVolume(volume);
		}
	}

	public double getDelay()
	{
		return delay;
	}

	public void setDelay(double delay)
	{
		this.delay = delay;
	}

	public ArrayList<AudioFile> getAudioFiles()
	{
		return audioFiles;
	}

	public void setAudioFiles(ArrayList<AudioFile> audioFiles)
	{
		this.audioFiles = audioFiles;
	}

	public String toString()
	{
		return this.name;
	}
}
