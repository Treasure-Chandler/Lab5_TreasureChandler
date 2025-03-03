/**
 * @author Treasure Chandler
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookGUI extends Application {
    /* Initial GUI design */
    @Override
    public void start(Stage stage) {
        // Initialize the proper GUI components
        MenuBar mBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu shoppingMenu = new Menu("Shopping");
        VBox menuVBox = new VBox(mBar);
        GridPane grid = new GridPane();
        Label welcomeLabel = new Label("Welcome to the PFW Online Book Store!");

        // Create and add menu items
        // MenuItem fileItem = new MenuItem("File");

        // fileMenu.getItems().add(fileItem);

        // Add the menu to the menu bar
        mBar.getMenus().add(fileMenu);
        mBar.getMenus().add(shoppingMenu);

        // Instantiate the scene and put all of the elements together, sizing if necessary
        menuVBox.setPrefWidth(500);
        grid.add(menuVBox, 0, 0);
        Scene sc = new Scene(grid, 500, 400);

        // Load CSS file for styling
        sc.getStylesheets().add("styles.css");

        // Set the stage's title
        stage.setTitle("Book Store Shopping Cart");

        // Set the scene
        stage.setScene(sc);

        // Show the stage
        stage.show();
    } // End of start()

    /**
     * Method that runs all of the statements to display the GUI
     * 
     * @param args Entered values
     */
    public static void main(String[] args) {
        // Launch the application
        launch(args);
    } // End of main()
} // End of BookGUI