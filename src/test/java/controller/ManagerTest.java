package controller;

import junit.framework.TestCase;
import model.Account;
import org.hibernate.Session;

import java.util.List;

import static controller.HibernateUtil.getSessionFactory;
import static controller.Manager.getManager;

/**
 * Created by Vasiliy on 17.12.2015.
 */
public class ManagerTest extends TestCase {

    public void testGetManager() throws Exception {
        assertTrue(getManager()!=null);

    }

    /*public void testGetAccount() throws Exception {
        assertTrue(Manager.getManager().getAccountById(5)!=null);

    }*/

    public void testGetAllAccounts() throws Exception {
        assertTrue(getManager().getAllAccounts()!=null);

    }

    public void testReduceAccount() throws Exception
    {
        List<Account> list = getManager().getAllAccounts();
        if (!list.isEmpty()) {
            Account account = list.get(0);
            int state = account.getState();
            int quantity = state + 1;
            assertFalse(getManager().updateAccount(account.getId(), quantity));
            assertTrue(getManager().updateAccount(account.getId(), quantity - 1));
        }
    }

    public void testMakeTransaction() throws Exception
    {
        List<Account> list = getManager().getAllAccounts();
        if (!list.isEmpty()) {
            Account account1 = list.get(0);
            Account account2 = list.get(1);

            account1.setState(15);
            account2.setState(20);
            getManager().updateAccount(account1.getId(),15);
            getManager().updateAccount(account2.getId(),20);

//            int state = account.getState();
            int quantity = 5;
            assertFalse(getManager().makeTransaction(account1.getId(),account2.getId(),quantity + 100));
            assertTrue(getManager().makeTransaction(account1.getId(),account2.getId(),quantity));
        }
    }

    public void testMultiThreadSafe() throws Exception
    {
        final int NACCOUNTS = 100;
        final int INITIAL_BALANCE = 1000;

        //начальные установки - удалить все записи кроме последенй, или все записи и добавить одну
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from Account").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            //todo доавбить что-нибудь
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        Account account1 = new Account();
        account1.setState(1);
        getManager().addAccount(account1);

        int lastId = getLastId();

        for(int i = 1; i <= NACCOUNTS; i++)
        {
            Account account = new Account();
            account.setState(INITIAL_BALANCE);
            getManager().addAccount(account);
        }

        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++ )
        {
            TransferRunnable runn = new TransferRunnable(NACCOUNTS,i, INITIAL_BALANCE, lastId + 1);
            Thread thread = new Thread(runn);
            thread.start();
        }

        Thread.sleep(10000);

        int resultSum = 0;
        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++)
        {
            Account account = getManager().getAccountById(i);
            resultSum = resultSum + account.getState();
        }
        assertEquals(NACCOUNTS*INITIAL_BALANCE,resultSum);

    }

    private class TransferRunnable implements Runnable {

        private int nAccounts;
        private int fromAccount;
        private int maxAmount;
        private int startIdFrom;
        private int DELAY = 10;

        public TransferRunnable(int nAccs, int from, int max, int startId)
        {
            nAccounts = nAccs;
            fromAccount = from;
            maxAmount = max;
            startIdFrom = startId;
        }

        public void run(){
            try
            {
                for (int i = 1; i <=  1; i++)
//                while(true)
                {
                    int toAccount = startIdFrom + (int) (nAccounts * Math.random());
                    int amount = (int) (maxAmount * Math.random());
                    Account accountFrom = getManager().getAccountById(fromAccount);
                    Account accountTo = getManager().getAccountById(toAccount);
                    System.out.println("Before - From: " + accountFrom.getId() + ", " + accountFrom.getState() + " To: " + accountTo.getId() + ", " + accountTo.getState() + " Amount^ " + amount);
                    getManager().makeTransaction(accountFrom.getId(), accountTo.getId(), amount);
                    accountFrom = getManager().getAccountById(fromAccount);
                    accountTo = getManager().getAccountById(toAccount);
                    System.out.println("After - From: " + accountFrom.getId() + ", " + accountFrom.getState() + " To: " + accountTo.getId() + ", " + accountTo.getState() + " Amount^ " + amount);
                    Thread.sleep((int)(DELAY * Math.random()));
                }
            }
            catch (Exception e)
            {}
        }
    }

    private int getLastId()
    {
        List<Account> list = getManager().getAllAccounts();
        return list.get(list.size()-1).getId();
    }

}