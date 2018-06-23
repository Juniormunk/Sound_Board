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
	private int volume;
	private double delay;
	private ArrayList<File> audioFiles;
	public MediaPlayer player;
	public MediaPlayer nextPlayer;

	public AudioChannel(String name)
	{
		this.name = name;
	}

	// plays the sound once
	public void playSound()
	{
		Runnable myRunnable = new Runnable()
		{

			public void run()
			{
				Platform.runLater(new Runnable()
				{

					@Override
					public void run()
					{
						ArrayList<MediaPlayer> players = new ArrayList<MediaPlayer>();
						for (File file : audioFiles)
						{

							Media soundFile1 = new Media("file:/" + file.getPath().toString().replace('\\', '/'));
							MediaPlayer player = new MediaPlayer(soundFile1);
							players.add(player);

						}

						MediaView mediaview = new MediaView(players.get(0));
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
									if(repeat)
									{
										nextPlayer.seek(Duration.ZERO);
									}
									mediaview.setMediaPlayer(nextPlayer);
									nextPlayer.play();

								}
							});

						}
						mediaview.setMediaPlayer(players.get(0));
						mediaview.getMediaPlayer().play();
						

					}
				});
			}
		};

		Thread thread = new Thread(myRunnable);
		thread.start();

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

	public int getVolume()
	{
		return volume;
	}

	public void setVolume(int volume)
	{
		this.volume = volume;
	}

	public double getDelay()
	{
		return delay;
	}

	public void setDelay(double delay)
	{
		this.delay = delay;
	}

	public ArrayList<File> getAudioFiles()
	{
		return audioFiles;
	}

	public void setAudioFiles(ArrayList<File> audioFiles)
	{
		this.audioFiles = audioFiles;
	}
}
