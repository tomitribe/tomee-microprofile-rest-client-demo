import org.superbiz.moviefun.model.Movie;
import org.superbiz.moviefun.rest.client.MovieClient;
import org.tomitribe.inget.client.ClientConfiguration;

import java.net.MalformedURLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        ClientConfiguration config = ClientConfiguration.builder().url("http://localhost:8181/services/api").build();

        MovieClient movieClient = new MovieClient(config);
        List<Movie> movies = movieClient.movies().getMovies();
        System.out.println(new org.apache.johnzon.mapper.MapperBuilder().setPretty(true).build()
                .writeObjectAsString(movies));
    }
}
