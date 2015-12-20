package servlet;

import model.Account;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controller.Manager.getManager;

public class ManagerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //обработка кнопка "Accounts" и кнопки "Make a transaction"
        if(request.getParameter("accounts")!=null
                || request.getParameter("transaction")!=null)
        {
            List dataList = getManager().getAllAccounts();
            request.setAttribute("data", dataList);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        //обработка кнопка "Add account"
        if(request.getParameter("addAccount")!=null)
        {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        //обработка кнопка "create account" на странице "adding account"
        if(request.getParameter("createAccount")!=null)
        {

            if("".equals(request.getParameter("sumInitial")))
            {
                response.sendRedirect(request.getHeader("referer")+"&error=true");
            }
            else
            {
                int state = Integer.parseInt(request.getParameter("sumInitial"));
                Account account = new Account(state);
                getManager().addAccount(account);
                //request.setAttribute("sumInitial",null);

                response.sendRedirect("/managerServlet?accounts=Accounts");
            }
        }

        //обработка кнопка "cancel" на странице "adding account", и при добавлении или снятии средств или транзакции
        if(request.getParameter("cancel")!=null)
        {
            //todo этот вариант работатет, но команды отображаются в url. Как скрыть?
            response.sendRedirect("/managerServlet?accounts=Accounts");
        }

        //Обработка группы кнопок на странице Accounts, кнопки "recharge state", "withdraw", "delete account", "Information"

        if(request.getParameter("information")!=null
                || request.getParameter("addMoney")!=null
                || request.getParameter("reduceMoney")!=null)
        {

            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        //обработка кнопки "recharge" при добавлении средств
        if(request.getParameter("executeRecharge")!=null)
        {
            if("".equals(request.getParameter("rechargeSum")))
            {
                response.sendRedirect(request.getHeader("referer") + "&error=true");;
            }
            else
            {
                int rechargeSum = Integer.parseInt(request.getParameter("rechargeSum"));
                int id = Integer.parseInt(request.getParameter("id"));
                //int state = Integer.parseInt(request.getParameter("state"));
                getManager().updateAccount(id, rechargeSum);
                response.sendRedirect("/managerServlet?accounts=Accounts");
            }
        }

        //обработка кнопки "withdraw" при снятии средств
        if(request.getParameter("executeWithdraw")!=null)
        {
            if("".equals(request.getParameter("withdrawSum")))
            {
                response.sendRedirect(request.getHeader("referer") + "&error=true");
            }
            else
            {
                int withdrawSum = Integer.parseInt(request.getParameter("withdrawSum"));
                int id = Integer.parseInt(request.getParameter("id"));
                if (getManager().updateAccount(id, -withdrawSum))
                    response.sendRedirect("/managerServlet?accounts=Accounts");
                else
                {//недостаточно средств на счету
                    response.sendRedirect(request.getHeader("referer")+"&error=true");
                }
            }
        }

        //обработка кнопки "delete account"
        if(request.getParameter("deleteAccount")!=null)
        {
            int id = Integer.parseInt(request.getParameter("id"));
            getManager().deleteAccount(id);
            response.sendRedirect(request.getHeader("referer"));
        }

        //обработка транзакции
        if(request.getParameter("makeTransaction")!=null)
        {
            if("".equals(request.getParameter("sumTransaction")))
            {
                response.sendRedirect(request.getHeader("referer") + "&error=true");
            }
            else
            {
                int accountFrom = Integer.parseInt(request.getParameter("selectAccountFrom"));
                int accountTo = Integer.parseInt(request.getParameter("selectAccountTo"));
                int sumTransaction = Integer.parseInt(request.getParameter("sumTransaction"));
                if (getManager().makeTransaction(accountFrom, accountTo, sumTransaction)) {
                    response.sendRedirect("/managerServlet?accounts=Accounts");
                } else {
                    response.sendRedirect(request.getHeader("referer") + "&error=true");

                }
            }
        }
    }
}
