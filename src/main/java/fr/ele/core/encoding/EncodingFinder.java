package fr.ele.core.encoding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class EncodingFinder {
    private final CharsetDetector detector;

    public EncodingFinder() {
        detector = new CharsetDetector();
    }

    public String findEncoding(InputStream is) {
        try {
            detector.setText(new BufferedInputStream(is));
            CharsetMatch charset = detector.detect();
            return charset.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
