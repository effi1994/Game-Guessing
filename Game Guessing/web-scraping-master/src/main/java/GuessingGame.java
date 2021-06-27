
import java.util.Scanner;

public class GuessingGame {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int user = 0;
        int  userResult=0;



        try {
            System.out.println("Which site will want to crawl from the three options above :\n"+
                     "1.new walla:\n" + "2.new ynet:\n" + "3.new mako.");
            user = userInput.nextInt();
        } catch (Exception e) {
            System.out.println("you must enter only numbers");
            userInput.nextLine();
        }


        switch (user) {

            case Def.WALLA:
                System.out.println("Please wait for the site to finish scanning");
                WallaRobot wallaRobot = new WallaRobot(Def.NEWS_WALLA);
                System.out.println("start the game :");
               userResult+=wallaRobot.gamePart1(wallaRobot,userInput);
               userResult+=wallaRobot.gamePart2(wallaRobot,userInput);
                System.out.println("results of the game: "  + userResult);

                break;
            case Def.YENT:
                System.out.println("Please wait for the site to finish scanning");
                YnetRobot ynetRobot=new YnetRobot(Def.NEWS_YENT);
                System.out.println("start the game :");
                userResult+=ynetRobot.gamePart1(ynetRobot,userInput);
                userResult+=ynetRobot.gamePart2(ynetRobot,userInput);
                System.out.println("results of the game: "  + userResult);

                break;
            case Def.MAKO:
                System.out.println("Please wait for the site to finish scanning");
                MakoRobot makoRobot =new MakoRobot(Def.NEWS_MAKO);
                System.out.println("start the game :");
                userResult+=makoRobot.gamePart1(makoRobot,userInput);
                userResult+=makoRobot.gamePart2(makoRobot,userInput);
                System.out.println("results of the game: "  + userResult);
                break;


        }


    }



}




