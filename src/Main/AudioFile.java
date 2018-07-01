package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public class AudioFile extends File
{
	boolean selected = false;
	int index;

	public AudioFile()
	{
		super("");
	}

	public AudioFile(String url)
	{
		super(url);
	}

	public AudioFile(File file)
	{
		super(file.toURI().toString());
	}

	public String toString()
	{
		return this.getName();
	}

	public AudioFile(String data, String nothing)
	{
		super(Arrays.asList(data.substring(6, data.length() - 1).split("\\|\\|")).get(0).split("=")[1]);

		data = data.substring(6, data.length() - 1);
		ArrayList<String> arr = new ArrayList<String>();
		arr.addAll(Arrays.asList(data.split("\\|\\|")));

		selected = getBool(arr.get(1).split("=")[1]);
		index = Integer.parseInt(arr.get(2).split("=")[1]);

	}

	public String toSaveString()
	{
		return "AudioFile [FilePath=" + this.getPath() + "|| Selected=" + selected + "|| Index=" + index + "]";
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
