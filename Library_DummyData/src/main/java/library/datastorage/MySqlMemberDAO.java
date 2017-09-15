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

import library.domain.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMemberDAO implements MemberDAOInf
{

    private DatabaseConnection databaseConnection;

    public MySqlMemberDAO()
    {
        databaseConnection = new DatabaseConnection();
    }

    /**
     * View the details of all the members.
     */
    public List<Member> details()
    {
        List<Member> result = new ArrayList<Member>();

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `member` gfhddhdfdfdhfddf;");

            while (resultSet.next())
            {
                int memberID = resultSet.getInt("MemberID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String street = resultSet.getString("Street");
                String houseNumber = resultSet.getString("HouseNumber");
                String city = resultSet.getString("City");
                String phoneNumber = resultSet.getString("PhoneNumber");
                String emailAddress = resultSet.getString("EmailAddress");
                double fine = resultSet.getDouble("Fine");
                result.add(new Member(memberID, firstName, lastName, street, houseNumber, city, phoneNumber, emailAddress, fine));
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        finally
        {
            if (databaseConnection.connectionIsOpen())
            {
                databaseConnection.closeConnection();
            }
        }

        return result;
    }


    /**
	 * View the details of the member.
	 *
	 * @param memberID	The member ID of the reservation.
	 */
	public Member details(int memberID)
	{
        Member result = null;

        try
        {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM `member` WHERE `MemberID`=" + memberID + ";");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String street = resultSet.getString("Street");
            String houseNumber = resultSet.getString("HouseNumber");
            String city = resultSet.getString("City");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String emailAddress = resultSet.getString("EmailAddress");
            double fine = resultSet.getDouble("Fine");

            result = new Member(memberID, firstName, lastName, street, houseNumber, city, phoneNumber, emailAddress, fine);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        finally
        {
            if (databaseConnection.connectionIsOpen())
            {
                databaseConnection.closeConnection();
            }
        }

        return result;
	}

	/**
	 * Creates a new member.
	 * 
	 * @param member	The Member object.
	 */
	public boolean create(Member member)
	{
        boolean result = false;

        if (member != null)
        {
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("INSERT INTO `member` (FirstName, LastName, Street, HouseNumber, City, PhoneNumber, EmailAddress, Fine) VALUES ('" + member.getFirstName() + "', '" + member.getLastName() + "', '" + member.getStreet() + "', '" + member.getHouseNumber() + "', '" + member.getCity() + "', '" + member.getPhoneNumber() + "', '" + member.getemailAddress() + "', " + member.getFine() + ");");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                if (databaseConnection.connectionIsOpen())
                {
                    databaseConnection.closeConnection();
                }
            }
        }

		return false;
	}

	/**
	 * Edit an existing member.
	 * 
	 * @param member	The Member object.
	 */
	public boolean update(Member member)
	{
        boolean result = false;

        if (member != null)
        {
            try
            {
                databaseConnection.openConnection();
                result = databaseConnection.executeSQLDMLStatement("UPDATE `member` SET FirstName='" + member.getFirstName() + "', LastName='" + member.getLastName() + "', Street='" + member.getStreet() + "', HouseNumber='" + member.getHouseNumber() + "', City='" + member.getCity() + "' PhoneNumber='" + member.getPhoneNumber() + "', EmailAddress='" + member.getemailAddress() + "',Fine=" + member.getFine() + ";");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            finally
            {
                if (databaseConnection.connectionIsOpen())
                {
                    databaseConnection.closeConnection();
                }
            }
        }

        return result;
	}

	/**
	 * Deletes an existing member.
	 *
	 * @param memberID	The member ID of the reservation.
	 */
	public boolean delete(int memberID)
	{
        boolean result = false;

        try
        {
            databaseConnection.openConnection();
            result =  databaseConnection.executeSQLDMLStatement("DELETE FROM `member` WHERE ISBN=" + memberID + ";");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if (databaseConnection.connectionIsOpen())
            {
                databaseConnection.closeConnection();
            }
        }

        return result;
	}
}