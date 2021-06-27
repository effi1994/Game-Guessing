
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class YnetRobot extends BaseRobot{
    public YnetRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }

    @Override
    public void getAllArticles() {
        Article article = null;
        Elements elements=this.getWbe().getElementsByTag("a");
        for(Element element : elements) {
            String elementString = element.toString();
            String url = "";
            if (elementString.contains("https://www.ynet.co.il/news")) {
                int endIndex = elementString.indexOf('>');
                int statIndex = elementString.indexOf("href=") + 6;
                url = elementString.substring(statIndex,endIndex);
                article = getArticle(url,Def.YENT_TAG_TITLE,Def.YENT_TAG_SUBTITLE,Def.YENT_TAG_BODY_ARTICLE);
                addUniqueArticle(url, article);

            }

        }

    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String,Integer> wordStatisticsOfYnet = new HashMap<>();
        for (Article article : getArticles().values()){
            String [] words =  article.getAllSiteContent().split(" ");
            for (String word : words){
                if(wordStatisticsOfYnet.get(word) != null) {
                    wordStatisticsOfYnet.put(word,  wordStatisticsOfYnet.get(word)+1 );
                }
                else
                    wordStatisticsOfYnet.put(word,1);
            }


        }

        return wordStatisticsOfYnet;
    }

    @Override
    public int countInArticlesTitles(String text) {
        StringBuilder ynetContentTitle = null;
        StringBuilder ynetContentSubtitle = null;

        int counter= 0;
        for (Article article : getArticles().values()){
            ynetContentTitle = new StringBuilder(article.getTitle());
            ynetContentSubtitle=new StringBuilder(article.getSubtitle());
            int indexTitle = 0;
            int indexSubtitle = 0;
            do{
                indexTitle = ynetContentTitle.indexOf(text);
                indexSubtitle =ynetContentSubtitle.indexOf(text);
                if(indexTitle != -1){
                    counter++;
                    ynetContentTitle.setCharAt(indexTitle, '@');
                }

                if (indexSubtitle !=-1){
                    counter++;
                    ynetContentSubtitle.setCharAt(indexSubtitle,'*');
                }

            }while(indexTitle!=-1 && indexSubtitle!=-1);

        }
        return counter;

    }

    @Override
    public String getLongestArticleTitle() {
        String yentTitleToReturn = "";
        int currentTitleLength = 0;
        int longest = 0;

        for(Article article : getArticles().values()){

            currentTitleLength = article.getContent().length();
            if(currentTitleLength > longest){
                yentTitleToReturn = article.getTitle();
                longest = currentTitleLength;

            }


        }
        return yentTitleToReturn;

    }


    public int gamePart1(YnetRobot ynetRobot, Scanner userInput){
        int userResult1=0;


        System.out.println("First part of the game:\n" +
                "You have 5 guesses, guess what are the most common words on the site.\n" +
                "  Hint: Help with the title." +  "(" + ynetRobot.getLongestArticleTitle() + ")");
        for (int i = Def.GUESS_NUMBERS_PRAT1; i>0; i--) {
            System.out.println("enter your guess text");
            String user1=userInput.next();
            if (ynetRobot.getWordsStatistics().get(user1)!=null){
                int point=ynetRobot.getWordsStatistics().get(user1);
                userResult1+= point;
                System.out.println("You guessed it ");

            }else {
                System.out.println("Wrong");
            }

        }
        return userResult1;
    }

    public int gamePart2(YnetRobot ynetRobot, Scanner userInput){
        int resultPrat2=0;
        System.out.println("The user enters some text, which he thinks should appear in the headlines of articles on the site");
        String userText=userInput.next();
        int numberGuess=ynetRobot.countInArticlesTitles(userText);
        userInput.nextLine();
        for (int i = 1; i <=Def.GUESS_NUMBERS_PRAT2; i++) {
            try {
                System.out.println("Please guess how many times a text appears in the headings you have selected :\n" +
                        "You have 2 guesses");
                int userNumbers=userInput.nextInt();

                if (numberGuess==userNumbers){
                    System.out.println("You guessed");
                    resultPrat2+=Def.POINT_PRAT2;
                    break;
                }else {
                    System.out.println("Wrong");
                }
            }catch (InputMismatchException e){
                System.out.println("enter Just numbers");
            }

        }
        return resultPrat2;
    }

    public void print() {
        for (Article article : getArticles().values()) {
            System.out.println("%%%%%%%%%%%%%%%");
            System.out.println(article.getTitle());
            System.out.println("****************");
            System.out.println(article.getSubtitle());
            System.out.println("///////////////////");
            System.out.println(article.getContent());
            System.out.println("###################");
        }
    }
}
