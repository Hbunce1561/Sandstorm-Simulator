import java.util.*;
import java.net.http.*;
import java.net.URI;

public class Sandstorm {
    private float gold;
    private float ecto;
    private float LD;
    private float ectoPrice; 
    private HashMap<String, dropRolls> dropTable;
    private HashMap<String, Integer> vOf;
    private HashMap<String, Float> wallet;
    private dropRolls LuckyDR;
    private dropRolls MassiveExotDR;
    private dropRolls MassiveAscDR;
    private dropRolls GlobDR;
    private dropRolls DragonDR;
    private dropRolls PriestessDR;
    private dropRolls PyramidDR;
    private float cost[];
    static int lengthArr = 7;
    private Random rand;
    private Scanner sc;
    private int dropOccurance[];
    public Sandstorm() {
        this.rand = new Random();
        this.dropOccurance = new int[lengthArr];
        this.wallet = new HashMap<>();
        this.cost = new float[2];
        this.sc = new Scanner(System.in);
        setEctoPrice();
        loadWallet();
        this.dropTable = new HashMap<>();
        loadFlushDT();
        this.vOf = new HashMap<>();
        loadValue();
        clDrop();
        this.gold = 0;
        this.ecto = 0;
        this.LD = 0;

    }

    public void roll() {
        this.wallet.put("gold", this.wallet.get("gold") - this.cost[0]);
        this.wallet.put("ecto", this.wallet.get("ecto") - this.cost[1]);
        this.dropOccurance[0] += rollAssistant(this.dropTable.get("Lucky Draw").getDropRate(),
                this.dropTable.get("Lucky Draw").getRolls());
        this.dropOccurance[1] += rollAssistant(this.dropTable.get("Massive Glob of Ectoplasm (Exotic)").getDropRate(),
                this.dropTable.get("Massive Glob of Ectoplasm (Exotic)").getRolls());
        this.dropOccurance[2] += rollAssistant(this.dropTable.get("Massive Glob of Ectoplasm (Ascended)").getDropRate(),
                this.dropTable.get("Massive Glob of Ectoplasm (Ascended)").getRolls());
        this.dropOccurance[3] += rollAssistant(this.dropTable.get("Glob of Ectoplasm").getDropRate(),
                this.dropTable.get("Glob of Ectoplasm").getRolls());
        this.dropOccurance[4] += rollAssistant(this.dropTable.get("Dragon Card").getDropRate(),
                this.dropTable.get("Dragon Card").getRolls());
        this.dropOccurance[5] += rollAssistant(this.dropTable.get("Priestess Card").getDropRate(),
                this.dropTable.get("Priestess Card").getRolls());
        this.dropOccurance[6] += rollAssistant(this.dropTable.get("Pyramid Card").getDropRate(),
                this.dropTable.get("Pyramid Card").getRolls());

    }

    public void calculate() {
        this.LD += this.dropOccurance[0] * this.vOf.get("Lucky Draw");
        this.ecto += this.dropOccurance[1] * this.vOf.get("Massive Glob of Ectoplasm (Exotic)");
        this.ecto += this.dropOccurance[2] * this.vOf.get("Massive Glob of Ectoplasm (Ascended)");
        this.ecto += this.dropOccurance[3] * this.vOf.get("Glob of Ectoplasm");
        this.gold += this.dropOccurance[4] * this.vOf.get("Dragon Card");
        this.gold += this.dropOccurance[5] * this.vOf.get("Priestess Card");
        float Pyrgold = this.dropOccurance[6] * this.vOf.get("Pyramid Card");
        Pyrgold /=10;
        this.gold += Pyrgold;
        this.wallet.put("gold", this.wallet.get("gold") + gold);
        this.wallet.put("ecto", this.wallet.get("ecto") + ecto);
        this.wallet.put("lucky", this.wallet.get("lucky") + LD);
        float ectoVal = this.ectoPrice * this.wallet.get("ecto"); 
        this.wallet.put("effectiveG", this.wallet.get("gold") + ectoVal);
        System.out.println("Roll Totals"
                + "\nGold: " + this.gold
                + "\nEcto: " + this.ecto
                + "\nLucky Draw: " + this.LD+ "\n");
        this.gold = 0;
        this.LD = 0;
        this.ecto = 0;
        clDrop();
    }

    public void printResult() {
        System.out.println(this.dropOccurance[0] + " Lucky Draw\n"
                + this.dropOccurance[1] + " Massive Glob of Ectoplasm (Exotic)\n"
                + this.dropOccurance[2] + " Massive Glob of Ectoplasm (Ascended)\n"
                + this.dropOccurance[3] + " Glob of Ectoplasm\n"
                + this.dropOccurance[4] + " Dragon Card\n"
                + this.dropOccurance[5] + " Priestess Card\n"
                + this.dropOccurance[6] + " Pyramid Card\n");
    }

