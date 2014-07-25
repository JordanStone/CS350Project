/* 
 * DispVoice.java
 * Created by Jordan Stone, 2014
 * 
 * DispVoice.java holds the text to speech implementation of Display. 
 * DispVoice outputs audio taken from a string, as well as to console.
 * 
 * Subclass of Display.java.
 */

package surveymaker;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class DispVoice implements Display{
	
	private String voiceName = "kevin16";  
    private VoiceManager voiceManager = VoiceManager.getInstance();
    private Voice voice = voiceManager.getVoice(voiceName);
	
	public void render(String in){ //Outputs object value (prompt)
		System.out.print(in);
		voice.allocate();
	    voice.speak(in);
//	    voice.deallocate();
	}
	
	public void renderLine(String in){
		System.out.println(in);
		voice.allocate();
	    voice.speak(in);
//	    voice.deallocate();
	}

}
