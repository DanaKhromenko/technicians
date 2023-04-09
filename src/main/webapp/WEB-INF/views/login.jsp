<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/login/" id="login" method="post">
  E-mail:
  <input type="email" name="email" required><br>
  Password:
  <input type="password" name="password" required><br>

  <input type="submit" name="login" form="login" value="Login">
  <button type="button" name="cancel" onclick="window.location.href='index.html'">Cancel</button>
</form>
</body>
</html>
