package library.datastorage.DAO.Factory;

import library.datastorage.DAO.Inf.*;

public interface DAOFactory
{
    MemberDAOInf createMemberDAO();
    CopyDAOInf createCopyDAO();
    BookDAOInf createBookDAO();
    LoanDAOInf createLoanDAO();
    ReservationDAOInf createReservationDAO();
}
