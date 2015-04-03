//import java.util.LinkedList;
import java.util.Vector;
public class Juggler {
private String name;
private int handEye, endurance, pizzazz, currentCircuitChoiceIndex;
private Vector<String> circuitChoices;
private Vector<Integer> dotProductValues;

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
dotProductValues = new Vector<Integer>(0);



}
String getName() {
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
public Vector<Integer> getDotProductValues() {
	return dotProductValues;
}
public void insDotProductValue(int index, int value) {
	dotProductValues.add(index, value);
}
public void remDotProductValue(int index) {
	dotProductValues.remove(index);
}

}
