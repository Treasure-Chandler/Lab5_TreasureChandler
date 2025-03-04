/**
 * @author Treasure Chandler
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookGUI extends Application {
    /* Initial GUI design */
    @Override
    public void start(Stage stage) {
        // Initialize the proper GUI components alsong with the window size
        GridPane upperHalfGrid = new GridPane();
        GridPane lowerhalfGrid = new GridPane();
        GridPane mainGrid = new GridPane();
        MenuBar mBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu shoppingMenu = new Menu("Shopping");
        VBox menuVBox = new VBox(mBar);
        VBox upperHalfVBox = new VBox(menuVBox, upperHalfGrid);
        Label welcomeLabel = new Label("Welcome to the PFW Online Book Store!");
        Label avBooksLabel = new Label("Available Books");
        Label cartLabel = new Label("Shopping Cart");
        ListView<String> avBooksList = new ListView<>();
        ListView<String> cartList = new ListView<>();
        ObservableList<String> booksList = FXCollections.observableArrayList();
        final int WIDTH = 560;
        final int HEIGHT = 295;

        // Create and add menu items
        MenuItem loadBooksItem = new MenuItem("Load Books");
        MenuItem exitItem = new MenuItem("Exit");

        fileMenu.getItems().add(loadBooksItem);
        fileMenu.getItems().add(exitItem);

        // Add the menu to the menu bar
        mBar.getMenus().add(fileMenu);
        mBar.getMenus().add(shoppingMenu);

        // Put all of the elements together
        upperHalfGrid.add(menuVBox, 0, 0);
        upperHalfGrid.add(welcomeLabel, 0, 1);
        lowerhalfGrid.add(avBooksLabel, 0, 0);
        lowerhalfGrid.add(avBooksList, 0, 1);
        lowerhalfGrid.add(cartLabel, 1, 0);
        lowerhalfGrid.add(cartList, 1, 1);
        mainGrid.add(upperHalfVBox, 0, 0);
        mainGrid.add(lowerhalfGrid, 0, 1);

        // Instantiate the scene
        Scene sc = new Scene(mainGrid, WIDTH, HEIGHT);

        // Load CSS file for styling
        sc.getStylesheets().add("styles.css");

        // Style elements with their respective ids as needed
        menuVBox.setId("menu");
        welcomeLabel.setId("welcome");
        avBooksList.setId("available-books");
        lowerhalfGrid.setId("lower-half");

        // Populate the available books list view
        avBooksList.setItems(booksList);

        /* Action events */
        // Open the file chooser when the user clicks this button
        loadBooksItem.setOnAction(
            new EventHandler<ActionEvent>() {
                /**
                 * Opens the file chooser
                 * 
                 * @param e     Action executed
                 */
                @Override
                public void handle(ActionEvent e) {
                    FileChooser fileChooser = new FileChooser();
                    // The application will open the project folder by default, which has the Books.txt file
                    File projectDir = new File(System.getProperty("user.dir"));
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                    fileChooser.setInitialDirectory(projectDir);
                    File books = fileChooser.showOpenDialog(stage);

                    // Reads the file contents and adds the book titles to Available Books
                    if (books != null) {
                        booksList.clear();
                        try {
                            List<String> lines = Files.readAllLines(Path.of(books.toURI()));
                            List<String> txtBooks = lines.stream()
                                                    .map(line -> line.split(",")[0])
                                                    .collect(Collectors.toList());
                            booksList.addAll(txtBooks);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } // End of handle()
            }
        );

        // Exit the application when the user clicks this button
        exitItem.setOnAction(
            new EventHandler<ActionEvent>() {
                /**
                 * Exits the application
                 * 
                 * @param e     Action executed
                 */
                @Override
                public void handle(ActionEvent e) {
                    stage.close();
                } // End of handle()
            }
        );

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