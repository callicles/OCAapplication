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
		if (oneClient == null){
			System.out.println("You need to connect to the node before trying to get the version");
		} else {
			System.out.println(oneClient.get_version().getMessage());
		}
	}
	
	public void terminate(String stringId){
		if (oneClient == null){
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else if (stringId.length()>0){
			
			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id,oneClient);
			vm.stop();
			vm.delete();
			System.out.println("VM "+stringId+" terminated");
		} else {
			System.out.println("You need to enter the id you want to terminate");
		}
	}
	
	public void suspend(String stringId) {
		if (oneClient == null){
			System.out.println("You need to connect to the node before trying to suspend any VM");
		} else if (stringId.length()>0){
			
			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id,oneClient);
			vm.suspend();
			System.out.println("VM "+stringId+" suspended");
		} else {
			System.out.println("You need to enter the id you want to suspend");
		}
	}
	
	public void migrate(String stringId, String hostStringId) {
		if (oneClient == null){
			System.out.println("You need to connect to the node before trying to migrate any VM");
		} else if (stringId.length()>0 && hostStringId.length()>0){
			
			int oldId = Integer.parseInt(stringId);
			int hostId = Integer.parseInt(hostStringId);
			VirtualMachine vm = new VirtualMachine(oldId, oneClient);
			vm.migrate(hostId);
			System.out.println("VM "+stringId+" migrated to"+ hostId);
		} else {
			System.out.println("You need to enter the id you want to migrate and the id of the host to receive the VM");
		}
	}
	
}
