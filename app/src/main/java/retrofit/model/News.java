package retrofit.model;

public class News {
    private int newsId;
    private String newsTitle;
    private String description;

    public int getNewsId(){
        return newsId;
    }

    public void setNewsId(int newsId){
        this.newsId = newsId;
    }

    public String getNewsTitle(){
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle){
        this.newsTitle = newsTitle;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
