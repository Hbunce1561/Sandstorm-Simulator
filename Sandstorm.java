import java.util.*;
import java.net.http.*;
import java.net.URI;


public class Sandstorm {
    private float gold;
    private float ecto;
    private float LD;
    private float ectoPrice; 
    private HashMap<String, dropRolls> dropTable;
    private HashMap<String, Float> wallet;
    private dropRolls LuckyDR;
    private dropRolls MassiveExotDR;
    private dropRolls MassiveAscDR;
    private dropRolls GlobDR;
    private dropRolls DragonDR;
    private dropRolls PriestessDR;
    private dropRolls PyramidDR;
    private float cost[];
    private Random rand;
    private Scanner sc;
    public Sandstorm() {
        this.rand = new Random();
        this.wallet = new HashMap<>();
        this.cost = new float[2];
        this.sc = new Scanner(System.in);
        setEctoPrice();
        loadWallet();
        this.dropTable = new HashMap<>();
        loadFlushDT();
        this.gold = 0;
        this.ecto = 0;
        this.LD = 0;

    }

    public void roll(dropRolls currentRoll) {
        for (int i = 0; i < currentRoll.getRolls(); i++)
        {
            if(this.rand.nextInt(10000) < currentRoll.getDropRate()){
                currentRoll.addCount();
            }
        }
    }

    public void calculate() {
        this.LD += this.LuckyDR.getCount() * this.LuckyDR.getValue();
        this.LuckyDR.clCount();
        this.ecto += this.MassiveExotDR.getCount() * this.MassiveExotDR.getValue();
        this.MassiveExotDR.clCount();
        this.ecto += this.MassiveAscDR.getCount() * this.MassiveAscDR.getValue();
        this.MassiveAscDR.clCount();
        this.ecto += this.GlobDR.getCount() * this.GlobDR.getValue();
        this.GlobDR.clCount();
        this.gold += this.DragonDR.getCount() * this.DragonDR.getValue();
        this.DragonDR.clCount();
        this.gold += this.PriestessDR.getCount() * this.PriestessDR.getValue();
        this.PriestessDR.clCount();
        float Pyrgold = this.PyramidDR.getCount() * this.PyramidDR.getValue();
        Pyrgold /=10;
        this.gold += Pyrgold;
        this.PyramidDR.clCount();
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
    }

    public void printResult() {
         for (Map.Entry<String, dropRolls> entry : dropTable.entrySet()) {
        System.out.println(entry.getValue().getCount() + " " + entry.getKey());
    }
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
            this.wallet.put("gold", this.wallet.get("gold") - this.cost[0]);
            this.wallet.put("ecto", this.wallet.get("ecto") - this.cost[1]);
            for (dropRolls dr : dropTable.values()){
                roll(dr);
            }
        }
        printResult();
        calculate();
        printProfit();

    }
    private void loadWallet() {
        this.wallet.put("gold", 0f);
        this.wallet.put("ecto", 0f);
        this.wallet.put("lucky", 0f);
        this.wallet.put("effectiveG", 0f);
    }

    private void loadFlushDT() {
        this.LuckyDR = new dropRolls(1600, 1, 1);
        this.MassiveExotDR = new dropRolls(5450, 8, 50);
        this.MassiveAscDR = new dropRolls(300, 1, 500);
        this.GlobDR = new dropRolls(5470, 30, 1);
        this.DragonDR = new dropRolls(8284, 100, 1);
        this.PriestessDR = new dropRolls(1400, 1, 100);
        this.PyramidDR = new dropRolls(-1,1, 1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= 100f;
        this.cost[1] = 250f;
    }
        private void loadMeldDT() {
        this.LuckyDR = new dropRolls(49, 1, 1);
        this.MassiveExotDR = new dropRolls(500, 1, 50);
        this.MassiveAscDR = new dropRolls(-1, 1, 500);
        this.GlobDR = new dropRolls(4718, 100, 1);
        this.DragonDR = new dropRolls(374, 100, 1);
        this.PriestessDR = new dropRolls(-1,1,100);
        this.PyramidDR = new dropRolls(-1,1, 1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= 4f;
        this.cost[1] = 50f;
    }
        private void loadTrumpDT() {
        this.LuckyDR = new dropRolls(-1, 1, 1);
        this.MassiveExotDR = new dropRolls(-1, 1, 50);
        this.MassiveAscDR = new dropRolls(-1, 1, 500);
        this.GlobDR = new dropRolls(4814, 10, 1);
        this.DragonDR = new dropRolls(953, 1, 1);
        this.PriestessDR = new dropRolls(-1, 1, 100);
        this.PyramidDR = new dropRolls(2548,10, 1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
        this.dropTable.put("Pyramid Card", this.PyramidDR);
        this.cost[0]= .4f;
        this.cost[1] = 5f;
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
        this.ectoPrice = .2f;
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
