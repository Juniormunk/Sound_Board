package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

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

		for (String fileString : allwords)
		{
			Pane file = new Pane(fileString, null);
			allFiles.add(file);
		}

		return allFiles;
	}

	public void load()
	{
		Main.audioButtons = stringToArrayList(props.getProperty("Buttons"), null);

		System.out.println(Main.audioButtons);

		Main.controller.grid.add(Main.audioButtons.get(0), 0, 0);
		Main.controller.grid.add(Main.audioButtons.get(1), 1, 0);
		Main.controller.grid.add(Main.audioButtons.get(2), 2, 0);

		Main.controller.grid.add(Main.audioButtons.get(3), 0, 1);
		Main.controller.grid.add(Main.audioButtons.get(4), 1, 1);
		Main.controller.grid.add(Main.audioButtons.get(5), 2, 1);

		Main.controller.grid.add(Main.audioButtons.get(6), 0, 2);
		Main.controller.grid.add(Main.audioButtons.get(7), 1, 2);
		Main.controller.grid.add(Main.audioButtons.get(8), 2, 2);

		Main.controller.grid.add(Main.audioButtons.get(9), 0, 3);
		Main.controller.grid.add(Main.audioButtons.get(10), 1, 3);
		Main.controller.grid.add(Main.audioButtons.get(11), 2, 3);
		
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
