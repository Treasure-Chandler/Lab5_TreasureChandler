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
import javafx.scene.control.Button;
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
        ListView<Book> avBooksList = new ListView<>();
        ListView<Book> cartList = new ListView<>();
        ObservableList<Book> booksList = FXCollections.observableArrayList();
        ObservableList<Book> cartBooks = FXCollections.observableArrayList();
        final int WIDTH = 560;
        final int HEIGHT = 280;

        // Create and add menu items
        MenuItem loadBooks = new MenuItem("Load Books");
        MenuItem exit = new MenuItem("Exit");
        MenuItem addBook = new MenuItem("Add Selected Book");
        MenuItem removeBook = new MenuItem("Remove Selected Book");
        MenuItem clearCart = new MenuItem("Clear Cart");
        MenuItem checkOut = new MenuItem("Check Out");

        fileMenu.getItems().add(loadBooks);
        fileMenu.getItems().add(exit);
        shoppingMenu.getItems().add(addBook);
        shoppingMenu.getItems().add(removeBook);
        shoppingMenu.getItems().add(clearCart);
        shoppingMenu.getItems().add(checkOut);

        // Add the menu to the menu bar
        mBar.getMenus().add(fileMenu);
        mBar.getMenus().add(shoppingMenu);

        // Put all of the elements in the scene together
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

        /*
         * Populate the available books list view, along with setting
         * up the shopping cart to be populated
         */
        avBooksList.setItems(booksList);
        cartList.setItems(cartBooks);

        /* Action events */
        // Open the file chooser when the user clicks this button
        loadBooks.setOnAction(
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
                            List<Book> txtFileBooks = lines.stream()
                                                    .map(line -> {
                                                        String[] parts = line.split(",");
                                                        String title = parts[0].trim();
                                                        double price =
                                                            (parts.length > 1) ? Double.parseDouble(parts[1].trim()) : 0.0;
                                                        return new Book(title, price);
                                                    })
                                                    .collect(Collectors.toList());
                            booksList.addAll(txtFileBooks);
                        } catch (IOException | NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                } // End of handle()
            }
        );

        // Exit the application when the user clicks this button
        exit.setOnAction(_ -> stage.close());

        // Get the selected book from the available books and add it to the shopping cart
        addBook.setOnAction(
            new EventHandler<ActionEvent>() {
                /**
                 * Adds book to the cart
                 * 
                 * @param e     Action executed
                 */
                @Override
                public void handle(ActionEvent e) {
                    Book selectedBook = avBooksList.getSelectionModel().getSelectedItem();
                    if (selectedBook != null) {
                        cartList.getItems().add(selectedBook);
                    }
                } // End of handle()
            }
        );

        // Remove the selected book from the shopping cart
        removeBook.setOnAction(
            new EventHandler<ActionEvent>() {
                /**
                 * Removes book from the cart
                 * 
                 * @param e     Action executed
                 */
                @Override
                public void handle(ActionEvent e) {
                    Book selectedBook = cartList.getSelectionModel().getSelectedItem();
                    if (selectedBook != null) {
                        cartList.getItems().remove(selectedBook);
                    }
                } // End of handle()
            }
        );

        // Clear the cart
        clearCart.setOnAction(_ -> cartBooks.clear());

        // Checking out
        checkOut.setOnAction(
            new EventHandler<ActionEvent>() {
                /**
                 * Removes book from the cart
                 * 
                 * @param e     Action executed
                 */
                @Override
                public void handle(ActionEvent e) {
                    // Variables declaration
                    double subtotal;
                    double salesTax;
                    double total;

                    // Create a seperate window showing the price calculations
                    Stage checkoutStage = new Stage();
                    Label subtotalLabel = new Label("Subtotal: ");
                    Label salesTaxLabel = new Label("Tax: ");
                    Label totalLabel = new Label("Total: ");
                    Button confirmButton = new Button("OK");
                    VBox costBox = new VBox(subtotalLabel, salesTaxLabel, totalLabel, confirmButton);
                    final int WIDTH = 130;
                    final int HEIGHT = 92;

                    // Instantiate and set the scene
                    Scene checkoutScene = new Scene(costBox, WIDTH, HEIGHT);
                    checkoutStage.setScene(checkoutScene);

                    // Load CSS file for styling
                    checkoutScene.getStylesheets().add("styles.css");

                    // Style elements with their respective ids as needed
                    subtotalLabel.setId("checkout");
                    salesTaxLabel.setId("checkout");
                    totalLabel.setId("checkout");

                    // Show the window
                    checkoutStage.show();
                }
            }
        );
        /* End of action events section */

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