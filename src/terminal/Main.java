package terminal;

import java.util.Scanner;

import one.OpenNebulaNode;

import org.opennebula.client.Client;


public class Main {
	
	public static void main(String[] args) {
		
		Boolean quit = false;
		String prompt = "";
		Scanner sc = new Scanner(System.in);
		
		OpenNebulaNode node = new OpenNebulaNode();
		
		System.out.println(" === Welcome in the CLI tool for ONE ===");
		
		while (!quit){
			System.out.print("> ");
			try{
				prompt = sc.nextLine();
				switch (prompt.substring(0,prompt.indexOf(' '))){
					case "connectTo":
						node.connect(prompt.substring(prompt.indexOf(' ')));
						break;
					case "version":
						node.printVersion();
						break;
					case "verify":
					
						break;
					case "print":
						
						break;
					case "suspend":
						
						break;
					case "migrate":
						
						break;
					case "terminate":
						node.terminate(prompt.substring(prompt.indexOf(' ')));
						break;
					case "exit":
						quit = true;
						break;
					default:
						System.out.println("That command is not handled by the system");
						break;
				}
			} catch (Exception e){
				
			}
		}
	}

}
