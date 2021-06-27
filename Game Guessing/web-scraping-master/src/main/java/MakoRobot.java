
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class  MakoRobot extends BaseRobot {




    public MakoRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);

    }

    @Override
    public void getAllArticles(){
        Article article = null;
        Elements elements=this.getWbe().getElementsByTag("a");
        for(Element element : elements){
            String elementString = element.toString();
            String url = "";
            if(elementString.contains("Article-")){
                int endIndex = elementString.indexOf(".htm") + 4;
                int statIndex = elementString.indexOf("href=") + 6;
                url = elementString.substring(statIndex,endIndex);
                if(!url.contains("mako.co.il")){
                    url = Def.NEWS_MAKO.concat(url);
                }

                if (url.contains("news")){
                    article = getArticle(url,Def.MAKO_TAG_TITLE,Def.MAKO_TAG_SUBTITLE,Def.MAKO_TAG_BODY_ARTICLE);
                    addUniqueArticle(url, article);

                }





            }
        }
    }





    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String,Integer> wordStatistics = new HashMap<>();
        for (Article article : getArticles().values()){
            String [] words =  article.getAllSiteContent().split(" ");


            for (String word : words){
                if(wordStatistics.get(word) != null) {
                    wordStatistics.put(word,  wordStatistics.get(word)+1 );
                }
                else
                    wordStatistics.put(word,1);
          }


        }

        return wordStatistics;  // <effi , 10>
    }

    @Override
    public int countInArticlesTitles(String text) {
        StringBuilder makoContentTitle = null;
        StringBuilder makoContentSubtitle = null;

        int counter= 0;
        for (Article article : getArticles().values()){
            makoContentTitle = new StringBuilder(article.getTitle());
            makoContentSubtitle=new StringBuilder(article.getSubtitle());
            int indexTitle = 0;
            int indexSubtitle = 0;
            do{
                indexTitle = makoContentTitle.indexOf(text);
                indexSubtitle =makoContentSubtitle.indexOf(text);
                if(indexTitle != -1){
                    counter++;
                    makoContentTitle.setCharAt(indexTitle, '@');
                }

                if (indexSubtitle !=-1){
                    counter++;
                    makoContentSubtitle.setCharAt(indexSubtitle,'*');
                }

            }while(indexTitle!=-1 && indexSubtitle!=-1);

        }
        return counter;
    }

    @Override
    public String getLongestArticleTitle() {

        String makoTitleToReturn = "";
        int currentTitleLength = 0;
        int longest = 0;

        for(Article article : getArticles().values()){

            currentTitleLength = article.getContent().length();
            if(currentTitleLength > longest){
                makoTitleToReturn = article.getTitle();
                longest = currentTitleLength;

            }


        }
        return makoTitleToReturn;

    }

    public int gamePart1(MakoRobot makoRobot, Scanner userInput){
        int userResult1=0;


        System.out.println("First part of the game:\n" +
                "You have 5 guesses, guess what are the most common words on the site.\n" +
                "  Hint: Help with the title." +  "(" + makoRobot.getLongestArticleTitle() + ")");
        for (int i = Def.GUESS_NUMBERS_PRAT1; i>0; i--) {
            System.out.println("enter your guess text");
            String user1=userInput.next();
            if (makoRobot.getWordsStatistics().get(user1)!=null){
                int point=makoRobot.getWordsStatistics().get(user1);
                userResult1+= point;
                System.out.println("You guessed it ");

            }else {
                System.out.println("Wrong");
            }

        }
        return userResult1;
    }

    public int gamePart2(MakoRobot makoRobot, Scanner userInput){
        int resultPrat2=0;
        System.out.println("The user enters some text, which he thinks should appear in the headlines of articles on the site");
        String userText=userInput.next();
        int numberGuess=makoRobot.countInArticlesTitles(userText);
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




    public void print(){
        for (Article article : getArticles().values()){
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
