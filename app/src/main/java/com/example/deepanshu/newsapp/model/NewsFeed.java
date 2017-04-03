package com.example.deepanshu.newsapp.model;

/**
 * Created by apple on 01/04/16.
 */
public class NewsFeed {

    private String title;
    private String sectioName;
    private String webUrl;
    private String publicationDate;
    private String apiUrl;
    private Boolean isHosted;

    public NewsFeed(String title, String sectionName, String webUrl, String publicationDate, String apiUrl, Boolean isHosted) {

        this.setTitle(title);
        this.setSectioName(sectionName);
        this.setWebUrl(webUrl);
        this.setPublicationDate(publicationDate);
        this.setApiUrl(apiUrl);
        this.setHosted(isHosted);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectioName() {
        return sectioName;
    }

    public void setSectioName(String sectioName) {
        this.sectioName = sectioName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Boolean getHosted() {
        return isHosted;
    }

    public void setHosted(Boolean hosted) {
        isHosted = hosted;
    }
}