    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
    <head>
        <title>View User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container mt-5">
    <h1 class="mb-4">User Details</h1>

    <div class="card">
        <div class="card-header">
            <h5>User Information</h5>
        </div>
        <div class="card-body">
            <p><strong>ID:</strong> ${utilisateur.id}</p>
            <p><strong>Username:</strong> ${utilisateur.username}</p>
            <p><strong>Email:</strong> ${utilisateur.email}</p>
            <a href="user?action=list" class="btn btn-secondary">Back to User List</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    </body>
    </html>
