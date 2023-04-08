<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>Add tool</title>
</head>
<body>
<form method="post" id="tool" action="${pageContext.request.contextPath}/tools/add"></form>
<h1 class="table_light">Add tool:</h1>
<table border="1" class="table_light">
    <tr>
        <td><label for="name">Name:</label></td>
        <td><input type="text" id="name" name="name" form="tool" required></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" name="add" form="tool" value="Add">
            <button type="button" name="cancel" onclick="window.location.href='index.html'">Cancel</button>
        </td>
    </tr>
</table>
</body>
</html>
