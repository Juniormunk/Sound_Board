
package Main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;

public class Main extends Application
{
	public static Stage stage;
	public static ArrayList<AudioChannel> audioChannels = new ArrayList<AudioChannel>();
	public static Controller controller;

	public static ArrayList<Pane> audioButtons = new ArrayList<Pane>();

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
		primary.getStylesheets().add("Test.css");
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

					ArrayList<AudioFile> output = new ArrayList<AudioFile>();

					output = Main.controller.selectedChannel.getAudioFiles();

					for (AudioFile file : list)
					{
						if (!output.contains(file))
						{
							output.add(file);
						}
					}

					for (int i = 0; i <= output.size() - 1; i++)
					{
						if (!list.contains(output.get(i)))
						{
							output.remove(output.get(i));
						}
					}

					Main.controller.selectedChannel.setAudioFiles(output);

					Main.controller.order.getItems().removeAll(Main.controller.order.getItems());
					Main.controller.order.getItems().addAll(output);

					System.out.println("On Change: " + output);

				}

			}
		});

		Main.controller.volume.valueProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				if (Main.controller.selectedChannel != null)
				{
					Main.controller.selectedChannel.setVolume((double) new_val / 100.0);
				}
			}
		});

		Main.controller.channels.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<AudioChannel>()
				{
					@Override
					public void changed(ObservableValue<? extends AudioChannel> observable, AudioChannel oldValue,
							AudioChannel newValue)
					{
						if (newValue == null)
						{
							Main.controller.audiofiles.setDisable(true);
							Main.controller.order.setDisable(true);
							Main.controller.addAudio.setDisable(true);
							Main.controller.removeAudio.setDisable(true);
							Main.controller.repeat.setDisable(true);
							Main.controller.delay.setDisable(true);
							Main.controller.random.setDisable(true);
							Main.controller.minRanDel.setDisable(true);
							Main.controller.maxRanDel.setDisable(true);
							Main.controller.clearOrder();
							Main.controller.audiofiles.getItems().clear();

						}
						if (Main.controller.channels.getSelectionModel().getSelectedItem() != null)
						{
							Main.controller.audioPane.setExpanded(true);
							Main.controller.audiofiles.setDisable(false);
							Main.controller.order.setDisable(false);
							Main.controller.addAudio.setDisable(false);
							Main.controller.removeAudio.setDisable(false);
							Main.controller.repeat.setDisable(false);
							Main.controller.delay.setDisable(false);
							Main.controller.random.setDisable(false);
							Main.controller.minRanDel.setDisable(false);
							Main.controller.maxRanDel.setDisable(false);
							Main.controller.lastSelectedChannel = Main.controller.selectedChannel;
							Main.controller.selectedChannel = (AudioChannel) Main.controller.channels
									.getSelectionModel().getSelectedItem();
							
							Main.controller.delay.getValueFactory().setValue(Main.controller.selectedChannel.getDelay());
							Main.controller.minRanDel.getValueFactory().setValue(Main.controller.selectedChannel.getMinDelay());
							Main.controller.maxRanDel.getValueFactory().setValue(Main.controller.selectedChannel.getMaxDelay());

							if (Main.controller.lastSelectedChannel != Main.controller.selectedChannel)
							{
								Main.controller.selectedChannel.ingnoreChange = true;
								Main.controller.audiofiles.getItems().clear();
								Main.controller.audiofiles.getCheckModel().clearChecks();
								Main.controller.audiofiles.getItems()
										.addAll(Main.controller.selectedChannel.availableFiles);
								Main.controller.audiofiles.getCheckModel().clearChecks();

								for (AudioFile file : Main.controller.selectedChannel.getAudioFiles())
								{
									Main.controller.audiofiles.getCheckModel().check(file.index);
								}

								Main.controller.repeat.setSelected(Main.controller.selectedChannel.isRepeat());

								Main.controller.random.setSelected(Main.controller.selectedChannel.isRandom());

								Main.controller.refreshOrder();

								Main.controller.selectedChannel.ingnoreChange = false;
							}

						}
					}
				});

		Main.controller.delay.valueProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				if (Main.controller.selectedChannel != null)
				{
					Main.controller.selectedChannel.setDelay((double) new_val);
				}
				if ((double) new_val < 0)
				{
					Main.controller.delay.getValueFactory().setValue(0.0);
				}
			}
		});

		Main.controller.minRanDel.valueProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				if (Main.controller.selectedChannel != null)
				{
					Main.controller.selectedChannel.setMinDelay((double) new_val);
					if ((double) Main.controller.maxRanDel.getValue() < (double) new_val)
					{
						Main.controller.maxRanDel.getValueFactory().setValue(new_val);
					}
					if ((double) new_val < 0)
					{
						Main.controller.minRanDel.getValueFactory().setValue(0.0);
					}
				}
			}
		});

		Main.controller.maxRanDel.valueProperty().addListener(new ChangeListener<Number>()
		{
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				if (Main.controller.selectedChannel != null)
				{
					Main.controller.selectedChannel.setMaxDelay((double) new_val);
				}
				if ((double) new_val < (double) Main.controller.minRanDel.getValue())
				{
					Main.controller.maxRanDel.getValueFactory().setValue((double) Main.controller.minRanDel.getValue());
				}
				if ((double) new_val < 0)
				{
					Main.controller.maxRanDel.getValueFactory().setValue(0.0);
				}
			}
		});

		Main.controller.order.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AudioFile>()
		{
			@Override
			public void changed(ObservableValue<? extends AudioFile> observable, AudioFile oldValue, AudioFile newValue)
			{
				if (newValue != null)
				{
					Main.controller.orderUp.setDisable(false);
					Main.controller.orderDown.setDisable(false);

				}
				else
				{
					Main.controller.orderUp.setDisable(true);
					Main.controller.orderDown.setDisable(true);
				}
			}
		});

		Main.controller.accordion.expandedPaneProperty().addListener(
				(ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) ->
				{

					if (oldPane != null && oldPane == Main.controller.audioPane)
					{
						Main.controller.buttonPane.setExpanded(true);
					}
					if (oldPane != null && oldPane == Main.controller.buttonPane)
					{
						Main.controller.audioPane.setExpanded(true);
					}
				});

		for (int i = 0; i <= 11; i++)
		{
			audioButtons.add(new Pane(new AudioButton(), new Slider(), Integer.toString(i)));
		}

		Main.controller.grid.setAlignment(Pos.CENTER);

		Main.controller.grid.add(audioButtons.get(0), 0, 0);
		Main.controller.grid.add(audioButtons.get(1), 1, 0);
		Main.controller.grid.add(audioButtons.get(2), 2, 0);

		Main.controller.grid.add(audioButtons.get(3), 0, 1);
		Main.controller.grid.add(audioButtons.get(4), 1, 1);
		Main.controller.grid.add(audioButtons.get(5), 2, 1);

		Main.controller.grid.add(audioButtons.get(6), 0, 2);
		Main.controller.grid.add(audioButtons.get(7), 1, 2);
		Main.controller.grid.add(audioButtons.get(8), 2, 2);

		Main.controller.grid.add(audioButtons.get(9), 0, 3);
		Main.controller.grid.add(audioButtons.get(10), 1, 3);
		Main.controller.grid.add(audioButtons.get(11), 2, 3);

		Main.controller.buttonPane.setExpanded(true);

		stage = defaultStage;
	}

	public static void updateGrid(ArrayList<Pane> arr)
	{
		Main.controller.grid.getChildren().clear();

		audioButtons.clear();

		for (int i = 0; i <= 11; i++)
		{
			audioButtons.add(arr.get(i));
		}
		for (Pane file : Main.audioButtons)
		{
			System.out.println(file.button.name);
		}
		Main.controller.grid.setAlignment(Pos.CENTER);

		Main.controller.grid.add(audioButtons.get(0), 0, 0);
		Main.controller.grid.add(audioButtons.get(1), 1, 0);
		Main.controller.grid.add(audioButtons.get(2), 2, 0);

		Main.controller.grid.add(audioButtons.get(3), 0, 1);
		Main.controller.grid.add(audioButtons.get(4), 1, 1);
		Main.controller.grid.add(audioButtons.get(5), 2, 1);

		Main.controller.grid.add(audioButtons.get(6), 0, 2);
		Main.controller.grid.add(audioButtons.get(7), 1, 2);
		Main.controller.grid.add(audioButtons.get(8), 2, 2);

		Main.controller.grid.add(audioButtons.get(9), 0, 3);
		Main.controller.grid.add(audioButtons.get(10), 1, 3);
		Main.controller.grid.add(audioButtons.get(11), 2, 3);

	}

}
