<%@ page import="com.devsync.entities.Tag" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Create Task</h1>

<form action="?action=create" method="post">
    <div class="mb-3">
        <label for="titre" class="form-label">Title</label>
        <input type="text" class="form-control" id="titre" name="titre" required>
    </div>

    <div class="mb-3">
        <label for="description" class="form-label">Description</label>
        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
    </div>

    <!-- Due Date (Limit to 3 days in advance) -->
    <div class="mb-3">
        <label for="date_limite" class="form-label">Due Date</label>
        <input type="date" class="form-control" id="date_limite" name="date_limite" min="<%= LocalDate.now() %>" max="<%= LocalDate.now().plusDays(3) %>" required>
        <small class="form-text text-muted">You can only schedule a task up to 3 days in advance.</small>
    </div>

    <!-- Multi-select dropdown for existing tags -->
    <!-- Tags (Multiple selection is required) -->
    <div class="mb-3">
        <label for="tags" class="form-label">Tags</label>
        <select class="form-select" id="tags" name="tags" multiple required>
            <%
                // Fetch all available tags
                List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                if (tags != null) {
                    for (Tag tag : tags) {
            %>
            <option value="<%= tag.getId() %>"><%= tag.getName() %></option>
            <%
                    }
                }
            %>
        </select>
        <small class="form-text text-muted">You must select at least two tags.</small>
    </div>

    <button type="submit" class="btn btn-primary">Create Task</button>
    <a href="?action=list" class="btn btn-secondary">Back to Task List</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

<script>
    // Simple form validation
    (function () {
        var forms = document.querySelectorAll('form');

        Array.prototype.forEach.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault(); // Prevent form submission if invalid
                }
            }, false);
        });
    })();
</script>
</body>
</html>
