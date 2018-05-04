<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit profile</title>
    <style>
        .container {
            position: center;
            margin-left: 30%;
        }
        body {font-family: Arial, Helvetica, sans-serif;}
        * {box-sizing: border-box}
        a {color: #1b75bc;}
        a:hover {color: #d6562b;}
        body {position: center;}
        input[type=text], input[type=password] {
            padding: 15px;
            margin: 5px 0 22px 0;
            display: inline-block;
            border: none;
            background: #f1f1f1;
        }
        input[type=text]:focus, input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }
    </style>
</head>
<body>
<div class="container">
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
        <label for="old_password">old password (necessarily):</label>
        <input type="password" id="old_password" name="old_password" placeholder="old password"/>
        <br/>
        <label for="new_password">new password (not necessary):</label>
        <input type="password" id="new_password" name="new_password" placeholder="new password"/>
        <br/>
        <label for="confirm_password">confirm password (not necessary):</label>
        <input type="password" id="confirm_password" name="confirm_password" placeholder="confirm password"/>
        <br/>
        <button type="submit">Edit</button>
    </form:form>
    <a href="/user/home">home</a>
</div>
</body>
</html>
