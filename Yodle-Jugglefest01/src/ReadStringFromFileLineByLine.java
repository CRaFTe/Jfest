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
			File file = new File("juggleInputTest2.txt"); // what input file to read from
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
			PrintWriter writer = new PrintWriter("juggleFestOutput.txt","UTF-8");
			int jugglersPerCircuit = jugglers.size() / circuits.size();
			
			// Print info and file Contents
			// un-comment these lines to see the juggler and circuit info

//			writer.printf("Jugglers Per Circuit: %d%n", jugglersPerCircuit);
//			for(int i = 0; i < circuits.size();i++){
//				writer.printf("%s, %d, %d, %d%n", circuits.get(i).getName(), 
//						circuits.get(i).getHandEye(), circuits.get(i).getEndurance(), 
//						circuits.get(i).getPizzazz());
//			}
//			for(int j = 0; j < jugglers.size();j++) {
//				String CircuitChoiceString = null;
//				for(int k = 0; k < jugglers.get(j).getCircuitChoices().size(); k++) {
//					if(k == 0) CircuitChoiceString = jugglers.get(j).getCircuitChoices().get(k);
//					else CircuitChoiceString += "," + jugglers.get(j).getCircuitChoices().get(k);
//				}
//				writer.printf("%s, %d, %d, %d, %s%n", jugglers.get(j).getName(), 
//						jugglers.get(j).getHandEye(), jugglers.get(j).getEndurance(), 
//						jugglers.get(j).getPizzazz(), CircuitChoiceString);
//			}

			int exJug = -1; // holds the return from inserting a juggler, initialized to -1
			for(int a = 0; a < jugglers.size(); a++) {
				 exJug = jugglers.get(a).insJugglerNextCC(circuits, jugglersPerCircuit);
				 // exJug will hold the juggler number to remove if a juggler was inserted in a full circuit
					if( exJug == -1) {
						// if a juggler wasn't inserted into a full list, insJuggler will return -1 to exJug
						// and we can continue inserting the next juggler
						//System.out.println("We're all groovy --- Keep going");
					}
					
				else { // exJug != -1, there is a juggler to remove
					while(exJug != -1){ // while inserts keep happening on full circuit lists
						jugglers.get(exJug).incCCCI(); // increment the circuit choice of the juggler
						exJug = jugglers.get(exJug).insJugglerNextCC(circuits, jugglersPerCircuit);
						// try to insert the juggler in it's next cicuit choice
					}

				}
			}
			// what each circuit holds after all insertions are complete:
			// un-Comment to see the jugglers present in each circuit
//			int totalJugglersInCircuits = 0;
//			for(int d = 0; d < circuits.size();d++) {
//				writer.println("Circuit: " + circuits.get(d).getName() + " | " + circuits.get(d).getJugglersPresent());
//				totalJugglersInCircuits+= circuits.get(d).getJugglersPresent().size();
//			}
//			writer.println("Total Jugglers in Circuits: " + totalJugglersInCircuits);
			
			
			
			// Generate the output file
			//
			//
			//writer.println("---------------------------");
			for(int m = 0; m < circuits.size(); m++) {
				writer.printf(circuits.get(m).getName() + " ");
				for(int n = 0; n < circuits.get(m).getJugglersPresent().size(); n++) {
					writer.printf(circuits.get(m).getJugglersPresent().get(n) + " ");
					for(int p = 0; p < jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n).substring(1)))
							.getCircuitChoices().size(); p++) {
						writer.print(circuits.get(Integer.parseInt(jugglers.get(Integer.parseInt(circuits.get(m)
								.getJugglersPresent().get(n).substring(1))).getCircuitChoices().get(p)
								.substring(1))).getName() + ":");
						writer.print(circuits.get(Integer.parseInt(jugglers.get(Integer.parseInt(circuits.get(m)
								.getJugglersPresent().get(n).substring(1))).getCircuitChoices().get(p).substring(1)))
								.getDotProduct(jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n)
										.substring(1)))));
						if(p!= jugglers.get(Integer.parseInt(circuits.get(m).getJugglersPresent().get(n).substring(1))).getCircuitChoices().size() - 1)
							writer.print(" ");
					}
				writer.printf(", ");
				}
				writer.printf("%n");
			}
			// printing to console C1970's Jugglers Present Numbers Total
			// remove this if running on the smaller input file
			int c1970Total = 0;
			for(int q = 0; q < circuits.get(1970).getJugglersPresent().size(); q++) {
				c1970Total+= Integer.parseInt(circuits.get(1970).getJugglersPresent().get(q).substring(1));
			}
			System.out.println("Circuit 1970 Juggler's Present Numbers Total: " + c1970Total);
			
		
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}