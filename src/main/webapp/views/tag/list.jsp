<%@ page import="java.util.List" %>
<%@ page import="com.devsync.entities.Utilisateur" %>
<%@ page import="com.devsync.entities.Tag" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>Tag List</title>
  <!-- Include Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">Tag List</h1>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null && !errorMessage.isEmpty()) {
  %>
  <div class="alert alert-danger"><%= errorMessage %></div>
  <%
    }
  %>

  <a href="tag?action=create" class="btn btn-success m-4">Create New Tag</a>
  <table class="table table-bordered table-striped">
    <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Tag> tags = (List<Tag>) request.getAttribute("tags");
      if (tags == null || tags.isEmpty()) {
    %>
    <tr>
      <td colspan="4" class="text-center">No tags found.</td>
    </tr>
    <%
    } else {
      for (Tag tag : tags) {
    %>
    <tr>
      <td><%= tag.getId() %></td>
      <td><%= tag.getName() %></td>

      <td>
        <a href="?action=update&id=<%= tag.getId() %>" class="btn btn-warning btn-sm">Update</a>
        <a href="?action=delete&id=<%= tag.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this tag?')">Delete</a>
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
