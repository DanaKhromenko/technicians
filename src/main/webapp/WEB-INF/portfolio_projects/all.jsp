<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>All portfolio projects</title>
</head>
<body>
<h1 class="table_light">All portfolio projects:</h1>
<table border="1" class="table_light">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Short Project Description</th>
        <th>Detailed Project Description</th>
        <th>Source Code Url</th>
        <th>Interactive Result Url</th>
        <th>Picture Url</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="portfolio_project" items="${portfolio_projects}">
        <tr>
            <td>
                <c:out value="${portfolio_project.id}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.name}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.shortProjectDescription}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.detailedProjectDescription}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.sourceCodeUrl}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.interactiveResultUrl}"/>
            </td>
            <td>
                <c:out value="${portfolio_project.pictureUrl}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/portfolio_projects/delete?id=${portfolio_project.id}">DELETE</a>
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
