/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.ArrayList;

/**
 *
 * @author ppthgast
 */
public class Book {
    
    private long ISBN;
    private String title;
    private String author;
    private String edition;
    
    private ArrayList<Copy> copies;
    private ArrayList<Reservation> reservations;
    
    public Book(long ISBN, String title, String author, String edition)
    {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.edition = edition;
        
        copies = new ArrayList();
        reservations = new ArrayList();
    }

    public void addCopy(Copy newCopy)
    {
        copies.add(newCopy);
    }

    public long getISBN()
    {
        return ISBN;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getEdition()
    {
        return edition;
    }

    public String getTitle()
    {
        return title;
    }
    
    public void removeReservation(Reservation reservation)
    {
        reservations.remove(reservation);
    }
    
    @Override
    public boolean equals(Object o)
    {
        boolean equal = false;
        
        if(o == this)
        {
            // Equal instances of this class.
            equal = true;
        }
        else
        {
            if(o instanceof Book)
            {
                Book b = (Book)o;
                
                // Book is identified by ISBN; checking on this attribute only will suffice.
                if(ISBN == b.ISBN)
                {
                    equal = true;
                }
            }
        }
        
        return equal;
    }
    
    @Override
    public int hashCode()
    {
        // This implementation is based on the best practice as described in Effective Java,
        // 2nd edition, Joshua Bloch.

        // ISBN is unique, so sufficient to be used as hashcode.
        int result = 17;
        result = 31 * result + String.valueOf(ISBN).hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + edition.hashCode();

        return result;
    }

    public String toString()
    {
        return title + ", " + author + ", editie " + edition + ", " + ISBN;
    }
}
