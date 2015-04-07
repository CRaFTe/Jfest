/*
 * Created by Micah T. Moore to solve the Jugglefest problem presented by Yodle.
 * Description: Inserts jugglers into circuits based on their preference and 
 * uses their dot product to determine how good of a fit they are for that circuit.  
 * If the issue arises that a juggler is trying to be inserted into an already full circuit,
 * the juggler will still be inserted in the appropriate dot product descending order
 * and the juggler with the lowest dot product (at the end of the list) will be removed
 * and found a new circuit, based on their next preference.  If a juggler has exhausted
 * all of his circuit choice preferences and still hasn't found a suitable circuit, it
 * is found a best fit (the highest dot product) circuit from the non-full circuits left.
 * This continues until all jugglers have been assigned to circuits.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class ReadStringFromFileLineByLine {

	public static void main(String[] args) {
		Vector<Circuit> circuits = new Vector<Circuit>(0); // holds all the circuits
		Vector<Juggler> jugglers = new Vector<Juggler>(0); // holds all the jugglers
		try {
			File file = new File("jugglefest.txt"); // what input file to read from
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.length() > 0) {
					String words[] = line.split(" "); // split the line by spaces

					if (words[0].equals("C")) { // if it's a circuit line
						String HEC[] = words[2].split(":"); // Hand-Eye Cicuit
						String ENC[] = words[3].split(":"); // Endurance Circuit
						String PIC[] = words[4].split(":"); // Pizzazz Circuit
						int handEyeCircuit = Integer.parseInt(HEC[1]);
						int enduranceCircuit = Integer.parseInt(ENC[1]);
						int pizzazzCircuit = Integer.parseInt(PIC[1]);
						Vector<String> jugglersPresent = new Vector<String>(0);
						Circuit C = new Circuit(words[1], handEyeCircuit, enduranceCircuit, 
								pizzazzCircuit, jugglersPresent);
						circuits.add(C);
					}
					else if (words[0].equals("J")) { // if it's a juggler line
						String HEJ[] = words[2].split(":"); // Hand-Eye Juggler
						String ENJ[] = words[3].split(":"); // Endurance Juggler
						String PIJ[] = words[4].split(":"); // Pizzazz Juggler
						String CCJ[] = words[5].split(","); // Circuit Choices Juggler
						int handEyeJuggler = Integer.parseInt(HEJ[1]);
						int enduranceJuggler = Integer.parseInt(ENJ[1]);
						int pizzazzJuggler = Integer.parseInt(PIJ[1]);
						Vector<String> circuitChoicesJuggler = new Vector<String>(0);
						for(int j = 0; j < CCJ.length;j++) {
							circuitChoicesJuggler.add(CCJ[j]); // create the circuit choices vector string
						}
						Juggler J = new Juggler(words[1],handEyeJuggler, enduranceJuggler, 
								pizzazzJuggler, circuitChoicesJuggler);
						jugglers.add(J);
					}
		
					
				}
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			PrintWriter writer = new PrintWriter("jugglefestOutput.txt","UTF-8");
			int jugglersPerCircuit = jugglers.size() / circuits.size();
			int exJug = -1; // holds the return from inserting a juggler, initialized to -1
			for(int a = 0; a < jugglers.size(); a++) {
				 exJug = jugglers.get(a).insJugglerNextCC(circuits, jugglersPerCircuit);
				 // exJug will hold the juggler number to remove if a juggler was inserted in a full circuit
					if( exJug == -1) {
						// if a juggler wasn't inserted into a full list, insJuggler will return -1 to exJug
						// and we can continue inserting the next juggler
					}
					
				else { // exJug != -1, there is a juggler to remove
					while(exJug != -1){ // while inserts keep happening on full circuit lists
						jugglers.get(exJug).incCCCI(); // increment the circuit choice of the juggler
						exJug = jugglers.get(exJug).insJugglerNextCC(circuits, jugglersPerCircuit);
						// try to insert the juggler in it's next circuit choice
					}

				}
			}			
			// Generate the output file
			//
			//
			for(int m = 0; m < circuits.size(); m++) {
				writer.printf(circuits.get(m).getName() + " ");
				for(int n = 0; n < circuits.get(m).getJugglersPresent().size(); n++) {
					writer.printf(circuits.get(m).getJugglersPresent().get(n) + " ");
					for(int p = 0; p < Math.min(jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n).substring(1)))
							.getCircuitChoices().size(),10); p++) {
						writer.print(circuits.get(Integer.parseInt(jugglers.get(Integer.parseInt(circuits.get(m)
								.getJugglersPresent().get(n).substring(1))).getCircuitChoices().get(p)
								.substring(1))).getName() + ":");
						writer.print(circuits.get(Integer.parseInt(jugglers.get(Integer.parseInt(circuits.get(m)
								.getJugglersPresent().get(n).substring(1))).getCircuitChoices().get(p).substring(1)))
								.getDotProduct(jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n)
										.substring(1)))));
						if(p!= Math.min((jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n).substring(1))).getCircuitChoices().size() - 1), 9))
							writer.print(" ");
					}
				writer.printf(", ");
				}
				writer.printf("%n");
			}
			// printing to console C1970's Jugglers Present Numbers Total
			if(circuits.size() >= 1970) {
				int c1970Total = 0;
				for(int q = 0; q < circuits.get(1970).getJugglersPresent().size(); q++) {
					c1970Total+= Integer.parseInt(circuits.get(1970).getJugglersPresent().get(q).substring(1));
				}
				System.out.println("Circuit 1970 Juggler's Present Numbers Total: " + c1970Total);
			}
		
			
		
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}