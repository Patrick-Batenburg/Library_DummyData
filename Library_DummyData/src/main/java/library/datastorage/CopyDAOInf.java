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

import library.domain.Copy;

import java.util.List;

public interface CopyDAOInf
{
	/**
	 * View the details of all the copies.
	 */
	List<Copy> details();

	/**
	 * View the details of the copy.
	 * 
	 * @param copyID	The copy ID of the book.
	 * @param ISBN		The ISBN of the copy.	
	 */
	Copy details(int copyID, long ISBN);

	/**
	 * Creates a new copy.
	 * 
	 * @param copy	The Copy object.
	 */
	boolean create(Copy copy);

	/**
	 * Edit an existing copy.
	 * 
	 * @param copy	The Copy object.
	 */
	boolean update(Copy copy);

	/**
	 *  Deletes an existing copy.
	 * 
	 * @param copyID	The copy ID of the book.
	 * @param ISBN		The ISBN of the copy.	
	 */
	boolean delete(int copyID, long ISBN);
}