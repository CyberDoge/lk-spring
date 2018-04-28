<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@page session="true" %>
<head>
    <title>profile</title>
    <style>
        h2 {color: darkblue;}
        a {color: #1b75bc;}
        a:hover {color: #d6562b;}
        body {position: center; text-align: center;}
    </style>
</head>
<html>
<body>
<h2>welcome to your profile, ${user.getUsername()}</h2>
<br/>
<h2>your current max score: ${user.getMaxScore()}</h2>
<form th:th:action="@{user/game}" method="post">
    <a href="/user/game">play game!!!</a>
</form>
<a href="/user/edit">edit</a>
<br />
<a href="/login?logout">Logout</a>

</form>
</body>


</body>
</html>
