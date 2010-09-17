package com.cbaplab.bogr.model;

public class Quote {
	private int mType;
	private int mSType;
	private CharSequence mTitle;
	private CharSequence mLink;
	private CharSequence mBody;
	private CharSequence mPubDate;

	public Quote() {
		mTitle = mLink = mBody = mPubDate = null;
	}

	public Quote(int _type,int _stype, CharSequence _title, CharSequence _link,
			CharSequence _body, CharSequence _pubdate) {
		mType = _type;
		mSType = _stype;
		mTitle = _title;
		mLink = _link;
		mBody = _body;
		mPubDate = _pubdate;

	}

	public int getType() {
		return mType;
	}
	public int getSType() {
		return mSType;
	}

	public CharSequence getBody() {
		return mBody;
	}
	public void setBody(CharSequence _body) {
		mBody = _body;
	}

	public CharSequence getLink() {
		return mLink;
	}
	public void setLink(CharSequence _link) {
		mLink = _link;
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence _title) {
		mTitle = _title;
	}

	public CharSequence getPubDate() {
		Long now = Long.valueOf(System.currentTimeMillis());
		if (mPubDate == null) return now.toString();
		return mPubDate;
	}

	public void setPubDate(CharSequence _pubdate) {
		mPubDate = _pubdate;
	}	
}