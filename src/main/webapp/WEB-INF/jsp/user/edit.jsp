<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>
<form:form method="post" modelAttribute="user" action="${save}">
    username: <input type="text" placeholder="${name}" name="username" id="username" >
    <br/>
    old password: <input type="password" name="password" id="password" >
    <br/>
    new password: <input type="password" name="confirmPassword" id="confirmPassword" >
    <br/>
    <button type="submit">Edit</button></form:form>
<form:form/>
</body>
</html>
