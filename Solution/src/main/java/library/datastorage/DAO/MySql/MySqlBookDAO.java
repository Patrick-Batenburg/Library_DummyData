/*
 * MIT License
 *
 * Copyright (c) 2017 Patrick van Batenburg
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package library.datastorage.DAO.MySql;

import library.datastorage.DAO.Inf.BookDAOInf;
import library.datastorage.DAO.Inf.CopyDAOInf;
import library.datastorage.DatabaseConnection;
import library.domain.Book;
import library.domain.Copy;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<Book> findDetails()
    {
        CopyDAOInf copyDAO = new MySqlCopyDAO();
        List<Book> result = new ArrayList<Book>();

        try
        {
            List<Copy> copies = copyDAO.findDetails();
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book`;");

            if (resultSet != null)
            {
                while (resultSet.next())
                {
                    long ISBN = resultSet.getInt("ISBN");
                    String title = resultSet.getString("Title");
                    String author = resultSet.getString("Author");
                    String edition = resultSet.getString("Edition");
                    Book book = new Book(ISBN, title, author, edition);

                    if (copies.size() != 0)
                    {
                        for (Copy copy : copies)
                        {
                            if (copy.getBook().getISBN() == ISBN)
                            {
                                book.addCopy(copy);
                                copies.remove(copy);
                            }
                        }
                    }

                    result.add(book);
                }
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
    public Book findDetails(long ISBN)
    {
        CopyDAOInf copyDAO = new MySqlCopyDAO();
        Book result = null;

        try
        {
            List<Copy> copies = copyDAO.findDetails();
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book` WHERE `ISBN`=" + ISBN + ";");

            if (resultSet != null)
            {
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String edition = resultSet.getString("Edition");
                Book book = new Book(ISBN, title, author, edition);

                if (copies.size() != 0)
                {
                    for (Copy copy : copies)
                    {
                        if (copy.getBook().getISBN() == ISBN)
                        {
                            book.addCopy(copy);
                        }
                    }
                }

                result = new Book(ISBN, title, author, edition);
            }
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