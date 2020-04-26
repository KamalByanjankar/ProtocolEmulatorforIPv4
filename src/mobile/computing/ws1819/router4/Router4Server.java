package mobile.computing.ws1819.router4;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Router4Server {

	private static final String router4Id = "10.10.40.40";
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
			HttpServer server = HttpServerFactory.create("http://localhost:7083/api/router4");
			server.start();
			
			System.out.println("\nRouter 4 with IP " + router4Id + " has been Started ");

			JOptionPane.showMessageDialog(null, "Press OK to shutdown Router 4.");
			server.stop(0);
	}

}
