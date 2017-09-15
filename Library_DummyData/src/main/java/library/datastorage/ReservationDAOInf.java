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

import library.domain.Reservation;

import java.sql.Timestamp;

public interface ReservationDAOInf
{
	/**
	 * View the details of the reservation.
	 *
	 * @param memberID	        The member ID of the reservation.
	 * @param ReservationDate	The reservation date of the reservation.
	 * @param ISBN		        The ISBN of the reservation.
	 */
	Reservation details(Timestamp ReservationDate, int memberID, int ISBN);

	/**
	 * Creates a new reservation.
	 * 
	 * @param reservation	The Reservation object.
	 */
	boolean create(Reservation reservation);

	/**
	 * Edit an existing reservation.
	 * 
	 * @param reservation	The Reservation object.
	 */
	boolean update(Reservation reservation);

	/**
	 * Deletes an existing reservation.
	 *
	 * @param memberID	        The member ID of the reservation.
	 * @param ReservationDate	The reservation date of the reservation.
	 * @param ISBN		        The ISBN of the reservation.
	 */
	boolean delete(Timestamp ReservationDate, int memberID, int ISBN);
}