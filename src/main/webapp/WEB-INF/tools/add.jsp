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
        <th>Name</th>
        <th>Add</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="name" form="tool" required>
        </td>
        <td>
            <input type="submit" name="add" form="tool">
        </td>
    </tr>
</table>
</body>
</html>
