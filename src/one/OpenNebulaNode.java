package one;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.opennebula.client.Client;
import org.opennebula.client.ClientConfigurationException;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.opennebula.client.host.HostPool;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;

import terminal.User;

public class OpenNebulaNode {

	private Client oneClient;
	
	private static String API_VERSION = "4.2.0";

	public OpenNebulaNode() {
	}

	public void connect(String adress) {
		if (adress.length() > 5) {
			try {
				oneClient = new Client(User.ID + ":" + User.PASSWORD, adress);
			} catch (ClientConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void printVersion() {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to get the version");
		} else {
			System.out.println(oneClient.get_version().getMessage());
		}
	}

	public void terminate(String stringId) {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else if (stringId.length() > 0) {

			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id, oneClient);
			OneResponse r = vm.delete();
			if (r.isError()) System.out.println("Error: "+r.getErrorMessage());
			else System.out.println("VM " + stringId + " terminated");
		} else {
			System.out.println("You need to enter the id you want to terminate");
		}
	}

	public void suspend(String stringId) {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to suspend any VM");
		} else if (stringId.length() > 0) {

			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id, oneClient);
			OneResponse r = vm.suspend();
			if (r.isError()) System.out.println("Error: "+r.getErrorMessage());
			else System.out.println("VM " + stringId + " suspended");
		} else {
			System.out.println("You need to enter the id you want to suspend");
		}
	}

	public void resume(String stringId) {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to resume any VM");
		} else if (stringId.length() > 0) {

			int id = Integer.parseInt(stringId);
			VirtualMachine vm = new VirtualMachine(id, oneClient);
			OneResponse r = vm.resume();
			if (r.isError()) System.out.println("Error: "+r.getErrorMessage());
			else System.out.println("VM " + stringId + " resumed");
		} else {
			System.out.println("You need to enter the id you want to resume");
		}
	}

	public void migrate(String stringId, String hostStringId) {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to migrate any VM");
		} else if (stringId.length() > 0 && hostStringId.length() > 0) {

			int oldId = Integer.parseInt(stringId);
			int hostId = Integer.parseInt(hostStringId);
			VirtualMachine vm = new VirtualMachine(oldId, oneClient);
			OneResponse r = vm.migrate(hostId);
			if (r.isError()) System.out.println("Error: "+r.getErrorMessage());
			else System.out.println("VM " + stringId + " migrated to" + hostId);
		} else {
			System.out.println("You need to enter the id you want to migrate and the id of the host to receive the VM");
		}
	}

	public void listVMs() {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else {
			VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);
			Iterator<VirtualMachine> vms = vmPool.iterator();
			VirtualMachine vm;
			while (vms.hasNext()) {
				vm = vms.next();
				String info = vm.info().getMessage();
				System.out.print("NAME : " + vm.getName() + "; ");
				System.out.print("STATUS : " + vm.stateStr() + "; ");

				if (vm.state() == 3) {// VM running
					String monitoring = vm.monitoring().getMessage();
					try {
						Document doc = DocumentHelper.parseText(monitoring);
						System.out.print("Hostname: "+ doc.selectSingleNode("//HOSTNAME").getText());
						System.out.print("Host ID: "+ doc.selectSingleNode("//HID").getText());
						System.out.print("CPU: "+ doc.selectSingleNode("//CPU").getText() + "%");
						System.out.println("Memory: "+ doc.selectSingleNode("//MEMORY").getText()+ " kB");
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void countVMs() {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else {
			VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);
			int i = vmPool.getLength();
			System.out.println("There are " + i + " VMs on this host.");
		}
	}

	public void listNodes() {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else {
			HostPool pool = new HostPool(oneClient);
			Iterator<Host> hosts = pool.iterator();
			while (hosts.hasNext()) {
				Host host = hosts.next();
				System.out.print("Name: " + host.getName());
				String monitoring = host.monitoring().getMessage();
				try {
					Document document = DocumentHelper.parseText(monitoring);
					System.out.print("Hypervisor: "+document.selectSingleNode("//VM_MAD").getText());
					System.out.print("Proc Capacity: "+ document.selectSingleNode("//MAX_CPU").getText());
					System.out.print("Proc Used: "+ document.selectSingleNode("//USED_CPU").getText());
					System.out.print("Proc Free: "+ document.selectSingleNode("//FREE_CPU").getText());
					System.out.print("Memory Used: "+ document.selectSingleNode("//USED_MEM").getText());
					System.out.println("Memory Free: "+ document.selectSingleNode("//FREE_MEM").getText());
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void countNodes() {
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else {
			HostPool pool = new HostPool(oneClient);
			System.out.println("There are " + pool.getLength() + " nodes");
		}
	}
	
	public void verifyVersion(){
		if (oneClient == null) {
			System.out.println("You need to connect to the node before trying to terminate any VM");
		} else {
			System.out.println("API version :         "+API_VERSION);
			System.out.println("Open Nebula Version : "+oneClient.get_version().getMessage());
		}
	}

}
