<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.youcode.model.User, java.util.List" %>
<%@ page import="org.youcode.model.enums.UserRole" %>

<!DOCTYPE html>
<html class="dark">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ users</title>
</head>
<body>

<!-- Include header -->
<jsp:include page="../layouts/header.jsp"/>

<div class="container bg-light">

    <section class="py-3 sm:py-5">
        <div class="px-4 mx-auto lg:px-12">
            <div class="relative overflow-hidden bg-white shadow-md sm:rounded-lg">
                <div class="">
                    <table class="table table-striped">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkbox-all">
                                    <label class="form-check-label sr-only" for="checkbox-all">checkbox</label>
                                </div>
                            </th>
                            <th scope="col">UserName</th>
                            <th scope="col">Email</th>
                            <th scope="col">Role</th>
                            <th scope="col">First Name</th>
                            <th scope="col">Last name</th>
                            <th scope="col">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<User> users = (List<User>) request.getAttribute("users");
                            if (users == null || users.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="7" class="text-center p-4">No users found</td>
                        </tr>
                        <%
                        } else {
                            for (User user : users) {
                        %>
                        <tr>
                            <td>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="checkbox-table-search-<%= user.getId() %>">
                                    <label class="form-check-label sr-only" for="checkbox-table-search-<%= user.getId() %>">checkbox</label>
                                </div>
                            </td>
                            <th scope="row" class="d-flex align-items-center">
                                <img src="assets/images/profile.png" alt="profile Image" class="w-auto h-8 mr-3">
                                <%= user.getUsername() %>
                            </th>
                            <td>
                                <span class="badge badge-primary"><%= user.getEmail() %></span>
                            </td>
                            <td>
                            <span class="badge <%= user.getRole().equals(UserRole.user) ? "badge-success" : "badge-info" %>">
                            <%= user.getRole() %><%= user.getRole() %>
                                    </span>
                            </td>
                            <td><%= user.getFirstName() %></td>
                            <td><%= user.getLastName() %></td>
                            <td class="d-flex">
                                <a href="<%= request.getContextPath() %>/users?action=edit&id=<%= user.getId() %>" class="btn btn-outline-success btn-sm me-2">Edit</a>
                                <form action="<%= request.getContextPath() %>/users?action=delete" method="post" class="m-0 p-0">
                                    <input type="hidden" name="id" value="<%= user.getId() %>">
                                    <button type="submit" class="btn btn-outline-danger btn-sm delete-btn">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>

</div>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const isConfirmed = confirm('Are you sure you want to delete this user?');
            if (isConfirmed) {
                e.target.closest('form').submit();
            }
        });
    });
</script>
</body>
</html>