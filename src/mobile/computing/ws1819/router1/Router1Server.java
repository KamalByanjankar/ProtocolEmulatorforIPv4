package mobile.computing.ws1819.router1;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import mobile.computing.ws1819.Message;

public class Router1Server {
	
	private static final String ipRouter1 = "10.10.10.10";
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		HttpServer server = HttpServerFactory.create("http://localhost:7080/api/router1");
		server.start();
		
		System.out.println("\nRouter 1 with IP " +ipRouter1+ " has been Started ");
		
		JOptionPane.showMessageDialog(null, "Press OK to shutdown Router 1.");
		server.stop(0);
	}
	

}
