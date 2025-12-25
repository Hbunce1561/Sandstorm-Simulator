import java.util.*;
public class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Sandstorm s = new Sandstorm();
        
        while(true){
            System.out.println("Would you like to play?"
                +"\n(Y)es / (N)o / (W)e Ball");
            switch (sc.nextLine()) {
                case "y", "Y":
                    System.out.println("(T)rump / (M)eld / (F)lush?"
                        +"\n5 Ecto, .4 G / 50 Ecto, 4 G / 250 Ecto, 100 G");
                    switch (sc.nextLine()) {
                        case "T", "t":
                            s.play(1,1);
                            break;
                        case "M", "m":
                            s.play(1,0);
                            break;
                        case "F", "f":
                            s.play(1, 2);
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                    break;
                case "w", "W":
                    s.play(100, 2);
                    break;
                case "n", "N":
                    System.exit(0);
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
      
    }
}