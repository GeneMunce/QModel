package QModel;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Qmodel extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Qmodel");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 15, 15, 15));
		int row = 0;

		Scene scene = new Scene(grid, 600, 600);
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Simulation Parameters");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, row, 4, 1);
		row++;
		
		// Add Column Labels for the model Components
		int col = 1;
		addLabels(col, row, grid);
		row++;
		
		// Add model components 
		ArrayList<ArrayList> components = new ArrayList<>();
		col = 0;
		ArrayList<TextField> tfa1;
		tfa1 = addComponent("Create1", col, row, grid, true);
		components.add(tfa1);
		row++;
		col = 0;
		ArrayList<TextField> tfa2;
		tfa2 = addComponent("Service1", col, row, grid, false);
		components.add(tfa2);
		row++;
		col = 0;
		ArrayList<TextField> tfa3;
		tfa3 = addComponent("Queue1", col, row, grid, false);
		components.add(tfa3);
		row++;
		col = 0;
		ArrayList<TextField> tfa4;
		tfa4 = addComponent("Service2", col, row, grid, false);
		components.add(tfa4);
		row++;

		Button btn = new Button();
		btn.setText("Run");
		btn.setTextFill(Color.GREEN);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 4, row);
		row++;

		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		xAxis.setAutoRanging(true);
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		//bc.getData().add(new XYChart.Series(FXCollections.observableArrayList(new XYChart.Data("",0))));
		//bc.getData().clear();
		

		bc.setTitle("Histogram");
		xAxis.setLabel("Buckets");
		yAxis.setLabel("Value");

		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName("Token Response Time");
		for (int i = 0; i < 20; i++) {
			// series1.getData().add(new XYChart.Data(String.valueOf(i), i*2));
		}
		
		bc.getData().add(series1);
		bc.setBarGap(0);
		grid.add(bc, 0, row, 5, 2);
		

		/*
		 * code to run the simulation
		 */
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// System.out.println("Pushed the run Button");
				int cy = Integer.parseInt(tfa1.get(4).getCharacters().toString());
				// create the executor with the first create component
				Executor exm = new Executor(cy / 10, tfa1);
				//add stations
				exm.addStation(tfa2);
				exm.addStation(tfa3);
				exm.addStation(tfa4);				

				// Get statistics
				TokenStats ts;
				ts = exm.run(cy);
				exm.showStatistics(); // log
				
				// Update the histogram with stats
				int bins = ts.getBins();
				int bin = ts.getBin();
				int[] hist = ts.getCount();
				series1.getData().clear();
				for (int i = 0; i < 20; i++) {
					series1.getData().add(new XYChart.Data(String.valueOf(i * bin), hist[i]));
				}
				btn.setTextFill(Color.BLACK);

			}
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void addLabels(int col, int row, GridPane grid) {
		Label typeLabel = new Label("Type:");
		grid.add(typeLabel, col++, row);

		Label departLabel = new Label("Rate:");
		grid.add(departLabel, col++, row);

		Label numberLabel = new Label("Tokens:");
		grid.add(numberLabel, col++, row);

		Label cyclesLabel = new Label("Cycles:");
		grid.add(cyclesLabel, col++, row);

	}

	public ArrayList<TextField> addComponent(String name, int col, int row, GridPane grid, boolean cyc) {
		ArrayList<TextField> tfa = new ArrayList<>();

		//Label createLabel = new Label(name);
		//grid.add(createLabel, col++, row);
		TextField nameTextField = new TextField(name);
		nameTextField.setMaxWidth(75);
		grid.add(nameTextField, col++, row);
		tfa.add(nameTextField);

		TextField ctTextField = new TextField("0");
		ctTextField.setMaxWidth(30);
		grid.add(ctTextField, col++, row);
		tfa.add(ctTextField);

		TextField departureRateTextField = new TextField("1");
		departureRateTextField.setMaxWidth(45);
		grid.add(departureRateTextField, col++, row);
		tfa.add(departureRateTextField);

		TextField numberTextField = new TextField("1");
		numberTextField.setMaxWidth(40);
		grid.add(numberTextField, col++, row);
		tfa.add(numberTextField);

		if (cyc) {
			TextField cyclesTextField = new TextField("100000");
			cyclesTextField.setMaxWidth(80);
			tfa.add(cyclesTextField);
			grid.add(cyclesTextField, col++, row);
		}

		return tfa;
	}
}