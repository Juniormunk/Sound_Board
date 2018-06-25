package Main;

import java.io.File;

@SuppressWarnings("serial")
public class AudioFile extends File
{
	boolean selected = false;
	int index;

	public AudioFile(File file)
	{
		super(file.toURI().toString());
	}

	public String toString()
	{
		return this.getName();
	}
	
}
