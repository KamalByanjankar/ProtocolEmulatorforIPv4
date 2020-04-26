package mobile.computing.ws1819;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Message
{
	private int id;
	private String timestamp;
	private String text;
	public String sourceId = "192.168.10.10";
	public String destinationId = "192.168.60.60";
	public int ttl = 127;
	public String data="";
	
	public List<RoutingTable> routingTable = new ArrayList<>();


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}



	public String getTimestamp()
	{
		return timestamp;
	}




	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}




	public String getText()
	{
		return text;
	}




	public void setText(String text)
	{
		this.text = text;
	}


	public String getSourceId() {
		return sourceId;
	}




	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}




	public String getDestinationId() {
		return destinationId;
	}




	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}




	public int getTtl() {
		return ttl;
	}


	public void setTtl(int ttl) {
		this.ttl = ttl;
	}


	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	

	


	public List<RoutingTable> getRoutingTable() {
		return routingTable;
	}


	public void setRoutingTable(List<RoutingTable> routingTable) {
		this.routingTable = routingTable;
	}


	public static Message generateExampleMessage()
	{
		Random random = new Random();
		Message message = new Message();
		message.setId(random.nextInt());
		message.setTimestamp(String.valueOf(random.nextInt()));

		
		//		message.setText("MessageText");
		message.setText("[  \r\n" + 
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
				"          \"ip.ttl\": "+message.getTtl()+",\r\n" + 
				"          \"ip.checksum\": \"0x00009b44\",\r\n" + 
				"          \"ip.checksum.status\": 2,\r\n" + 
				"          \"ip.src\": "+message.getSourceId()+",\r\n" + 
				"          \"ip.dst\": "+message.getDestinationId()+",\r\n" + 
				"        },\r\n" + 
				"        \"data\": {\r\n" + 
				"          \"data.data\": "+message.data+",\r\n" + 
				"          \"data.len\": "+message.data.length()+",\r\n" + 
				"        }\r\n" + 
				"  }\r\n" + 
				"]");
		return message;
	}



	@Override
	
	public String toString()
	{
		String outputMessage = String.format("Message: [id: %s, timestamp: %s, text: %s]", id, timestamp, text);
		return outputMessage;
	}


}
