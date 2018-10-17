package org.superbiz.moviefun.mpclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.superbiz.moviefun.model.Movie;

import javax.enterprise.context.Dependent;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Dependent
@RegisterRestClient
@Path("/movies/api/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MovieResourceClient  {


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Movie find(@PathParam("id") Long id);

    @GET
    List<Movie> getMovies();


    @POST
    @Consumes("application/json")
    Movie addMovie(Movie movie);

    @DELETE
    @Path("{id}")
    void deleteMovie(@PathParam("id") long id);

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    Movie updateMovie(@PathParam("id") long id, Movie movie);


    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    int count(@QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm);
}
