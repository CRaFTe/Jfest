import java.util.Vector;
public class Circuit {
private String name;
private int handEye, endurance, pizzazz;
private Vector<String> jugglersPresent;
Circuit()
{}
Circuit(String name1, int handEye1, int endurance1, int pizzazz1, Vector<String>jugglersPresent1) {
	name = name1;
	handEye = handEye1;
	endurance = endurance1;
	pizzazz = pizzazz1;
	jugglersPresent = jugglersPresent1;
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
}
