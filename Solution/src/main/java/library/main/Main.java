/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.main;

import library.businesslogic.MemberAdminManager;
import library.datastorage.DAO.Factory.DAOFactory;
import library.datastorage.DAO.Factory.MySqlDAOFactory;
import library.datastorage.DAO.Factory.TestDataDAOFactory;
import library.presentation.MemberAdminUI;

/**
 *
 * @author ppthgast
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DAOFactory mySqlDAOFactory = new MySqlDAOFactory();
        DAOFactory testDataDAOFactory = new TestDataDAOFactory();

        MemberAdminManager manager = new MemberAdminManager(mySqlDAOFactory);
        MemberAdminUI ui = new MemberAdminUI(manager);

        try
        {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex)
        {
            // handle the error
        }
    }
}
