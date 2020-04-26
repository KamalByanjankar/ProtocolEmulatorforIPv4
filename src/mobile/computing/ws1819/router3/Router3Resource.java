package mobile.computing.ws1819.router3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@Path("router3")
public class Router3Resource
{
	
	private static final String router3Id = "10.10.30.30";
	private static final String destinationId = "192.168.60.60";
	
	
	private Message newMessage;
	

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createMessage(String messageAsJSONstring) throws JsonParseException, JsonMappingException, IOException
	{
		
		System.out.println("A connection has been established");
		
		
		// Deserialise JSON message
		ObjectMapper mapper = new ObjectMapper();
		
		Message message = mapper.readValue(messageAsJSONstring, Message.class);
		
		message.setSourceId("10.10.30.30");
		message.setDestinationId("192.168.60.60");
		message.setTtl(message.getTtl()-1);
		
		List<RoutingTable> routingTableList = message.getRoutingTable();
		displayRoutingTable(routingTableList);
		
		Message newMessage = getNewMessage(message);
		
		messageAsJSONstring = mapper.writeValueAsString(newMessage);
		
		
		
		System.out.println("\nSending Request to the server at IP : "+ destinationId);
		System.out.println("Connected direct to the Server " + router3Id + " ----> " + destinationId);
		
		
		//Creating a client
		Client create = Client.create();
		WebResource service = create.resource("http://localhost:8080/api");
		String response = service.path("message").type(MediaType.APPLICATION_JSON).post(String.class, messageAsJSONstring);
		System.out.println(response);
		
		System.out.println("Creating Message Object...\n" + newMessage);
		System.out.println("\nReceived POST Request with JSON String:\n" + messageAsJSONstring);
		
		System.out.println("\nRoute " + router3Id + " -------> " + destinationId);
		System.out.println("Data :  " + message.data);

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
		
//		System.err.println(newMessage + " #########newMessage ##########");
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
		
		System.out.println("\nRouting Table of Router 3");
		StringBuilder string = new StringBuilder();
		for(RoutingTable routingTable:routingTableList) {
			string.append("\n").append("Destination IP:").append(routingTable.getDestinationIp()).append("    ").append("Subnet Mask:").append(routingTable.getSubnetMask()).append("    ").append("Subnet IP:").append(routingTable.getSubnetIP()).append("    ").append("Next Router:").append(routingTable.getNextRouter()).append("    ").append("Metric:").append(routingTable.getMetric()).append("    ").append("Exit Interface :").append(routingTable.getExit_interface());
			
		}
		
		System.out.println(string.toString());
		
	}
}

