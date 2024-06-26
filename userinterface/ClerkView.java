// specify the package
package userinterface;

// system imports
import java.util.Properties;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// project imports
import impresario.IModel;

/** The class containing the Clerk View  for the ClothesCloset application */
//==============================================================
public class ClerkView extends View {
	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ClerkView(IModel clerk) {


		super(clerk, "ClerkView");
		String css = getClass().getResource("Styles.css").toExternalForm();
        getStylesheets().add(css);

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setAlignment(Pos.CENTER);

		container.setPadding(new Insets(15, 5, 5, 5));
		container.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		
		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		// STEP 0: Be sure you tell your model what keys you are interested in
		// myModel.subscribe("LoginError", this);
	}

	// Create the label (Text) for the title of the screen
	//-------------------------------------------------------------
	private Node createTitle() {
		Text titleText = new Text("Choose Transaction");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
	}

	private VBox createFormContents() {
		VBox form = new VBox(20);
		form.setAlignment(Pos.CENTER);
		form.setPadding(new Insets(25,100, 25, 100));


		
		VBox articleTypeBox = createOptionBox("Article Type", "AddArticleType", "ModifyArticleType", "DeleteArticleType");
		VBox colorBox = createOptionBox("Color", "AddColor", "ModifyColor", "DeleteColor");
		VBox inventoryBox = createOptionBox("Inventory Item", "AddInventory", "ModifyInventory", "DeleteInventory");
	
		Button checkoutInventoryButton = new Button("Checkout an Item");
		checkoutInventoryButton.setOnAction(e -> myModel.stateChangeRequest("CheckoutInventory", null));
	
		Button listAvailableInventoryButton = new Button("List Available Inventory");
		listAvailableInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListAvailableInventory", null));
	
		Button listCheckedOutInventoryButton = new Button("List Checked-out Inventory");
		listCheckedOutInventoryButton.setOnAction(e -> myModel.stateChangeRequest("ListCheckedOutInventory", null));
	
		VBox buttonsBox = new VBox(10);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.getChildren().addAll(checkoutInventoryButton, listAvailableInventoryButton, listCheckedOutInventoryButton);
	
		form.getChildren().addAll(articleTypeBox, colorBox, inventoryBox, buttonsBox);
		return form;
	}
	
	private VBox createOptionBox(String title, String addCommand, String modifyCommand, String deleteCommand) {
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
	
		Text titleLabel = new Text(title);
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		titleLabel.setFill(Color.DARKGREEN);
		box.getChildren().add(titleLabel);
	
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20); 
		grid.setVgap(10);
	
		Button addButton = new Button("Add");
		addButton.setMaxWidth(Double.MAX_VALUE); 
		addButton.setOnAction(e -> myModel.stateChangeRequest(addCommand, null));
	
		Button modifyButton = new Button("Modify");
		modifyButton.setMaxWidth(Double.MAX_VALUE); 
		modifyButton.setOnAction(e -> myModel.stateChangeRequest(modifyCommand, null));
	
		Button deleteButton = new Button("Delete");
		deleteButton.setMaxWidth(Double.MAX_VALUE); 
		deleteButton.setOnAction(e -> myModel.stateChangeRequest(deleteCommand, null));
	
		grid.addRow(0, addButton, modifyButton, deleteButton);
		box.getChildren().add(grid);
	
		return box;
	}
	
	

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void processSearchBooks(Event evt) {
		clearErrorMessage();
		Properties props = new Properties();
		myModel.stateChangeRequest("SearchBooks", props);

	}

	public void processSearchPatrons(Event evt) {
		clearErrorMessage();
		Properties props = new Properties();
		myModel.stateChangeRequest("SearchPatrons", props);

	}

	//---------------------------------------------------------
	public void updateState(String key, Object value) {
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true) {
			// display the passed text
			displayErrorMessage((String)value);
		}

	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}
}

