package terminal;

import java.util.Scanner;

import one.OpenNebulaNode;

import org.opennebula.client.Client;


public class Main {
	
	public static void main(String[] args) {
		
		Boolean quit = false;
		String prompt = "";
		String command = "";
		String arg1 ="";
		Scanner sc = new Scanner(System.in);
		
		OpenNebulaNode node = new OpenNebulaNode();
		
		System.out.println(" === Welcome in the CLI tool for ONE ===");
		
		while (!quit){
			System.out.print("> ");
			prompt = "";
			command = "";
			arg1 = "";
			try{
				prompt = sc.nextLine();
				command = prompt;
				if (prompt.indexOf(' ') != -1){
					command = prompt.substring(0,prompt.indexOf(' '));
					arg1 = prompt.substring(prompt.indexOf(' '));
				}
				System.out.println(arg1);
				switch (command){
					case "connect":
						node.connect(arg1);
						break;
					case "version":
						node.printVersion();
						break;
					case "verify":
						// TODO En dernier, quand on aura testé le reste (il faut verifier les retours)
						break;
					case "print":
						switch(arg1) {
							case "-VMs":
								//TODO
								break;
							case "-nodes":
								//TODO
								break;
							default:
								System.out.println("Wrong arguments for print. See help");
						}
						break;
					case "suspend":
						node.suspend(arg1);
						break;
					case "migrate":
						node.migrate(null, null); //TODO					
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
			} catch (Exception e){
				
			}
		}
	}
	
	public static void showHelp(){
		System.out.println("Help :");
		System.out.println("Commands available :");
		System.out.println("connect <adressOfNode>");
		System.out.println("version");
		//TODO complete for the commands to do
		System.out.println("exit");
		System.out.println("help");		
	}

}
