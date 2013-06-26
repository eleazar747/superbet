package fr.ele.feeds.marathonbet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class mathonbetLink {

    public void linkTodayMathFootball() throws IOException {
        // http://www.betvictor.com/sports/en/coupons/todays-matches
        // ZTable table=new ZTable();

        int cpt = 0;
        Object[][] data = null;
        String Title[] = {"Article", "Time"};
        String str[] = null;
        URL yahoofin = new URL(
                "http://www.paddypower.com/football/football-matches/today-and-tomorrow");

        URLConnection yc = yahoofin.openConnection();
        // yc.setAllowUserInteraction(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        // System.out.println(yc.getRequestProperties());

        String inputLine2last = "";
        String inputLinelast = "fd";
        String inputLine;

        boolean check = true;

        // Pour chaque line trouver une appel betvictorFootball new
        // betVictorFootball(link)
        while (check && cpt < 2000) {
            cpt++;
            inputLine = in.readLine();
            System.out.println(cpt);
            System.out.println(inputLine);

        }

    }

}
