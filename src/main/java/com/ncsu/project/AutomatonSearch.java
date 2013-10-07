package com.ncsu.project;

/**
 * Created with IntelliJ IDEA. User: shoubhik Date: 31/8/13 Time: 9:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class AutomatonSearch {

    static void preAut(char x[], Graph g){
        int state, i;
       for(state = g.getInitial(), i=0;i<x.length; ++i){
           int oldTarget = g.getTarget(state, x[i]);
           int target = g.newVertex();
           g.setTarget(state, x[i], target);
           g.copyVertex(target, oldTarget);
           state = target;
       }
       g.setTerminal(state);
    }

    public static void Aut(char x[], char y[]) {
        Graph aut = Graph.newAutomaton(x.length+1, (x.length+1)*256);
        int j, state;
        preAut(x,aut);
        for(state=aut.getInitial(), j=0; j<y.length; ++j){
            state = aut.getTarget(state, y[j]);
            if(aut.isTerminal(state)) System.out.println(j-x.length+1);
        }
    }

    public static void main(String[] args) {
        char x[] = {'a','n', 'd'};
        String y = "nand nand";
        Aut(x, y.toCharArray());
    }
}
