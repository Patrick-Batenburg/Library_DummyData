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
import library.domain.Member;
import library.domain.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class MySqlReservationDAO implements ReservationDAOInf
{
    //ReservationID	ReservationDate	MemberID	CopyID	UpdatedDate
	private DatabaseConnection databaseConnection;
	private MemberDAOInf memberDAO;
    private BookDAOInf bookDAO;
    private CopyDAOInf copyDAO;

	public MySqlReservationDAO()
	{
		databaseConnection = new DatabaseConnection();
		memberDAO = new MySqlMemberDAO();
        bookDAO = new MySqlBookDAO();
        copyDAO = new MySqlCopyDAO();
    }

	/**
	 * View the details of the reservation.
	 *
     * @param memberID	        The member ID of the reservation.
     * @param reservationDate	The reservation date of the reservation.
     * @param ISBN		        The ISBN of the reservation.
     */
    public Reservation details(Timestamp reservationDate, int memberID, int ISBN)
    {
        Reservation result = null;
        Member member = memberDAO.details(memberID);
        Book book = bookDAO.details(ISBN);

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `reservation` WHERE `MemberID`=" + memberID + " AND `BookISBN`=" + ISBN + " AND ReservationDate='" + reservationDate + "';");
            Timestamp updatedDate = resultSet.getTimestamp("UpdatedDate");

            result = new Reservation(member, book, reservationDate);
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
	 * Creates a new reservation.
	 * 
	 * @param reservation	The Reservation object.
	 */
	public boolean create(Reservation reservation)
	{
        boolean result = false;

        if (reservation != null)
        {
            try
            {
                databaseConnection.openConnection();
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT `CopyID`, `LendingPeriod`, MIN(`UpdatedDate`) AS `UpdatedDate` FROM `copy` WHERE `BookISBN`=" + reservation.getBook().getISBN() + ";");
                int copyID = resultSet.getInt("CopyID");
                int lendingPeriod = resultSet.getInt("LendingPeriod");
                Timestamp updatedDate = resultSet.getTimestamp("UpdatedDate");

                result = databaseConnection.executeSQLDMLStatement("INSERT INTO `reservation` (MemberID, CopyID, UpdatedDate) VALUES (" + reservation.getMember().getMemberID() + ", " + copyID + ", '" + updatedDate + "');");
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
	 * Edit an existing reservation.
	 * 
	 * @param reservation	The Reservation object.
	 */
	public boolean update(Reservation reservation)
	{
        boolean result = false;

        if (reservation != null)
        {
            try
            {
                databaseConnection.openConnection();
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT `CopyID`, `LendingPeriod`, MIN(`UpdatedDate`) AS `UpdatedDate` FROM `copy` WHERE `BookISBN`=" + reservation.getBook().getISBN() + ";");
                int copyID = resultSet.getInt("CopyID");
                int lendingPeriod = resultSet.getInt("LendingPeriod");
                Timestamp updatedDate = resultSet.getTimestamp("UpdatedDate");

                Date date = new Date(updatedDate.getTime());
                int noOfDays = lendingPeriod * 7;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                date = calendar.getTime();

                result = databaseConnection.executeSQLDMLStatement("UPDATE `reservation` SET UpdatedDate='" + date + "' WHERE MemberID=" + reservation.getMember().getMemberID() + " AND CopyID=" + copyID + " AND ReservationDate='" + reservation.getReservationDate() + "';");
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
	 * Deletes an existing reservation.
	 *
     * @param memberID	        The member ID of the reservation.
     * @param reservationDate	The reservation date of the reservation.
	 * @param ISBN		        The ISBN of the reservation.
	 */
	public boolean delete(Timestamp reservationDate, int memberID, int ISBN)
	{
        boolean result = false;

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT `CopyID`, MIN(`UpdatedDate`) AS `UpdatedDate` FROM `copy` WHERE `BookISBN`=" + ISBN + ";");
            int copyID = resultSet.getInt("CopyID");
            Timestamp updatedDate = resultSet.getTimestamp("UpdatedDate");

            result =  databaseConnection.executeSQLDMLStatement("DELETE FROM `reservation` WHERE ReservationDate=" + reservationDate + " AND MemberID=" + memberID + " AND CopyID= " + copyID + ";");
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