/* 
 * Display.java
 * Created by Jordan Stone, 2014
 * 
 * Display.java holds the Display interface. Display is used for outputting information in questions in a given format.
 * Implementations for Display to Console and Voice both currently exist.
 * 
 * Subclasses include DispConsole and DispVoice.
 */

package surveymaker;

public interface Display{ //Display interface used for all Display classes.

	abstract public void render(String in); //Display output (Console outputs string to console, etc.)
	
	abstract public void renderLine(String in); //Display output plus a newline
	
}
