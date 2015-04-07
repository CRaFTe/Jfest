import java.util.Vector;
public class Juggler {
private String name;
private int handEye, endurance, pizzazz, currentCircuitChoiceIndex;
private Vector<String> circuitChoices;


Juggler()
{}
Juggler(String name1, int handEye1, int endurance1, int pizzazz1, Vector<String> circuitChoices1)
{
name = name1;
handEye = handEye1;
endurance = endurance1;
pizzazz = pizzazz1;
circuitChoices = new Vector<String>(circuitChoices1);
currentCircuitChoiceIndex = 0;




}
public String getName() {
	return name;
}
public int getHandEye() {
	return handEye;
}
public int getEndurance() {
	return endurance;
}
public int getPizzazz() {
	return pizzazz;
}
public int getCCCI() {
	return currentCircuitChoiceIndex;
}
public void incCCCI() {
	currentCircuitChoiceIndex++;
}
public Vector<String> getCircuitChoices() {
	return circuitChoices;
}
public void addCircuitChoice(String circuitChoice) {
	circuitChoices.add(circuitChoices.size(),circuitChoice);
}
public int insJugglerNextCC(Vector<Circuit> circuits, int jugglersPerCircuit) { 
	//will return juggler number removed if list full, OW -1
	int jugToRem = -1;
	if(this.getCCCI() >= this.getCircuitChoices().size()) {
		// if the juggler has exhausted all it's circuit choices, assign an available circuit to it's circuit choices
		//System.out.println(this.getName() + " Has exhausted all his circuit choices");
		int oldDot = 0;
		int newDot = 0;
		Circuit currCircuit = null;
			for(int i = 0; i < circuits.size();i++) {
				if(circuits.get(i).getJugglersPresent().size() < jugglersPerCircuit) {
					newDot = circuits.get(i).getDotProduct(this);
					if(newDot >= oldDot) {
						oldDot = newDot;
						currCircuit = circuits.get(i);
					}
				}		
		}
			this.addCircuitChoice(currCircuit.getName()); 
			// add the best next available circuit to the juggler's circuit choices
			this.insJugglerNextCC(circuits, jugglersPerCircuit); // insert the juggler using the new available circuit found
	}
	else {
		Circuit jugglersCCC = circuits.get(Integer.parseInt(this.getCircuitChoices()
				.get(this.getCCCI()).substring(1))); // jugglers current circuit choice
		int currDot = jugglersCCC.getDotProduct(this);
		if(jugglersCCC.getJugglersPresent().size() == 0) { // if it's an empty circuit
			jugglersCCC.insertJuggler(this.getName(),0); // insert the juggler in the first spot
			jugglersCCC.insDotProductValue(0, currDot);
		}
		
		else { // the circuit already has at least 1 juggler in it
		for(int b = 0; b < jugglersCCC.getJugglersPresent().size(); b++) {
			if(currDot > jugglersCCC.getDotProductValues().get(b)) { //find the right spot to insert it based on the dot product
				jugglersCCC.insertJuggler(this.getName(), b);
				jugglersCCC.insDotProductValue(b, currDot);
				if(jugglersCCC.getJugglersPresent().size() > jugglersPerCircuit) { // if the list is over-full after the insertion
					jugToRem = Integer.parseInt(jugglersCCC.getJugglersPresent().lastElement().substring(1));
					// find the juggler to remove (the least dot product)
					jugglersCCC.getJugglersPresent().remove(jugglersCCC.getJugglersPresent().lastElement());
					jugglersCCC.remDotProductValue();
					
				}
			break;
			}
			else if(b == jugglersCCC.getJugglersPresent().size()-1) { 
				// if the dot product is the smallest out of the circuit's jugglers present, insert the juggler at the end
				jugglersCCC.insertJuggler(this.getName(), b+1);
				jugglersCCC.insDotProductValue(b+1, currDot);
				if(jugglersCCC.getJugglersPresent().size() > jugglersPerCircuit) { 
					// again check if the insertion caused the list to be over-full
					jugToRem = Integer.parseInt(jugglersCCC.getJugglersPresent().lastElement().substring(1));
					jugglersCCC.getJugglersPresent().remove(jugglersCCC.getJugglersPresent().lastElement());
					jugglersCCC.remDotProductValue();
					// remove the juggler at the end of the list (the least dot product)	
				}
				break;
			}
		}
		}
	}
	return jugToRem;
}

}
