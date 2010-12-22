package core.engines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Reader {
		
	String content[];
	
	Reader(String [] content){
		this.content = content;
		System.out.println("reader contructor called");
	}
	
	public void parseParagraphToKalimat(int posparagraph, int poskalimat){
		
	}
	
	public String getSummary(){
		String s = "";
		for (int ii=0;ii<content.length;ii++){
			if (content[ii]==null)
				break;
			s+=content[ii];		
		}
		return s;
	}
}
