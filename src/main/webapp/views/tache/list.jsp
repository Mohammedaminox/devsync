<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entities.Tache" %>
<%@ page import="com.devsync.entities.Utilisateur" %>
<%@ page import="com.devsync.services.UtilisateurService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Tache List</title>
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
    <%
        Utilisateur loggedInUser = (Utilisateur) session.getAttribute("utilisateur");

    %>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>DateCreation</th>
            <th>DateLimite</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Tache> taches = (List<Tache>) request.getAttribute("taches");
            if (taches == null || taches.isEmpty()) {
        %>
        <tr>
            <td colspan="4" class="text-center">No taches found.</td>
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
            <td>
                <a href="?action=details&id=<%= tache.getId() %>" class="btn btn-info btn-sm">Details</a>
                <a href="?action=update&id=<%= tache.getId() %>" class="btn btn-warning btn-sm">Update</a>
                <a href="?action=delete&id=<%= tache.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this tache?')">Delete</a>

                <!-- Add "Mark as Completed" button only if task is not already completed -->
                <% if (!tache.isTerminee()) { %>
                <a href="?action=markAsComplete&id=<%= tache.getId() %>" class="btn btn-success btn-sm" onclick="return confirm('Are you sure you want to mark this task as completed?')">Mark as Completed</a>
                <% } else { %>
                <span class="badge bg-success">Completed</span>
                <% } %>


                <% if (loggedInUser.isManager() && tache.getUtilisateur() == null) { %>
                <!-- Button to assign the task to a user -->
                <button class="btn btn-primary btn-sm" onclick="showAssignModal(<%= tache.getId() %>)">Assign User</button>
                <% } %>
            </td>

        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <!-- Task assignment modal (hidden by default) -->
    <div id="assignModal" style="display:none;">
        <form action="tache?action=assign" method="POST">
            <input type="hidden" id="tacheId" name="tacheId">
            <label for="assignedUserId">Assign to User:</label>
            <select name="assignedUserId" id="assignedUserId">
                <% List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
                    for (Utilisateur user : utilisateurs) { %>
                <option value="<%= user.getId() %>"><%= user.getNom() %></option>
                <% } %>
            </select>


            <button type="submit" class="btn btn-success">Assign</button>
            <button type="button" class="btn btn-secondary" onclick="closeAssignModal()">Cancel</button>
        </form>
    </div>

    <script>
        function showAssignModal(tacheId) {
            document.getElementById("tacheId").value = tacheId;
            document.getElementById("assignModal").style.display = "block";
        }

        function closeAssignModal() {
            document.getElementById("assignModal").style.display = "none";
        }
    </script>



</div>

<!-- Optional: Include Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
