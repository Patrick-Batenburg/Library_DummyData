package library.datastorage;

public interface DAOFactory
{
    MemberDAOInf createMemberDAO();
    CopyDAOInf createCopyDAO();
    BookDAOInf createBookDAO();
    LoanDAOInf createLoanDAO();
    ReservationDAOInf createReservationDAO();
}
