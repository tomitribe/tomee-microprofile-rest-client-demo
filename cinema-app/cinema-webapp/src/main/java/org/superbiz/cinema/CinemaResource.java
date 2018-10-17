package org.superbiz.cinema;


import org.superbiz.moviefun.model.Movie;
import org.superbiz.moviefun.rest.client.MovieClient;
import org.tomitribe.inget.client.ClientConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cinema")
public class CinemaResource {

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("movies")
    public Response getMovies() {
        ClientConfiguration config = ClientConfiguration.builder().url("http://localhost:8181/services/api").build();

        MovieClient movieClient = new MovieClient(config);
        List<Movie> movies = movieClient.movies().getMovies();
        System.out.println(new org.apache.johnzon.mapper.MapperBuilder().setPretty(true).build()
                .writeObjectAsString(movies));
        return Response.ok().entity(movies).build();
    }
}
