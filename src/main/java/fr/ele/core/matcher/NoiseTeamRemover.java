package fr.ele.core.matcher;

import org.apache.commons.lang3.StringUtils;

public class NoiseTeamRemover {
    private static final String[] NOISES = new String[]{" FC", "FC ", " CF",
            "CF ", "DEPORTIVO ", " DEPORTIVO", "DEPORTIVO"};

    public String removeNoise(String team) {
        String clean = team.toUpperCase();
        for (String noise : NOISES) {
            clean = StringUtils.remove(clean, noise.toUpperCase());
        }
        return clean;
    }
}
