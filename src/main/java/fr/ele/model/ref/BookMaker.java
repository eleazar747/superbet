package fr.ele.model.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import fr.ele.model.HasCodeEntity;
import fr.ele.model.SuperBetTables;

@Entity
@Table(name = SuperBetTables.BookMakerTable.TABLE)
@Proxy(proxyClass = BookMaker.class)
public class BookMaker extends HasCodeEntity {
    @Column(name = SuperBetTables.BookMakerTable.URL_COLUMN, nullable = false)
    private String url;

    @Column(name = SuperBetTables.BookMakerTable.URL_SYNC_COLUMN, nullable = false)
    private String urlSync;

    @Column(name = SuperBetTables.BookMakerTable.CLASS_SYNC_COLUMN, nullable = false)
    private String classSync;

    public String getClassSync() {
        return classSync;
    }

    public void setClassSync(String classSync) {
        this.classSync = classSync;
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

}
