package core.engines;



public class Reader {
		
	String content[];
	
	Reader(String [] content){
		this.content = content;
	}
	
	
	public void luceneSummarizer(){
		MyIndexFiles index = new MyIndexFiles(content);
		index.init();
		//index.createIndex();
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
