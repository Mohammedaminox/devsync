<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Tag</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1>Update Tag</h1>

<form action="tag?action=update" method="post">

    <input type="hidden" name="id" value="${tag.id}">

    <!-- Input for the nom -->
    <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" id="name" name="name" value="${tag.name}" required>
    </div>



    <!-- Submit button to update the tag -->
    <button type="submit" class="btn btn-primary">Update Tag</button>
    <!-- Button to go back to the tag list -->
    <a href="tag?action=list" class="btn btn-secondary">Back to Tag List</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
