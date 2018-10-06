package Main;


public class Chromecast
{
	String title;
	String address;
	int port;

	public Chromecast(String title, String address, int port)
	{
		this.title=title;
		this.address=address;
		this.port=port;
	}

	@Override
	public String toString()
	{
		return title;
	}
}
