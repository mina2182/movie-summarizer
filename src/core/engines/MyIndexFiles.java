package core.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;



public class MyIndexFiles {
	Directory ramdir;
	StandardAnalyzer analyzer;
	String [] content;
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	
	
	public MyIndexFiles(String [] content){
		this.content = content;
	}
	
	public void ParseParagraph(){
		list.clear();
		for (int ii = 0 ;ii< content.length; ii++){
			if (content[ii] == null)
				break;
			list.add(ParseSentence(content[ii]));
		}
	}
	
	public ArrayList<String> ParseSentence(String s ){
		ArrayList<String> temp = new ArrayList<String>();
		temp.clear();
		StringTokenizer token = new StringTokenizer(s," ");
		while (token.hasMoreTokens()){
			temp.add(token.nextToken());
		}
		return temp;
	}
	
	public void print(){
		for (int ii=0 ; ii<list.size(); ii ++){
			ArrayList<String> temp = list.get(ii);
			for (int jj=0; jj<temp.size();jj++){
				System.out.println(temp.get(jj));
			}
		}
	}
	
	public void init(){
		ParseParagraph();
		print();
	}
	
	@SuppressWarnings("deprecation")
	public void index(){
		try {
			IndexWriter writer = new IndexWriter(ramdir, new StandardAnalyzer(Version.LUCENE_CURRENT), true, IndexWriter.MaxFieldLength.UNLIMITED);
			
			writer.optimize();
			writer.commit();
			writer.close();
			 
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
