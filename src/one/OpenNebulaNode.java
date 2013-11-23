package one;

import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.vm.VirtualMachine;

import terminal.User;

public class OpenNebulaNode {

	private Client oneClient;
	
	public OpenNebulaNode(){
	}
	
	public void connect(String adress){
		if (adress.length()>5){
			try {
				oneClient = new Client(User.ID+":"+User.PASSWORD,adress);
			} catch (ClientConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void printVersion() {
		if (oneClient.equals(null)){
			System.out.println("You need to connect to the node before trying to get the version");
		} else {
			System.out.println(oneClient.get_version());
		}
	}
	
	public void terminate(String stringId){
		if (oneClient.equals(null)){
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else if (stringId.length()>0){
			
			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id,oneClient);
			vm.stop();
			
			System.out.println("VM "+stringId+" terminated");
		} else {
			System.out.println("You need to enter the id you want to terminate");
		}
	}
	
	
}
