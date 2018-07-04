package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javafx.geometry.Pos;

public class ConfigHandler
{
	Properties props;
	FileOutputStream output;
	FileInputStream input;

	public ConfigHandler(File file, boolean shouldOutput) throws IOException
	{
		input = new FileInputStream(file);
		props = new Properties();

		props.load(input);
		if (shouldOutput)
		{
			output = new FileOutputStream(file);
		}
	}

	// For Save
	public static String arrayToStringAudioFile(ArrayList<AudioFile> files, String seperator)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (AudioFile file : files)
		{
			outputlist.add(file.toSaveString("||"));
		}

		// String output = String.join("|||||", outputlist);
		String output = String.join(seperator, outputlist);

		return output;
	}

	public static String arrayToStringAudioChannel(ArrayList<AudioChannel> files, String seperator)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (AudioChannel file : files)
		{
			outputlist.add(file.toSaveString("|||||"));
		}

		String output = String.join(seperator, outputlist);

		return output;
	}

	public static String arrayToStringPane(ArrayList<Pane> files, String seperator)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (Pane file : files)
		{
			outputlist.add(file.toSaveString());
		}

		String output = String.join(seperator, outputlist);

		return output;
	}

	// For Load
	public static ArrayList<AudioFile> stringToArrayListAudioFile(String array, String seperator)
	{
		ArrayList<AudioFile> allFiles = new ArrayList<AudioFile>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split(seperator)));

		for (String fileString : allwords)
		{
			if (!fileString.equals("QQQNULLQQQ"))
			{
				AudioFile file = new AudioFile(fileString, null);
				allFiles.add(file);
			}
		}

		return allFiles;
	}

	public static ArrayList<AudioChannel> stringToArrayListAudioChannel(String array, String seperator)
	{
		ArrayList<AudioChannel> allFiles = new ArrayList<AudioChannel>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split(seperator)));

		for (String fileString : allwords)
		{
			AudioChannel file = new AudioChannel(fileString, "\\|\\|\\|\\|\\|");
			allFiles.add(file);
		}

		return allFiles;
	}

	public static ArrayList<Pane> stringToArrayListPane(String array, String seperator)
	{
		ArrayList<Pane> allFiles = new ArrayList<Pane>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split(seperator)));

		System.out.println("StringToArrayListPANE : " + allwords + "END");
		int i = 0;
		for (String fileString : allwords)
		{
			Pane file = new Pane(fileString, null);
			file.name = Integer.toString(i++);
			allFiles.add(file);
		}

		return allFiles;
	}

	public void load()
	{

		Main.updateGrid(stringToArrayListPane(props.getProperty("Buttons"), "\\|\\|\\|\\|\\|"));
		Main.audioChannels = stringToArrayListAudioChannel(props.getProperty("AudioChannels"), "\\|\\|\\|\\|\\|\\|");
		Main.controller.refreshAudioChennels();

	}

	public void save() throws IOException
	{
		props.setProperty("Buttons", arrayToStringPane(Main.audioButtons, "|||||"));
		props.setProperty("AudioChannels", arrayToStringAudioChannel(Main.audioChannels, "||||||"));
		props.store(output, null);
	}

	public static Boolean getBool(String bool)
	{
		if (bool.equals("true"))
		{
			return true;
		}
		else if (bool.equals("false"))
		{
			return false;
		}

		return false;

	}

}
