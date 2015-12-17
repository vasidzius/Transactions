package controller;

import model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static controller.HibernateUtil.getSessionFactory;

/**
 * Created by Vasiliy on 16.12.2015.
 */
public class Manager {

    private static Manager manager;

    public static Manager getManager()
    {
        if(manager==null){
            manager = new Manager();
        }
        return manager;
    }

    public static void main(String...args) throws SQLException
    {
        Account account = getManager().getAccountById(5);
        account.setState(12345);
        getManager().updateAccount(account);

        System.exit(1);


    }

    public void addAccount(Account account) throws SQLException  {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    public void updateAccount(Account account) throws SQLException   {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.update(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Account getAccountById(Integer id) throws SQLException   {
        Session session = null;
        Account account = null;
        try {
            session = getSessionFactory().openSession();
            account = (Account) session.get(Account.class, id);
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return account;
    }

    public List<Account> getAllAccounts() throws SQLException {
        Session session = null;
        List<Account> accountList = new ArrayList<Account>();
        try {
            session = getSessionFactory().openSession();
            accountList = session.createCriteria(Account.class).list();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountList;
    }

    public void deleteAccount(Account account) throws SQLException {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(account);
            session.getTransaction().commit();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean reduceAccount(Account account, int quantity) throws SQLException
    {
        int state = account.getState();
        if(state - quantity >= 0)
        {
            account.setState(state - quantity);
            updateAccount(account);
            return true;
        }
        else return false;
    }

    public boolean makeTransaction(Account accountFrom, Account accountTo, int quantity) throws SQLException
    {
        if(reduceAccount(accountFrom,quantity))
        {
            accountTo.setState(accountTo.getState()+quantity);
            updateAccount(accountTo);
            return true;
        }
        else return false;
    }


}
