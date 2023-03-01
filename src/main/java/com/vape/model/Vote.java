package com.vape.model;

public class Vote {
    private Long id;
    private double point;
    private String review;

    public Vote() {
    }

    public Vote(Long id, double point, String review) {
        this.id = id;
        this.point = point;
        this.review = review;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
