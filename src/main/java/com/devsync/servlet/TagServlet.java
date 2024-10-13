package com.devsync.servlet;

import com.devsync.entities.Tag;
import com.devsync.services.TagService;
import com.devsync.entities.Utilisateur;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class TagServlet extends HttpServlet {

    @Inject
    private TagService tagService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        Utilisateur loggedInUser = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to access this page.");
            return;
        }

        try {
            switch (action) {
                case "list":
                    listTags(request, response);
                    break;

                case "create":

                        showCreateForm(request, response);

                    break;
                case "update":
                        showUpdateForm(request, response);

                    break;
                case "delete":
                        deleteTag(request, response);

                    break;
                default:
                    listTags(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing GET request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Utilisateur loggedInUser = (Utilisateur) request.getSession().getAttribute("utilisateur");

        try {
            switch (action) {
                case "create":

                        createTag(request, response);

                    break;
                case "update":

                        updateTag(request, response);

                    break;
                default:
                    listTags(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing POST request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    private void listTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Tag> tags = tagService.getAllTags();
            request.setAttribute("tags", tags);
            request.getRequestDispatcher("/views/tag/list.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error retrieving tag list: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Failed to retrieve tag list. Please try again later.");
            request.setAttribute("tags", List.of());
            request.getRequestDispatcher("/views/tag/list.jsp").forward(request, response);
        }
    }

    private void createTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");


        if (name == null || name.isEmpty() ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        Tag newTag = new Tag(name);
        tagService.createTag(newTag);

        response.sendRedirect("tag?action=list");
    }

    private void updateTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            String name = request.getParameter("name");

            if (name == null || name.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            Tag tag = tagService.getTagById(id);
            if (tag != null) {
                tag.setName(name);

                tagService.updateTag(tag);
                response.sendRedirect("tag?action=list");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tag not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Tag ID format.");
        }
    }

    private void deleteTag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            tagService.deleteTag(id);
            response.sendRedirect("tag?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Tag ID format.");
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/tag/create.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Tag tag = tagService.getTagById(id);
            if (tag != null) {
                request.setAttribute("tag", tag);
                request.getRequestDispatcher("/views/tag/update.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tag not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Tag ID format.");
        }
    }

}
