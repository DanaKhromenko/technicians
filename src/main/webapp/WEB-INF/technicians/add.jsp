<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>Add technician</title>
</head>
<body>
<form method="post" id="technician" action="${pageContext.request.contextPath}/technicians/add"></form>
<h1 class="table_light">Add technician:</h1>
<table border="1" class="table_light">
    <tr>
        <td><label for="name">Name:</label></td>
        <td><input type="text" id="name" name="name" form="technician" required></td>
    </tr>
    <tr>
        <td><label for="email">Email:</label></td>
        <td><input type="email" id="email" name="email" form="technician" required></td>
    </tr>
    <tr>
        <td><label for="password">Password:</label></td>
        <td><input type="password" id="password" name="password" form="technician" required></td>
    </tr>
    <tr>
        <td><label for="desired_position">Desired Position:</label></td>
        <td><input type="text" id="desired_position" name="desired_position" form="technician" required></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" name="add" form="technician" value="Add">
            <button type="button" name="cancel" onclick="window.location.href='index.html'">Cancel</button>
        </td>
    </tr>
</table>
</body>
</html>
