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

package library.datastorage.DAO.TestData;

import library.datastorage.DAO.Inf.BookDAOInf;
import library.datastorage.DAO.Inf.CopyDAOInf;
import library.datastorage.DAO.Inf.MemberDAOInf;
import library.datastorage.DAO.Inf.ReservationDAOInf;
import library.domain.Book;
import library.domain.Copy;
import library.domain.Member;
import library.domain.Reservation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestDataReservationDAO implements ReservationDAOInf
{
    private List<Reservation> reservations;
    private List<Member> members;
    private List<Book> books;

	public TestDataReservationDAO(List<Member> members, List<Book> books)
	{
	    reservations = new ArrayList<Reservation>();
        this.members = members;
        this.books = books;
    }

    /**
     * View the details of all the reservation.
     */
    public List<Reservation> findDetails()
    {
        return reservations;
    }

    /**
	 * View the details of the reservation.
	 *
     * @param memberID	        The member ID of the reservation.
     * @param reservationDate	The reservation date of the reservation.
     * @param ISBN		        The ISBN of the reservation.
     */
    public Reservation findDetails(Timestamp reservationDate, int memberID, long ISBN)
    {
        Reservation result = null;

        for (Reservation reservation : reservations)
        {
            if (reservation.getMember().getMemberID() == memberID && reservation.getBook().getISBN() == ISBN)
            {
                result = reservation;
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
            reservations.add(reservation);
            result = true;
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
        int index = 0;

        if (reservation != null)
        {
            if (reservations.size() != 0)
            {
                for (Reservation item : reservations)
                {
                    index++;

                    if (reservation.getBook().getISBN() == item.getBook().getISBN() && reservation.getMember().getMemberID() == item.getMember().getMemberID() && reservation.getReservationDate() == item.getReservationDate())
                    {
                        reservations.set(index, item);
                        result = true;
                    }
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
	public boolean delete(Timestamp reservationDate, int memberID, long ISBN)
	{
        boolean result = false;

        if (reservations.size() != 0)
        {
            for (Reservation reservation : reservations)
            {
                if (reservation.getBook().getISBN() == ISBN && reservation.getMember().getMemberID() == memberID && reservation.getReservationDate() == reservationDate)
                {
                    reservations.remove(reservation);
                    result = true;
                }
            }
        }

        return result;
	}
}