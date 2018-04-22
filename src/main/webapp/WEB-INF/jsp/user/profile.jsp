<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
welcome to your profile, ${user.getUsername()}
<br/>
your current max score: ${user.getMaxScore()}

<c:url value="/login?logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>
<form th:action="@{/game}" method="post">
    <a href="/user/game">play game!!!</a>
</form>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>






</body>
</html>
