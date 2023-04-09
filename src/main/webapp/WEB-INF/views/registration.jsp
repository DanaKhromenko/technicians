<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registration</title>
  <script>
    function showFields() {
      var type = document.getElementById("type").value;
      var employerFields = document.getElementById("employer_fields");
      var technicianFields = document.getElementById("technician_fields");

      if (type == "employer") {
        employerFields.style.display = "block";
        technicianFields.style.display = "none";
      } else if (type == "technician") {
        employerFields.style.display = "none";
        technicianFields.style.display = "block";
      } else {
        employerFields.style.display = "none";
        technicianFields.style.display = "none";
      }
    }
  </script>
</head>
<body onload="showFields()">
<h1>New User</h1>
<form action="${pageContext.request.contextPath}/registration/" id="registration" method="post">
  Full Name:
  <input type="text" name="name" required><br>

  E-mail:
  <input type="email" name="email" required><br>

  Password:
  <input type="password" name="password" required><br>

  About You:
  <textarea name="description" rows="4" cols="50"></textarea><br>

  Country:
  <input type="text" name="country"><br>

  City:
  <input type="text" name="city"><br>

  Phone:
  <input type="text" name="phone" required><br>

  You are:
  <select name="type" id="type" onchange="showFields()">
    <option value="employer">I am hiring people</option>
    <option value="technician">I am a job seeker</option>
  </select><br>

  <div id="employer_fields" style="display:none;">
    Are you hiring?
    <input type="checkbox" name="is_hiring" checked><br>

    Company Name:
    <input type="text" name="company_name"><br>

    Current Position:
    <input type="text" name="current_position"><br>
  </div>

  <div id="technician_fields" style="display:none;">
    Are you open to work?
    <input type="checkbox" name="open_to_work" checked><br>

    Desired Position:
    <input type="text" name="desired_position"><br>

    Desired Annual Salary:
    <input type="number" name="desired_annual_salary"><br>

    Annual Salary Currency:
    <select name="annual_salary_currency">
      <option value="USD">USD</option>
      <option value="EUR">EUR</option>
      <option value="UAH">UAH</option>
    </select><br>
  </div>

  <input type="submit" name="submit" form="registration" value="Create Account">
  <button type="button" name="cancel" onclick="window.location.href='index.html'">Cancel</button>
</form>
</body>
</html>
