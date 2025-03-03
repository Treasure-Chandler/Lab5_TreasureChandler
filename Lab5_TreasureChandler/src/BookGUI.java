/**
 * @author Treasure Chandler
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        GridPane upperHalfGrid = new GridPane();
        MenuBar mBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu shoppingMenu = new Menu("Shopping");
        VBox menuVBox = new VBox(mBar);
        VBox upperHalfVBox = new VBox(menuVBox, upperHalfGrid);
        Label welcomeLabel = new Label("Welcome to the PFW Online Book Store!");
        GridPane lowerhalfGrid = new GridPane();
        Label avBooksLabel = new Label("Available Books");
        Label cartLabel = new Label("Shopping Cart");
        ListView<String> avBooksList = new ListView<>();
        ListView<String> cartList = new ListView<>();
        GridPane mainGrid = new GridPane();

        // Create and add menu items
        // MenuItem fileItem = new MenuItem("File");

        // fileMenu.getItems().add(fileItem);

        // Add the menu to the menu bar
        mBar.getMenus().add(fileMenu);
        mBar.getMenus().add(shoppingMenu);

        // Put all of the elements together, sizing if necessary
        menuVBox.setPrefWidth(600);
        welcomeLabel.setPrefWidth(600);
        upperHalfGrid.add(menuVBox, 0, 0);
        upperHalfGrid.add(welcomeLabel, 0, 1);
        lowerhalfGrid.add(avBooksLabel, 0, 0);
        lowerhalfGrid.add(cartLabel, 1, 0);
        mainGrid.add(upperHalfVBox, 0, 0);
        mainGrid.add(lowerhalfGrid, 0, 1);

        // Instantiate the scene
        Scene sc = new Scene(mainGrid, 600, 400);

        // Load CSS file for styling
        sc.getStylesheets().add("styles.css");

        // Style elements with their respective ids as needed
        welcomeLabel.setId("welcome");

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