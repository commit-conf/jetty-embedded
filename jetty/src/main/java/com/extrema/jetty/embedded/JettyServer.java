package com.extrema.jetty.embedded;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Iterator;


/**
 * Starts an embedded Jetty server with the applications passed as parameters
 * with the format domain:project-name, e.g.: helloworld:helloworld
 * If the project name is missing, it will be assumed to be the same as the domain name.
 */
public final class JettyServer {

	private JettyServer() {
	}

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

        // add web applications
        HandlerCollection handlers = new HandlerCollection();
        Preconditions.checkArgument(args != null && args.length > 0, "Missing args: web project. Please pass a list of web projects, e.g.: JettyServer helloworld");
        for (String arg : args) {
            Iterator<String> it = Splitter.on(":").split(arg).iterator();
            String domain = it.next();
            String projectName = it.hasNext() ? it.next() : domain;
            WebAppContext webappContext = createContext(domain, projectName);
            handlers.addHandler(webappContext);
        }
        server.setHandler(handlers);

        // enable web 3.0 annotations
        Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);
        classList.addBefore(JettyWebXmlConfiguration.class.getName(), AnnotationConfiguration.class.getName());

        server.start();
        server.join();
    }

	private static WebAppContext createContext(String domain, String projectName) {
		WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/" + domain);
        webapp.setWar("../" + projectName + "/src/main/webapp");

        // fail if the web app does not deploy correctly
        webapp.setThrowUnavailableOnStartupException(true);

        // disable directory listing
        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        return webapp;
	}

}
