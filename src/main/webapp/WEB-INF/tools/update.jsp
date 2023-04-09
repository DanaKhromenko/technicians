<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>Update tool</title>
</head>
<body>
<form method="post" id="tool" action="${pageContext.request.contextPath}/tools/update">
    <input type="hidden" name="id" value="${id}">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="${name}" required>
    <button type="submit">Update</button>
    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tools'">Cancel</button>
</form>
</body>
</html>
