package by.bsuir.lab1mobilki.model;

public class New {
    private String title;
    private String createdAt;

    public New(String title, String createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
