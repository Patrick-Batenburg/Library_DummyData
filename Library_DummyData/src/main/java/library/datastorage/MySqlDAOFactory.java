package library.datastorage;

public class MySqlDAOFactory implements DAOFactory
{
    private BookDAOInf bookDAOInf;
    private MemberDAOInf memberDAOInf;
    private LoanDAOInf loanDAOInf;
    private ReservationDAOInf reservationDAOInf;
    private CopyDAOInf copyDAOInf;

    public MySqlDAOFactory()
    {
        bookDAOInf = new MySqlBookDAO();
        memberDAOInf = new MySqlMemberDAO();
        loanDAOInf = new MySqlLoanDAO();
        reservationDAOInf = new MySqlReservationDAO();
        copyDAOInf = new MySqlCopyDAO();
    }

    @Override
    public MemberDAOInf createMemberDAO()
    {
        return memberDAOInf;
    }

    @Override
    public CopyDAOInf createCopyDAO()
    {
        return copyDAOInf;
    }

    @Override
    public BookDAOInf createBookDAO()
    {
        return bookDAOInf;
    }

    @Override
    public LoanDAOInf createLoanDAO()
    {
        return loanDAOInf;
    }

    @Override
    public ReservationDAOInf createReservationDAO()
    {
        return reservationDAOInf;
    }
}
