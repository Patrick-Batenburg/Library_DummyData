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

public class Member {
    
    private int memberID;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String city;
    private String phoneNumber;
    private String emailAddress;
    private double fine;
    
    private ArrayList<Loan> loans;
    private ArrayList<Reservation> reservations;
            
    public Member(int memberID, String firstName, String lastName, String street, String houseNumber, String city, String phoneNumber, String emailAddress, double fine)
    {
        this.memberID = memberID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.fine = fine;
        
        loans = new ArrayList();
        reservations = new ArrayList();
    }
    
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getemailAddress()
    {
        return emailAddress;
    }

    public void setemailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getHouseNumber()
    {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    public int getMemberID()
    {
        return memberID;
    }

    public void setMemberID(int memberID)
    {
        this.memberID = memberID;
    }

    public String getCity() 
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public double getFine()
    {
        return fine;
    }
    
    public void setFine(double fine)
    {
        this.fine = fine;
    }
    
    public void setLoans(Loan[] loans)
    {
        removeAllLoans();
        
        for(Loan theLoan: loans)
        {
            addLoan(theLoan);
        }
    }
    
    public void addLoan(Loan newLoan)
    {
        loans.add(newLoan);
    }
    
    public void removeAllLoans()
    {
        loans.clear();
    }
    
    public void addReservation(Reservation newReservation)
    {
        reservations.add(newReservation);
    }
    
    public boolean remove()
    {
        // Result is always true. If we later on use a database from which
        // the member needs to be removed as well, we can return a more
        // meaningfull value.
        boolean result = true;
        
        removeAllReservations();
        
        return result;
    }
    
    private void removeAllReservations()
    {
        int index = reservations.size() - 1;

        while(index >= 0)
        {
            Reservation reservation = reservations.get(index);
            reservation.remove();

            index--;
        }
        
        // Alternatives for this construction with the temporary copy of the
        // ArrayList field are for example:
        //
        // -- 1 --
        // Start removing the reservations starting from the END OF the list
        // moving backwards until the list is empty. Starting from the first
        // element towards the end of the list will not succeed since the
        // index values are messed up.
        //    int index = reservations.size() - 1;
        //    
        //    while(index >= 0)
        //    {
        //        reservations.r
        //        Reservation reservation = reservations.get(index);
        //        reservation.remove();
        //        
        //        index--;
        //    }
        //
        // -- 2 --
        //
        //    ArrayList<Reservation> tempList = new ArrayList<>(reservations);
        //      
        //    for(Reservation r: reservations)
        //    {
        //        r.remove();
        //    }
        //
        // This defenitely needs clarification. Using a for each or iterator
        // on a list, has the limitation that deletion of elements from that
        // list is not allowed. Therefore a new ArrayList object is created,
        // referring to the same Reservation objects as the reservations field.
        // The local variable tempList is not modified during execution of the
        // loop. The reservations field on the other hand is.
    }
    
    public boolean hasLoans()
    {
        return !loans.isEmpty();
    }
    
    public boolean hasFine()
    {
        return fine > 0;
    }
    
    public boolean isRemovable()
    {
        return !hasLoans() && !hasFine();
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
            if(o instanceof Member)
            {
                Member l = (Member)o;

                // Member is identified by memberID; checking on this attribute only will suffice.
                equal = this.memberID == l.memberID;
            }
        }
        
        return equal;
    }
    
    @Override
    public int hashCode()
    {
        // This implementation is based on the best practice as described in Effective Java,
        // 2nd edition, Joshua Bloch.
        
        // memberID is unique, so sufficient to be used as hashcode.
        return memberID;
    }

    public String toString()
    {
        String result = "========== lid info ==========\n" +
                memberID + " - " + firstName + " " + lastName + "\n" +
                "Boete: " + "\u20ac" + fine + "\n\n";

        result += "========== leningen ==========\n";
        if(loans.isEmpty())
        {
            result += "Geen leningen";
        }
        else
        {
            for (Loan loan : loans)
            {
                result += loan + "\n\n";
            }
        }

        result += "\n\n";
        result += "========== reserveringen ==========\n";

        if(reservations.isEmpty())
        {
            result += "Geen reserveringen";
        }
        else
        {
            for (Reservation reservation : reservations)
            {
                result += reservation + "\n\n";
            }
        }

        return result;
    }
}
