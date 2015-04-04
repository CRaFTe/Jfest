import java.util.Vector;
public class Circuit {
private String name;
private int handEye, endurance, pizzazz;
private Vector<String> jugglersPresent;
private Vector<Integer> dotProductValues;
Circuit()
{}
Circuit(String name1, int handEye1, int endurance1, int pizzazz1, Vector<String>jugglersPresent1) {
	name = name1;
	handEye = handEye1;
	endurance = endurance1;
	pizzazz = pizzazz1;
	jugglersPresent = jugglersPresent1;
	dotProductValues = new Vector<Integer>(0);
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
public Vector<String> getJugglersPresent() {
	return jugglersPresent;
}
public void insertJuggler(String juggler, int index) {
	jugglersPresent.add(index, juggler);
}
public int getDotProduct(Juggler juggler) {
	return juggler.getEndurance() * this.getEndurance() + 
			juggler.getHandEye() * this.getHandEye() + 
			juggler.getPizzazz() * this.getPizzazz();
}
public Vector<Integer> getDotProductValues() {
	return dotProductValues;
}
public void insDotProductValue(int index, int value) {
	dotProductValues.add(index, value);
}
public void remDotProductValue() {
	dotProductValues.remove(this.dotProductValues.lastElement());
}
}
