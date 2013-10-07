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

    private static long match = 0;

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
            if(aut.isTerminal(state)) match++;
        }
    }

    public static void main(String[] args) throws IOException {
        char x[] = new String("several").toCharArray();
        String y = "nand nand";
        long startRead = System.currentTimeMillis();
        byte[] encoded = Files.readAllBytes(Paths.get("input1_100mb.txt"));
        Charset encoding = StandardCharsets.US_ASCII;
        y = encoding.decode(ByteBuffer.wrap(encoded)).toString();
        long endRead = System.currentTimeMillis();
        System.out.println("Total time to read = " + (endRead-startRead));
        long start = System.currentTimeMillis();
        Aut(x, y.toCharArray());
        long end = System.currentTimeMillis();
        System.out.println("time taken = " + (end-start));
        System.out.println("Number of matches = " + match);
    }
}
