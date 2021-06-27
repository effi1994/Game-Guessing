public class Article {
    private  String title;
    private  String content;
    private String subtitle;
    private String allSiteContent;


    public Article(String title, String content ,String subtitle) {
        this.title = title;
        this.subtitle=subtitle;
        this.content = content;
        this.allSiteContent=title + " " + subtitle + " " + content;

    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAllSiteContent() {
        return allSiteContent;
    }

    public void setAllSiteContent(String allSiteContent) {
        this.allSiteContent = allSiteContent;
    }
}
