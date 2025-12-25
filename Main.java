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
                    s.play(1);
                    break;
                case "w", "W":
                    s.play(100);
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