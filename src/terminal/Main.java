package terminal;

import java.util.Scanner;

import one.OpenNebulaNode;

import org.opennebula.client.Client;


public class Main {
	
	public static void main(String[] args) {
		
		Boolean quit = false;
		String prompt = "";
		String command ="";
		Scanner sc = new Scanner(System.in);
		
		OpenNebulaNode node = new OpenNebulaNode();
		
		System.out.println(" === Welcome in the CLI tool for ONE ===");
		
		while (!quit){
			System.out.print("> ");
			try{
				prompt = sc.nextLine();
				command = prompt;
				if (prompt.indexOf(' ') != -1){
					command = prompt.substring(0,prompt.indexOf(' '));
				}
				switch (command){
					case "connectTo":
						node.connect(prompt.substring(prompt.indexOf(' ')));
						break;
					case "version":
						node.printVersion();
						break;
					case "verify":
						// TODO En dernier, quand on aura testé le reste (il faut verifier les retours)
						break;
					case "print":
						switch(prompt.substring(prompt.indexOf(' '))) {
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
						// TODO
						break;
					case "migrate":
						// TODO						
						break;
					case "terminate":
						node.terminate(prompt.substring(prompt.indexOf(' ')));
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
		System.out.println("connectTo <adressOfNode>");
		System.out.println("version");
		//TODO complete for the commands to do
		System.out.println("exit");
		System.out.println("help");		
	}

}
