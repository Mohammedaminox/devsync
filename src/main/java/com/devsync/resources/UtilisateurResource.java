package com.devsync.resources;

import com.devsync.entities.Utilisateur;
import com.devsync.services.UtilisateurService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    private UtilisateurService utilisateurService;

    @POST
    public Response createUtilisateur(Utilisateur utilisateur) {
        utilisateurService.createUtilisateur(utilisateur);
        return Response.ok(utilisateur).build();
    }

    @GET
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GET
    @Path("/{id}")
    public Utilisateur getUtilisateurById(@PathParam("id") Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateUtilisateur(@PathParam("id") Long id, Utilisateur utilisateur) {
        utilisateur.setId(id);
        utilisateurService.updateUtilisateur(utilisateur);
        return Response.ok(utilisateur).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUtilisateur(@PathParam("id") Long id) {
        utilisateurService.deleteUtilisateur(id);
        return Response.noContent().build();
    }
}
