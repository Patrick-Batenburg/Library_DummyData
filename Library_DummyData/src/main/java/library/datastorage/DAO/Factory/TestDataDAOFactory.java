package library.datastorage.DAO.Factory;

import library.datastorage.DAO.Inf.*;

public class TestDataDAOFactory implements DAOFactory
{
    public TestDataDAOFactory()
    {
    }

    @Override
    public MemberDAOInf createMemberDAO()
    {
        return null;
    }

    @Override
    public CopyDAOInf createCopyDAO()
    {
        return null;
    }

    @Override
    public BookDAOInf createBookDAO()
    {
        return null;
    }

    @Override
    public LoanDAOInf createLoanDAO()
    {
        return null;
    }

    @Override
    public ReservationDAOInf createReservationDAO()
    {
        return null;
    }
}
