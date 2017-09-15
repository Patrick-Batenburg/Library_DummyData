/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2017 Patrick van Batenburg
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package library.datastorage;

import library.domain.Book;
import library.domain.Copy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCopyDAO implements CopyDAOInf
{
    private DatabaseConnection databaseConnection;
    private BookDAOInf bookDAO;

    public MySqlCopyDAO()
    {
        databaseConnection = new DatabaseConnection();
        bookDAO = new MySqlBookDAO();
    }

    /**
     * View the details of all the copies.
     */
    public List<Copy> details()
    {
        List<Copy> result = new ArrayList<Copy>();

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `copy` INNER JOIN `book` ON `copy`.`BookISBN`= `book`.`ISBN`;");
            int copyID = resultSet.getInt("CopyID");
            int lendingPeriod = resultSet.getInt("LendingPeriod");
            long ISBN = resultSet.getInt("ISBN");
            String title = resultSet.getString("Title");
            String author = resultSet.getString("Author");
            String edition = resultSet.getString("Edition");

            result.add(new Copy(copyID, lendingPeriod, new Book(ISBN, title, author, edition)));
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
	 * View the details of the copy.
	 * 
	 * @param copyID	The copy ID of the book.
	 * @param ISBN		The ISBN of the copy.	
	 */
	public Copy details(int copyID, long ISBN)
	{
        Copy result = null;
        Book book = bookDAO.details(ISBN);

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `copy` WHERE `CopyID`=" + copyID + " AND `BookISBN`=" + ISBN + ";");
            int lendingPeriod = resultSet.getInt("LendingPeriod");

            result = new Copy(copyID, lendingPeriod, book);
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
	 * Creates a new copy.
	 * 
	 * @param copy	The Copy object.
	 */
	public boolean create(Copy copy)
	{
        boolean result = false;

        if (copy != null)
        {
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("INSERT INTO `copy` (LendingPeriod, BookISBN, UpdatedDate) VALUES (" + copy.getLendingPeriod() +", " + copy.getBook().getISBN() + ", '" +  copy.getLoan().getReturnDate() + "');");
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
	 * Edit an existing copy.
	 * 
	 * @param copy	The Copy object.
	 */
	public boolean update(Copy copy)
	{
        boolean result = false;

        if (copy != null)
        {
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("UPDATE `copy` SET LendingPeriod=" + copy.getLendingPeriod() + ", UpdatedDate='" + copy.getLoan().getReturnDate() + "' WHERE CopyID=" + copy.getCopyID() + " AND BookISBN=" + copy.getBook().getISBN() + ";");
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
	 *  Deletes an existing copy.
	 * 
	 * @param copyID	The copy ID of the book.
	 * @param ISBN		The ISBN of the copy.	
	 */
	public boolean delete(int copyID, long ISBN)
	{
        boolean result = false;

        try
        {
            databaseConnection.openConnection();
            result =  databaseConnection.executeSQLDMLStatement("DELETE FROM `copy` WHERE `CopyID`=" + copyID + " AND `BookISBN`=" + ISBN + ";");
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