package mobile.computing.ws1819.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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

@Path("message")
public class MessageResource
{
	
	private Message newMessage;

	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createMessage(String messageAsJSONstring) throws JsonParseException, JsonMappingException, IOException
	{
		
		// Deserialise JSON message
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(messageAsJSONstring, Message.class);
		
		message.setSourceId("192.168.10.10");
		message.setDestinationId("192.168.60.60");
		message.setTtl(message.getTtl()-1);

		Message newMessage = getNewMessage(message);
		
		messageAsJSONstring = mapper.writeValueAsString(newMessage);
		
		
		System.out.println("A router has established a connection");
		
		System.out.println("Creating Message Object...\n" + newMessage);
		System.out.println("\nReceived POST Request with JSON String:\n" + messageAsJSONstring);
		
		System.out.println("\nData :  " + message.data);
		
		
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
}