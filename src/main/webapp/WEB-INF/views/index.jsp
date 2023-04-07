<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@include file='/WEB-INF/css/table_light.css' %>
</style>
<html>
<head>
    <title>My team</title>
</head>
<body>
<form method="post" id="redirect"></form>
<h1 class="table_light">Welcome to TECHNICIANS!</h1>
<table class="table_light">
    <tr>
        <th>Redirect to</th>
    </tr>
    <tr><td><a href="${pageContext.request.contextPath}/technicians/">Display All Technicians</a></td></tr>
    <tr><td><a href="${pageContext.request.contextPath}/technicians/add">Add New Technician</a></td></tr>
</table>
</body>
</html>
