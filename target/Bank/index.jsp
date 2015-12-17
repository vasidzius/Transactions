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
    <title>Банк</title>
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
</head>
<body class="text-center">
<h1 class="text-center"><a href="index.jsp">Банк</a></h1>
<form method="get">
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


<%--обработка кнопки "Счета"--%>
<c:if test="${not empty param.accounts}">
    <%--todo здесь должен вызываться сервлет по отображению БД таблица счет--%>
    <h2>Счета</h2>
    <br>
    <form method="get">
        <input type="submit" name="addAccount" value="Добавить счет">
    </form>
    <br>
    <form method="get">
        <table class="table">
            <tr >
                <th class="text-center">Номер счета</th>
                <th class="text-center">Состояние счета</th>
            </tr>
            <tr>
                <td>1</td>
                <td>200</td>
                <td>
                    <input type="submit" name="addMoney" value="Пополнение">
                </td>
                <td>
                    <input type="submit" name="reduceMoney" value="Снятие">
                </td>
                <td>
                    <input type="submit" name="deleteAccount" value="Удалить счет">
                </td>
                <td>
                    <input type="submit" name="information" value="Подробная информация">
                </td>
            </tr>

        </table>
    </form>
</c:if>

<%--Обработка кнопки "Выполнить транзакцию--%>
<c:if test="${not empty param.transaction}">
    <h2>Транзакция</h2>
    <form method="get">
        <table class="table">
            <tr>
                <td>Снять со счета № </td>
                <td>
                    <select name="selectAccountFrom">
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Зачислить на счет № </td>
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
            <%--todo почему кнопка на верх вылазит?--%>
        </table>
        <input type="submit" name="makeTransaction" value="Выполнить">
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
                    <input type="submit" name="createAccount" value="Создать счет">
                </td>
                <td>
                    <input type="submit" name="cancelCreateAccount" value="Отмена">
                </td>
            </tr>
        </table>
    </form>
</c:if>





<%--<c:if test="${not empty param.accounts}"> Hello there</c:if>--%>
<%--<c:if test="${request.getParameter(\"lang\")=='En'}"> selected="selected" </c:if>--%>
</body>
</html>
