package com.devsync.servlet;

import com.devsync.entities.Utilisateur;
import com.devsync.services.UtilisateurService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UtilisateurServlet extends HttpServlet {

    @Inject
    private UtilisateurService utilisateurService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        // Check if the user is logged in
        Utilisateur loggedInUser = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in to access this page.");
            return;
        }

        try {
            switch (action) {
                case "list":
                    listUtilisateurs(request, response);
                    break;
                case "details":
                    if (loggedInUser.getIsManager()) {
                        detailsUtilisateur(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to view this page.");
                    }
                    break;
                case "create":
                    if (loggedInUser.getIsManager()) {
                        showCreateForm(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to create users.");
                    }
                    break;
                case "update":
                    if (loggedInUser.getIsManager()) {
                        showUpdateForm(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to update users.");
                    }
                    break;
                case "delete":
                    if (loggedInUser.getIsManager()) {
                        deleteUtilisateur(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to delete users.");
                    }
                    break;
                default:
                    listUtilisateurs(request, response);
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

        // Check if the user is logged in
        Utilisateur loggedInUser = (Utilisateur) request.getSession().getAttribute("utilisateur");
//        response.getWriter().println(loggedInUser);


        try {
            switch (action) {
                case "login":
                    loginUtilisateur(request, response);

                    break;
                case "create":
                    if (loggedInUser.getIsManager()) {
                        createUtilisateur(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to create users.");
                    }
                    break;
                case "update":
                    if (loggedInUser.getIsManager()) {
                        updateUtilisateur(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to update users.");
                    }
                    break;
                default:
                    listUtilisateurs(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing POST request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }


    private void loginUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Existing login method
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email and password are required.");
            return;
        }

        Utilisateur utilisateur = utilisateurService.authenticate(email, password);

        if (utilisateur != null) {
            // Successful login
            request.getSession().setAttribute("utilisateur", utilisateur);



            response.sendRedirect("index.jsp");
        } else {
            // Invalid login
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid email or password.");

        }
    }

    private void listUtilisateurs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            request.setAttribute("utilisateurs", utilisateurs);
            request.getRequestDispatcher("/views/user/list.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error retrieving user list: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Failed to retrieve user list. Please try again later.");
            request.setAttribute("utilisateurs", List.of());
            request.getRequestDispatcher("/views/user/list.jsp").forward(request, response);
        }
    }


    private void detailsUtilisateur(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                request.setAttribute("utilisateur", utilisateur);
                request.getRequestDispatcher("/views/user/details.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/user/create.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                request.setAttribute("utilisateur", utilisateur);
                request.getRequestDispatcher("/views/user/update.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }

    private void createUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get parameters from the request
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String isManagerParam = request.getParameter("is_manager");


        Boolean isManager = isManagerParam != null && isManagerParam.equals("on");


        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() ||
                username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }


        Utilisateur newUtilisateur = new Utilisateur(nom, prenom, username, password, email, isManager);


        utilisateurService.createUtilisateur(newUtilisateur);


        response.sendRedirect("utilisateur?action=list");
    }

    private void updateUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the user ID from the request
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);  // Parse the user ID
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String isManagerParam = request.getParameter("is_manager");


            Boolean isManager = isManagerParam != null && isManagerParam.equals("on");


            if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() ||
                    username == null || username.isEmpty() || email == null || email.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }


            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                // Update user details
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setUsername(username);
                utilisateur.setEmail(email);
                utilisateur.setIsManager(isManager); // Assuming there's a setter for isManager

                // Only update the password if it's provided
                if (password != null && !password.isEmpty()) {
                    utilisateur.setPassword(password);
                }


                utilisateurService.updateUtilisateur(utilisateur);


                response.sendRedirect("utilisateur?action=list");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }


    private void deleteUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            utilisateurService.deleteUtilisateur(id);
            response.sendRedirect("utilisateur?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }
}
