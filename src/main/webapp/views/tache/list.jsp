<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entities.Tache" %>
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
    <h1 class="mb-4">Tache List</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
    <div class="alert alert-danger"><%= errorMessage %></div>
    <%
        }
    %>

    <a href="tache?action=create" class="btn btn-success m-4">Create New tache</a>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>DateCreation</th>
            <th>DateLimite</th>
            <th>IsTermine</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Tache> taches = (List<Tache>) request.getAttribute("taches");
            if (taches == null || taches.isEmpty()) {
        %>
        <tr>
            <td colspan="4" class="text-center">No users found.</td>
        </tr>
        <%
        } else {
            for (Tache tache : taches) {
        %>
        <tr>
            <td><%= tache.getId() %></td>
            <td><%= tache.getTitre() %></td>
            <td><%= tache.getDateCreation() %></td>
            <td><%= tache.getDateLimite() %></td>
            <td><%= tache.isTerminee() %></td>
            <td>
                <a href="?action=details&id=<%= tache.getId() %>" class="btn btn-info btn-sm">Details</a>
                <a href="?action=update&id=<%= tache.getId() %>" class="btn btn-warning btn-sm">Update</a>
                <a href="?action=delete&id=<%= tache.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this tache?')">Delete</a>
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
