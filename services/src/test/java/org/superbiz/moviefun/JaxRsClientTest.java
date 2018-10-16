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


package org.superbiz.moviefun;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.superbiz.moviefun.model.Movie;
import org.superbiz.moviefun.rest.ApplicationConfig;
import org.superbiz.moviefun.rest.MoviesResource;
import org.superbiz.moviefun.service.MoviesService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

//https://jersey.github.io/documentation/latest/client.html
@RunWith(Arquillian.class)
public class JaxRsClientTest {

    private static final Logger LOGGER = Logger.getLogger(JaxRsClientTest.class.getName());

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Movie.class,  MoviesService.class)
                .addClasses(MoviesResource.class, ApplicationConfig.class)
                .addAsWebInfResource(new StringAsset("<beans/>"), "beans.xml");

        LOGGER.info(webArchive.toString(true));
        return webArchive;
    }

    @ArquillianResource
    private URL base;



    @Test
    public void Get() {
        Client client = ClientBuilder.newClient();
        String response = client.target(base.toExternalForm()).path("/api/movies/count")
                          .request(MediaType.TEXT_PLAIN)
                          .get(String.class);

        LOGGER.info("I found ["+response+"] movies.");
        assertEquals("5",response);
        client.close();
    }


    @Test
    public void Post() throws Exception{
        Client client = ClientBuilder.newClient();

        //POST
        Movie newMovieObj = new Movie("Duke","The JAX-RS WebClient API.",2018);
        Response response = client.target(base.toExternalForm()).path("/api/movies")
                            .request(MediaType.APPLICATION_JSON).post(Entity.json(newMovieObj));
        LOGGER.info("POST request obtained ["+response.getStatus()+"]response code.");
        assertEquals(200,response.getStatus());


        //GET
        Movie obtainedMovie = client.target(base.toExternalForm()).path("/api/movies/6")
                              .request(MediaType.APPLICATION_JSON).get(Movie.class);
        LOGGER.info("Movie with id [200] hast the title: " +obtainedMovie.getTitle());
        assertEquals("The JAX-RS WebClient API.",obtainedMovie.getTitle());
        client.close();
    }
}
