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
import library.domain.Book;
import library.domain.Copy;

import java.util.ArrayList;
import java.util.List;

public class TestDataBookDAO implements BookDAOInf
{
    private List<Book> books;
    private List<Copy> copies;

    public TestDataBookDAO(List<Copy> copies)
    {
        books = new ArrayList<Book>();
        this.copies = copies;
    }

    /**
     * View the details of all the books.
     */
    public List<Book> findDetails()
    {
        if (books.size() == 0)
        {
            for (Book book : books)
            {
                for (Copy copy : copies)
                {
                    if (copy.getBook().getISBN() == book.getISBN())
                    {
                        book.addCopy(copy);
                        copies.remove(copy);
                    }
                }
            }
        }

        return books;
    }

    /**
     * View the details of the book.
     *
     * @param ISBN The ISBN of the book.
     */
    public Book findDetails(long ISBN)
    {
        Book result = null;

        for (Book book : books)
        {
            if (book.getISBN() == ISBN)
            {
                result = book;
            }
        }

        return result;
    }

    /**
     * Creates a new book.
     *
     * @param book The Book object.
     */
    public boolean create(Book book)
    {
        boolean result = false;

        if (book != null)
        {
            books.add(book);
            result = true;
        }

        return result;
    }

	/**
	 * Edit an existing book.
	 *
	 * @param book	The Book object.
	 */
	public boolean update(Book book)
	{
        boolean result = false;
        int index = 0;

        if (book != null)
        {
            index++;

            if (books.size() != 0)
            {
                for (Book item : books)
                {
                    if (book.getISBN() == item.getISBN())
                    {
                        books.set(index, book);
                        result = true;
                    }
                }
            }
        }

        return result;
	}

	/**
	 *  Deletes an existing book.
	 *
	 * @param ISBN	The ISBN of the book.
	 */
	public boolean delete(long ISBN)
	{
        boolean result = false;

        if (books.size() != 0)
        {
            for (Book item : books)
            {
                if (ISBN == item.getISBN())
                {
                    books.remove(item);
                    result = true;
                }
            }
        }

        return result;
	}
}