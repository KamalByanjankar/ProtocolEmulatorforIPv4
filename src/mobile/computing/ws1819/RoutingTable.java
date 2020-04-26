package mobile.computing.ws1819;

public class RoutingTable {
	
	private int metric;
	private String destinationIp;
	private String nextRouter;
	private String subnetMask;
	private String subnetIP;
	private String exit_interface;
	
	public String getExit_interface() {
		return exit_interface;
	}
	public void setExit_interface(String exit_interface) {
		this.exit_interface = exit_interface;
	}
	public String getSubnetIP() {
		return subnetIP;
	}
	public void setSubnetIP(String subnetIP) {
		this.subnetIP = subnetIP;
	}
	public int getMetric() {
		return metric;
	}
	public void setMetric(int metric) {
		this.metric = metric;
	}
	public String getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	public String getNextRouter() {
		return nextRouter;
	}
	public void setNextRouter(String nextRouter) {
		this.nextRouter = nextRouter;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	public RoutingTable() {
		super();		
	}
	public RoutingTable(String destinationIp, String subnetMask, String subnetIP, String nextRouter, int metric, String exit_interface)
	{
		super();
		this.destinationIp = destinationIp;
		this.subnetMask = subnetMask;
		this.subnetIP = subnetIP;
		this.nextRouter = nextRouter;
		this.metric = metric;
		this.exit_interface = exit_interface;
	}
}
