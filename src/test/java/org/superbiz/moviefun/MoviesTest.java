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
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.superbiz.moviefun.rest.ApplicationConfig;
import org.superbiz.moviefun.rest.LoadDataResource;
import org.superbiz.moviefun.rest.MoviesResource;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class MoviesTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Comment.class, Movie.class,  MoviesBean.class, LoadDataResource.class)
                .addClasses(MoviesResource.class, ApplicationConfig.class)
                .addAsWebInfResource(new StringAsset("<beans/>"), "beans.xml")
                .addAsResource(new ClassLoaderAsset("META-INF/persistence.xml"), "META-INF/persistence.xml");

        System.out.println(webArchive.toString(true));

        return webArchive;
    }

    @ArquillianResource
    private URL base;


    @Test
    @RunAsClient
    public void TestMoviesCount() throws Exception{



        final WebClient webClient = WebClient.create(base.toExternalForm());


        /**
            SEVERE: EjbTransactionUtil.handleSystemException: null
<openjpa-2.4.3-r422266:1833086 nonfatal general error> org.apache.openjpa.persistence.PersistenceException: null
FailedObject: 1 [org.apache.openjpa.util.LongId] [java.lang.String]
        at org.apache.openjpa.kernel.BrokerImpl.find(BrokerImpl.java:1029)
        at org.apache.openjpa.kernel.BrokerImpl.find(BrokerImpl.java:923)
        at org.apache.openjpa.kernel.DelegatingBroker.find(DelegatingBroker.java:230)
        at org.apache.openjpa.persistence.EntityManagerImpl.find(EntityManagerImpl.java:488)
        at org.apache.openejb.persistence.JtaEntityManager.find(JtaEntityManager.java:224)
        at org.superbiz.moviefun.MoviesBean.addCommentToMovie(MoviesBean.java:62)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.openejb.core.interceptor.ReflectionInvocationContext$Invocation.invoke(ReflectionInvocationContext.java:205)
        at org.apache.openejb.core.interceptor.ReflectionInvocationContext.proceed(ReflectionInvocationContext.java:186)
        at org.apache.openejb.monitoring.StatsInterceptor.record(StatsInterceptor.java:191)
        at org.apache.openejb.monitoring.StatsInterceptor.invoke(StatsInterceptor.java:102)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.openejb.core.interceptor.ReflectionInvocationContext$Invocation.invoke(ReflectionInvocationContext.java:205)
        at org.apache.openejb.core.interceptor.ReflectionInvocationContext.proceed(ReflectionInvocationContext.java:186)
        at org.apache.openejb.core.interceptor.InterceptorStack.invoke(InterceptorStack.java:85)
        at org.apache.openejb.core.singleton.SingletonContainer._invoke(SingletonContainer.java:272)
        at org.apache.openejb.core.singleton.SingletonContainer.invoke(SingletonContainer.java:221)
        at org.apache.openejb.core.ivm.EjbObjectProxyHandler.synchronizedBusinessMethod(EjbObjectProxyHandler.java:265)
        at org.apache.openejb.core.ivm.EjbObjectProxyHandler.businessMethod(EjbObjectProxyHandler.java:260)
        at org.apache.openejb.core.ivm.EjbObjectProxyHandler._invoke(EjbObjectProxyHandler.java:89)
        at org.apache.openejb.core.ivm.BaseEjbProxyHandler.invoke(BaseEjbProxyHandler.java:347)
        at org.superbiz.moviefun.MoviesBean$$LocalBeanProxy.addCommentToMovie(org/superbiz/moviefun/MoviesBean.java)
        at org.superbiz.moviefun.rest.LoadDataResource.addComments(LoadDataResource.java:67)
        at org.superbiz.moviefun.rest.LoadDataResource.load(LoadDataResource.java:44)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.apache.openejb.server.cxf.rs.PojoInvoker.performInvocation(PojoInvoker.java:43)
        at org.apache.cxf.service.invoker.AbstractInvoker.invoke(AbstractInvoker.java:96)
        at org.apache.cxf.jaxrs.JAXRSInvoker.invoke(JAXRSInvoker.java:191)
        at org.apache.cxf.jaxrs.JAXRSInvoker.invoke(JAXRSInvoker.java:101)
        at org.apache.openejb.server.cxf.rs.AutoJAXRSInvoker.invoke(AutoJAXRSInvoker.java:68)
        at org.apache.cxf.interceptor.ServiceInvokerInterceptor$1.run(ServiceInvokerInterceptor.java:59)
        at org.apache.cxf.interceptor.ServiceInvokerInterceptor.handleMessage(ServiceInvokerInterceptor.java:96)
        at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:308)
        at org.apache.cxf.transport.ChainInitiationObserver.onMessage(ChainInitiationObserver.java:121)
        at org.apache.cxf.transport.http.AbstractHTTPDestination.invoke(AbstractHTTPDestination.java:267)
        at org.apache.openejb.server.cxf.rs.CxfRsHttpListener.doInvoke(CxfRsHttpListener.java:253)
        at org.apache.tomee.webservices.CXFJAXRSFilter.doFilter(CXFJAXRSFilter.java:94)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        at org.apache.openejb.server.httpd.EEFilter.doFilter(EEFilter.java:65)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        at org.apache.tomee.microprofile.jwt.MPJWTFilter.doFilter(MPJWTFilter.java:64)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
        at org.apache.tomee.catalina.OpenEJBValve.invoke(OpenEJBValve.java:44)
        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)
        at org.apache.tomee.catalina.OpenEJBSecurityListener$RequestCapturer.invoke(OpenEJBSecurityListener.java:97)
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)
        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:800)
        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:800)
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1471)
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
        at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.NullPointerException
        at org.apache.openjpa.jdbc.kernel.JDBCStoreManager.setInverseRelation(JDBCStoreManager.java:452)
        at org.apache.openjpa.jdbc.kernel.JDBCStoreManager.initializeState(JDBCStoreManager.java:412)
        at org.apache.openjpa.jdbc.kernel.JDBCStoreManager.initialize(JDBCStoreManager.java:305)
        at org.apache.openjpa.kernel.DelegatingStoreManager.initialize(DelegatingStoreManager.java:112)
        at org.apache.openjpa.kernel.ROPStoreManager.initialize(ROPStoreManager.java:57)
        at org.apache.openjpa.kernel.BrokerImpl.initialize(BrokerImpl.java:1048)
        at org.apache.openjpa.kernel.BrokerImpl.find(BrokerImpl.java:1006)
        ... 74 more

I GOT: 500
Oct 02, 2018 5:25:49 PM org.apache.openejb.assembler.classic.Assembler destroyApplication
INFO: Undeploying app: /Users/cesar/git/tomee-microprofile-rest-client-demo/target/arquillian/0/test
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 9.23 sec <<< FAILURE! - in org.superbiz.moviefun.MoviesTest
TestMoviesCount(org.superbiz.moviefun.MoviesTest)  Time elapsed: 2.551 sec  <<< FAILURE!
java.lang.AssertionError: expected:<204> but was:<500>
        at org.junit.Assert.fail(Assert.java:88)
        at org.junit.Assert.failNotEquals(Assert.java:834)
        at org.junit.Assert.assertEquals(Assert.java:645)
        at org.junit.Assert.assertEquals(Assert.java:631)
        at org.superbiz.moviefun.MoviesTest.TestMoviesCount(MoviesTest.java:73)

         */
        final Response response2 = webClient.path("/api/load").post(null);
        System.out.println("I GOT: "+response2.getStatus());
        assertEquals(204, response2.getStatus());


        //This Works fine
        final Response response = webClient.path("/api/movies/count").get();
        assertEquals(200, response.getStatus());
        final String content = slurp ((InputStream) response.getEntity());
        assertEquals("0", content);



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
