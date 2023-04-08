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
<form method="post" id="portfolio_project" action="${pageContext.request.contextPath}/portfolio_projects/add"></form>
<h1 class="table_light">Add portfolio project:</h1>
<table border="1" class="table_light">
    <tr>
        <td><label for="name">Name:</label></td>
        <td><input type="text" id="name" name="name" form="portfolio_project" required></td>
    </tr>
    <tr>
        <td><label for="short_project_description">Short Description:</label></td>
        <td><input type="text" id="short_project_description" name="short_project_description" form="portfolio_project" required></td>
    </tr>
    <tr>
        <td><label for="detailed_project_description">Detailed Description:</label></td>
        <td><input type="text" id="detailed_project_description" name="detailed_project_description" form="portfolio_project" required></td>
    </tr>
    <tr>
        <td><label for="source_code_url">Source Code URL:</label></td>
        <td><input type="text" id="source_code_url" name="source_code_url" form="portfolio_project"></td>
    </tr>
    <tr>
        <td><label for="interactive_result_url">Interactive Result URL:</label></td>
        <td><input type="text" id="interactive_result_url" name="interactive_result_url" form="portfolio_project"></td>
    </tr>
    <tr>
        <td><label for="picture_url">Picture URL:</label></td> <%-- TODO: update to picture upload --%>
        <td><input type="text" id="picture_url" name="picture_url" form="portfolio_project"></td>
    </tr>
    <tr>
        <td><%-- TODO: place for multibox for tools--%></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" name="add" form="portfolio_project" value="Add">
            <button type="button" name="cancel" onclick="window.location.href='index.html'">Cancel</button>
        </td>
    </tr>
</table>
</body>
</html>
