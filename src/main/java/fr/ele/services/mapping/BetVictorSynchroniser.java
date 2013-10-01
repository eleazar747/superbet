package fr.ele.services.mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class BetVictorSynchroniser {
	private Document doc;
	private Elements el;
	private String date;
	public BetVictorSynchroniser() throws IOException{
		parseLigue1();
		parseLigue2();
		parseBundesliga();
		parseBundesliga2();
		parseBundesliga3();
	}
	public void parseLigue1() throws IOException{
		String text="";
		String ligne="";
		doc=Jsoup.connect("http://www.betvictor.com/sports/en/football/fra-division-1/coupons/100/6270510/0/4529/0/MPE/0/0/0/30/1").get();
		readDataOver(doc.select("tr"));
		//ecrirText(text, "src/betvictor/betvictorLigue1");
	}
	public void parseLigue2() throws IOException{
		String text="";
		String ligne="";
		doc=Jsoup.connect("http://www.betvictor.com/sports/en/football/fra-division-2/coupons/100/7006510/0/4428/0/MPE/0/0/0/30/1").get();
		readDataOver(doc.select("tr"));
		//ecrirText(text, "src/betvictor/betvictorLigue2");
	}
	public void parseBundesliga() throws IOException{
		String text="";
		String ligne="";
		doc=Jsoup.connect("http://www.betvictor.com/sports/en/football/ger-bundesliga/coupons/100/6316510/0/4428/0/MPE/0/0/0/30/1").get();
		readDataOver(doc.select("tr"));
		//ecrirText(text, "src/betvictor/betvictorBundesliga");
	}
	public void parseBundesliga2() throws IOException{
		String text="";
		String ligne="";
		doc=Jsoup.connect("http://www.betvictor.com/sports/en/football/ger-bundesliga-2/coupons/100/5170510/0/4428/0/MPE/0/0/0/30/1").get();
		readDataOver(doc.select("tr"));
		
		
		//ecrirText(text, "src/betvictor/betvictorBundesliga2");
	}
	public void parseBundesliga3() throws IOException{
		String text="";
		String ligne="";
		readDataOver(doc.select("tr"));
		
		
		
		//ecrirText(text, "src/betvictor/betvictorBundesliga3");
	}
	/*public void ecrirText(String text, String path) throws IOException{
		PrintWriter pw=new PrintWriter(new FileWriter(path, false));
		BufferedWriter bw=new BufferedWriter(pw);
		bw.write(text);
		bw.close();
	}*/
	
	
	private void readDataOver(Elements elements){
		Elements e=doc.select("tr");
		String ligne="";
		String text="";
		for (Element t : e){
			ligne=t.text();
			if (ligne.substring(0, 4).equalsIgnoreCase("Time")){
			//rien
			}else{

					for(Element d:t.select("td")){
						System.out.println(d.text()+ d.attributes().get("class"));
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
						if(d.attributes().get("class")=="date"){
							date=d.text();
						}
					}
					text+=ligne+"\n";
				}
			
		}
	}
}
