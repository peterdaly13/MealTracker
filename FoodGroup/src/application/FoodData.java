package application;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author Peter Daly (pndaly@wisc.edu)
           Andrew Elert (elert3@wisc.edu)
           Huzaifa Sohail (hsohail@wisc.edu)
           Sasha Arkhagha (arkhagha@wisc.edu)
           Ethan Yeck (eyeck@wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList= new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
          BufferedReader br = null;
          String line = "";
          String cvsSplitBy = ",";
          String[] dataArray = null;
          FoodItem newFood = null;
         
          try {
        	  foodItemList.clear();
              br = new BufferedReader(new FileReader(filePath));
              //reads each line separately
              while ((line = br.readLine()) != null) {

                  // Makes a food item for each line
                  dataArray = line.split(cvsSplitBy);
                  
                 
                  newFood = new FoodItem(dataArray[0],dataArray[1]);
                  newFood.addNutrient("Calories", Double.parseDouble(dataArray[3]) );
                  newFood.addNutrient("Fat", Double.parseDouble(dataArray[5]));
                  newFood.addNutrient("Carbs", Double.parseDouble(dataArray[7]));
                  newFood.addNutrient("Fiber", Double.parseDouble(dataArray[9]));
                  newFood.addNutrient("Protein",Double.parseDouble(dataArray[11]) );
                  foodItemList.add(newFood);
              }
              //Inititalizes BPTrees for each nutrient
              BPTree<Double,FoodItem> calories = new BPTree<Double,FoodItem>();
              BPTree<Double,FoodItem> fat = new BPTree<Double,FoodItem>();
              BPTree<Double,FoodItem> carbs = new BPTree<Double,FoodItem>();
              BPTree<Double,FoodItem> protein = new BPTree<Double,FoodItem>();
              BPTree<Double,FoodItem> fiber = new BPTree<Double,FoodItem>();
              //Puts each fooditem's data into all 5 trees
              for(int i=0; i<foodItemList.size(); i++) {
            	  calories.insert(foodItemList.get(i).getNutrientValue("Calories"), foodItemList.get(i));
            	  fat.insert(foodItemList.get(i).getNutrientValue("Fat"), foodItemList.get(i));
            	  carbs.insert(foodItemList.get(i).getNutrientValue("Carbs"), foodItemList.get(i));
            	  protein.insert(foodItemList.get(i).getNutrientValue("Protein"), foodItemList.get(i));
            	  fiber.insert(foodItemList.get(i).getNutrientValue("Fiber"), foodItemList.get(i));
              }
              //Puts the BPTrees into a HashMap
              indexes.put("Calories", calories);
              indexes.put("Fat", fat);
              indexes.put("Carbs", carbs);
              indexes.put("Protein", protein);
              indexes.put("Fiber", fiber);
              
            
              
             


          } catch (FileNotFoundException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          } catch (Exception e) {
        	  e.printStackTrace();
          }
          finally {
              if (br != null) {
                  try {
                      br.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
    	//list to return
    	List<FoodItem> filteredList =new ArrayList<FoodItem>();
    	//deals with capitalization
    	substring =substring.toLowerCase();
    	//checks each food
    	for(int i=0; i<foodItemList.size(); i++) {
    		String currentFood = foodItemList.get(i).getName().toLowerCase();
    		if(currentFood.contains(substring)) {
    			filteredList.add(foodItemList.get(i));
    		}
    	}
        
        return filteredList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {	
    	//gets the first rule
    	String firstRule= rules.get(0);
    	//parses rule
    	String [] parsed = firstRule.split(" ");
    	//finds correct bptree based on nutrient
    	BPTree<Double,FoodItem> nutrientTree= indexes.get(parsed[0]);
    	//queryList now holds all fooditems that passed the first filter
    	
    	List<FoodItem> queryList=nutrientTree.rangeSearch(Double.parseDouble(parsed[2]),parsed[1]);
    	//For subsequent filters, check FoodItems already in queryList to see if their nutrients meet all other filters.
    	for(int i=1; i<rules.size(); i ++) {
    		String [] currentRule = rules.get(i).split(" ");
    		String nutrient= currentRule[0];
    		String comparator= currentRule[1];
    		double reqValue= Double.parseDouble(currentRule[2]);
    		for(int j=0; j<queryList.size(); j++) {
    			FoodItem currentFood= queryList.get(j);
    			double nutrientValue = currentFood.getNutrientValue(nutrient);
    			//removes items that fail filters from queryList
    			if(comparator.equals(">=")) {
    				if(nutrientValue<=reqValue) {
    					queryList.remove(j);
    					//since we remove an item we can't increment j as the 'next' FoodItem will still be in the j index
    					j--;
    				}
    			}
    			else if(comparator.equals("==")) {
    				if(nutrientValue!=reqValue) {
    					queryList.remove(j);
    					j--;
    				}
    			}
    			else if(comparator.equals("<=")) {
    				if(nutrientValue>=reqValue) {
    					queryList.remove(j);
    					j--;
    				}
    			}
    		}
    	}
        
        return queryList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
    	/*This method is used to add a FoodItem to the foodItemList.
    	 * As shown below, we accomplish this by using the add method from the list class, and finishing the statement with a semicolon
    	 * This may seem confusing at first, but hopefully my commenting helped clear up any details :)
    	*/
        foodItemList.add(foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
    	/*Here we have another very confusing method
    	 * The goal of this method is to return the foodItemList
    	 * So our code for this method is "return foodItemList;"
    	*/
        return foodItemList;
    }
    public void saveFoodItems(String filename) {
    	//Sorts food list into ascending order by name
    	foodItemList.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
    	BufferedWriter writer;
    	try
        {
            File file = new File(filename);
            file.createNewFile();

            writer = new BufferedWriter(new FileWriter(file));
            //Writes a new line for each food Item
            for(FoodItem f : foodItemList) {
            	String foodString = f.getID() + "," + f.getName() + ",calories," +f.getNutrientValue("Calories") + 
            			",fat," +f.getNutrientValue("Fat") +",carbohydrate," +f.getNutrientValue("Carbs") +",fiber," +f.getNutrientValue("Fiber") +
            			",protein," +f.getNutrientValue("Protein");
            	writer.write(foodString);
                writer.newLine();
                writer.flush();
            }
            
          
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found");
            System.exit( 1 );
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            System.exit( 1 );
        }
    }

}
