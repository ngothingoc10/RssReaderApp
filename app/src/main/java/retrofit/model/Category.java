package retrofit.model;

public class Category {
    private int categoriesId;
    private String categoriesTitle;
    private int newsId;
    private String rssLink;

    public Category(int categoriesId){
        this.categoriesId = categoriesId;
    }

    public String getCategoriesTitle(){
        return categoriesTitle;
    }

    public void setCategoriesTitle(String categoriesTitle){
        this.categoriesTitle = categoriesTitle;
    }

    public int getNewsId(){
        return newsId;
    }

    public String getRssLink(){
        return rssLink;
    }

    public void setRssLink(String rssLink){
        this.rssLink = rssLink;
    }

    public void setNewsId(int newsId){
        this.newsId = newsId;
    }



    public int getCategoriesId(){
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId){
        this.categoriesId = categoriesId;
    }
}
