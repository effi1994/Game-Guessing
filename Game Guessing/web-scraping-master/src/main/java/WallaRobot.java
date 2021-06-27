
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class WallaRobot extends BaseRobot {
    public WallaRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);


    }





    @Override
    public void getAllArticles() {

        Article article = null;
        Elements elements=this.getWbe().getElementsByTag("a");
        for(Element element : elements){
            String elementString = element.toString();
            String url = "";
            if (elementString.contains("https://news.walla.co.il/item")){
                int endIndex = elementString.indexOf("item") + 12;
                int statIndex = elementString.indexOf("href=") + 6;
                url = elementString.substring(statIndex,endIndex);
                article = getArticle(url,Def.WALLA_TAG_TITLE,Def.WALLA_TAG_SUBTITLE,Def.WALLA_TAG_BODY_ARTICLE);
                addUniqueArticle(url, article);

            }

            }
        }




    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String,Integer> wordStatistics = new HashMap<>();
        for (Article article : getArticles().values()){
            String [] words =  article.getContent().split(" ");
            for (String word : words){
                if(wordStatistics.get(word) != null) {
                    wordStatistics.put(word,  wordStatistics.get(word)+1 );
                }
                else
                    wordStatistics.put(word,1);
            }


        }

        return wordStatistics;
    }

    @Override
    public int countInArticlesTitles(String text) {
        StringBuilder wallaContentTitle = null;
        StringBuilder wallaContentSubtitle = null;

        int counter= 0;
        for (Article article : getArticles().values()){
            wallaContentTitle = new StringBuilder(article.getTitle());
            wallaContentSubtitle=new StringBuilder(article.getSubtitle());
            int indexTitle = 0;
            int indexSubtitle = 0;
            do{
                indexTitle = wallaContentTitle.indexOf(text);
                indexSubtitle =wallaContentSubtitle.indexOf(text);
                if(indexTitle != -1){
                    counter++;
                    wallaContentTitle.setCharAt(indexTitle, '@');
                }

                if (indexSubtitle !=-1){
                    counter++;
                    wallaContentSubtitle.setCharAt(indexSubtitle,'*');
                }

            }while(indexTitle!=-1 && indexSubtitle!=-1);

        }
        return counter;
    }

    @Override
    public String getLongestArticleTitle() {
        String wallaTitleToReturn = "";
        int currentTitleLength = 0;
        int longest = 0;

        for(Article article : getArticles().values()){

            currentTitleLength = article.getContent().length();
            if(currentTitleLength > longest){
                wallaTitleToReturn = article.getTitle();
                longest = currentTitleLength;

            }


        }
        return wallaTitleToReturn;
    }










    public int gamePart1(WallaRobot wallaRobot, Scanner userInput){
        int resultaPrat1=0;

        System.out.println("First part of the game:\n" +
                "You have 5 guesses, guess what are the most common words on the site.\n" +
                "  Hint: Help with the title." +  "(" + wallaRobot.getLongestArticleTitle() + ")");
        for (int i = Def.GUESS_NUMBERS_PRAT1; i>0; i--) {
            System.out.println("enter your guess text");
            String user1=userInput.next();
            if (wallaRobot.getWordsStatistics().get(user1)!=null){
                int point=wallaRobot.getWordsStatistics().get(user1);
                resultaPrat1+= point;
                System.out.println("You guessed it ");

            }else {
                System.out.println("Wrong");
            }

        }
        return resultaPrat1;
    }

    public int gamePart2(WallaRobot wallaRobot, Scanner userInput){
        int resultPrat2=0;
        System.out.println("The user enters some text, which he thinks should appear in the headlines of articles on the site");
        String userText=userInput.next();
        int numberGuess=wallaRobot.countInArticlesTitles(userText);
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
