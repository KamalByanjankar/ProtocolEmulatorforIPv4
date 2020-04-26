package mobile.computing.ws1819.router1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import mobile.computing.ws1819.Message;
import mobile.computing.ws1819.RoutingTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


	
	@Path("router1")
	public class Router1Resource
	{
		
		private static final String router1Id = "10.10.10.10";
		private static final String router3Id = "10.10.30.30";
		private static final String router4Id = "10.10.40.40";
		private Message newMessage;
		
		List<RoutingTable> routingTableList = new ArrayList<>();

		@POST
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.APPLICATION_JSON)
		public String createMessage(String messageAsJSONstring) throws JsonParseException, JsonMappingException, IOException
		{
			Scanner sc = new Scanner(System.in);
			
			// Deserialise JSON message
			ObjectMapper mapper = new ObjectMapper();
			Message message = mapper.readValue(messageAsJSONstring, Message.class);
			
			message.setSourceId("10.10.10.10");
			message.setDestinationId("192.168.60.60");
			message.setTtl(message.getTtl()-1);
			
			
			routingTableList = message.getRoutingTable();
			displayRoutingTable(routingTableList);
			
			
			System.out.print("\nEnter cost for Router 3 with IP " + router3Id + " : ");
			int R3 = sc.nextInt();
			
			System.out.print("Enter cost for Router 4 with IP " + router4Id + " : ");
			int R4 = sc.nextInt();
			
			RoutingTable routingTable = new RoutingTable();

			
			if(message.getTtl()-1>0) {
				if(R3 <= R4)
				{
					
					int nextCost = R3;
					
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.10.0", "10.10.10.10", 1, "R301"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.30.0", "Directly connected", 0, "R301"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.20.0", "10.10.20.20", 1, "R302"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.30.0", "Directly Connected", 0, "R302"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "Directly connected", 0, "R303"));
					
					
					Message newMessage = getNewMessage(message);
					newMessage.setRoutingTable(routingTableList);
					messageAsJSONstring = mapper.writeValueAsString(newMessage);
					
					System.out.println("\nSending Request to Router 3 .... ");
					System.out.println("Connection established from " + router1Id + " -------> " + router3Id + " Cost = " + nextCost);
					System.out.println("Waiting for data to be transferred to Router 3 .... ");
					
					Client create = Client.create();
					WebResource service = create.resource("http://localhost:7082/api/router3");
					String response = service.path("router3").type(MediaType.APPLICATION_JSON).post(String.class, messageAsJSONstring);
					System.out.println(response);
					
					System.out.println(" \n Creating Message Object...\n" + newMessage);
					System.out.println("\nReceived POST Request with JSON String:\n" + messageAsJSONstring);
					System.out.println("\nData :  " + message.data);
					System.out.println("Route " +router1Id+ "----> "+router3Id+ " Cost : " +nextCost);
				}
				
				else 
				{
					int nextCost = R4;
					
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.20.0", "10.10.20.20", 1, "R401"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.40.0", "Directly connected", 0, "R401"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.10.0", "10.10.10.10", 1, "R402"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "10.10.40.0", "Directly Connected", 0, "R402"));
					routingTableList.add(new RoutingTable("192.168.60.60", "255.255.255.0", "192.168.60.0", "Directly connected", 0, "R403"));
					
					Message newMessage = getNewMessage(message);
					newMessage.setRoutingTable(routingTableList);
					messageAsJSONstring = mapper.writeValueAsString(newMessage);
					
					System.out.println("\nSending Request to Router 4 ....");
					System.out.println("Connection established from " + router1Id + " -------> " + router4Id + " Cost = " + nextCost);
					System.out.println("Waiting for data to be transferred to Router 4 .... ");
					
					
					Client create = Client.create();
					WebResource service = create.resource("http://localhost:7083/api/router4");
					String response = service.path("router4").type(MediaType.APPLICATION_JSON).post(String.class, messageAsJSONstring);
					System.out.println(response);
					
					System.out.println(" \n Creating Message Object...\n" + newMessage);
					System.out.println("\nReceived POST Request with JSON String:\n" + messageAsJSONstring);
					System.out.println("\nData :  " + message.data);
					System.out.println("Route " +router1Id+ "---->" +router4Id+ "Cost : " +nextCost);
				}
			}
			return "200 OK";
		}
	
		private Message getNewMessage(Message message) {
			newMessage = new Message();
			newMessage.setDestinationId(message.getDestinationId());
			newMessage.setSourceId(message.getSourceId());
			newMessage.setTtl(message.getTtl());
			newMessage.setData(message.getData());
			newMessage.setTimestamp(message.getTimestamp());
			newMessage.setId(message.getId());
			newMessage.setText(messageText(newMessage));
			newMessage.setRoutingTable(routingTableList);
			
			
			return newMessage;
		}
		
		private String messageText(Message newMessage) {
			return "[  \r\n" + 
					"	{\"ip\": {\r\n" + 
					"          \"ip.version\": 4,\r\n" + 
					"          \"ip.hdr_len\": 20,\r\n" + 
					"          \"ip.len\": 84,\r\n" + 
					"          \"ip.flags\": \"0x00000000\",\r\n" + 
					"          \"ip.flags_tree\": {\r\n" + 
					"            \"ip.flags.rb\": 0,\r\n" + 
					"            \"ip.flags.df\": 0,\r\n" + 
					"            \"ip.flags.mf\": 0,\r\n" + 
					"            \"ip.frag_offset\": 0\r\n" + 
					"          },\r\n" + 
					"          \"ip.ttl\": "+newMessage.getTtl()+",\r\n" + 
					"          \"ip.checksum\": \"0x00009b44\",\r\n" + 
					"          \"ip.checksum.status\": 2,\r\n" + 
					"          \"ip.src\": "+newMessage.getSourceId()+",\r\n" + 
					"          \"ip.dst\": "+newMessage.getDestinationId()+",\r\n" + 
					"        },\r\n" + 
					"        \"data\": {\r\n" + 
					"          \"data.data\": "+newMessage.data+",\r\n" + 
					"          \"data.len\": "+newMessage.data.length()+",\r\n" + 
					"        }\r\n" + 
					"  }\r\n" + 
					"]";
		}
		
		private void displayRoutingTable(List<RoutingTable> routingTableList) {
	
			System.out.println("\nRouting Table of Router 1");
			StringBuilder string = new StringBuilder();
			for(RoutingTable routingTable: routingTableList) {
				string.append("\n").append("Destination IP:").append(routingTable.getDestinationIp()).append("    ").append("Subnet Mask:").append(routingTable.getSubnetMask()).append("    ").append("Subnet IP:").append(routingTable.getSubnetIP()).append("    ").append("Next Router:").append(routingTable.getNextRouter()).append("    ").append("Metric:").append(routingTable.getMetric()).append("    ").append("Exit Interface :").append(routingTable.getExit_interface());
				
			}
			
			System.out.println(string.toString());
			
		}
		
}
