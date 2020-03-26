/**
 * Filename:   AddFood.java
 * Project:    Food Query Milestone 3
 * Authors:    Sasha Arkhagha, Peter Daly, Andrew Elert, Huzaifa Sohail, Ethan Yeck
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 * 
 * Due Date:   Before 10pm on December 12, 2018
 * Version:    1.0
 * 
 * Credits:    JavaAPI, stackoverflow
 * 
 * Bugs:       None known yet
 */
package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * This class will set up the Graphic user Interface to allow the user to add an
 * item to the foodList. If the user enters in invalid inputs for the nutrients,
 * then it will alert the user via a alert box and will re prompt the user to
 * enter in valid inputs until there are no empty textFields
 *
 */
public class AddFood extends FoodData {

	/**
	 * This method sets up the window for adding food and will read
	 * the values that the user enters (valid inputs ie non negative decimal values)
	 * The window will not allow the user to enter in a negative number and will simply
	 * not type the negative character.
	 * @return
	 */
	public static FoodItem foodAdd() {
		FoodItem newFood = new FoodItem(); // new instance of FoodItem

		StackPane pane = new StackPane(); // layout that will be used for this window

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED); // removes the option to minimize or exit the 
												  // window from the top right corner

		window.initModality(Modality.APPLICATION_MODAL); // does not allow the user to interact with
														 // other screens unless addFood is closed
		window.setTitle("Food Adder"); // Title of window

		// Buttons
		Button submit = new Button("Submit");
		Button cancel = new Button("Cancel");

		// Labels and TextFields for inputs
		Label foodID = new Label("Food ID");
		TextField foodIDText = new TextField();
		Label foodName = new Label("Name of Food");
		TextField foodNameText = new TextField();
		Label calories = new Label("Calories");
		TextField caloriesNameText = new TextField();
		Label fat = new Label("Fat");
		TextField fatNameText = new TextField();
		Label carbs = new Label("Carbohydrates");
		TextField carbsNameText = new TextField();
		Label fiber = new Label("Fat");
		TextField fiberNameText = new TextField();
		Label protein = new Label("Protein");
		TextField proteinNameText = new TextField();
		Label availFood = new Label("ADD FOOD");
		
		availFood.setFont(Font.font("verdana", FontWeight.BOLD, 20)); // sets font of "ADD FOOD"
		availFood.setMaxWidth(Double.MAX_VALUE); // sets max width
		availFood.setAlignment(Pos.CENTER); // sets alignment to the center position

		// sets Min Width for all Label
		foodID.setMinWidth(80);
		foodName.setMinWidth(80);
		calories.setMinWidth(80);
		fat.setMinWidth(80);
		carbs.setMinWidth(80);
		fiber.setMinWidth(80);
		protein.setMinWidth(80);

		// sets the column size of all textfields so all are aligned
		foodIDText.setPrefColumnCount(10);
		foodNameText.setPrefColumnCount(10);
		caloriesNameText.setPrefColumnCount(10);
		fatNameText.setPrefColumnCount(10);
		carbsNameText.setPrefColumnCount(10);
		fiberNameText.setPrefColumnCount(10);
		proteinNameText.setPrefColumnCount(10);

		// Horizontal Boxes for respective labels and text fields
		HBox idHBox = new HBox();
		idHBox.getChildren().addAll(foodID, foodIDText);
		idHBox.setSpacing(5);
		idHBox.setAlignment(Pos.CENTER);

		HBox nameHBox = new HBox();
		nameHBox.getChildren().addAll(foodName, foodNameText);
		nameHBox.setSpacing(5);
		nameHBox.setAlignment(Pos.CENTER);

		HBox calHBox = new HBox();
		calHBox.getChildren().addAll(calories, caloriesNameText);
		calHBox.setSpacing(5);
		calHBox.setAlignment(Pos.CENTER);

		HBox fatHBox = new HBox();
		fatHBox.getChildren().addAll(fat, fatNameText);
		fatHBox.setSpacing(5);
		fatHBox.setAlignment(Pos.CENTER);

		HBox carbHBox = new HBox();
		carbHBox.getChildren().addAll(carbs, carbsNameText);
		carbHBox.setSpacing(5);
		carbHBox.setAlignment(Pos.CENTER);

		HBox fiberHBox = new HBox();
		fiberHBox.getChildren().addAll(fiber, fiberNameText);
		fiberHBox.setSpacing(5);
		fiberHBox.setAlignment(Pos.CENTER);

		HBox proteinHBox = new HBox();
		proteinHBox.getChildren().addAll(protein, proteinNameText);
		proteinHBox.setSpacing(5);
		proteinHBox.setAlignment(Pos.CENTER);

		HBox btn = new HBox();
		btn.getChildren().addAll(submit, cancel);
		btn.setSpacing(10);
		btn.setAlignment(Pos.CENTER);
		
		// Vertical Box for adding all the Hboxes 
		VBox addFoodLayout = new VBox(5);
		addFoodLayout.getChildren().addAll(availFood, idHBox, nameHBox, calHBox, fatHBox, carbHBox,
				fiberHBox, proteinHBox, btn);

		addFoodLayout.setPadding(new Insets(5, 5, 15, 5)); // sets padding on all 4 side
		addFoodLayout.setAlignment(Pos.CENTER); // positions the VBox at the center of the screen

		cancel.setOnAction(e -> { // When user clicks on the cancel button, it will close the window
								  // and go back to the main window
			window.close();
			newFood.setName("Canceled");
		});

		submit.setOnAction(e -> { // Obtain data from user inputs and will open up a food error
									// alert box to show the user what should be entered in what
									// format

			try {
				newFood.setID(foodIDText.getText());
				newFood.setName(foodNameText.getText());
				newFood.addNutrient("Calories", Double.parseDouble(caloriesNameText.getText()));
				newFood.addNutrient("Fat", Double.parseDouble(fatNameText.getText()));
				newFood.addNutrient("Carbs", Double.parseDouble(carbsNameText.getText()));
				newFood.addNutrient("Fiber", Double.parseDouble(fiberNameText.getText()));
				newFood.addNutrient("Protein", Double.parseDouble(proteinNameText.getText()));
				
				
			} catch (Exception exp) {
				newFood.setName("InvalidFoodItem");
				foodError(); // calls foodError to print alert messages
			}
			window.close();
		});
		
		/**
		 * force the textfields to be non-negative values only only
		 */
		caloriesNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d*)")) {
					caloriesNameText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		fatNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d*)")) {
					fatNameText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		carbsNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d*)")) {
					carbsNameText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		fiberNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d*)")) {
					fiberNameText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		proteinNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d*)")) {
					proteinNameText.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		

		pane.setStyle("-fx-background-color: GREY;"); // sets the color of window
		pane.getChildren().add(addFoodLayout);

		Scene scene = new Scene(pane, 400, 400);
		window.setScene(scene);
		window.showAndWait();

		return newFood; // returns new food to main

	}
	
	/**
	 * This will open up an alert window and will tell the user that invalid inputs were put in
	 * and will allow the user to reenter in values once they close the current window
	 */
	public static void foodError() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Food Adder Error");
		alert.setHeaderText("Incorrect/Invalid inputs");
		alert.setContentText("Please enter:\nCalories (Decimal)\nFat (Decimal)"
				+ "\nCarbohydrates (Decimal)\nFat (Decimal)\nProtein (Decimal)");
		alert.showAndWait();

	}

}
