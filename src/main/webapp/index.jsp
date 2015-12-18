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
    <%--todo здесь должен вызываться сервлет по отображению БД таблица счет--%>
    <h2>Accounts</h2>
    <br>
    <form method="get">
        <input type="submit" name="addAccount" value="Add account">
    </form>
    <br>
    <form method="get">
        <table class="table">
            <tr >
                <th class="text-center">account #</th>
                <th class="text-center">account state</th>
            </tr>
            <c:forEach var="account" items="${data}">
            <tr>
                <td>${account.id}</td>
                <td>${account.state}</td>
                <td>
                    <input type="submit" name="addMoney" value="recharge state">
                </td>
                <td>
                    <input type="submit" name="reduceMoney" value="withdraw">
                </td>
                <td>
                    <input type="submit" name="deleteAccount" value="delete account">
                </td>
                <td>
                    <input type="submit" name="information" value="Information">
                </td>
            </tr>
            </c:forEach>

        </table>
    </form>
</c:if>

<%--Обработка кнопки "Выполнить транзакцию--%>
<c:if test="${not empty param.transaction}">
    <h2>Transaction</h2>
    <form method="get">
        <table class="table">
            <tr>
                <td>from account # </td>
                <td>
                    <select name="selectAccountFrom">
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>to account # </td>
                <td>
                    <select name="selectAccountTo">
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Сумма </td>
                <td>
                    <input type="number" name="sumTransaction" min="1">
                </td>
            </tr>
        </table>
        <input type="submit" name="makeTransaction" value="commit">
    </form>
</c:if>

<%--Обработка кнопки "Добавить счет"--%>
<c:if test="${not empty param.addAccount}">
    <h2>Добавление счета</h2>
    <%--todo здесь отслыка на сервлет и вызов функции "добавление счета"--%>
    <form method="get">
        <table class="table">
            <tr>
                <td>Начальная сумма</td>
                <td>
                    <input type="number" name="sumInitial" min="0">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="createAccount" value="create account">
                </td>
                <td>
                    <input type="submit" name="cancelCreateAccount" value="cancel">
                </td>
            </tr>
        </table>
    </form>
</c:if>

</body>
</html>
