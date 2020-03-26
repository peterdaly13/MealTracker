/**
 * Filename:   Main.java
 * Project:    Milestone 3
 * Authors:    Sasha Arkhagha, Peter Daly, Andrew Elert, Huzaifa Sohail, Ethan Yeck
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 * 
 * Due Date:   Before 10pm on December 12, 2018
 * Version:    1.0
 * 
 * Credits:    JavaAPI
 * 
 * Bugs:       None known yet
 */
package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** This program opens a java program that runs a meal analysis. The user can input a file name and
 * the program will read the file and add the food items to a list that the user can interact with.
 * This food in the list can be added to a meal list that will be analyzed for nutrient information
 * and printed in a text area. The user can also add custom food items and save the new list to a file
 * of their choice. Food rules can be added to filter the foods by nutrients or by name.
 *
 */

public class Main extends Application {
	// Nutrient Info
	double totalCals = 0.0;
	double totalFat = 0.0;
	double totalCarbs = 0.0;
	double totalProtein = 0.0;
	double totalFiber = 0.0;

	FoodItem newFood;

	@Override
	public void start(Stage primaryStage) {
		try {
			FoodData foodData = new FoodData(); // Instance of FoodData

			BorderPane root = new BorderPane();
			BorderPane filterPane = new BorderPane();
			Scene main = new Scene(root, 1120, 615); // Main scene for interaction
			Scene filter = new Scene(filterPane, 1120, 615); // Scene for adding food rules
			main.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Buttons
			Button addNewFoodBtn = new Button("Add Food Item");
			Button analyze = new Button("Analyze Meal");
			Button load = new Button("Load");
			Button save = new Button("Save");
			Button foodQuery = new Button("Enter Food Rules");

			Button queryEnter = new Button("Enter");
			Button queryCancel = new Button("Cancel");

			Button addBtn = new Button("Add");
			Button removeBtn = new Button("Remove");
			addBtn.setMaxWidth(Double.MAX_VALUE);
			removeBtn.setPrefSize(100, 20);
			removeBtn.setMaxWidth(Double.MAX_VALUE);

			// CheckBox
			CheckBox calories = new CheckBox("Calories");
			CheckBox fat = new CheckBox("Fat");
			CheckBox carbs = new CheckBox("Carbohydrates");
			CheckBox fiber = new CheckBox("Fiber");
			CheckBox protein = new CheckBox("Protein");
			CheckBox foodName = new CheckBox("Food Name");

			// TextField
			TextField caloriesMax = new TextField(); // Creating textField
			caloriesMax.setPromptText("Max Calories"); // Setting faded prompt text
			TextField fatMax = new TextField();
			fatMax.setPromptText("Max Fat in Grams");
			TextField carbsMax = new TextField();
			carbsMax.setPromptText("Max Carbs in Grams");
			TextField fiberMax = new TextField();
			fiberMax.setPromptText("Max Fiber in Grams");
			TextField proteinMax = new TextField();
			proteinMax.setPromptText("Max Protein in Grams");
			TextField caloriesMin = new TextField();
			caloriesMin.setPromptText("Min Calories");
			TextField fatMin = new TextField();
			fatMin.setPromptText("Min Fat in Grams");
			TextField carbsMin = new TextField();
			carbsMin.setPromptText("Min Carbs in Grams");
			TextField fiberMin = new TextField();
			fiberMin.setPromptText("Min Fiber in Grams");
			TextField proteinMin = new TextField();
			proteinMin.setPromptText("Min Protein in Grams");
			TextField foodNameText = new TextField();
			foodNameText.setPromptText("Name of Food");
			TextField fileName = new TextField();
			fileName.setPromptText("Load File Name");
			TextField loadFile = new TextField();
			loadFile.setPromptText("Save File Name");

			// force the field to be numeric only
			caloriesMin.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						caloriesMin.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			caloriesMax.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						caloriesMax.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			fatMin.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						fatMin.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			fatMax.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						fatMax.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			carbsMin.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						carbsMin.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			carbsMax.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						carbsMax.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			fiberMin.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						fiberMin.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			fiberMax.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						fiberMax.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			proteinMin.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						proteinMin.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});
			proteinMax.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*([\\.]\\d*)")) {
						proteinMax.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});

			// Label
			Label availFood = new Label("Available Food");
			availFood.setFont(Font.font("verdana", FontWeight.BOLD, 20));
			availFood.setMaxWidth(Double.MAX_VALUE);
			availFood.setAlignment(Pos.CENTER);

			Label mealInfoLabel = new Label("User Meal List");
			mealInfoLabel.setFont(Font.font("verdana", FontWeight.BOLD, 20));
			mealInfoLabel.setMaxWidth(Double.MAX_VALUE);
			mealInfoLabel.setAlignment(Pos.CENTER);

			// TextArea
			TextArea mealInfo = new TextArea();
			mealInfo.setPadding(new Insets(5, 5, 5, 5));
			mealInfo.setEditable(false);

			ObservableList<String> selected = FXCollections.observableArrayList();

			// ListView
			ListView<String> foodList = new ListView<String>(); // list with all foods
			ListView<String> mealList = new ListView<String>(selected);
			foodList.setPrefWidth(298);

			mealList.setItems(selected);

			// Scroll Panes
			ScrollPane foodAvailable = new ScrollPane(); // shows a scrollable foodList
			foodAvailable.setContent(foodList);
			foodList.setPrefWidth(298);
			foodList.setPrefHeight(500);

			ScrollPane meal = new ScrollPane(); // shows a scrollable mealList
			meal.setContent(mealList);
			mealList.setPrefWidth(298);
			mealList.setPrefHeight(500);

			// VBox
			VBox analysis = new VBox();
			analysis.getChildren().addAll(mealInfo); //VBox for analysis info

			VBox availableFoodVBox = new VBox(); // Vertical Box for all available foods (foodList)
			availableFoodVBox.getChildren().addAll(availFood, foodAvailable);

			VBox userMealList = new VBox(); // Vertical Box for user MealList
			userMealList.getChildren().addAll(mealInfoLabel, meal);

			VBox leftAndRightBtn = new VBox(5); // button placememnt for Add and Remove
			leftAndRightBtn.setTranslateY(210);
			leftAndRightBtn.getChildren().addAll(addBtn, removeBtn);

			// HBox
			HBox bottomBox = new HBox();
			bottomBox.getChildren().addAll(addNewFoodBtn, analyze, fileName, load, loadFile, save, foodQuery); //HBox for the interaction buttons and fields
			bottomBox.setPadding(new Insets(5));
			bottomBox.setSpacing(6);

			HBox upper = new HBox();
			upper.getChildren().addAll(availableFoodVBox, leftAndRightBtn, userMealList, analysis); //HBox for  the lists and analysis info
			upper.setSpacing(6);

			HBox caloriesHBox = new HBox();		//HBoxes for the foodRules
			caloriesHBox.getChildren().addAll(calories, caloriesMin, caloriesMax); //Calorie Rules
			HBox fatHBox = new HBox();
			fatHBox.getChildren().addAll(fat, fatMin, fatMax); //Fat Rules
			HBox carbsHBox = new HBox();
			carbsHBox.getChildren().addAll(carbs, carbsMin, carbsMax); //Carb Rules
			HBox fiberHBox = new HBox();
			fiberHBox.getChildren().addAll(fiber, fiberMin, fiberMax); //Fiber Rules
			HBox proteinHBox = new HBox();
			proteinHBox.getChildren().addAll(protein, proteinMin, proteinMax); //Protein Rules
			HBox filterPaneBottom = new HBox();
			filterPaneBottom.getChildren().addAll(queryEnter, queryCancel); //Enter and Cancel Buttons

			// Also VBox
			VBox nutritionFilterVBox = new VBox(caloriesHBox, fatHBox, carbsHBox, fiberHBox, proteinHBox); //Assembling the Scene for the food rules input
			VBox nutritionFilterFull = new VBox(nutritionFilterVBox);
			VBox nameFilterVBox = new VBox(foodName, foodNameText);

			// Also HBox
			HBox filterTopHBox = new HBox(nutritionFilterFull, nameFilterVBox);

			root.setTop(upper); //Sets the areas of the scene with Boxes
			root.setBottom(bottomBox);

			filterPane.setTop(filterTopHBox); //Sets the areas of the scene with Boxes
			filterPane.setBottom(filterPaneBottom);

			load.setOnAction(event -> {
				foodData.loadFoodItems(fileName.getText());
				List<FoodItem> temp = foodData.getAllFoodItems();
				ObservableList<String> foodNames = FXCollections.observableArrayList();

				for (int i = 0; i < temp.size(); i++) {
					foodNames.add(temp.get(i).getName());
				}
				foodList.setItems(foodNames.sorted()); // sorts all the foodItems in the foodList
				mealList.getItems().clear(); // clears MealList
			});

			save.setOnAction(e -> foodData.saveFoodItems(loadFile.getText())); //Loads the file with food items

			addNewFoodBtn.setOnAction(e -> { //Adds a new food item to the available food item list
				do {
					newFood = AddFood.foodAdd();

				} while (newFood.getName().equals("InvalidFoodItem"));
				if (!newFood.getName().equals("Canceled")) {
					List<FoodItem> temp = foodData.getAllFoodItems();

					foodData.addFoodItem(newFood);
					ObservableList<String> foodNames = FXCollections.observableArrayList();

					for (int i = 0; i < temp.size(); i++) {
						foodNames.add(temp.get(i).getName());
					}
					foodList.setItems(foodNames.sorted()); // sorts all the foodItems in the foodList
				}
			});

			analyze.setOnAction(e -> { //analyzes the food items in meal list and displays the meal information
				totalCals = 0.0;
				totalFat = 0.0; //Zeros out the nutrition information before starting
				totalCarbs = 0.0;
				totalProtein = 0.0;
				totalFiber = 0.0;

				for (int i = 0; i < mealList.getItems().size(); i++) {
					// Lists all items with the same name as item i of mealList
					List<FoodItem> thisFoodList = foodData.filterByName(mealList.getItems().get(i));
					FoodItem thisFood = thisFoodList.get(0);
					totalCals += thisFood.getNutrientValue("Calories");
					totalFat += thisFood.getNutrientValue("Fat"); //Deconstructs the nutrition info of the food item and increments the elements
					totalCarbs += thisFood.getNutrientValue("Carbs");
					totalProtein += thisFood.getNutrientValue("Protein");
					totalFiber += thisFood.getNutrientValue("Fiber");
				}

				mealInfo.setText("Total Calories: " + totalCals + "\n" + "Total Fat " + totalFat + " grams\n"
						+ "Total Carbohydrates " + totalCarbs + " grams\n" + "Total Fiber " + totalFiber + " grams\n"
						+ "Total Protein " + totalProtein + " grams\n"); //Sends the text to be displayed to the TextArea

			});

			// sends the foodItem selected from the foodList into the mealList
			addBtn.setOnAction(event -> {
				String potential = foodList.getSelectionModel().getSelectedItem();
				if (potential != null) {
					foodList.getSelectionModel().clearSelection();
					selected.add(potential);
				}
			});

			// Removes the food from the mealList
			removeBtn.setOnAction((ActionEvent event) -> {
				String s = mealList.getSelectionModel().getSelectedItem();
				if (s != null) {
					mealList.getSelectionModel().clearSelection();
					selected.remove(s);
				}
			});
			
			// Displays the food analysis scene to the stage
			foodQuery.setOnAction(event -> {
				primaryStage.setScene(filter);
				primaryStage.show();
			});
			
			// Removes all the curent inputted food rules and goes back to the main scene
			queryCancel.setOnAction(event -> {
				primaryStage.setScene(main);
				primaryStage.show();

				calories.setSelected(false);
				fat.setSelected(false);
				carbs.setSelected(false); //Unchecks checkBoxes
				fiber.setSelected(false);
				protein.setSelected(false);
				foodName.setSelected(false);

				caloriesMin.setText("");
				caloriesMax.setText("");
				fatMin.setText("");
				fatMax.setText("");
				carbsMin.setText(""); //Clears text boxes
				carbsMax.setText("");
				fiberMin.setText("");
				fiberMax.setText("");
				proteinMin.setText("");
				proteinMax.setText("");

				foodNameText.setText("");
			});
			
			// Saves the food rules by calling the filter methods in FoodData
			queryEnter.setOnAction(event -> {
				List<String> rules = new ArrayList<String>();
				if (calories.isSelected()) {
					if (!caloriesMin.getText().trim().equals("")) {
						String ruleString = "Calories >= " + caloriesMin.getText().trim();
						rules.add(ruleString); //Adds rule for calories greater or equal to value
					}
					if (!caloriesMax.getText().trim().equals("")) {
						String ruleString = "Calories <= " + caloriesMax.getText().trim(); 
						rules.add(ruleString); //Adds rule for calories less or equal to value
					}
				}
				if (fat.isSelected()) {
					if (!fatMin.getText().trim().equals("")) {
						String ruleString = "Fat >= " + fatMin.getText().trim();
						rules.add(ruleString); //Adds rule for fat greater or equal to value
					}
					if (!fatMax.getText().trim().equals("")) {
						String ruleString = "Fat <= " + fatMax.getText().trim();
						rules.add(ruleString); //Adds rule for fat less or equal to value
					}
				}
				if (carbs.isSelected()) {
					if (!carbsMin.getText().trim().equals("")) {
						String ruleString = "Carbs >= " + carbsMin.getText().trim();
						rules.add(ruleString); //Adds rule for carbs greater of equal to value
					}
					if (!carbsMax.getText().trim().equals("")) {
						String ruleString = "Carbs <= " + carbsMax.getText().trim();
						rules.add(ruleString); //Adds rule for carbs less or equal to value
					}
				}
				if (fiber.isSelected()) {
					if (!fiberMin.getText().trim().equals("")) {
						String ruleString = "Fiber >= " + fiberMin.getText().trim();
						rules.add(ruleString); //Adds rule for fiber greater or equal to value
					}
					if (!fiberMax.getText().trim().equals("")) {
						String ruleString = "Fiber <= " + fiberMax.getText().trim();
						rules.add(ruleString); //Adds rule for fiber less or equal to value
					}
				}
				if (protein.isSelected()) {
					if (!proteinMin.getText().trim().equals("")) {
						String ruleString = "Protein >= " + proteinMin.getText().trim();
						rules.add(ruleString); //Adds rule for protein greater or equal to value
					}
					if (!proteinMax.getText().trim().equals("")) {
						String ruleString = "Protein <= " + proteinMax.getText().trim();
						rules.add(ruleString); //Adds rule for protein less or equal to value
					}
				}
				List<FoodItem> nameTemp = foodData.getAllFoodItems();
				if (foodName.isSelected()) //Adds rule for name of food item looking for
					nameTemp = foodData.filterByName(foodNameText.getText().trim());
				if (foodName.isSelected() == false)
					foodNameText.setText("");
				if (calories.isSelected() == false) {
					calories.setSelected(false);
					caloriesMin.setText(""); //Clears calories field if check box not selected
					caloriesMax.setText("");
				}
				if (fat.isSelected() == false) {
					fat.setSelected(false);
					fatMin.setText(""); //Clears fat field if check box is not selected
					fatMax.setText("");
				}
				if (carbs.isSelected() == false) {
					carbs.setSelected(false);
					carbsMin.setText(""); //Clears carbs field if check box is not selected
					carbsMax.setText("");
				}
				if (fiber.isSelected() == false) {
					fiber.setSelected(false);
					fiberMin.setText(""); //Clears fiber field if check box is not selected
					fiberMax.setText("");
				}
				if (protein.isSelected() == false) {
					protein.setSelected(false);
					proteinMin.setText(""); //Clears protein field if check box is not selected
					proteinMax.setText("");
				}
				List<FoodItem> nutrientsTemp = new ArrayList<FoodItem>();
				if (rules.size() > 0) {
					nutrientsTemp = foodData.filterByNutrients(rules); //List of foods if there are nutrition rules
				} else {
					nutrientsTemp = foodData.getAllFoodItems(); //List of all foods if no nutrition rules
				}

				List<FoodItem> intersect = nutrientsTemp.stream().filter(nameTemp::contains)
						.collect(Collectors.toList()); //The food items that only exist in both food lists
				ObservableList<String> foodNames = FXCollections.observableArrayList();

				for (int i = 0; i < intersect.size(); i++) {
					foodNames.add(intersect.get(i).getName());
					System.out.println(intersect.get(i).getName());
				}
				foodList.setItems(foodNames.sorted()); // sorts all the foodItems in the foodList

				mealList.getItems().clear(); // clears MealList

				mealInfo.clear(); //clears the nutrition analysis text info

				primaryStage.setScene(main);
				primaryStage.show(); //Goes back the main scene
			});

			primaryStage.setScene(main);
			primaryStage.show(); //Shows main scene initially at program start
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
