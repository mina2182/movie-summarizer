package core.engines;

import org.apache.lucene.search.Query;



public class Reader {
		
	String content[];
	
	Reader(String [] content){
		this.content = content;
	}
	
	
	public String luceneSummarizer(){
		MyIndexFiles index = new MyIndexFiles(content);
		index.init();
		index.index();
		Query q = index.computeTopTermQuery();
		return index.searchIndex(q);
	}
	
	public String mySummarizer(){
		MyIndexFiles index = new MyIndexFiles(content);
		index.init();
		index.index();
		index.computeTopTermQuery();
		return index.mySearch();
	}
	
	public String getSinopsis(){
		String s = "";
		for (int ii=0;ii<content.length;ii++){
			if (content[ii]==null)
				break;
			s+=content[ii];		
		}
		return s;
	}
}
