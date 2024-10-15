package com.devsync.servlet;

import com.devsync.entities.Tache;
import com.devsync.entities.Tag;  // Assuming you have a Tag entity
import com.devsync.services.TacheService;
import com.devsync.services.TagService; // Assuming you have a TagService
import com.devsync.entities.Utilisateur;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TacheServlet extends HttpServlet {

    @Inject
    private TacheService tacheService;

    @Inject
    private TagService tagService; // Inject the TagService

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
                    listTaches(request, response);
                    break;
                case "details":
                    detailsTache(request, response);
                    break;
                case "create":
                    showCreateForm(request, response); // Show create form
                    break;
                case "update":
                    showUpdateForm(request, response); // Show update form
                    break;
                case "delete":
                    deleteTache(request, response);
                    break;
                default:
                    listTaches(request, response);
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
                    createTache(request, response);
                    break;
                case "update":
                    updateTache(request, response);
                    break;
                default:
                    listTaches(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing POST request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    private void listTaches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Tache> taches = tacheService.getAllTaches();



            // Set attributes for JSP
            request.setAttribute("taches", taches);


            // Forward to the JSP
            request.getRequestDispatcher("/views/tache/list.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error retrieving task list: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Failed to retrieve task list. Please try again later.");
            request.setAttribute("taches", List.of());
            request.getRequestDispatcher("/views/tache/list.jsp").forward(request, response);
        }
    }


    private void createTache(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String dateLimite = request.getParameter("date_limite");  // You might need proper date handling here

        if (titre == null || titre.isEmpty() || description == null || description.isEmpty() || dateLimite == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        // Retrieve the logged-in user from the session
        Utilisateur loggedInUser = (Utilisateur) request.getSession().getAttribute("utilisateur");

        // Check if the user is logged in
        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        // Proper parsing of date and task creation
        LocalDate parsedDateLimite;
        try {
            parsedDateLimite = LocalDate.parse(dateLimite); // Ensure the date is in the correct format
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        // Retrieve the tag IDs from the request
        String[] tagIds = request.getParameterValues("tags");
        Set<Tag> selectedTags = new HashSet<>();

        if (tagIds != null) {
            for (String tagId : tagIds) {
                // Fetch the Tag entity from the database using the ID
                Tag tag = tagService.getTagById(Long.parseLong(tagId));
                selectedTags.add(tag);
            }
        }

        Tache newTache = new Tache(titre, description, LocalDate.now(), parsedDateLimite, false, loggedInUser, selectedTags);
        tacheService.createTache(newTache);// Assuming this method exists to handle task creation

        // Redirect to the task list page after creation
        response.sendRedirect("tache?action=list");
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the list of tags and set it in the request
        List<Tag> tags = tagService.getAllTags();  // Assuming the method exists in TagService
        request.setAttribute("tags", tags);

        // Forward to the create form
        request.getRequestDispatcher("/views/tache/create.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Tache tache = tacheService.getTacheById(id);
            if (tache != null) {
                // Fetch the list of tags and set it in the request
                List<Tag> allTags = tagService.getAllTags(); // Assuming you have a tagService for fetching all tags

// Initialize the task's tags and fetch the task
                Hibernate.initialize(tache.getTags());
                List<Tag> tacheTags = new ArrayList<>(tache.getTags()); // These are the tags for the specific task

// Set all available tags and the task in the request
                request.setAttribute("tags", allTags); // These are all available tags
                request.setAttribute("tacheTags", tacheTags); // Set the task's tags
                request.setAttribute("tache", tache); // Set the tache object itself
                request.getRequestDispatcher("/views/tache/update.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Task ID format.");
        }
    }

    private void updateTache(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Tache tache = tacheService.getTacheById(id);

            if (tache == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found.");
                return;
            }

            tache.setTitre(request.getParameter("titre"));
            tache.setDescription(request.getParameter("description"));
            tache.setDateLimite(LocalDate.parse(request.getParameter("date_limite")));

            // Update tags if provided
            String[] selectedTagIds = request.getParameterValues("tags");
            if (selectedTagIds != null) {
                Set<Tag> updatedTags = Arrays.stream(selectedTagIds)
                        .map(tagId -> tagService.getTagById(Long.parseLong(tagId)))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                tache.setTags(updatedTags);
            }

            tacheService.updateTache(tache);
            response.sendRedirect("tache?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Task ID format.");
        }
    }



    private void deleteTache(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            tacheService.deleteTache(id);
            response.sendRedirect("tache?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Task ID format.");
        }
    }

    private void detailsTache(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            // Fetch the task by ID
            Tache tache = tacheService.getTacheById(id);
            if (tache == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found.");
                return;
            }

            // Fetch associated tags (assuming lazy loading)
            Hibernate.initialize(tache.getTags());
            List<Tag> tags = new ArrayList<>(tache.getTags());

            // Set attributes to pass to JSP
            request.setAttribute("tache", tache);
            request.setAttribute("tags", tags);

            // Forward to details page
            request.getRequestDispatcher("/views/tache/details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Task ID format.");
        } catch (Exception e) {
            // Handle any unexpected errors (such as database issues)
            log("Error retrieving task details: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving task details.");
        }
    }


}
