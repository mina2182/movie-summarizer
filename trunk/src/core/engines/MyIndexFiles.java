package core.engines;
/**
 * inspired by http://sujitpal.blogspot.com/2009/02/summarization-with-lucene.html
 * basic lucene connector to summary feature
 * the heuristic method according to zipf law, and logic
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;


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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;



public class MyIndexFiles {
	Directory ramdir;
	StandardAnalyzer analyzer;
	String [] content;
	int numSentence = 0;
	ArrayList<String> topTerms;
	Map<String,Integer> frequencyMap;
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	
	public void addNumSentence(int numSentence){
		this.numSentence += numSentence;
	}
	
	public MyIndexFiles(String [] content){
		ramdir = new RAMDirectory();
		this.content = content;
	}
	
	public void ParseParagraph(){
		list.clear();
		for (int ii = 0 ;ii< content.length; ii++){
			if (content[ii] == null){
				break;
			}
			list.add(ParseSentence(content[ii]));
		}
		
	}
	
	public ArrayList<String> ParseSentence(String s ){
		ArrayList<String> temp = new ArrayList<String>();
		temp.clear();
		addNumSentence(15);
		s = s.replaceAll("&amp;", "&");
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&rsquo;","\'");
		String spliting[] = s.split(" |<p>|</p>|&nbsp;|<a (.*)>|</a>");
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
		//print();
	}
	
	public float calcBoost(int posparagraph, int possentence){
		float score = 0.0F;
		if (posparagraph < 5) score = 0.55F;
		else if (posparagraph < 10) score = 0.20F;
		else if (posparagraph < 15) score = 0.15F;
		else score = 0.10F;
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
			frequencyMap = new HashMap<String,Integer>();
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
			ArrayList<Integer> listint = new ArrayList<Integer>(frequencyMap.values());
			ArrayList<String> liststring = new ArrayList<String>(frequencyMap.keySet());
			PriorityQueue<Integer> sorted = new PriorityQueue<Integer>();
			for (int ii =0; ii<listint.size();ii++){
				if (listint.get(ii)>1)
					sorted.add(listint.get(ii));
			}
			System.out.println(sorted.size());
			ArrayList<String> asc = new ArrayList<String>();
			topTerms = new ArrayList<String>();
			
			while (!sorted.isEmpty()){
				asc.add(liststring.get(listint.indexOf(sorted.peek())));
				System.out.println(sorted.peek()+ " " + liststring.get(listint.indexOf(sorted.peek())));
				int id = listint.indexOf(sorted.poll());
				listint.remove(id);
				listint.add(id, 0);
			}
			for (int ii =asc.size()-1 ; ii >= 0; ii--){
				topTerms.add(asc.get(ii));
			}
			
			StringBuilder termbuf = new StringBuilder();
			BooleanQuery q = new BooleanQuery();
			BooleanQuery.setMaxClauseCount(10000);
			for (String topterm : topTerms){
				if (topterm.equals("he")||topterm.equals("she")||topterm.equals("his")||topterm.equals("her"))
				{
					frequencyMap.remove(topterm);
					frequencyMap.put(topterm, 5);
				}
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
	
	public String searchIndex(Query q){
		try {
			SortedMap<Integer,String> sentenceMap = new TreeMap<Integer,String>();
			IndexSearcher searcher = new IndexSearcher(ramdir);
			TopDocs topdocs = searcher.search(q,numSentence);
			for (ScoreDoc scoreDoc : topdocs.scoreDocs) {
				int docId = scoreDoc.doc;
				Document doc = searcher.doc(docId);
				sentenceMap.put(scoreDoc.doc, doc.get("text"));
			}
			searcher.close();
			ArrayList<String> hasil = new ArrayList<String>(sentenceMap.values());
			String s ="";
			for (int ii =0 ; ii< hasil.size(); ii++){
				s += hasil.get(ii)+" ";
			}
			return s;
			//System.out.println(sentenceMap.values().toString());
			//System.out.println(sentenceMap.keySet().toString());
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String bobotSearch(){
		final int NUM_SUMMARY = 15;
		int id = 0;

		PriorityQueue<Double> pq = new PriorityQueue<Double>();
		TreeMap<Integer,String> mapkata = new TreeMap<Integer,String>();
		TreeMap<Integer,Double> mapval = new TreeMap<Integer,Double>();
		for (int ii = 0 ;ii<content.length;ii++ ){
			String temp = content[ii];
			if (temp == null) break;
			else{
				temp = temp.replaceAll("&amp;","&");
				temp = temp.replaceAll("&quot;","\"");
				temp = temp.replaceAll("&rsquo;","\'");
				temp = temp.replaceAll("<p>|</p>","");
				temp = temp.replaceAll("<a (.*)>|</a>","");
				
				String [] split = temp.split("&nbsp;|[. ]{2}");
				
				for (int jj=0; jj<split.length; jj++){
					int val = 0;
					
					String [] kata = split[jj].split("[ ,.()]");
					for (int kk =0 ; kk<kata.length; kk++){
						if (!kata[kk].equals("")){
							String s = kata[kk];
							if (frequencyMap.containsKey(s))
								val += frequencyMap.get(s.toLowerCase());
						}
					}
					
					mapkata.put(id, split[jj]);
					double bobot = (double) val + (double) jj*10/ (double) ii; 
					mapval.put(id, bobot);
					pq.add(bobot);
					id++;
				}
			}
		}
		
		while (pq.size()>NUM_SUMMARY){
			System.out.println(pq.poll());
		}
		String hasil = "";
		for (int ii =0 ; ii<id; ii++){
			double pos = mapval.get(ii);
			if (pq.contains(pos))
				hasil += mapkata.get(ii);
		}
		return hasil;
	}
	
	public String mySearch(){
		final int NUM_SUMMARY = 15;
		int id = 0;

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		TreeMap<Integer,String> mapkata = new TreeMap<Integer,String>();
		TreeMap<Integer,Integer> mapval = new TreeMap<Integer,Integer>();
		for (int ii = 0 ;ii<content.length;ii++ ){
			String temp = content[ii];
			if (temp == null) break;
			else{
				temp = temp.replaceAll("&amp;","&");
				temp = temp.replaceAll("&quot;","\"");
				temp = temp.replaceAll("&rsquo;","\'");
				temp = temp.replaceAll("<p>|</p>","");
				temp = temp.replaceAll("<a (.*)>|</a>","");
				
				String [] split = temp.split("&nbsp;|[. ]{2}");
				
				for (int jj=0; jj<split.length; jj++){
					int val = 0;
					split[jj]=split[jj]+". ";
					String [] kata = split[jj].split("[ ,.()]");
					for (int kk =0 ; kk<kata.length; kk++){
						if (!kata[kk].equals("")){
							String s = kata[kk];
							if (frequencyMap.containsKey(s))
								val += frequencyMap.get(s.toLowerCase());
						}
					}
					
					mapkata.put(id, split[jj]);
					mapval.put(id, val);
					pq.add(val);
					id++;
				}
			}
		}
		
		while (pq.size()>NUM_SUMMARY){
			System.out.println(pq.poll());
		}
		String hasil = "";
		for (int ii =0 ; ii<id; ii++){
			int pos = mapval.get(ii);
			if (pq.contains(pos))
			{
				hasil += mapkata.get(ii);
				System.out.println();
				System.out.println(mapkata.get(ii));
				System.out.println();
			}
		}
		return hasil;
	}
}
