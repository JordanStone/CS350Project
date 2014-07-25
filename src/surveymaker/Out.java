/* 
 * Out.java
 * 
 * Console to hold a global Display variable that will output for anywhere. 
 * Includes a set method to change how output is displayed.
 * 
 */

package surveymaker;

public class Out {

	private static Display disp = new DispConsole();
	
	private Out(){
		
	}
	
	public static Display getDisp(){ //returns disp
		return disp;
	}
	
	public static void setDisp(int cond){ //sets type of disp based on conditional variable.
		switch(cond){
		case 1:
			disp = new DispConsole();
			break;
		case 2:
			disp = new DispVoice();
			break;
		}
	}
}
