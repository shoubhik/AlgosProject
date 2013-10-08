package com.ncsu.project;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA. User: shoubhik Date: 31/8/13 Time: 9:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class AutomatonSearch {

    private long match = 0;
    private long comparisons ;
    private Graph aut;

    void preAut(char x[], Graph g){
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

    public void createAutomata(char pattern[]){
        aut = Graph.newAutomaton(pattern.length+1, (pattern.length+1)*256);
        preAut(pattern,aut);
    }

    public void Aut(/*char pattern[],*/ char text[]) {

        int j, state;
        for(state=aut.getInitial(), j=0; j<text.length; ++j){
            state = aut.getTarget(state, text[j]);
            if(aut.isTerminal(state)){
                match++;
            }
            comparisons++; // comparison is made everytime
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public AutomatonSearch(){
        comparisons = 0;
    }

    public long getMatch() {
        return match;
    }
//    public static void main(String[] args) throws IOException {
//        char x[] = new String("several").toCharArray();
//        String y = "nand nand";
//        long startRead = System.currentTimeMillis();
//        byte[] encoded = Files.readAllBytes(Paths.get("input1_100mb.txt"));
//        Charset encoding = StandardCharsets.US_ASCII;
//        y = encoding.decode(ByteBuffer.wrap(encoded)).toString();
//        long endRead = System.currentTimeMillis();
//        System.out.println("Total time to read = " + (endRead-startRead));
//        long start = System.currentTimeMillis();
//        Aut(x, y.toCharArray());
//        long end = System.currentTimeMillis();
//        System.out.println("time taken = " + (end-start));
//        System.out.println("Number of matches = " + match);
//    }
}
