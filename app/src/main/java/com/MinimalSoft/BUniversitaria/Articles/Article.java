package com.MinimalSoft.BUniversitaria.Articles;

class Article {
    private Title title;
    private String link;
    private String image;

    public Title getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public class Title {
        private String rendered;

        public String getRendered() {
            return rendered;
        }
    }
}