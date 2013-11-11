package fr.ele.core.matcher.noise;

import org.apache.commons.lang3.StringUtils;

public class NoiseTeamRemover extends BlankRemover {
    private static final String[] NOISES = new String[]{" FC", "FC ", " CF",
            "CF ", "DEPORTIVO ", " DEPORTIVO"};

    @Override
    public String removeNoise(String team) {
        String clean = super.removeNoise(team).toUpperCase();
        for (String noise : NOISES) {
            clean = StringUtils.remove(clean, noise.toUpperCase());
        }
        return clean;
    }
}
