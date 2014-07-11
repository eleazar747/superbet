package fr.ele.dto;

import com.codiform.moo.annotation.Property;

public class BookmakerDto extends HasIdAndCodeDto {
    private String url, urlSync, encoding;

    @Property(source = "synchronizerService")
    private String synchronizer;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlSync() {
        return urlSync;
    }

    public void setUrlSync(String urlSync) {
        this.urlSync = urlSync;
    }

    public String getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(String synchronizer) {
        this.synchronizer = synchronizer;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
