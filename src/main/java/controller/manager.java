package controller;

import model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;

import static controller.HibernateUtil.getSessionFactory;

/**
 * Created by Vasiliy on 16.12.2015.
 */
public class Manager {

    private static Manager instance;

    public static  Manager getManager()
    {
        if(instance==null){
            instance = new Manager();
        }
        return instance;
    }
    public void addAccount(Account account)   {
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
    public void updateAccount(int account, int state)    {

        Session session = null;
        try {
            Account account1 = getAccountById(account);
            account1.setState(state);
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.update(account1);
            session.getTransaction().commit();
            } catch (Exception e) {
            //todo доавбить что-нибудь
            } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    synchronized public Account getAccountById(Integer id)    {

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

    public List<Account> getAllAccounts()  {
        Session session = null;
        List<Account> accountList = new ArrayList<Account>();
        try {
            session = getSessionFactory().openSession();
            Query query = session.createQuery("from Account ");
            accountList = query.list();
            //accountList = session.createCriteria(Account.class).list();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountList;
    }

    public void deleteAccount(int account)  {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("delete Account where id = "+account);
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public boolean reduceAccount(int account, int quantity)
    {
        int state = instance.getAccountById(account).getState();
        if (state - quantity >= 0) {
            updateAccount(account, state - quantity );
            return true;
        } else return false;
    }

    synchronized public boolean makeTransaction(int accountFrom, int accountTo, int quantity)  {

        if (accountFrom != accountTo) {
            if (reduceAccount(accountFrom, quantity)) {
                updateAccount(accountTo, instance.getAccountById(accountTo).getState() + quantity);
                return true;
            }
            else return false;
        }
        else return false;
    }

}
