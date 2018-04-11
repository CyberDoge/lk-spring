<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<form action="registerProcess"  modelAttribute="userForm" method="post">
    <input type="text" name="username" value="Enter login" onclick="this.value=''"><br/>
    <input type="password" name="password"><br/>
    <input type="password" name="confirmPassword"><br/>
    <input type="submit" value="register">
</form>
</body>
</html>