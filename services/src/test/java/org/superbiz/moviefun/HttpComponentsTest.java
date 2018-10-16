package org.superbiz.moviefun;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;

//http://hc.apache.org/index.html
@RunWith(Arquillian.class)
public class HttpComponentsTest {
    private static final Logger LOGGER = Logger.getLogger(HttpComponentsTest.class.getName());

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
    @RunAsClient
    public void Get() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(base.toExternalForm()+"api/movies/count/");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            String content =slurp(response1.getEntity().getContent());
            LOGGER.info("I found ["+content+"] movies.");
            assertEquals("5",content);
        } finally {
            response1.close();
        }
    }

    @Test
    @RunAsClient
    public void GetFluentAPI() throws Exception {
        String response = Request.Get(base.toExternalForm()+"api/movies/count/")
                .execute().returnContent().asString();
        LOGGER.info(response);
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
