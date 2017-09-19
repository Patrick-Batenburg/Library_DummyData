/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.businesslogic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.datastorage.DAO.Factory.DAOFactory;
import library.datastorage.DAO.Inf.BookDAOInf;
import library.datastorage.DAO.Inf.MemberDAOInf;
import library.datastorage.DAO.MySql.MySqlBookDAO;
import library.datastorage.DAO.MySql.MySqlMemberDAO;
import library.domain.*;

/**
 *
 * @author ppthgast
 */
public class MemberAdminManager
{
    private List<Member> members;
    private List<Book> books;
    private MemberDAOInf memberDAO;
    private BookDAOInf bookDAO;
    private DAOFactory daoFactory;

    public MemberAdminManager(DAOFactory daoFactory)
    {
        members = new ArrayList();
        books = new ArrayList();
        memberDAO = new MySqlMemberDAO();
        bookDAO = new MySqlBookDAO();
        this.daoFactory = daoFactory;
        fillData();
    }

    private void fillData()
    {
        members = memberDAO.findDetails();
    }

    private void fillTestData()
    {
        Book aBook = new Book(9780345803481L, "Fifty Shades of Grey", "E L James", "1");
        aBook.addCopy(new Copy(10001, 20, aBook));
        aBook.addCopy(new Copy(10002, 20, aBook));
        books.add(aBook);

        aBook = new Book(9780330351690L, "Into the Wild", "J Krakauer", "3");
        Copy copyIntoTheWild1 = new Copy(10003, 20, aBook);
        aBook.addCopy(copyIntoTheWild1);
        aBook.addCopy(new Copy(10004, 10, aBook));
        books.add(aBook);

        Book bookMulisch = new Book(9789023428220L, "De ontdekking van de Hemel", "H Mulisch", "24");
        Copy copyMulisch = new Copy(10005, 30, bookMulisch);
        aBook.addCopy(copyMulisch);
        books.add(bookMulisch);

        Member aNewMember = new Member(1000, "Pascal", "van Gastel", "", "", "", "", "", 0.0d);

        // Initialize a calendar object with today as the current day.
        Calendar c = Calendar.getInstance();
        Date dateToday = new Date();
        c.setTime(dateToday);
        c.add(Calendar.DATE, 1);

        aNewMember.addLoan(new Loan(aNewMember, copyMulisch, new Timestamp(c.getTimeInMillis())));
        members.add(aNewMember);

        aNewMember = new Member(1001, "Erco", "Argante", "", "", "", "", "", 0.0d);
        members.add(aNewMember);
        c.setTime(dateToday);
        c.add(Calendar.DATE, 8);
        aNewMember.addLoan(new Loan(aNewMember, copyIntoTheWild1, new Timestamp(c.getTimeInMillis())));
        Reservation aReservation = new Reservation(aNewMember, bookMulisch, new Timestamp(c.getTimeInMillis()));
        c.setTime(dateToday);
        aReservation.setReservationDate(new Timestamp(c.getTimeInMillis()));
        aNewMember.addReservation(aReservation);

        aNewMember = new Member(1002, "Jan", "Montizaan", "", "", "", "", "", 0.0d);
        members.add(aNewMember);
        aReservation = new Reservation(aNewMember, bookMulisch, new Timestamp(c.getTimeInMillis()));
        c.setTime(dateToday);
        aReservation.setReservationDate(new Timestamp(c.getTimeInMillis()));
        aNewMember.addReservation(aReservation);

        aNewMember = new Member(1003, "Frans", "Sprijkerman", "", "", "", "", "", 0.0d);
        aNewMember.setFine(5.25);
        members.add(aNewMember);

        members.add(new Member(1004, "Maurice", "van Haperen", "", "", "", "", "", 0.0d));
    }

    public Member findMemberDetails(int memberID)
    {
        Member member = null;
        int index = 0;

        while (member == null && index < members.size())
        {
            Member currentMember = members.get(index);

            if (currentMember.getMemberID() == memberID)
            {
                // Found the member!
                member = currentMember;
            }
            else
            {
                // Not the correct member, try the next one in the list.
                index++;
            }
        }

        return member;
    }

    public boolean deleteMember(Member member)
    {
        boolean result = false;

        if(member.isRemovable())
        {
            result = member.remove();
        }
        else
        {
            result = false;
        }

        return result;
    }
}
