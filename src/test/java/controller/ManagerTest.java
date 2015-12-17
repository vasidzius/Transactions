package controller;

import junit.framework.TestCase;
import model.Account;

import java.sql.SQLException;
import java.util.List;

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
            assertFalse(getManager().reduceAccount(account, quantity));
            assertTrue(getManager().reduceAccount(account, quantity - 1));
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
            getManager().updateAccount(account1);
            getManager().updateAccount(account2);

//            int state = account.getState();
            int quantity = 5;
            assertFalse(getManager().makeTransaction(account1,account2,quantity + 100));
            assertTrue(getManager().makeTransaction(account1,account2,quantity));
        }
    }

    public void testMultiThreadSafe() throws Exception
    {
        final int NACCOUNTS = 100;
        final int INITIAL_BALANCE = 1000;

        int lastId = getLastId();

        for(int i = 1; i <= NACCOUNTS; i++)
        {
            Account account = new Account();
            account.setState(INITIAL_BALANCE);
            getManager().addAccount(account);
        }

        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++ )
        {
            TransferRunnable runn = new TransferRunnable(NACCOUNTS,i, INITIAL_BALANCE);
            Thread thread = new Thread(runn);
            thread.start();
        }


        int resultSum=0;
        for (int i = lastId + 1; i < lastId + 1 + NACCOUNTS; i++)
        {
            Account account = getManager().getAccountById(i);
            resultSum = resultSum + account.getState();
        }

        assertTrue(resultSum==NACCOUNTS*INITIAL_BALANCE);
    }

    private class TransferRunnable implements Runnable {

        private int nAccounts;
        private int fromAccount;
        private int maxAmount;
        private int DELAY = 10;

        public TransferRunnable (int nAccs, int from, int max)
        {
            nAccounts = nAccs;
            fromAccount = from;
            maxAmount = max;
        }

        public void run(){
            try
            {
                while(true)
                {
                    int toAccount = (int) (nAccounts * Math.random());
                    int amount = (int) (maxAmount * Math.random());
                    Account accountFrom = getManager().getAccountById(fromAccount);
                    Account accountTo = getManager().getAccountById(toAccount);
                    getManager().makeTransaction(accountFrom, accountTo, amount);
                    Thread.sleep((int)(DELAY * Math.random()));
                }
            }
            catch (Exception e)
            {}
        }
    }

    private int getLastId()
    {
        List<Account> list=null;
        try {
            list = getManager().getAllAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.get(list.size()-1).getId();
    }
}