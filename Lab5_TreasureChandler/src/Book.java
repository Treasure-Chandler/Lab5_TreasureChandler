
/**
 * @author Treasure Chandler
 * 
 * The values in this class are referenced in the BookGUI class
 * for easy access to the titles and prices in the Books.txt file.
 */

public class Book {
    // Variables declaration
    public String title;
    public double price;

    /**
     * Constructor to accept and assign the price and title
     * 
     * @param title
     * @param price
     */
    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    } // End of Book constructor

    /**
     * Returns the title of the book
     */
    @Override
    public String toString() {
        return title;
    } // End of toString()
} // End of Book