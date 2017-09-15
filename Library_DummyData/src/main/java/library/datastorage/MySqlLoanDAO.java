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
import library.domain.Loan;
import library.domain.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MySqlLoanDAO implements LoanDAOInf
{
    private DatabaseConnection databaseConnection;
    private MemberDAOInf memberDAO;
    private CopyDAOInf copyDAO;

    public MySqlLoanDAO()
    {
        databaseConnection = new DatabaseConnection();
        memberDAO = new MySqlMemberDAO();
        copyDAO = new MySqlCopyDAO();
    }

    /**
     * View the details of all the loans.
     */
    public List<Loan> details()
    {
        List<Loan> result = new ArrayList<Loan>();

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `loan` INNER JOIN `member` ON `loan`.`MemberID` = `member`.`MemberID` INNER JOIN `copy` ON `loan`.`CopyID` = `copy`.`CopyID` INNER JOIN `book` ON `copy`.`BookISBN`= `book`.`ISBN`;");

            Timestamp returnedDate = resultSet.getTimestamp("ReturnedDate");

            int memberID = resultSet.getInt("MemberID");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String street = resultSet.getString("Street");
            String houseNumber = resultSet.getString("HouseNumber");
            String city = resultSet.getString("City");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String emailAddress = resultSet.getString("EmailAddress");
            double fine = resultSet.getDouble("Fine");

            int copyID = resultSet.getInt("CopyID");
            int lendingPeriod = resultSet.getInt("LendingPeriod");
            long ISBN = resultSet.getInt("ISBN");
            String title = resultSet.getString("Title");
            String author = resultSet.getString("Author");
            String edition = resultSet.getString("Edition");

            result.add(new Loan(new Member(memberID, firstName, lastName, street, houseNumber, city, phoneNumber, emailAddress, fine), new Copy(copyID, lendingPeriod, new Book(ISBN, title, author, edition)), returnedDate));
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
	 * View the details of the loan.
	 *
     * @param loanDate	The loan date of the reservation.
     * @param memberID	The member ID of the reservation.
	 * @param copyID	The ID of the copy.
	 */
	public Loan details(Timestamp loanDate, int memberID, int copyID)
	{
        Loan result = null;
        Member member;
        Copy copy;

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `loan` WHERE `LoanDate`=" + loanDate + " AND `memberID`=" + memberID + " AND `copyID`=" + copyID);
            Timestamp returnedDate = resultSet.getTimestamp("ReturnedDate");

            resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book` WHERE `CopyID`=" + copyID + " AND `UpdatedDate`='" + loanDate + "';");
            int ISBN = resultSet.getInt("ISBN");
            databaseConnection.closeConnection();

            copy = copyDAO.details(copyID, ISBN);
            member = memberDAO.details(memberID);

            result = new Loan(member, copy, returnedDate);
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
	 * Creates a new loan.
	 * 
	 * @param loan	The Loan object.
	 */
	public boolean create(Loan loan)
	{
        boolean result = false;

        if (loan != null)
        {
            try
            {
                //LoanDate	ReturnedDate	MemberID	CopyID
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("INSERT INTO `loan` (ReturnedDate, MemberID, CopyID) VALUES ('" + loan.getReturnDate() + "', " + loan.getMember().getMemberID() + ", " + loan.getCopy().getCopyID() + ");");
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
	 * Edit an existing loan.
	 * 
	 * @param loan	The Loan object.
	 */
	public boolean update(Loan loan)
	{
        boolean result = false;

        if (loan != null)
        {
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("UPDATE `Loan` SET ReturnedDate='" + loan.getReturnDate() + "', MemberID=" + loan.getMember().getMemberID() + ", CopyID=" + loan.getCopy().getCopyID() + ";");
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
	 *  Deletes an existing loan.
	 *
	 * @param memberID	The membership number of the reservation.
	 * @param copyID	The ID of the copy.
	 */
	public boolean delete(int memberID, int copyID)
	{
        boolean result = false;

        try
        {
            databaseConnection.openConnection();
            result =  databaseConnection.executeSQLDMLStatement("DELETE FROM `loan` WHERE MemberID=" + memberID + " AND CopyID=" + copyID + ";");
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