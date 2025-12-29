
public class dropRolls{

private int dropRate;
private int rolls;
private int value;

public dropRolls (int i, int j, int k){
    setDropRate(i);
    setRolls(j);
    setValue(k);
}
public void setValue(int i){
    this.value = i;
}
public void setDropRate(int i){
    this.dropRate = i;
}
public void setRolls(int i){
    this.rolls = i;
}
public int getValue(){
    return this.value;
}
public int getDropRate(){
    return this.dropRate;
}
public int getRolls(){
    return this.rolls;
}
}
