package fr.ele.core.matcher.noise;

public class BlankRemover implements NoiseRemover {

    @Override
    public String removeNoise(String input) {
        return input.trim();
    }

}
