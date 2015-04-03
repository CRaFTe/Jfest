import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ReadStringFromFileLineByLine {

	public static void main(String[] args) {
		Vector<Circuit> circuits = new Vector<Circuit>(0);
		Vector<Juggler> jugglers = new Vector<Juggler>(0);
		try {
			File file = new File("juggleInputTest2.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.length() > 0) {
					String words[] = line.split(" ");

					if (words[0].equals("C")) {
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
					else if (words[0].equals("J")) {
						String HEJ[] = words[2].split(":"); // Hand-Eye Juggler
						String ENJ[] = words[3].split(":"); // Endurance Juggler
						String PIJ[] = words[4].split(":"); // Pizzazz Juggler
						String CCJ[] = words[5].split(","); // Circuit Choices Juggler
						int handEyeJuggler = Integer.parseInt(HEJ[1]);
						int enduranceJuggler = Integer.parseInt(ENJ[1]);
						int pizzazzJuggler = Integer.parseInt(PIJ[1]);
						Vector<String> circuitChoicesJuggler = new Vector<String>(0);
						for(int j = 0; j < CCJ.length;j++) {
							circuitChoicesJuggler.add(CCJ[j]);
							//System.out.println("Inserting" + CCJ[j]);
						}
						Juggler J = new Juggler(words[1],handEyeJuggler, enduranceJuggler, 
								pizzazzJuggler, circuitChoicesJuggler);
						jugglers.add(J);
					}
		
					

					
					
				//----- Commented out for initial line reading testing -------	
				//System.out.println(line.substring(0,1));
					//for(int i = 1; i <= line.length();i++) {
						//System.out.printf(line.substring(i-1,i));
					//}
				/*if(line.substring(0, 1).equals("C")) {
					System.out.printf("\nThis is a circuit: ");
					String words[] = line.split(" ");
					for(int i = 0; i < words.length; i++) {
						System.out.printf(words[i] + " ");
					}
				}
				if(line.substring(0, 1).equals("J")) {
					System.out.printf("\nThis is a juggler: ");
					String words[] = line.split(" ");
					for(int i = 0; i < words.length; i++) {
						System.out.printf(words[i] + " ");
					}
				}*/
				}
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			//System.out.println("Contents of file:");
			//System.out.println(stringBuffer.toString());
			
			// Print info and file Contents
			int jugglersPerCircuit = jugglers.size() / circuits.size();
			System.out.printf("Jugglers Per Circuit: %d\n", jugglersPerCircuit);
			for(int i = 0; i < circuits.size();i++){
				System.out.printf("%s, %d, %d, %d\n", circuits.get(i).getName(), 
						circuits.get(i).getHandEye(), circuits.get(i).getEndurance(), 
						circuits.get(i).getPizzazz());
			}
			for(int j = 0; j < jugglers.size();j++) {
				String CircuitChoiceString = null;
				for(int k = 0; k < jugglers.get(j).getCircuitChoices().size(); k ++) {
					if(k == 0) CircuitChoiceString = jugglers.get(j).getCircuitChoices().get(k);
					else CircuitChoiceString += "," + jugglers.get(j).getCircuitChoices().get(k);
				}
				System.out.printf("%s, %d, %d, %d, %s\n", jugglers.get(j).getName(), 
						jugglers.get(j).getHandEye(), jugglers.get(j).getEndurance(), 
						jugglers.get(j).getPizzazz(), CircuitChoiceString);
			}
			//
			//
			for(int a = 0; a < jugglers.size(); a++) {
				Circuit jugglersCCC = circuits.get(Integer.parseInt(jugglers.get(a).getCircuitChoices()
						.get(jugglers.get(a).getCCCI()).substring(1))); // jugglers current circuit choice
				int currDot = jugglersCCC.getDotProduct(jugglers.get(a));
				//System.out.println(jugglersCCC.getJugglersPresent().size() + " " + currDot);
				if(jugglersCCC.getJugglersPresent().size() == 0) {
					jugglersCCC.insertJuggler(jugglers.get(a).getName(),0);
					System.out.println("Inserting " + jugglers.get(a).getName() + 
							" to " + jugglersCCC.getName() + " " + currDot + " " + "0 Size 0");
				}
				
				else {
				for(int b = 0; b < jugglersCCC.getJugglersPresent().size(); b++) {
					//System.out.println("Entering second for Loop -- " + jugglersCCC.getJugglersPresent());
					if(currDot > jugglersCCC.getDotProduct(jugglers
							.get(Integer.parseInt(jugglersCCC.getJugglersPresent()
									.get(b).substring(1))))) {
						System.out.println("Inserting " + jugglers.get(a).getName() + 
								" to " + jugglersCCC.getName() + " " + currDot + " " + b);
						jugglersCCC.insertJuggler(jugglers.get(a).getName(), b);
						System.out.println("Circuit: " + jugglersCCC.getName() + " Jugglers Present: " + 
						jugglersCCC.getJugglersPresent());
						if(jugglersCCC.getJugglersPresent().size() > jugglersPerCircuit) 
							System.out.println("This list is full! need to remove " + 
						jugglersCCC.getJugglersPresent().lastElement());
						break;
					}
				}
				}
				//System.out.println(circuits.get(0).getJugglersPresent().get(0));
			}
			//System.out.printf("Jugglers Per Circuit: %d\n", jugglersPerCircuit);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//	static int getDotProduct(Juggler juggler, Circuit circuit) {
//		return juggler.getEndurance() * circuit.getEndurance() + 
//				juggler.getHandEye() * circuit.getHandEye() + 
//				juggler.getPizzazz() * circuit.getPizzazz();
//	}
}