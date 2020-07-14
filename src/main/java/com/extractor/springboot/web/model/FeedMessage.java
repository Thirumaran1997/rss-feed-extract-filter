package com.extractor.springboot.web.model;

public class FeedMessage {

    String title;
	String author;
    String link;
    String description;
    String pubDate;
    String cityId;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setCity(String cityId) {
		this.cityId = cityId;
	}
	
	public String getCity() {
		return cityId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", city=" + cityId + ",author=" + author
                + ", link=" + link + ", description=" + description+",pubDate="+pubDate
                + "]";
    }

}