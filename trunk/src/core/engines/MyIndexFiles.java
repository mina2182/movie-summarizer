package core.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;



public class MyIndexFiles {
	Directory ramdir;
	StandardAnalyzer analyzer;
	String [] content;
	
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	
	
	public MyIndexFiles(String [] content){
		ramdir = new RAMDirectory();
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
		s = s.replaceAll("&amp;", "&");
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&rsquo;","\'");
		String spliting[] = s.split(" |<p>|</p>|&nbsp;|[.,]");
		for (int jj = 0 ; jj< spliting.length; jj++){
			if (!spliting[jj].equals(""))temp.add(spliting[jj]);
		}
		return temp;
	}
	
	public void print(){
		for (int ii=0 ; ii<list.size(); ii ++){
			ArrayList<String> temp = list.get(ii);
			for (int jj=0; jj<temp.size();jj++){
				System.out.println(temp.get(jj));
			}
			System.out.println("---------------");
		}
	}
	
	public void init(){
		ParseParagraph();
		print();
	}
	
	public float calcBoost(int posparagraph, int possentence){
		float score = 0.5F;
		return score;
	}
	
	@SuppressWarnings("deprecation")
	public void index(){
		try {
			IndexWriter writer = new IndexWriter(ramdir, new StandardAnalyzer(Version.LUCENE_CURRENT), true, IndexWriter.MaxFieldLength.UNLIMITED);
			for (int ii = 0; ii< list.size(); ii++){
				ArrayList<String> temp = list.get(ii);
				for (int jj = 0; jj < temp.size(); jj++){
					temp.get(jj);
					Document doc = new Document();
					doc.add(new Field("text",temp.get(jj), Store.YES, Index.ANALYZED));
					doc.setBoost(calcBoost(ii,jj));
					writer.addDocument(doc);
				}
			}
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
	
	public Query computeTopTermQuery(){
		try {
			final Map<String,Integer> frequencyMap = new HashMap<String,Integer>();
			ArrayList<String> termlist = new ArrayList<String>();
			IndexReader reader = IndexReader.open(ramdir);
			TermEnum terms = reader.terms();
			while (terms.next()){
				Term term = terms.term();
				String termtext = term.text();
				int freq = reader.docFreq(term);
				frequencyMap.put(termtext, freq);
				termlist.add(termtext);
			}
			reader.close();
			
			StringBuilder termbuf = new StringBuilder();
			BooleanQuery q = new BooleanQuery();
			for (String topterm : termlist){
				termbuf.append(topterm).append("(").append(frequencyMap.get(topterm)).append(")");
				q.add(new TermQuery(new Term("text",topterm)),Occur.SHOULD);
			}
			System.out.println(">>> top term : " + termbuf.toString());
			System.out.println(">>> query : " + q.toString());
			return q;
			
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
