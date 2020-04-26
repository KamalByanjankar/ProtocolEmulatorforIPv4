package mobile.computing.ws1819.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class StartRestServer{
	
	private static final String destinationId = "192.168.60.60";
	
	
	public static void main(String[] args) throws IllegalArgumentException, IOException
	{
		
		
		HttpServer server = HttpServerFactory.create("http://localhost:8080/api");
		server.start();		
		
		System.out.println("\nServer with IP " + destinationId+ " has been Started");
		
		JOptionPane.showMessageDialog(null, "Press OK to shutdown server.");
		server.stop(0);
		
	}

}
