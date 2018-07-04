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
	public static String arrayToString(ArrayList<AudioFile> files)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (AudioFile file : files)
		{
			outputlist.add(file.toSaveString());
		}

		String output = String.join("|||||", outputlist);

		return output;
	}

	public static String arrayToString(ArrayList<Pane> files, String nothing)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (Pane file : files)
		{
			outputlist.add(file.toSaveString());
		}

		String output = String.join("|||||", outputlist);

		return output;
	}

	// For Load
	public static ArrayList<AudioFile> stringToArrayList(String array)
	{
		ArrayList<AudioFile> allFiles = new ArrayList<AudioFile>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split("\\|\\|\\|\\|\\|")));

		for (String fileString : allwords)
		{
			AudioFile file = new AudioFile(fileString, null);
			allFiles.add(file);
		}

		return allFiles;
	}

	public static ArrayList<Pane> stringToArrayList(String array, String nothing)
	{
		ArrayList<Pane> allFiles = new ArrayList<Pane>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split("\\|\\|\\|\\|\\|")));

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

		Main.updateGrid(stringToArrayList(props.getProperty("Buttons"), null));

	}

	public void save() throws IOException
	{
		props.setProperty("Buttons", arrayToString(Main.audioButtons, null));
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
