package com.ncsu.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA. User: shoubhik Date: 7/10/13 Time: 6:20 PM To
 * change this template use File | Settings | File Templates.
 */
public class Driver {

    public static enum StringMatchingAlgos{
        AUTOMATON_SEARCH("AutomatonSearch", 1), RABIN_KARP("RabinKarp", 2),
        SHIFT_OR("ShiftOr", 3);

        private String algoName;
        private int algoCode;

        private StringMatchingAlgos(String algoName, int algoCode) {
            this.algoName = algoName;
            this.algoCode = algoCode;
        }


        private String getAlgoName() {
            return algoName;
        }

        private int getAlgoCode() {
            return algoCode;
        }
    }

    private static int REPEAT = 5;

    public static void main(String[] args) throws IOException {
        int fileSizes[] = {150,200,250,300,500};
        String pattenList[] = {"for", "jungle in general"};
//        if(args.length != 1) {
//            System.out.println("enter 1,2or 3");
//            System.out.println("1-Automaton search");
//            System.out.println("2-Rabin Karp");
//            System.out.println("3-Shift or");
//        }
//        int choice = Integer.parseInt(args[0]);

        System.out.println("Filesize, trialnumber, pattern length, time taken, number of comparisons,number of matches, algo name");
        for(StringMatchingAlgos algos : StringMatchingAlgos.values()){
        for(int fileSize : fileSizes){
            String fileName = String.format("input1_%dmb.txt", fileSize);
            for(String patternStr : pattenList){
                for(int  i =1; i <=REPEAT; i++){
                    switch (algos.getAlgoCode()){
                        case 1:
                            AutomatonSearch automatonSearch = new AutomatonSearch();
                            automatonSearch.createAutomata(patternStr.toCharArray());
                            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
                            String line;
                            long timeTaken = 0;
                            while ((line = br.readLine()) != null) {
                                // process the line.
                                long start  = System.currentTimeMillis();
                                automatonSearch.Aut( line.toCharArray());
                                long end = System.currentTimeMillis();
                                timeTaken += (end-start);
                            }
                            br.close();
                            System.out.printf("%d,%d,%d,%d,%d,%d,%s\n", fileSize, i,
                                              patternStr.length(),
                                              timeTaken,
                                              automatonSearch.getComparisons(),
                                              automatonSearch.getMatch(), algos.getAlgoName());
                            break;
                        case 2:
                            RabinKarp searcher = new RabinKarp(patternStr);
                            br = new BufferedReader(new FileReader(new File(fileName)));
                            timeTaken = 0;
                            while ((line = br.readLine()) != null) {
                                // process the line.
                                long start  = System.currentTimeMillis();
                                searcher.search(line);
                                long end = System.currentTimeMillis();
                                timeTaken += (end-start);
                            }
                            br.close();
                            System.out.printf("%d,%d,%d,%d,%d,%d,%s\n", fileSize, i,
                                              patternStr.length(),
                                              timeTaken,
                                              searcher.getComparison(),
                                              searcher.getMatch(), algos.getAlgoName());
                            break;
                        case 3:
                            ShiftOr shiftOr = ShiftOr.compile(patternStr);
                            br = new BufferedReader(new FileReader(new File(fileName)));
                            timeTaken = 0;
                            while ((line = br.readLine()) != null) {
                                // process the line.
                                long start  = System.currentTimeMillis();
                                shiftOr.findAll(line);
                                long end = System.currentTimeMillis();
                                timeTaken += (end-start);
                            }
                            br.close();
                            System.out.printf("%d,%d,%d,%d,%d,%d,%s\n", fileSize, i,
                                              patternStr.length(),
                                              timeTaken,
                                              shiftOr.getComparison(),
                                              shiftOr.getMatch(),algos.getAlgoName());
                            break;
                    }
                }
            }
        }
    }
    }
}
