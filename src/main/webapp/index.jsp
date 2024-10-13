<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        h1 {
            color: brown;
        }
        .btn {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Welcome to the User Management System</h1>
<a href="utilisateur?action=list" class="btn">View User List</a>
<a href="tache?action=list" class="btn">View Tache List</a>
<a href="tag?action=list" class="btn">View Tag List</a>
</body>
</html>
