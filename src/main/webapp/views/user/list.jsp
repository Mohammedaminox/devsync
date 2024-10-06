<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entities.Utilisateur" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">User List</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="alert alert-danger"><%= errorMessage %></div>
    <%
        }
    %>

    <a href="utilisateur?action=create" class="btn btn-success m-4">Create New User</a>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Username</th>
            <th>Email</th>
            <th>IsManager</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
            if (utilisateurs == null || utilisateurs.isEmpty()) {
        %>
        <tr>
            <td colspan="4" class="text-center">No users found.</td>
        </tr>
        <%
        } else {
            for (Utilisateur utilisateur : utilisateurs) {
        %>
        <tr>
            <td><%= utilisateur.getId() %></td>
            <td><%= utilisateur.getNom() %></td>
            <td><%= utilisateur.getPrenom() %></td>
            <td><%= utilisateur.getUsername() %></td>
            <td><%= utilisateur.getEmail() %></td>
            <td><%= utilisateur.getIsManager() %></td>
            <td>
                <a href="?action=details&id=<%= utilisateur.getId() %>" class="btn btn-info btn-sm">Details</a>
                <a href="?action=update&id=<%= utilisateur.getId() %>" class="btn btn-warning btn-sm">Update</a>
                <a href="?action=delete&id=<%= utilisateur.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>


</div>

<!-- Optional: Include Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
