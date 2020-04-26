package mobile.computing.ws1819.router2;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Router2Server {
	
	private static final String router2Id = "10.10.20.20";

		public static void main(String[] args) throws IllegalArgumentException, IOException {
			HttpServer server = HttpServerFactory.create("http://localhost:7081/api/router2");
			server.start();
			
			System.out.println("\nRouter 2 with IP " + router2Id + " has been Started ");
			
			JOptionPane.showMessageDialog(null, "Press OK to shutdown Router 2.");
			server.stop(0);
	}

}
