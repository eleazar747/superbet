package fr.ele.feeds.expekt;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class expektParseFeedXml {

    private String sport = null;

    private String event = null;

    private String player1 = null;

    private String player2 = null;

    private String match = null;

    private Date startdate = null;

    private String bet = null;

    private double oddbet;

    private String oddtype;

    private String strtmp, strtemp;

    private String strinfo[];

    private Element element;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    public expektParseFeedXml(String feedurl) {

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
            getnodeChild(nList);

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

                if (nodetemp.getNodeName().contains("game")) {
                    try {
                        setStartDate(nodetemp.getAttributes()
                                .getNamedItem("date").getTextContent()
                                .toString());
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setStartTime(nodetemp.getAttributes().getNamedItem("time")
                            .getTextContent().toString());
                    Element eElement = (Element) nodetemp;
                    strtmp = eElement.getElementsByTagName("description")
                            .item(0).getTextContent();
                    getnodeChild(nodetemp.getChildNodes());

                }
                if (nodetemp.getNodeName() == "description") {

                    Element eElement = (Element) nodetemp;

                    strtemp = eElement.getElementsByTagName("category").item(0)
                            .getTextContent();
                    strtmp = strtmp.replaceAll(strtemp, "");
                    if (strtmp.contains("-")) {
                        strtmp = strtmp.replaceAll("\n", "");
                        strtmp = strtmp.replaceAll(" ", "");
                        if (strtmp.contains(":")) {
                            strtmp = strtmp.replaceAll(":", "-");
                            strinfo = strtmp.split("-");

                            setPlayer1(strinfo[0].toLowerCase());
                            setPlayer2(strinfo[1].toLowerCase());
                            System.out.println(strtmp);
                            setBet(strinfo[2].toLowerCase());
                        } else {
                            strinfo = strtmp.split("-");

                            setPlayer1(strinfo[0].toLowerCase());
                            setPlayer2(strinfo[1].toLowerCase());
                            setBet("result final");
                        }

                        // this.setEvent(nodetemp.getAttributes().getNamedItem("name").getTextContent().toString());

                        getnodeChild(nodetemp.getChildNodes());
                    }
                }

                if (nodetemp.getNodeName() == "category") {
                    if (nodetemp.getAttributes().getNamedItem("id")
                            .getTextContent().toString().toLowerCase()
                            .contains("soc")) {
                        ;
                    }
                    setSport("football");

                }

                if (nodetemp.getNodeName() == "alternatives") {
                    element = (Element) nodetemp;
                    strinfo = new String[element.getElementsByTagName(
                            "alternative").getLength()];
                    for (int j = 0; j < element.getElementsByTagName(
                            "alternative").getLength(); j++) {
                        strinfo[j] = element
                                .getElementsByTagName("alternative").item(j)
                                .getTextContent();
                    }
                    getnodeChild(nodetemp.getChildNodes());
                }

                if (nodetemp.getNodeName() == "alternative") {
                    if (getBet() == "result final") {
                        if (i > 0
                                && element.getElementsByTagName("alternative")
                                        .getLength() < 2) {
                            if (i == 1) {
                                setodd(Double.valueOf(nodetemp.getAttributes()
                                        .getNamedItem("odds").getTextContent()));
                                setoddstring("player1win");
                                // new
                                // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                                // this.getSport(),this.getodd());
                            }
                            if (i == 2) {
                                setodd(Double.valueOf(nodetemp.getAttributes()
                                        .getNamedItem("odds").getTextContent()));
                                setoddstring("player2win");
                                // new
                                // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                                // this.getSport(),this.getodd());
                            }
                        } else {
                            if (i == 1) {
                                setodd(Double.valueOf(nodetemp.getAttributes()
                                        .getNamedItem("odds").getTextContent()));
                                setoddstring("player1win");
                                // new
                                // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                                // this.getSport(),this.getodd());
                            }
                            if (i == 2) {
                                setodd(Double.valueOf(nodetemp.getAttributes()
                                        .getNamedItem("odds").getTextContent()));
                                setoddstring("Draw");
                                // new
                                // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                                // this.getSport(),this.getodd());
                            }
                            if (i == 3) {
                                setodd(Double.valueOf(nodetemp.getAttributes()
                                        .getNamedItem("odds").getTextContent()));
                                setoddstring("player2win");
                                // new
                                // storageDB(this.getStartDate(),this.getPlayer1(),this.getPlayer2(),this.getBet(),this.getBookmaker(),this.getMatch(),this.getoddstring(),
                                // this.getSport(),this.getodd());
                            }
                        }
                    }
                }
            }

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

    public void setStartTime(String oddstring) {
        oddtype = oddstring;
    }

    public String getStartTime() {
        return oddtype;
    }

    public void setBet(String tmp) {
        bet = tmp;
    }

    public String getBet() {
        return bet;
    }

    public String getBookmaker() {
        return "expekt";
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
