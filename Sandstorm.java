import java.util.*;
import java.math.*;
import java.net.http.*;
import java.text.DecimalFormat;
import java.net.URI;

public class Sandstorm {
    private double gold;
    private double ecto;
    private double LD;
    private double ectoPrice; 
    private HashMap<String, dropRolls> dropTable;
    private HashMap<String, Integer> vOf;
    private HashMap<String, Double> wallet;
    private dropRolls LuckyDR;
    private dropRolls MassiveExotDR;
    private dropRolls MassiveAscDR;
    private dropRolls GlobDR;
    private dropRolls DragonDR;
    private dropRolls PriestessDR;
    static int lengthArr = 6;
    private Random rand;
    private int dropOccurance[];

    public Sandstorm() {
        this.rand = new Random();
        this.dropOccurance = new int[lengthArr];
        this.wallet = new HashMap<>();
        setEctoPrice();
        loadWallet();
        this.dropTable = new HashMap<>();
        loadDT();
        this.vOf = new HashMap<>();
        loadValue();
        clDrop();
        this.gold = 0.0;
        this.ecto = 0;
        this.LD = 0;

    }

    public void roll() {
        this.wallet.put("gold", this.wallet.get("gold") - 100.0);
        this.wallet.put("ecto", this.wallet.get("ecto") - 250);
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

    }

    public void calculate() {
        this.LD += this.dropOccurance[0] * this.vOf.get("Lucky Draw");
        this.ecto += this.dropOccurance[1] * this.vOf.get("Massive Glob of Ectoplasm (Exotic)");
        this.ecto += this.dropOccurance[2] * this.vOf.get("Massive Glob of Ectoplasm (Ascended)");
        this.ecto += this.dropOccurance[3] * this.vOf.get("Glob of Ectoplasm");
        this.gold += this.dropOccurance[4] * this.vOf.get("Dragon Card");
        this.gold += this.dropOccurance[5] * this.vOf.get("Priestess Card");
        this.wallet.put("gold", this.wallet.get("gold") + gold);
        this.wallet.put("ecto", this.wallet.get("ecto") + ecto);
        this.wallet.put("lucky", this.wallet.get("lucky") + LD);
        double ectoVal = this.ectoPrice * this.wallet.get("ecto"); 
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
                + this.dropOccurance[5] + " Priestess Card\n");
    }

    public void printProfit() {
        System.out.println("----------------------\nCurrent Profit"
                + "\nGold: " + this.wallet.get("gold")
                + "\nEcto: " + this.wallet.get("ecto")
                + "\nLucky Draw: " + this.wallet.get("lucky")
                + "\nEffective Gold: " + this.wallet.get("effectiveG")+"\n");
    }

    public void play(int i) {
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
        this.wallet.put("gold", 0.0);
        this.wallet.put("ecto", 0.0);
        this.wallet.put("lucky", 0.0);
        this.wallet.put("effectiveG", 0.0);
    }

    private void loadDT() {
        this.LuckyDR = new dropRolls(1600, 1);
        this.MassiveExotDR = new dropRolls(5450, 8);
        this.MassiveAscDR = new dropRolls(300, 1);
        this.GlobDR = new dropRolls(5470, 30);
        this.DragonDR = new dropRolls(8284, 100);
        this.PriestessDR = new dropRolls(1400, 1);
        this.dropTable.put("Lucky Draw", this.LuckyDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Exotic)", this.MassiveExotDR);
        this.dropTable.put("Massive Glob of Ectoplasm (Ascended)", this.MassiveAscDR);
        this.dropTable.put("Glob of Ectoplasm", this.GlobDR);
        this.dropTable.put("Dragon Card", this.DragonDR);
        this.dropTable.put("Priestess Card", this.PriestessDR);
    }

    private void loadValue() {
        this.vOf.put("Lucky Draw", 1);
        this.vOf.put("Massive Glob of Ectoplasm (Exotic)", 50);
        this.vOf.put("Massive Glob of Ectoplasm (Ascended)", 500);
        this.vOf.put("Glob of Ectoplasm", 1);
        this.vOf.put("Dragon Card", 1);
        this.vOf.put("Priestess Card", 100);
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
    ectoPrice = Double.parseDouble(arr2[0])/10000;
    System.out.println("Current price of Ecto: "+ectoPrice);
    }catch(Exception exception){
        System.out.println(exception);
        System.out.println("Unable to set live Ecto price, setting price to .2 G");
        this.ectoPrice = .2;
        return;
    }
    }

}