    public void printProfit() {
        System.out.println("----------------------\nCurrent Profit"
                + "\nGold: " + this.wallet.get("gold")
                + "\nEcto: " + this.wallet.get("ecto")
                + "\nLucky Draw: " + this.wallet.get("lucky")
                + "\nEffective Gold: " + this.wallet.get("effectiveG")+"\n");
    }

    public void play(int i, int h) {
        switch (h){
            case 0:
                loadMeldDT();
                break;
            case 1: 
                loadTrumpDT();
                break;
            default:
                loadFlushDT();
                break;
        }
        for (int j = 0; j < i; j++) {
            roll();
        }
        printResult();
        calculate();
        printProfit();

    }
    private void clDrop(){
        for(int i = 0; i < dropOccurance.length; i++){
            this.dropOccurance[i] = 0;
        }
    }
    private void loadWallet() {
        this.wallet.put("gold", Float.parseFloat("0"));
        this.wallet.put("ecto", Float.parseFloat("0"));
        this.wallet.put("lucky", Float.parseFloat("0"));
        this.wallet.put("effectiveG", Float.parseFloat("0"));
    }

    private void loadFlushDT() {
        this.LuckyDR = new dropRolls(1600, 1);
        this.MassiveExotDR = new dropRolls(5450, 8);
        this.MassiveAscDR = new dropRolls(300, 1);
        this.GlobDR = new dropRolls(5470, 30);
        this.DragonDR = new dropRolls(8284, 100);
        this.PriestessDR = new dropRolls(1400, 1);
        this.PyramidDR = new dropRolls(-1,1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= 100;
        this.cost[1] = 250;
    }
        private void loadMeldDT() {
        this.LuckyDR = new dropRolls(49, 1);
        this.MassiveExotDR = new dropRolls(500, 1);
        this.MassiveAscDR = new dropRolls(-1, 1);
        this.GlobDR = new dropRolls(4718, 100);
        this.DragonDR = new dropRolls(374, 100);
        this.PriestessDR = new dropRolls(-1,1);
        this.PyramidDR = new dropRolls(-1,1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= 4;
        this.cost[1] = 50;
    }
        private void loadTrumpDT() {
        this.LuckyDR = new dropRolls(-1, 1);
        this.MassiveExotDR = new dropRolls(-1, 1);
        this.MassiveAscDR = new dropRolls(-1, 1);
        this.GlobDR = new dropRolls(4814, 10);
        this.DragonDR = new dropRolls(953, 1);
        this.PriestessDR = new dropRolls(-1, 1);
        this.PyramidDR = new dropRolls(2548,10);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= .4;
        this.cost[1] = 5;
    }

    private void loadValue() {
        this.vOf.put("Lucky Draw", 1);
        this.vOf.put("Massive Glob of Ectoplasm (Exotic)", 50);
        this.vOf.put("Massive Glob of Ectoplasm (Ascended)", 500);
        this.vOf.put("Glob of Ectoplasm", 1);
        this.vOf.put("Dragon Card", 1);
        this.vOf.put("Priestess Card", 100);
        this.vOf.put("Pyramid Card", 1);
    }

    private int rollAssistant(int d, int r) {
        int drop = 0;
        for (int i = 0; i < r; i++) {
            int temp = this.rand.nextInt(10000);
            if (temp <= d) {
                drop++;
            }
        }
        return drop;
    }
    private void setEctoPrice() {
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;
    try{
    client = HttpClient.newHttpClient();
    request = HttpRequest.newBuilder().uri(URI.create("https://api.gw2tp.com/1/items?ids=19721&fields=sell")).build();    
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String s = response.body();
    String[] arr = s.split(",");
    String[] arr2 = arr[1].split("]]");
    ectoPrice = Float.parseFloat(arr2[0])/10000;
    System.out.println("Current price of Ecto: "+ectoPrice);
    }catch(Exception exception){
        System.out.println(exception);
        System.out.println("Unable to set live Ecto price, setting price to .2 G");
        this.ectoPrice = Float.parseFloat("0.2");
        return;
    }
    }
    public void start(){
         while(true){
            System.out.println("Would you like to play?"
                +"\n(Y)es / (N)o / (W)e Ball");
            switch (this.sc.nextLine()) {
                case "y", "Y":
                    System.out.println("(T)rump / (M)eld / (F)lush?"
                        +"\n5 Ecto, .4 G / 50 Ecto, 4 G / 250 Ecto, 100 G");
                    switch (this.sc.nextLine()) {
                        case "T", "t":
                            play(1,1);
                            break;
                        case "M", "m":
                            play(1,0);
                            break;
                        case "F", "f":
                            play(1, 2);
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                    break;
                case "w", "W":
                    play(100, 2);
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
