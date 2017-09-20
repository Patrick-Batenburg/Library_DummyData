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
import library.domain.Copy;
import library.domain.Loan;
import library.domain.Member;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestDataLoanDAO implements LoanDAOInf
{
    private List<Loan> loans;
    private List<Copy> copies;
    private List<Member> members;

    public TestDataLoanDAO(List<Copy> copies, List<Member> members)
    {
        loans = new ArrayList<Loan>();
        this.copies = copies;
        this.members = members;
    }

    /**
     * View the details of all the loans.
     */
    public List<Loan> findDetails()
    {
        return loans;
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
        Loan result = null;

        for (Loan loan : loans)
        {
            if (loan.getMember().getMemberID() == memberID && loan.getCopy().getCopyID() == copyID)
            {
                result = loan;
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
            loans.add(loan);
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
        int index = 0;

        if (loan != null)
        {
            if (loans.size() != 0)
            {
                for (Loan item : loans)
                {
                    index++;

                    if (loan.getCopy().getCopyID() == item.getCopy().getCopyID() && loan.getMember().getMemberID() == item.getMember().getMemberID())
                    {
                        loans.set(index, loan);
                        result = true;
                    }
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

        if (loans.size() != 0)
        {
            for (Loan loan : loans)
            {
                if (loan.getMember().getMemberID() == memberID && loan.getCopy().getCopyID() == copyID)
                {
                    copies.remove(loan);
                    result = true;
                }
            }
        }

        return result;
	}
}