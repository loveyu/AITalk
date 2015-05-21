package net.loveyu.aitalk;

import java.util.Stack;

public class Talk {
    public String msg;
    public final static int ME = 0;
    public final static int Robots = 1;
    public int type = ME;
    public static Stack<Talk> selfList = null;

    public Talk(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public static void initList() {
        if (selfList != null) {
            selfList.clear();
        }
        selfList = new Stack<Talk>();
    }

    public static void add(String msg, int type) {
        selfList.push(new Talk(msg, type));
    }

    public static void add(Talk talk) {
        selfList.push(talk);
    }
}
