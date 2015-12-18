package controller;

import model.Account;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

import static controller.HibernateUtil.getSessionFactory;
import static controller.Manager.getManager;

/**
 * Created by Superuser on 18.12.2015.
 */
public class test {

      public static void main(String...args) throws Exception
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

/*//        int toAccount = (lastId + 1) + (int) (NACCOUNTS * Math.random());
        int toAccount = (lastId + 2);
        int amount = (int) (INITIAL_BALANCE * Math.random());
        Account accountFrom = getManager().getAccountById(lastId + 1);
        Account accountTo = getManager().getAccountById(toAccount);
        System.out.println("Before - From: " + accountFrom.getId() + ", " + accountFrom.getState() + " To: " + accountTo.getId() + ", " + accountTo.getState() + " Amount^ " + amount);
        getManager().makeTransaction(accountFrom, accountTo, amount);
        System.out.println("After - From: " + accountFrom.getId() + ", " + accountFrom.getState() + " To: " + accountTo.getId() + ", " + accountTo.getState() + " Amount^ " + amount);
        Thread.sleep((int)(10 * Math.random()));*/

        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++ )
        {
            TransferRunnable runn = new TransferRunnable(NACCOUNTS,i, INITIAL_BALANCE, lastId + 1);
            Thread thread = new Thread(runn);
            thread.start();
        }

        Thread.sleep(10000);
        System.exit(1);

/*        int resultSum = 0;
        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++)
        {
            Account account = getManager().getAccountById(i);
            resultSum = resultSum + account.getState();
        }
        System.out.println("\n Должно быть " + NACCOUNTS*INITIAL_BALANCE + " получилось " + resultSum);
*/


    }

    private static class TransferRunnable implements Runnable {

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
                for (int i = 1; i <=  2; i++)
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

    private static int getLastId()
    {
        List<Account> list = getManager().getAllAccounts();
        return list.get(list.size()-1).getId();
    }


}
