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

import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.superbiz.moviefun.model.Movie;
import org.superbiz.moviefun.rest.ApplicationConfig;
import org.superbiz.moviefun.rest.MoviesResource;
import org.superbiz.moviefun.service.MoviesService;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

//CXF WebClient API http://cxf.apache.org/docs/jax-rs-client-api.html
@RunWith(Arquillian.class)
public class CxfJaxrsClientTest {

    private static final Logger LOGGER = Logger.getLogger(CxfJaxrsClientTest.class.getName());

    @Deployment()
    public static WebArchive createDeployment() {
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Movie.class,  MoviesService.class)
                .addClasses(MoviesResource.class, ApplicationConfig.class)
                .addAsWebInfResource(new StringAsset("<beans/>"), "beans.xml");

        LOGGER.info(webArchive.toString(true));
        return webArchive;
    }




    @Test
    public void Get() throws Exception{
        WebClient webClient = WebClient.create("http://localhost:4444/test");

        //GET
        webClient.reset();
        Response response = webClient.path("/api/movies/count").get();

        String content = slurp ((InputStream) response.getEntity());
        LOGGER.info("I found ["+content+"] movies.");
        assertEquals(200, response.getStatus());
        webClient.close();
    }


    @Test
    public void Post() {
        WebClient webClient = WebClient.create("http://localhost:4444/test");

        //POST
        webClient.path("api/movies").type("application/json").accept("application/json");
        Movie newMovieObj = new Movie("Duke","The CXF WebClient API.",2018);
        Response response = webClient.post(newMovieObj);
        LOGGER.info("POST request obtained ["+response.getStatus()+"]response code.");
        assertEquals(200,response.getStatus());

        //GET
        webClient.reset();
        Movie obtainedMovie = webClient.path("api/movies/6") .accept("application/json").get(Movie.class);
        LOGGER.info("Movie with id [200] hast the title: " +obtainedMovie.getTitle());
        assertEquals("The CXF WebClient API.",obtainedMovie.getTitle());
        webClient.close();
    }



    /**
     * Reusable utility method
     * Move to a shared class or replace with equivalent
     */
    public static String slurp (final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        out.flush();
        return new String(out.toByteArray());
    }

}
