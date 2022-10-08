package chapter01;

public class Apple {
    //intellij 생성자 자동완성 단축키 : command + n
    int wegiht;
    public Apple() {
    }
    public Apple(int wegiht){
        this.wegiht = wegiht;
    }

    public int getWegiht() {
        return wegiht;
    }

    public void setWegiht(int wegiht) {
        this.wegiht = wegiht;
    }
}
