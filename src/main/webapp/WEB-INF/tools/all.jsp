<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>All tools</title>
</head>
<body>
<h1 class="table_light">All tools:</h1>
<table border="1" class="table_light">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="tool" items="${tools}">
        <tr>
            <td>
                <c:out value="${tool.id}"/>
            </td>
            <td>
                <c:out value="${tool.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/tools/update?id=${tool.id}&name=${tool.name}">UPDATE</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/tools/delete?id=${tool.id}">DELETE</a>
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
