package fr.ele.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.ele.model.Bet;
import fr.ele.model.EntityImpl;
import fr.ele.model.ref.BookMarker;
import fr.ele.model.ref.RefKey;


@Entity
@Table(name = "BET")
public class BetImpl extends EntityImpl implements Bet {

	@Column(name = "ODD", nullable = false)
	private double odd;
	
	@Column(name = "DATE", nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKMAKER_ID", nullable = false)
	private BookMarker bookMarker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFKEY_ID", nullable = false)
	private RefKey refKey;
	
	public RefKey getRefKey() {
		return refKey;
	}

	public void setRefKey(RefKey refKey) {
		this.refKey = refKey;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BookMarker getBookMarker() {
		return bookMarker;
	}

	public void setBookMarker(BookMarker bookMarker) {
		this.bookMarker = bookMarker;
	}

}
