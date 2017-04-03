package com.example.deepanshu.newsapp.model;

/**
 * Created by apple on 01/04/16.
 */
public class NewsFeed {

    private String title;
    private String sectioName;
    private String webUrl;
    private String publicationDate;

    public NewsFeed(String title, String sectionName, String webUrl, String publicationDate) {

        this.setTitle(title);
        this.setSectioName(sectionName);
        this.setWebUrl(webUrl);
        this.setPublicationDate(publicationDate);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getSectioName() {
        return sectioName;
    }

    private void setSectioName(String sectioName) {
        this.sectioName = sectioName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    private void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    private void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}