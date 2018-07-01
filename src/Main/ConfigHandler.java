package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ConfigHandler
{
	static Properties props;
	static OutputStream output;
	static FileInputStream input;

	public ConfigHandler(File file) throws IOException
	{
		input = new FileInputStream(file);
		props = new Properties();
		props.load(input);
	}

	public void save() throws IOException
	{
		props.store(output, null);
	}

	// For Save
	public String arrayToString(ArrayList<AudioFile> files)
	{
		ArrayList<String> outputlist = new ArrayList<String>();

		for (AudioFile file : files)
		{
			outputlist.add(file.toString());
		}

		String output = String.join("|||", outputlist);

		return output;
	}

	// For Load
	public static ArrayList<AudioFile> stringToArrayList(String array)
	{
		ArrayList<AudioFile> allFiles = new ArrayList<AudioFile>();
		ArrayList<String> allwords = new ArrayList<String>();

		allwords.addAll(Arrays.asList(array.split("\\|\\|\\|")));

		for (String fileString : allwords)
		{
			AudioFile file = new AudioFile(fileString);
			allFiles.add(file);
		}

		return allFiles;
	}

}
