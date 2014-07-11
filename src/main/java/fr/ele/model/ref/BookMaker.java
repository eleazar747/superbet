package fr.ele.model.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.codiform.moo.annotation.Property;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;
import fr.herman.metatype.annotation.MetaBean;

@MetaBean
@Entity
@Table(name = SuperBetTables.BookMakerTable.TABLE)
@Proxy(proxyClass = BookMaker.class)
public class BookMaker extends HasCodeEntity {
    @Column(name = SuperBetTables.BookMakerTable.URL_COLUMN, nullable = false)
    private String url;

    @Column(name = SuperBetTables.BookMakerTable.URL_SYNC_COLUMN, nullable = false)
    private String urlSync;

    @Property(translation = "synchronizer")
    @Column(name = SuperBetTables.BookMakerTable.SYNC_SERVICE, nullable = false)
    private String synchronizerService;

    @Column(name = SuperBetTables.BookMakerTable.ENCODING, nullable = true)
    private String encoding;

    public String getSynchronizerService() {
        return synchronizerService;
    }

    public void setSynchronizerService(String synchronierService) {
        synchronizerService = synchronierService;
    }

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

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
