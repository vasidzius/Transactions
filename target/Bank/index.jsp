<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Superuser
  Date: 17.12.2015
  Time: 10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Bank</title>
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
</head>
<body class="text-center">
<h1 class="text-center"><a href="index.jsp">Bank</a></h1>
<form action="/managerServlet" method="get">
    <table class="table">
        <tr>
            <td>
                <input type="submit" name="accounts" value="Accounts">
            </td>
            <td>
                <input type="submit" name="transaction" value="Make a transaction">
            </td>
        </tr>
    </table>
</form>


<%--обработка кнопки "Accounts"--%>
<c:if test="${not empty param.accounts}">
    <h2>Accounts</h2>
    <br>
    <form action="/managerServlet" method="get">
        <input type="submit" name="addAccount" value="Add account">
    </form>
    <br>

        <table class="table">
            <tr >
                <th class="text-center">#</th>
                <th class="text-center">account #</th>
                <th class="text-center">account state</th>
                <th class="text-center" colspan="4">actions</th>
            </tr>
            <c:set var="pageN" value="1"/>
            <c:forEach var="account" items="${data}">
            <tr>
                <form action="/managerServlet" method="get">
                    <td>${pageN}</td><c:set var="pageN" value="${pageN+1}"/>
                    <td>${account.id}</td><input type="hidden" name="id" value="${account.id}">
                    <td>${account.state}</td><input type="hidden" name="state" value="${account.state}">
                    <td>
                        <input type="submit" name="addMoney" value="recharge state">
                    </td>
                    <td>
                        <input type="submit" name="reduceMoney" value="withdraw">
                    </td>
                    <td>
                        <input type="submit" name="deleteAccount" value="delete account"  onclick="return confirm('Are you sure?')">
                    </td>
                    <td>
                        <input type="submit" name="information" value="Information">
                    </td>
                </form>
            </tr>
            </c:forEach>

        </table>

</c:if>

<%--Обработка кнопки "Выполнить транзакцию--%>
<c:if test="${not empty param.transaction}">
    <h2>Transaction</h2>
    <form action="/managerServlet" method="get">
        <table class="table">
            <tr>
                <td>from account # </td>
                <td>
                    <select name="selectAccountFrom">
                        <c:forEach var="account" items="${data}">
                            <option value="${account.id}">${account.id}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>to account # </td>
                <td>
                    <select name="selectAccountTo">
                        <c:forEach var="account" items="${data}">
                            <option value="${account.id}">${account.id}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Amount </td>
                <td>
                    <input type="number" name="sumTransaction" min="1" value="0">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="makeTransaction" value="commit">
                </td>
                <td>
                    <input type="submit" name="cancel" value="cancel">
                </td>
            </tr>
        </table>

        <c:if test="${not empty param.error}">
            <script type="text/javascript">
                alert("Not enough money or you try to make transaction on the same account");
            </script>
        </c:if>
    </form>
</c:if>

<%--Обработка кнопки "Добавить счет"--%>
<c:if test="${not empty param.addAccount}">
    <h2>adding account</h2>
    <form action="/managerServlet" method="get">
        <table class="table">
            <tr>
                <td>initial sum</td>
                <td>
                    <input type="number" name="sumInitial" min="0" value="0">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="createAccount" value="create account">
                </td>
                <td>
                    <input type="submit" name="cancel" value="cancel">
                </td>
                <c:if test="${not empty param.error}">
                    <script type="text/javascript">
                        alert("Initial sum is empty");
                    </script>
                </c:if>
            </tr>
        </table>
    </form>
</c:if>

<%--Обработка группы кнопок на странице Accounts, кнопки "recharge state", "withdraw", "delete account", "Information"--%>

<c:if test="${not empty param.information || not empty param.addMoney || not empty param.reduceMoney}">
    <h2>information</h2>
    <form action="/managerServlet" method="get">
        <table class="table">
            <tr>
                <td>account #</td>
                <td>${param.id}</td><input type="hidden" name="id" value="${param.id}">
            </tr>
            <tr>
                <td>current state</td>
                <td>${param.state}</td><input type="hidden" name="state" value="${param.state}">
            </tr>
         <c:if test="${not empty param.addMoney}">
            <tr>
                <td><b>recharge sum (enter the value)</b></td>
                <td>
                    <input type="number" name="rechargeSum" min="0" value="0">
                </td>
            </tr>
             <tr >
                <td><input type="submit" name="executeRecharge" value="recharge"></td>
                <td><input type="submit" name="cancel" value="cancel"></td>
            </tr>
             <c:if test="${not empty param.error}">
                 <script type="text/javascript">
                     alert("Recharge sum is empty")
                 </script>
             </c:if>

         </c:if>
         <c:if test="${not empty param.reduceMoney}">
                <tr>
                    <td><b>withdraw (enter the value)</b></td>
                    <td>
                        <input type="number" name="withdrawSum" min="0" value="0">
                    </td>
                </tr>
                <tr >
                    <td><input type="submit" name="executeWithdraw" value="execute withdraw"></td>
                    <td><input type="submit" name="cancel" value="cancel"></td>
                </tr>
             <%--вывод сообщения об ошибке ошибка--%>
             <c:if test="${not empty param.error}">
                 <script type="text/javascript">
                     alert("Not enough money or the field is empty");
                 </script>
             </c:if>
         </c:if>
        </table>
    </form>
</c:if>
</body>
</html>
