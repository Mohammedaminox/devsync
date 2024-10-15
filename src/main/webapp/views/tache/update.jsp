<%@ page import="com.devsync.entities.Tag" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Update Task</h1>

<form action="?action=update" method="post">
    <!-- Task ID -->
    <input type="hidden" name="id" value="${tache.id}">

    <!-- Task Title -->
    <div class="mb-3">
        <label for="titre" class="form-label">Title</label>
        <input type="text" class="form-control" id="titre" name="titre" value="${tache.titre}" required>
    </div>

    <!-- Task Description -->
    <div class="mb-3">
        <label for="description" class="form-label">Description</label>
        <textarea class="form-control" id="description" name="description" rows="3" required>${tache.description}</textarea>
    </div>

    <!-- Due Date -->
    <div class="mb-3">
        <label for="date_limite" class="form-label">Due Date</label>
        <input type="date" class="form-control" id="date_limite" name="date_limite" value="${tache.dateLimite}" required>
    </div>

    <!-- Tags Selection -->
    <div class="mb-3">
        <label for="tags" class="form-label">Tags</label>
        <select class="form-select" id="tags" name="tags" multiple required>
            <%
                List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                com.devsync.entities.Tache tache = (com.devsync.entities.Tache) request.getAttribute("tache");

                if (tags != null && tache != null) {
                    for (Tag tag : tags) {
                        boolean isSelected = tache.getTags().stream()
                                .anyMatch(tacheTag -> tacheTag.getId().equals(tag.getId()));
            %>
            <option value="<%= tag.getId() %>" <%= isSelected ? "selected" : "" %>><%= tag.getName() %></option>
            <%
                    }
                }
            %>
        </select>
        <small class="form-text text-muted">Hold down Ctrl (Windows) or Command (Mac) to select multiple options.</small>
    </div>

    <!-- Action Buttons -->
    <button type="submit" class="btn btn-primary">Update Task</button>
    <a href="?action=list" class="btn btn-secondary">Back to Task List</a>
</form>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

<!-- Simple Form Validation -->
<script>
    (function () {
        const forms = document.querySelectorAll('form');

        Array.prototype.forEach.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                }
            }, false);
        });
    })();
</script>
</body>
</html>
