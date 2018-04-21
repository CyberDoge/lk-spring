<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>login</title>
    <link href="css/register.css"
          rel="stylesheet">

</head>

<form th:action="@{/login}" method="post">
    <div class="container">
        <h1>Sign In</h1>
        <p>Enter to your account.</p>
        <hr>

        <label for="username"><b>username</b></label>
        <input type="text" placeholder="Enter login" name="j_username" id="username" required>

        <label for="password"><b>password</b></label>
        <input type="password" placeholder="Enter Password" name="j_password" id="password" required>

        <label>
            <input type="checkbox" checked="checked" name="remember-me”" style="margin-bottom:15px"> Remember me
        </label>

        <p><a href="#" style="color:dodgerblue">Forgot password?</a>.</p>

        <div class="clearfix">
            <form action="localhost:8080/home">
                <button type="button" class="cancelbtn">Cancel</button>
            </form>
            <button type="submit" class="signupbtn">Sign In</button>
        </div>
    </div>
</form>

</body>
</html>