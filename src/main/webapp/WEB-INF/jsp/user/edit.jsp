<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>
<c:if test="${not empty errors}">
    <ul>
        <c:forEach var="errorElement" items="${errors}">
            <li>${errorElement}</li>
        </c:forEach>
    </ul>
</c:if>
<form:form method="post">
    <label for="username">username:</label>
    <input type="text" id="username" name="username" placeholder="username" value="${name}"/>
    <br/>
    <label for="old_password">old password:</label>
    <input type="password" id="old_password" name="old_password" placeholder="old password"/>
    <br/>
    <label for="new_password">new password:</label>
    <input type="password" id="new_password" name="new_password" placeholder="new password"/>
    <br/>
    <label for="confirm_password">confirm password:</label>
    <input type="password" id="confirm_password" name="confirm_password" placeholder="confirm password"/>
    <br/>
    <button type="submit">Edit</button></form:form>
<form:form/>
</body>
</html>
