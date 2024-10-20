package org.youcode.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.youcode.controller.MainController;
import org.youcode.controller.RequestController;

@WebServlet(name = "RequestServlet", value = {"/requests"})
public class RequestServlet extends HttpServlet {
    private final RequestController requestController;
    private final MainController mainController;

    public RequestServlet() {
        requestController = new RequestController();
        mainController = new MainController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);
        response.setContentType("text/html");
        // Index
        if (action == null) {
            requestController.index(request, response);
        }
        else
            mainController.notFound(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);

        // Register
        switch (action) {
            case "save":
                requestController.save(request, response);
                break;
            // Mark as accepted
            case "accept":
                requestController.acceptRequest(request, response);
                break;
            // Assign to a developer
            case "acceptModify":
                requestController.acceptModify(request, response);
                break;
            // Mark as rejected
            case "reject":
                requestController.rejectRequest(request, response);
                break;
            default:
                requestController.index(request, response);
                break;
        }
    }
}
