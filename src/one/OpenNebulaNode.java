package one;


import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;

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
	
	public void resume(String stringId) {
		if (oneClient == null){
			System.out.println("You need to connect to the node before trying to resume any VM");
		} else if (stringId.length()>0){
			
			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id,oneClient);
			vm.resume();
			System.out.println("VM "+stringId+" resumed");
		} else {
			System.out.println("You need to enter the id you want to resume");
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
	
	public void listVMs() {
		VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);
		Iterator<VirtualMachine> vms = vmPool.iterator();
		VirtualMachine vm;
		while (vms.hasNext()) {
			vm = vms.next();
			String info = vm.info().getMessage();
			System.out.print("NAME : "+vm.getName()+"; ");
			System.out.print("STATUS : "+vm.stateStr()+"; ");
			
			if (vm.state() == 3) {//VM running
				String monitoring = vm.monitoring().getMessage();
				try {
					Document doc = DocumentHelper.parseText(monitoring);
					System.out.print("Hostname: "+doc.selectSingleNode("//HOSTNAME").getText());
                    System.out.print("Host ID: "+doc.selectSingleNode("//HID").getText());
                    System.out.print("CPU: "+doc.selectSingleNode("//CPU").getText()+"%");
                    System.out.println("Memory: "+doc.selectSingleNode("//MEMORY").getText()+" kB");
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void countVMs() {
		VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);
		Iterator<VirtualMachine> vms = vmPool.iterator();
		int i = 0;
		while (vms.hasNext()) {
			vms.next();
			i++;
		}
		System.out.println("There is "+i+" VMs on this host.");
	}
	
}
