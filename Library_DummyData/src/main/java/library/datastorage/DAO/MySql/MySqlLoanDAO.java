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

import library.datastorage.DAO.Inf.CopyDAOInf;
import library.datastorage.DAO.Inf.LoanDAOInf;
import library.datastorage.DAO.Inf.MemberDAOInf;
import library.datastorage.DatabaseConnection;
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

    public MySqlLoanDAO()
    {
        databaseConnection = new DatabaseConnection();
    }

    /**
     * View the details of all the loans.
     */
    public List<Loan> findDetails()
    {
        MemberDAOInf memberDAO = new MySqlMemberDAO();
        CopyDAOInf copyDAO = new MySqlCopyDAO();
        List<Loan> result = new ArrayList<Loan>();

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `loan` INNER JOIN `member` ON `loan`.`MemberID` = `member`.`MemberID` INNER JOIN `copy` ON `loan`.`CopyID` = `copy`.`CopyID` INNER JOIN `book` ON `copy`.`BookISBN`= `book`.`ISBN`;");

            if (resultSet != null)
            {
                while (resultSet.next())
                {
                    Timestamp returnedDate = resultSet.getTimestamp("ReturnedDate");
                    int memberID = resultSet.getInt("MemberID");
                    int copyID = resultSet.getInt("CopyID");
                    long ISBN = resultSet.getInt("ISBN");
                    Copy copy = copyDAO.findDetails(copyID, ISBN);
                    Member member = memberDAO.findDetails(memberID);

                    result.add(new Loan(member, copy, returnedDate));
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
	 * View the details of the loan.
	 *
     * @param loanDate	The loan date of the reservation.
     * @param memberID	The member ID of the reservation.
	 * @param copyID	The ID of the copy.
	 */
	public Loan findDetails(Timestamp loanDate, int memberID, int copyID)
	{
        MemberDAOInf memberDAO = new MySqlMemberDAO();
        CopyDAOInf copyDAO = new MySqlCopyDAO();

        Loan result = null;
        Member member;
        Copy copy;

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `loan` WHERE `LoanDate`=" + loanDate + " AND `memberID`=" + memberID + " AND `copyID`=" + copyID);

            if (resultSet != null)
            {
                Timestamp returnedDate = resultSet.getTimestamp("ReturnedDate");

                resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `book` WHERE `CopyID`=" + copyID + " AND `UpdatedDate`='" + loanDate + "';");

                if (resultSet != null)
                {
                    int ISBN = resultSet.getInt("ISBN");
                    databaseConnection.closeConnection();

                    copy = copyDAO.findDetails(copyID, ISBN);
                    member = memberDAO.findDetails(memberID);

                    result = new Loan(member, copy, returnedDate);
                }
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