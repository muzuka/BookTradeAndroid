package cpsc433;

/**
 * Convenience Class used for Refactoring the getGoodness()
 * function of the Solution class. 
 * @author akmadsen
 */
public class Constraint {
	private int penalty; 
	private int count; 
	
	public Constraint(int penalty){
		this.penalty = penalty; 
		count = 0; 
	}
	
	public void addTick(){
		count++; 
	}
	
	public void addTicks(int num){
		if(num > 0){
			count += num; 
		}
	}
	
	public int getCount(){
		return count; 
	}
	
	public int getCost(){
		return (count * penalty); 
	}
	
	public int getPenalty(){
		return penalty; 
	}
	
	public void setPenalty(int penalty){
		this.penalty = penalty; 
	}
}
