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

    public static void main(String...args) throws SQLException
    {
        Manager manager = new Manager();
        Account account = manager.getAccountById(1);
        int i = account.getState();
        System.out.println(i);

        account.setState(i+1);

        manager.updateAccount(account);

        account.setState(400+i);

        manager.addAccount(account);

        List<Account> list = manager.getAllAccounts();

        manager.deleteAccount(account);

        System.out.println(list);

        System.exit(1);


/*        Session session = null;
        Account account = null;

            session = getSessionFactory().openSession();
            account = (Account) session.load(Account.class, 1);


        System.out.println(account.getState());*/

/*        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from Account where id=1");
        System.out.println(query.getNamedParameters());*/



    }
    //todo надо ли чтобы методы пробрасывали SQLException?
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
    //todo надо ли чтобы методы пробрасывали SQLException?
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


}
