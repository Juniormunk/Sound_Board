package Main;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public static Stage stage;
	public static ArrayList<AudioFile> audioFiles = new ArrayList<AudioFile>();

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage defaultStage) throws Exception
	{
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Test.fxml"));
		Scene primary = new Scene(loader1.load());
		// Controller controller = loader1.getController();
		defaultStage.setScene(primary);
		defaultStage.show();
		defaultStage.setResizable(false);
		@SuppressWarnings("unused")
		Controller controller = loader1.getController();

		stage = defaultStage;
		
		
		
		
		
		
		
		
		ArrayList<File> files = new ArrayList<File>();
		
		files.add(new File("C:/Users/camco/Documents/air.mp3"));
		files.add(new File("C:/Users/camco/Documents/crik.mp3"));
		
		AudioChannel channel = new AudioChannel("Test");
		
		channel.setAudioFiles(files);
		
		channel.playSound();

		// controller.channels.getItems().addAll("Okay?", "hey");
		// controller.audiofiles.getItems().addAll("test","Test1");
	}

}
