<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entities.Tag" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tache Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Tache Details</h1>

<div class="card">
    <div class="card-header">
        <h5>Tache Information</h5>
    </div>
    <div class="card-body">
        <p><strong>ID:</strong> ${tache.id}</p>
        <p><strong>Titre:</strong> ${tache.titre}</p>
        <p><strong>Date de création:</strong> ${tache.dateCreation}</p>
        <p><strong>Description:</strong> ${tache.description}</p>
        <p><strong>Date limite:</strong> ${tache.dateLimite}</p>
        <p><strong>Terminée:</strong> ${tache.terminee ? "Yes" : "No"}</p>

        <!-- Display tags if available -->
        <p><strong>Tags:</strong>
            <%
                List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                if (tags != null && !tags.isEmpty()) {
                    for (Tag tag : tags) {
            %>
            <span class="badge bg-primary"><%= tag.getName() %></span>
            <%
                }
            } else {
            %>
            <em>No tags available</em>
            <%
                }
            %>
        </p>

        <a href="tache?action=list" class="btn btn-secondary">Back to Tache List</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
