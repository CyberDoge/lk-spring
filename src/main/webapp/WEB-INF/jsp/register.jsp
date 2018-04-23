<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
>
<head>
    <title>register</title>
    <link href="css/register.css"
          rel="stylesheet">

</head>
<h1>Sign Up</h1>
<p>Please fill in this form to create an account.</p>
<hr>


<form:form modelAttribute="userForm" method="post">

    <c:if test="${not empty errors}">
    <ul>
        <c:forEach var="errorElement" items="${errors.getAllErrors()}">
            <li>${errorElement.getCode()}</li>
        </c:forEach>
    </ul>
    </c:if>

        <label for="username"><b>login</b></label>
        <input type="text" placeholder="Enter login" name="username" id="username" required>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter email" name="email" id="email" required>


        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <label for="confirmPassword"><b>Confirm Password</b></label>
        <input type="password" placeholder="Confirm Password" name="confirmPassword" id="confirmPassword" required>

        <label>
            <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
        </label>

        <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

        <div class="clearfix">
            <button type="button" class="cancelbtn">Cancel</button>
            <button type="submit" class="signupbtn">Sign Up</button>
        </div>
    </div>
</form:form>

</body>
</html>