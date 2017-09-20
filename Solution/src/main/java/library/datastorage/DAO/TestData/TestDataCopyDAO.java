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

import library.datastorage.DAO.Inf.CopyDAOInf;
import library.domain.Copy;
import library.domain.Loan;

import java.util.ArrayList;
import java.util.List;

public class TestDataCopyDAO implements CopyDAOInf
{
    private List<Copy> copies;
    List<Loan> loans;

    public TestDataCopyDAO(List<Loan> loans)
    {
        copies = new ArrayList<Copy>();
        this.loans = loans;
    }

    /**
     * View the details of all the copies.
     */
    public List<Copy> findDetails()
    {
        if (copies.size() == 0)
        {
            for (Copy copy : copies)
            {
                if (loans.size() != 0)
                {
                    for (Loan loan : loans)
                    {
                        if (loan.getCopy().getCopyID() == copy.getCopyID())
                        {
                            copy.setLoan(loan);
                        }
                    }
                }
            }
        }

        return copies;
    }

    /**
     * View the details of the copy.
     *
     * @param copyID	The copy ID of the book.
     * @param ISBN		The ISBN of the copy.
     */
    public Copy findDetails(int copyID, long ISBN)
    {
        Copy result = null;

        for (Copy copy : copies)
        {
            if (copy.getCopyID() == copyID && copy.getBook().getISBN() == ISBN)
            {
                if (loans.size() != 0)
                {
                    for (Loan loan : loans)
                    {
                        if (loan.getCopy().getCopyID() == copyID)
                        {
                            copy.setLoan(loan);
                        }
                    }
                }

                result = copy;
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
            copies.add(copy);
            result = true;
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
        int index = 0;

        if (copy != null)
        {
            if (copies.size() != 0)
            {
                for (Copy item : copies)
                {
                    index++;

                    if (copy.getBook().getISBN() == item.getBook().getISBN() && copy.getCopyID() == item.getCopyID())
                    {
                        copies.set(index, copy);
                        result = true;
                    }
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

        if (copies.size() != 0)
        {
            for (Copy copy : copies)
            {
                if (ISBN == copy.getBook().getISBN() && copy.getCopyID() == copyID)
                {
                    copies.remove(copy);
                    result = true;
                }
            }
        }

        return result;
    }
}