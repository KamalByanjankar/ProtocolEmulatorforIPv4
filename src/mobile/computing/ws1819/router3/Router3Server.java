package mobile.computing.ws1819.router3;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Router3Server {

	private static final String router3Id = "10.10.30.30";
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
			HttpServer server = HttpServerFactory.create("http://localhost:7082/api/router3");
			server.start();
			
			System.out.println("\nRouter 3 with IP " + router3Id + " has been Started ");

			JOptionPane.showMessageDialog(null, "Press OK to shutdown Router 3.");
			server.stop(0);
	}

}
