package org.superbiz.moviefun;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.superbiz.moviefun.model.Movie;
import org.superbiz.moviefun.mpclient.MovieResourceClient;
import org.superbiz.moviefun.rest.ApplicationConfig;
import org.superbiz.moviefun.rest.MoviesResource;
import org.superbiz.moviefun.service.MoviesService;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;


@RunWith(Arquillian.class)
public class MpRestClientTest {
    private static final Logger LOGGER = Logger.getLogger(MpRestClientTest.class.getName());

    @Deployment()
    public static WebArchive createDeployment() {
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "movies.war")
                .addClasses(Movie.class,  MoviesService.class, MovieResourceClient.class)
                .addClasses(MoviesResource.class, ApplicationConfig.class)
                .addAsWebInfResource(new StringAsset("<beans/>"), "beans.xml")
                .addAsResource("META-INF/microprofile-config.properties");
        LOGGER.info(webArchive.toString(true));
        return webArchive;
    }




    @Test
    public void GetProgrammatic() throws Exception{

        MovieResourceClient simpleGetApi = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://localhost:4444"))
                .build(MovieResourceClient.class);
        int count = simpleGetApi.count("","");
        LOGGER.info(Integer.toString(count));
    }



    @Inject
    @RestClient
    private MovieResourceClient movieResourceClient;

    @Test
    public void GetCDI(){
        int count = movieResourceClient.count("","");
        LOGGER.info(Integer.toString(count));
    }

    @Test
    public void GetCDIMovies(){
        List<Movie> movies = movieResourceClient.getMovies();
        for (Movie movie : movies) {
            LOGGER.info(movie.toString());
        }
    }

    @Test
    public void PostCDI() {
        Movie newMovie = new Movie ("Duke","The MicroProfile Type-safe REST Client.",2018);
        movieResourceClient.addMovie(newMovie);

        List<Movie> movies = movieResourceClient.getMovies();
        for (Movie movie : movies) {
            LOGGER.info(movie.toString());
        }
    }

}
