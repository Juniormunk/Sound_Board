package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class AudioChannel
{
	private boolean isPlaying;
	private boolean repeat; // Save
	private boolean repeatDelay; // Add Save
	private double minDelay;
	private double maxDelay;
	private String name; // Save
	private double volume; // Save
	private double delay; // Save
	private ArrayList<AudioFile> audioFiles = new ArrayList<AudioFile>(); // Save
	public ArrayList<AudioFile> availableFiles = new ArrayList<AudioFile>(); // Save
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

					player.setOnPlaying(new Runnable()
					{
						@Override
						public void run()
						{
							System.out.println("Running");
						}
					});
					nextPlayer.setOnPlaying(new Runnable()
					{
						@Override
						public void run()
						{
							System.out.println("Running");
						}
					});

					player.setOnEndOfMedia(new Runnable()
					{
						@Override
						public void run()
						{
							if (repeat)
							{
								nextPlayer.seek(Duration.ZERO);
							}
							System.out.println("Before ran");
							if (repeatDelay)
							{
								Random rand = new Random();
								double randomValue = minDelay + (maxDelay - minDelay) * rand.nextDouble();

								try
								{
									Thread.sleep((long) randomValue * 1000);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
							}
							media.setMediaPlayer(nextPlayer);
							nextPlayer.play();
							System.out.println("Next");

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

	// private boolean repeat; //Save
	// private String name; //Save
	// private double volume; //Save
	// private double delay; //Save
	// private ArrayList<AudioFile> audioFiles = new ArrayList<AudioFile>(); //Save
	// public ArrayList<AudioFile> availableFiles = new ArrayList<AudioFile>();
	// //Save

	public String toSaveString(String seperator)
	{

		String arr1 = "QQQNULLQQQ";
		String arr2 = "QQQNULLQQQ";

		if (this.audioFiles.size() > 0)
			arr1 = ConfigHandler.arrayToStringAudioFile(this.audioFiles, "||||");
		if (this.availableFiles.size() > 0)
			arr2 = ConfigHandler.arrayToStringAudioFile(this.availableFiles, "|||");

		return "AudioChannel [Repeat=" + this.repeat + seperator + " Name=" + name + seperator + " Volume="
				+ this.volume + seperator + " Delay=" + this.delay + seperator + " AudioFiles=" + arr1 + seperator
				+ " AvailableFiles=" + arr2 + seperator + " minRanDel=" + minDelay + seperator + " maxRanDel="
				+ maxDelay + seperator + " doRandom=" + repeatDelay + "]";
	}

	public AudioChannel(String data, String seperator)
	{

		data = data.substring(14, data.length() - 1);
		ArrayList<String> arr = new ArrayList<String>();
		arr.addAll(Arrays.asList(data.split(seperator)));

		repeat = ConfigHandler.getBool(arr.get(0).split("=")[1]);
		name = arr.get(1).split("=")[1];
		volume = Double.parseDouble(arr.get(2).split("=")[1]);
		delay = Double.parseDouble(arr.get(3).split("=")[1]);
		audioFiles = ConfigHandler.stringToArrayListAudioFile(arr.get(4).split("=")[1], "\\|\\|\\|\\|");
		availableFiles = ConfigHandler.stringToArrayListAudioFile(arr.get(5).split("=")[1], "\\|\\|\\|");
		minDelay = Double.parseDouble(arr.get(6).split("=")[1]);
		maxDelay = Double.parseDouble(arr.get(7).split("=")[1]);
		repeatDelay = ConfigHandler.getBool(arr.get(8).split("=")[1]);

		System.out.println(minDelay);

		System.out.println(maxDelay);

	}

	public double getMinDelay()
	{
		return minDelay;
	}

	public void setMinDelay(double minDelay)
	{
		this.minDelay = minDelay;
	}

	public double getMaxDelay()
	{
		return maxDelay;
	}

	public void setMaxDelay(double maxDelay)
	{
		this.maxDelay = maxDelay;
	}

	public void setRandomDelay(boolean doDelay)
	{
		this.repeatDelay = doDelay;
	}

	public boolean isRandom()
	{
		return repeatDelay;
	}
}
