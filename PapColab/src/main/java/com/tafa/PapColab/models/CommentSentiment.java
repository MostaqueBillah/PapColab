package com.tafa.PapColab.models;

public class CommentSentiment {
    private String comment;
    private String sentiment;

    public CommentSentiment() {
        // Default constructor
    }

    public CommentSentiment(String comment, String sentiment) {
        this.comment = comment;
        this.sentiment = sentiment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
}

