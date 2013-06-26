package fr.ele.feeds.betclick;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class betclickParsFeedXml {

    private String sport = null;

    private String event = null;

    private String player1 = null;

    private String player2 = null;

    private String match = null;

    private Date startdate = null;

    private String bet = null;

    private double oddbet;

    private String oddtype;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    public betclickParsFeedXml(String feedurl) {

        DocumentBuilder builder;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            URL url = new URL(feedurl);
            Document doc = builder.parse(url.openStream());

            Element element = null;

            doc.getDocumentElement().normalize();

            ;
            NodeList nList = doc.getChildNodes();
            element = doc.getDocumentElement();
            nList = element.getChildNodes();
            getnodeChild(nList);// sport

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getnodeChild(NodeList nlist) {

        Node nodetemp = null;

        if (nlist.getLength() != 0) {
            for (int i = 0; i < nlist.getLength(); i++) {
                nodetemp = nlist.item(i);

                if (nodetemp.getNodeName() == "sport") {
                    setSport(nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString());

                    getnodeChild(nodetemp.getChildNodes());
                    System.out.println(getSport());
                }
                if (nodetemp.getNodeName() == "event") {

                    setEvent(nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString());

                    getnodeChild(nodetemp.getChildNodes());

                }

                if (nodetemp.getNodeName() == "match") {

                    String[] player = nodetemp.getAttributes()
                            .getNamedItem("name").getTextContent().toString()
                            .split(" - ");

                    try {
                        setStartDate(nodetemp.getAttributes()
                                .getNamedItem("start_date").getTextContent()
                                .toString());
                    } catch (DOMException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    setMatch(nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().toLowerCase());
                    setPlayer1(player[0].toLowerCase());
                    try {
                        setPlayer2(player[1].toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getnodeChild(nodetemp.getChildNodes());
                }
                if (nodetemp.getNodeName() == "bets") {
                    getnodeChild(nodetemp.getChildNodes());
                }
                if (nodetemp.getNodeName() == "bet") {

                    // football
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString()
                            .contains("Match Result")) {

                        setBet(nodetemp.getAttributes().getNamedItem("name")
                                .getTextContent().toString().toLowerCase());

                        getnodeChild(nodetemp.getChildNodes());

                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString()
                            .contains("Half-Time Result")) {

                        setBet(nodetemp.getAttributes().getNamedItem("name")
                                .getTextContent().toString().toLowerCase());

                        getnodeChild(nodetemp.getChildNodes());
                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString()
                            .contains("Total Goal Lines")) {

                        setBet(nodetemp.getAttributes().getNamedItem("name")
                                .getTextContent().toString().toLowerCase());

                        getnodeChild(nodetemp.getChildNodes());
                    }

                    // Tennis
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString()
                            .contains("Match Winner")) {
                        setBet(nodetemp.getAttributes().getNamedItem("name")
                                .getTextContent().toString().toLowerCase());

                        getnodeChild(nodetemp.getChildNodes());

                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Total Sets")) {
                        setBet(nodetemp.getAttributes().getNamedItem("name")
                                .getTextContent().toString().toLowerCase());

                        getnodeChild(nodetemp.getChildNodes());

                    }
                }

                if (nodetemp.getNodeName() == "choice") {
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("%1%")) {

                        setoddstring("player1win");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));

                        // new
                        // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                        // this.getSport(),this.getodd());

                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("%2%")) {

                        setoddstring("player2win");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Draw")) {
                        setoddstring("draw");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 6.5")) {
                        setoddstring("over65");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 5.5")) {
                        setoddstring("over55");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }

                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 4.5")) {
                        setoddstring("over45");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 3.5")) {
                        setoddstring("over35");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 2.5")) {
                        setoddstring("over25");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Over 1.5")) {
                        setoddstring("under15");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 6.5")) {
                        setoddstring("under65");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 5.5")) {
                        setoddstring("under55");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 4.5")) {
                        setoddstring("under45");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));

                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 3.5")) {
                        setoddstring("under35");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 2.5")) {
                        setoddstring("under25");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("Under 1.5")) {
                        setoddstring("under15");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("2 sets")) {
                        setoddstring("2sets");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));

                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("3 sets")) {
                        setoddstring("3sets");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("4 sets")) {
                        setoddstring("4sets");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }
                    if (nodetemp.getAttributes().getNamedItem("name")
                            .getTextContent().toString().contains("5 sets")) {
                        setoddstring("5sets");
                        setodd(Double.valueOf(nodetemp.getAttributes()
                                .getNamedItem("odd").getTextContent()
                                .toString()));
                    }

                }

            }
            // at the end --> storage

        }
    }

    public void setSport(String tmp) {
        sport = tmp;
    }

    public String getSport() {
        return sport;
    }

    public void setEvent(String tmp) {
        event = tmp;
    }

    public String getEvent() {
        return event;
    }

    public void setMatch(String tmp) {
        match = tmp;
    }

    public String getMatch() {
        return match;
    }

    public void setPlayer1(String tmp) {
        player1 = tmp;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer2(String tmp) {
        player2 = tmp;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setStartDate(String tmp) throws ParseException {
        startdate = formatter.parse(tmp);
    }

    public Date getStartDate() {
        return startdate;
    }

    public void setBet(String tmp) {
        bet = tmp;
    }

    public String getBet() {
        return bet;
    }

    public String getBookmaker() {
        return "betclick";
    }

    public void setoddstring(String oddstring) {
        oddtype = oddstring;
    }

    public String getoddstring() {
        return oddtype;
    }

    public void setodd(double odd) {
        oddbet = odd;
    }

    public double getodd() {
        return oddbet;
    }
}
