package terminal;

import java.util.Scanner;

import one.OpenNebulaNode;

import org.opennebula.client.Client;

public class Main {

	public static void main(String[] args) {

		Boolean quit = false;
		String prompt = "";
		String command = "";
		String arg1, arg2 = "";
		String[] commands = null;
		Scanner sc = new Scanner(System.in);

		OpenNebulaNode node = new OpenNebulaNode();

		System.out.println(" === Welcome in the CLI tool for ONE ===");
		showHelp();

		while (!quit) {
			System.out.print("> ");
			prompt = "";
			command = "";
			arg1 = "";
			arg2 = "";
			try {
				prompt = sc.nextLine();
				command = prompt;
				if (prompt.indexOf(' ') != -1) {
					commands = prompt.split(" ");
					command = commands[0];
					if (commands.length > 1) {
						arg1 = commands[1];
						if (commands.length > 2) {
							arg2 = commands[2];
						}
					}
				}
				switch (command) {
				case "connect":
					node.connect(arg1);
					break;
				case "version":
					node.printVersion();
					break;
				case "verify":
					node.verifyVersion();
					break;
				case "print":
					switch (arg1) {
					case "-VMs":
						node.countVMs();
						node.listVMs();
						break;
					case "-nodes":
						node.countNodes();
						node.listNodes();
						break;
					default:
						System.out.println("Wrong arguments for print. See help");
					}
					break;
				case "suspend":
					node.suspend(arg1);
					break;
				case "resume":
					node.resume(arg1);
					break;
				case "migrate":
					node.migrate(arg1, arg2);
					break;
				case "terminate":
					node.terminate(arg1);
					break;
				case "exit":
					quit = true;
					break;
				case "help":
					showHelp();
					break;
				default:
					System.out.println("That command is not handled by the system");
					break;
				}
			} catch (Exception e) {

			}
		}
	}

	public static void showHelp() {
		System.out.println("Commands available :");
		System.out.println("connect <adressOfNode>");
		System.out.println("version");
		System.out.println("print -VMs");
		System.out.println("print -nodes");
		System.out.println("suspend <VM ID>");
		System.out.println("resume <VM ID>");
		System.out.println("terminate <VM ID>");
		System.out.println("migrate <VM ID> <host ID>");
		System.out.println("exit");
		System.out.println("help");
	}

}
