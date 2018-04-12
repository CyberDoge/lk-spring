<html>
<head>
    <title>login</title>
    <link href="css/register.css"
          rel="stylesheet">

</head>

<form action="/home"  modelAttribute="userForm" method="post" style="border:1px solid #ccc">
    <div class="container">
        <h1>Sign Ip</h1>
        <p>Enter to your account.</p>
        <hr>

        <label for="username"><b>login</b></label>
        <input type="text" placeholder="Enter login" name="username" id="username" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <label>
            <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
        </label>

        <p><a href="#" style="color:dodgerblue">Forgot password?</a>.</p>

        <div class="clearfix">
            <button type="button" class="cancelbtn">Cancel</button>
            <button type="submit" class="signupbtn">Sign In</button>
        </div>
    </div>
</form>

</body>
</html>