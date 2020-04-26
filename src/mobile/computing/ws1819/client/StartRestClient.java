package mobile.computing.ws1819.client;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;

import mobile.computing.ws1819.Message;
import mobile.computing.ws1819.RoutingTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartRestClient
{

	private static final String sourceId = "192.168.10.10";
	private static final String router1Id = "10.10.10.10";
	private static final String router2Id = "10.10.20.20";



	public static void main(String[] args) throws IOException
	{

		doPostRequest();

	}


		private static void doPostRequest() throws JsonProcessingException
		{
			
			Scanner sc = new Scanner(System.in);
		
			System.out.println("\nClient with IP " + sourceId + " has been started .... \n");
			
			System.out.print("Enter data to be send to the destination : ");
			String textInput= sc.nextLine();
			
			// Send POST request
			System.out.print("\nEnter cost for Router 1 with IP " + router1Id + " : ");
			int R1 = sc.nextInt();
			
			System.out.print("Enter cost for Router 2  with IP " + router2Id + " : ");
			int R2 = sc.nextInt();	
			
			RoutingTable routingTable = new RoutingTable();
			List<RoutingTable> routingTableList = new ArrayList<RoutingTable>();
			
			// Serialise Message Object
			ObjectMapper mapper = new ObjectMapper();
			
			if (R1 <= R2)
			{
				int nextCost = R1;
				
				//Creating Routing Table
				
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.10.0", "Directly connected", 0, "R101"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.30.0", "Directly connected", 0, "R103"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.40.0", "Directly connected", 0, "R102"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "10.10.30.30", 1, "R103"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "10.10.40.40", 1, "R102"));

				
				Message message = Message.generateExampleMessage();
				message.setRoutingTable(routingTableList);
				message.setData(textInput);
				
				// Writing in JSON string
				String messageAsJSONstring = mapper.writeValueAsString(message);
				System.out.println("\nConnection established from " + sourceId + " -------> " + router1Id+ " Cost = " + nextCost);

//				System.out.println("Waiting for data to be transferred .... ");
			
				
				// Client created
				Client create = Client.create();
				WebResource service = create.resource("http://localhost:7080/api/router1");
				String response = service.path("router1").type(MediaType.APPLICATION_JSON).post(String.class, messageAsJSONstring);
				System.out.println(response);
				
				System.out.println("\nData : " + textInput);
				System.out.println("Route " +sourceId+ "---->" +router1Id+ " Cost = " +nextCost );
				
			}
			
			else
			{
				int nextCost = R2;
				
				//Creating Routing Table
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.20.0", "Directly connected", 0, "R201"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.30.0", "Directly connected", 0, "R203"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.40.0", "Directly connected", 0, "R202"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "10.10.30.30", 1, "R203"));
				routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "10.10.40.40", 1, "R202"));
				
				Message message = Message.generateExampleMessage();
				message.setRoutingTable(routingTableList);
				message.setData(textInput);
				
				// Writing in JSON string
				String messageAsJSONstring = mapper.writeValueAsString(message);
			
				System.out.println("\nConnection established from " + sourceId + " -------> " + router2Id+ " Cost = " + nextCost);
//				System.out.println("Waiting for data to be transferred .... ");
				
				
				// Client Created
				Client create = Client.create();
				WebResource service = create.resource("http://localhost:7081/api/router2");
				String response = service.path("router2").type(MediaType.APPLICATION_JSON).post(String.class, messageAsJSONstring);					
				System.out.println(response);
				
				System.out.println("\nData : " + textInput);
				System.out.println("Route " +sourceId+ "---->" +router2Id+ " Cost = " + nextCost );
			}	
				
	}
}
