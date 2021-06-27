
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public abstract class BaseRobot {
    private Map<String, Article> articles; // <url,article>
    private Map<String, Integer> wordsStatistics; // so we won't need to reconnect everytime, therefore we save a lot of time

    private String rootWebsiteUrl;
    private Document wbe;




    public BaseRobot(String rootWebsiteUrl) {
        this.rootWebsiteUrl = rootWebsiteUrl;
        this.articles = new HashMap<>();


        try {
             this.wbe= Jsoup.connect(rootWebsiteUrl).get();

        }catch (IOException e){
            e.printStackTrace();
        }

        getAllArticles();

    }


    public Article getArticle(String url,String tagTitle,String tagSubtitle,String tagBodyArticle){
        Article article = null;
        String title = "";
        String subtitle="";
        String content = "";
        try {
            this.wbe =Jsoup.connect(url).get();
            title = this.wbe.getElementsByTag(tagTitle).text();
            subtitle=this.wbe.getElementsByTag(tagSubtitle).text();
            content = this.wbe.getElementsByTag(tagBodyArticle).text();
            article = new Article(title,subtitle,content);
            //System.out.println(content);

        }catch (IOException e){
            //e.printStackTrace();
            System.out.println("an article has failed to connect! ");
        }
        return article;
    }

    public void addUniqueArticle(String url, Article article){

        this.articles.putIfAbsent(url, article);
    }

    public void printArticles(){
        for(String url : this.articles.keySet()){
            System.out.println(url);
        }
    }


    public Map<String, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, Article> articles) {
        this.articles = articles;
    }

    public abstract void getAllArticles();


    public Document getWbe() {
        return wbe;
    }

    public void setWbe(Document wbe) {
        this.wbe = wbe;
    }

    public String getRootWebsiteUrl() {
        return rootWebsiteUrl;
    }

    public void setRootWebsiteUrl(String rootWebsiteUrl) {
        this.rootWebsiteUrl = rootWebsiteUrl;
    }

    public abstract Map<String, Integer> getWordsStatistics();

    public abstract int countInArticlesTitles(String text);

    public abstract String getLongestArticleTitle();



}
