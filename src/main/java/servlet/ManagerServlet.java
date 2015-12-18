package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static controller.Manager.getManager;

/**
 * Created by Vasiliy on 27.10.2015.
 * Этот сервлет изменяет уже существующую запись в БД по id
 */
@WebServlet(name = "managerServlet")
public class ManagerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/*        PrintWriter pw = response.getWriter();
        pw.println("Hello MFC");*/

        if(request.getParameter("accounts")!=null) {
            List dataList = getManager().getAllAccounts();

            request.setAttribute("data", dataList);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

//        response.sendRedirect("Technologies.jsp");
        //request.getRequestDispatcher("Technologies_old.jsp").forward(request, response);



        /*PureSQLManagementClass mClass = null;
        try {
            mClass = (new PureSQLManagementClass()).getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //принимаем от changeForTech_old.jsp значения name desc id и изменяем содержимое БД по id
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");


        try {
            mClass.getInstance().setNameDescById(id, name, desc);
        }
        catch (SQLException e){throw new ServletException(e);} catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("Technologies.jsp");
        //request.getRequestDispatcher("Technologies_old.jsp").forward(request, response);*/

    }
}
