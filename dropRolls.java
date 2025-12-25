
public class dropRolls{

private int dropRate;
private int rolls;

public dropRolls (int i, int j){
    setDropRate(i);
    setRolls(j);
}
public void setDropRate(int i){
    this.dropRate = i;
}
public void setRolls(int i){
    this.rolls = i;
}
public int getDropRate(){
    return this.dropRate;
}
public int getRolls(){
    return this.rolls;
}
}
