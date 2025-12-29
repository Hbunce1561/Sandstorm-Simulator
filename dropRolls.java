
public class dropRolls{

private int dropRate;
private int rolls;
private int value;
private int count;


public dropRolls (int i, int j, int k){
    setDropRate(i);
    setRolls(j);
    setValue(k);
    setCount(0);
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
public void setCount(int i){
    this.count = i; 
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
public int getCount(){
    return this.count;
}
public void addCount(){
    this.count++;
}
public void clCount(){
    this.count = 0;
}


}
