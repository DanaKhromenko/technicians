<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>All technicians</title>
</head>
<body>
<h1 class="table_light">All technicians:</h1>
<table border="1" class="table_light">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>E-mail</th>
        <th>Desired position</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="technician" items="${technicians}">
        <tr>
            <td>
                <c:out value="${technician.id}"/>
            </td>
            <td>
                <c:out value="${technician.name}"/>
            </td>
            <td>
                <c:out value="${technician.email}"/>
            </td>
            <td>
                <c:out value="${technician.desiredPosition}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/technicians/delete?id=${technician.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2">
            <button type="button" name="back" onclick="window.location.href='index.html'">Back</button>
        </td>
    </tr>
</table>
</body>
</html>
