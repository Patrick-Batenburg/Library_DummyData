package library.datastorage;

import library.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlBookDAO implements BookDAOInf
{
    private DatabaseConnection databaseConnection;

    public MySqlBookDAO()
    {
        databaseConnection = new DatabaseConnection();
    }

    /**
     * View the details of all the books.
     */
    public List<Book> details()
    {
        List<Book> result = new ArrayList<Book>();

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book`;");

            while(resultSet.next())
            {
                long ISBN = resultSet.getInt("ISBN");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String edition = resultSet.getString("Edition");
                result.add(new Book(ISBN, title, author, edition));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            result = null;
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

    /**
     * View the details of the book.
     *
     * @param ISBN The ISBN of the book.
     */
    public Book details(long ISBN)
    {
        Book result = null;

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book` WHERE `ISBN`=" + ISBN + ";");
            String title = resultSet.getString("Title");
            String author = resultSet.getString("Author");
            String edition = resultSet.getString("Edition");

            result = new Book(ISBN, title, author, edition);
        }
        catch (SQLException e)
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
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("INSERT INTO `book` (ISBN, Title, Author, Edition) VALUES (" + book.getISBN() + ", '" + book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getEdition() + "');");
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
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("UPDATE `book` SET Title='" + book.getTitle() + "', Author='" + book.getAuthor() + "', Edition='" + book.getEdition() + "' WHERE  ISBN=" + book.getISBN() + ";");
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