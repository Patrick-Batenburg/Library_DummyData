package library.datastorage;

import library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDataBookDAO implements BookDAOInf
{
    private DatabaseConnection databaseConnection;
    private List<Book> books;

    public TestDataBookDAO()
    {
        databaseConnection = new DatabaseConnection();
        books = new ArrayList<Book>();
    }

    /**
     * View the details of all the books.
     */
    public List<Book> details()
    {
        return books;
    }

    /**
     * View the details of the book.
     *
     * @param ISBN The ISBN of the book.
     */
    public Book details(long ISBN)
    {
        Book result = null;

        for (Book book : books)
        {
            if (book.getISBN() == ISBN)
            {
                result = book;
            }
        }

        return result;
    }

    /**
     * Creates a new book.
     *
     * @param book The Book object.
     */
    public boolean create(Book book)
    {
        boolean result = false;

        if (book != null)
        {
            books.add(book);
            result = true;
        }

        return result;
    }

	/**
	 * Edit an existing book.
	 *
	 * @param book	The Book object.
	 */
	public boolean update(Book book)
	{
        boolean result = false;

        if (book != null)
        {
            for (Book item : books)
            {
                if (book.getISBN() == item.getISBN())
                {
                    item = book;
                    result = true;
                }
            }
        }

        return result;
	}

	/**
	 *  Deletes an existing book.
	 *
	 * @param ISBN	The ISBN of the book.
	 */
	public boolean delete(long ISBN)
	{
        boolean result = false;

        try
        {
            databaseConnection.openConnection();
            result =  databaseConnection.executeSQLDMLStatement("DELETE FROM `book` WHERE ISBN=" + ISBN + ";");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if (databaseConnection.connectionIsOpen())
            {
                databaseConnection.closeConnection();
            }
        }

        return result;
	}
}