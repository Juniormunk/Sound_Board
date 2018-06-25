package Main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public static Stage stage;
	public static Stage newAudio;
	public static ArrayList<AudioFile> availableFiles = new ArrayList<AudioFile>();
	public static ArrayList<AudioChannel> audioChannels = new ArrayList<AudioChannel>();
	public static Controller controller;

	private String test1 = "C:/Users/Cameron/Documents/air.mp3";
	private String test2 = "C:/Users/Cameron/Documents/crik.mp3";

	// private String test1 = "C:/Users/camco/Documents/air.mp3";
	// private String test2 = "C:/Users/camco/Documents/crik.mp3";

	public static void main(String[] args)
	{
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage defaultStage) throws Exception
	{
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Test.fxml"));
		Scene primary = new Scene(loader1.load());
		// Controller controller = loader1.getController();
		defaultStage.setScene(primary);
		defaultStage.show();
		defaultStage.setResizable(false);
		Controller controller = loader1.getController();

		Main.controller = controller;

		Main.controller.audiofiles.getCheckModel().getCheckedItems().addListener(new ListChangeListener<AudioFile>()
		{
			public void onChanged(ListChangeListener.Change<? extends AudioFile> c)
			{
				if (Main.controller.selectedChannel != null && !Main.controller.selectedChannel.ingnoreChange)
				{
					ArrayList<AudioFile> list = new ArrayList<AudioFile>();
					list.addAll(Main.controller.audiofiles.getCheckModel().getCheckedItems());
					Main.controller.selectedChannel.setAudioFiles(list);
					System.out.println("On Change: "+list);

					
				}

			}
		});

		stage = defaultStage;

		newAudio = new Stage();

		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("newAudio.fxml"));
		Scene primary2 = new Scene(loader2.load());
		// Controller controller = loader1.getController();
		newAudio.setScene(primary2);
		newAudio.setResizable(false);
		@SuppressWarnings("unused")
		NewAudioController controller2 = loader2.getController();

		// ArrayList<File> files = new ArrayList<File>();
		//
		// files.add(new File(test1));
		// files.add(new File(test2));
		//
		// AudioChannel channel = new AudioChannel("Test");
		//
		//
		// channel.setAudioFiles(files);
		//
		// channel.playSound();
		//
		// ArrayList<File> files2 = new ArrayList<File>();
		//
		// files2.add(new File(test2));
		// files2.add(new File(test1));
		//
		// AudioChannel channel2 = new AudioChannel("Test1");
		//
		//
		// channel2.setAudioFiles(files2);
		//
		// channel2.playSound();

		// controller.channels.getItems().addAll("Okay?", "hey");
		// controller.audiofiles.getItems().addAll("test","Test1");
	}

}
