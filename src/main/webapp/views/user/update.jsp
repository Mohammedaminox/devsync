<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1>Update User</h1>

<form action="user?action=update" method="post">
    <!-- Hidden input to store the user's ID -->
    <input type="hidden" name="id" value="${user.id}">

    <!-- Input for the username -->
    <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
    </div>

    <!-- Input for the email -->
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
    </div>

    <!-- Input for the password (optional) -->
    <div class="mb-3">
        <label for="password" class="form-label">New Password (leave blank to keep current)</label>
        <input type="password" class="form-control" id="password" name="password">
    </div>

    <!-- Submit button to update the user -->
    <button type="submit" class="btn btn-primary">Update User</button>
    <!-- Button to go back to the user list -->
    <a href="user?action=list" class="btn btn-secondary">Back to User List</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
