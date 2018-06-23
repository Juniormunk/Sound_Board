package Main;

import java.io.File;

@SuppressWarnings("serial")
public class AudioFile extends File
{
	boolean selected = false;

	public AudioFile(File file)
	{
		super(file.toURI().toString());
	}

	public String toString()
	{
		return this.getName();
	}
	
}
