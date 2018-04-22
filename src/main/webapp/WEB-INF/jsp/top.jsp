<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: pekar
  Date: 22.04.18
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Top results</title>
</head>
<body>
<h2>Top user:</h2>
<br/>
<table>
    <tr>
        <td>username</td>
        <td>max score</td>
    </tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td>
                <c:out value="${item.getUsername()}" />
            </td>
            <td>
                <c:out value="${item.getMaxScore()}" />
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
