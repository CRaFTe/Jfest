//import java.util.LinkedList;
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
public int insJugglerNextCC(Vector<Circuit> circuits, int jugglersPerCircuit) { //will return juggler number removed if list full, OW -1
	int jugToRem = -1;
	if(this.getCCCI() >= this.getCircuitChoices().size()) {
		System.out.println(this.getName() + " Has exhausted all his circuit choices");
			for(int i = 0; i < circuits.size();i++) {
				if(circuits.get(i).getJugglersPresent().size() != jugglersPerCircuit) {
					this.addCircuitChoice(circuits.get(i).getName());
					break;
					
				}
				
					
		}
			this.insJugglerNextCC(circuits, jugglersPerCircuit);
	}
	else {
		Circuit jugglersCCC = circuits.get(Integer.parseInt(this.getCircuitChoices()
				.get(this.getCCCI()).substring(1))); // jugglers current circuit choice
		int currDot = jugglersCCC.getDotProduct(this);
		//System.out.println(jugglersCCC.getJugglersPresent().size() + " " + currDot);
		if(jugglersCCC.getJugglersPresent().size() == 0) {
			jugglersCCC.insertJuggler(this.getName(),0);
			jugglersCCC.insDotProductValue(0, currDot);
			//System.out.println("Inserting " + this.getName() + 
					//" to " + jugglersCCC.getName() + " Dot: " + currDot + " Index: " + "0 Size: 0");
			//System.out.println("Circuit: " + jugglersCCC.getName() + ", Jugglers Present: " + 
					//jugglersCCC.getJugglersPresent());
		}
		
		else {
			//System.out.println("Entered Else");
		for(int b = 0; b < jugglersCCC.getJugglersPresent().size(); b++) {
			//System.out.println("currDot: " + currDot + "jugglersCCC.getDotProductValues: " + jugglersCCC.getDotProductValues().get(b));
			//System.out.println("Entering second for Loop -- " + jugglersCCC.getJugglersPresent());
			if(currDot > jugglersCCC.getDotProductValues().get(b)) {
				//System.out.println("Got within the if currDot >");
				//System.out.println("Inserting " + this.getName() + 
						//" to " + jugglersCCC.getName() + " Dot: " + currDot + " Index: " + b);
				jugglersCCC.insertJuggler(this.getName(), b);
				jugglersCCC.insDotProductValue(b, currDot);
				//System.out.println("Circuit: " + jugglersCCC.getName() + ", Jugglers Present: " + 
				//jugglersCCC.getJugglersPresent());
				if(jugglersCCC.getJugglersPresent().size() > jugglersPerCircuit) {
					//System.out.println("This list is full! need to remove " + 
				//jugglersCCC.getJugglersPresent().lastElement());
					jugToRem = Integer.parseInt(jugglersCCC.getJugglersPresent().lastElement().substring(1));
					jugglersCCC.getJugglersPresent().remove(jugglersCCC.getJugglersPresent().lastElement());
					jugglersCCC.remDotProductValue();
					
				}
			
				//System.out.println("Got to before break");
				break;
			}
			else if(b == jugglersCCC.getJugglersPresent().size()-1 && currDot < jugglersCCC.getDotProductValues().get(b)) {
				//System.out.println("Inserting " + this.getName() + 
						//" to " + jugglersCCC.getName() + " Dot: " + currDot + " Index: " + (b+1));
				jugglersCCC.insertJuggler(this.getName(), b+1);
				jugglersCCC.insDotProductValue(b+1, currDot);
				if(jugglersCCC.getJugglersPresent().size() > jugglersPerCircuit) {
					//System.out.println("This list is full! need to remove " + 
				//jugglersCCC.getJugglersPresent().lastElement());
					jugToRem = Integer.parseInt(jugglersCCC.getJugglersPresent().lastElement().substring(1));
					jugglersCCC.getJugglersPresent().remove(jugglersCCC.getJugglersPresent().lastElement());
					jugglersCCC.remDotProductValue();
					
				}
				break;
			}
		}
		}
	}
	
	//System.out.println("jugToRem: " + jugToRem);
	return jugToRem;
}

}
