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

package library.datastorage.DAO.Inf;

import library.domain.Loan;

import java.sql.Timestamp;
import java.util.List;

public interface LoanDAOInf
{
	/**
	 * View the details of all the loans.
	 */
     List<Loan> findDetails();

	/**
	 * View the details of the loan.
	 *
	 * @param loanDate	The loan date of the reservation.
	 * @param memberID	The member ID of the reservation.
	 * @param copyID	The ID of the copy.
	 */
	Loan findDetails(Timestamp loanDate, int memberID, int copyID);

	/**
	 * Creates a new loan.
	 * 
	 * @param loan	The Loan object.
	 */
	boolean create(Loan loan);

	/**
	 * Edit an existing loan.
	 * 
	 * @param loan	The Loan object.
	 */
	boolean update(Loan loan);

	/**
	 *  Deletes an existing loan.
	 *
	 * @param memberID	The member ID of the reservation.
	 * @param copyID	The ID of the copy.
	 */
	boolean delete(int memberID, int copyID);
}