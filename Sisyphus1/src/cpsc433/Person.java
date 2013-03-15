package cpsc433;

public class Person extends Entity {
	
	private String job = null; 
	private String group = null; 
	private String project = null; 
	private boolean isSmoker = false;
	
	public Person(){
		super("unnamed"); 
	}
	
	public void setJob(String job){
		this.job = job; 
	}
	
	public String getJob(){
		return this.job; 
	}
	
	public void setGroup(String group){
		this.group = group; 
	}
	
	public String getGroup(){
		return this.group; 
	}
	
	public void setProject(String project){
		this.project = project; 
	}
	
	public String getProject(){
		return this.project; 
	}
	
	public void setSmoker(boolean smoker){
		this.isSmoker = smoker; 
	}
	
	public boolean getSmoker(){
		return this.isSmoker; 
	}
}
