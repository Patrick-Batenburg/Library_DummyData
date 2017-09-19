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

import library.datastorage.DAO.Inf.LoanDAOInf;
import library.datastorage.DAO.Inf.MemberDAOInf;
import library.datastorage.DAO.Inf.ReservationDAOInf;
import library.domain.Loan;
import library.domain.Member;
import library.domain.Reservation;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestDataMemberDAO implements MemberDAOInf
{
    private List<Member> members;
    private List<Loan> loans;
    private List<Reservation> reservations;

    public TestDataMemberDAO(List<Loan> loans, List<Reservation> reservations)
    {
        members = new ArrayList<Member>();
        this.loans = loans;
        this.reservations = reservations;
    }

    /**
     * View the details of all the members.
     */
    public List<Member> findDetails()
    {
        if (members.size() == 0)
        {
            for (Member member : members)
            {
                if (loans.size() != 0)
                {
                    for (Loan loan : loans)
                    {
                        if (loan.getMember().getMemberID() == member.getMemberID())
                        {
                            member.addLoan(loan);
                            loans.remove(loan);
                        }
                    }
                }

                if (reservations.size() != 0)
                {
                    for (Reservation reservation : reservations)
                    {
                        if (reservation.getMember().getMemberID() == member.getMemberID())
                        {
                            member.addReservation(reservation);
                            reservations.remove(reservation);
                        }
                    }
                }
            }
        }

        return members;
    }


    /**
	 * View the details of the member.
	 *
	 * @param memberID	The member ID of the reservation.
	 */
	public Member findDetails(int memberID)
	{
        Member result = null;

        for (Member member : members)
        {
            if (member.getMemberID() == memberID)
            {
                result = member;
            }
        }

        return result;
	}

	/**
	 * Creates a new member.
	 * 
	 * @param member	The Member object.
	 */
	public boolean create(Member member)
	{
        boolean result = false;

        if (member != null)
        {
            members.add(member);
            result = true;
        }

		return result;
	}

	/**
	 * Edit an existing member.
	 * 
	 * @param member	The Member object.
	 */
	public boolean update(Member member)
	{
        boolean result = false;
        int index = 0;

        if (member != null)
        {
            if (members.size() != 0)
            {
                for (Member item : members)
                {
                    index++;

                    if (member.getMemberID() == item.getMemberID())
                    {
                        members.set(index, member);
                        result = true;
                    }
                }
            }
        }

        return result;
	}

	/**
	 * Deletes an existing member.
	 *
	 * @param memberID	The member ID of the reservation.
	 */
	public boolean delete(int memberID)
	{
        boolean result = false;

        if (members.size() != 0)
        {
            for (Member member : members)
            {
                if (member.getMemberID() == memberID)
                {
                    members.remove(member);
                    result = true;
                }
            }
        }

        return result;
	}
}