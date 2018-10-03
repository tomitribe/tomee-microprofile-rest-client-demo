/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun.rest;

import org.superbiz.moviefun.movie.Movie;
import org.superbiz.moviefun.service.MoviesService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("movies")
@Produces({"application/json"})
@ApplicationScoped
public class MoviesResource {

    private static final Logger LOGGER = Logger.getLogger(MoviesResource.class.getName());

    @EJB
    private MoviesService service;


    @GET
    @Path("{id}")
    public Movie find(@PathParam("id") Long id) {
        LOGGER.info("find: " + id);
        return service.find(id);
    }

    @GET
    public List<Movie> getMovies() {
        return service.getMovies();
    }

    @POST
    @Consumes("application/json")
    public Movie addMovie(Movie movie) {
        service.addMovie(movie);
        return movie;
    }

    @DELETE
    @Path("{id}")
    public void deleteMovie(@PathParam("id") long id) {
        service.deleteMovie(id);
    }

    @PUT
    @Path("{id}")
    public Movie updateMovie(@PathParam("id") long id, Movie movie) {
        service.updateMovie(id,movie);
        return service.find(id);
    }


    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm) {
        return service.count();
    }

}