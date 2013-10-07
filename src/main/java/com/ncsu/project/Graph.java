package com.ncsu.project;

import javax.lang.model.type.TypeKind;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: shoubhik Date: 31/8/13 Time: 2:33 PM To
 * change this template use File | Settings | File Templates.
 */
public class Graph {

    int vertexNumber;
    int edgeNumber;
    int vertexCounter;
    int initial;
    List<Integer> terminal;
    List<Integer> target;
    List<Integer> suffixLink;
    List<Integer> length;
    List<Integer> position;
    List<Integer> shift;

    /**
     * graph with v vertices and e edges
     *
     * @param v
     * @param e
     */
    public Graph(int v, int e) {
        this.vertexNumber = v;
        this.edgeNumber = e;
        this.initial = 0;
        this.vertexCounter = 1;
    }

    public static Graph newAutomaton(int v, int e) {
        Graph g = new Graph(v, e);
        g.target = new ArrayList<Integer>(e);
        for(int i = 0;i<e;i++)
            g.target.add(i,0);
        g.terminal = new ArrayList<Integer>(v);
        for(int i = 0;i<v;i++)
            g.terminal.add(i,0);
        return g;
    }

    public int getInitial() {
        return this.initial;
    }

    /**
     * returns the target of edge from vertex v labelled by character c in graph
     * g
     *
     * @param v
     * @param c
     * @return
     */
    public int getTarget(int v, char c) {
        if (this.target != null && v < this.vertexNumber && v * (int) c <
                this.edgeNumber)
            return this.target.get(v * (edgeNumber / vertexNumber) + (int) c);
        throw new IllegalStateException();
    }

    public int newVertex() {
        if (this.vertexCounter <= this.vertexNumber) return vertexCounter++;
        throw new IllegalStateException();
    }

    /**
     * add the edge from vertex v to vertex t labelled by character c in graph
     * g
     */
    public void setTarget(int v, char c, int t) {
        if (target != null && v < vertexNumber && v * (int) c < edgeNumber &&
                t < vertexNumber)
            target.set(v * (edgeNumber / vertexNumber) + (int) c, t);
        else throw new IllegalStateException();
    }

    /**
     * copies all characteristics of source vertex to destination vertex
     *
     * @param target
     * @param source
     * @return
     */
    public void copyVertex(int target, int source) {
        if (target < vertexNumber && source < vertexNumber) {
            if (this.target != null) {
                for (int i = 0; i < (edgeNumber / vertexNumber); i++) {
                    int val = this.target
                            .get((source * (edgeNumber / vertexNumber)) + i);
                    this.target.set((target * (edgeNumber / vertexNumber)) + i,
                                    val);
                }
            }
            if(this.shift != null){
                for (int i = 0; i < (edgeNumber / vertexNumber); i++) {
                    int val = this.shift
                            .get((source * (edgeNumber / vertexNumber)) + i);
                    this.shift.set((target * (edgeNumber / vertexNumber)) + i,
                                    val);
                }
            }
            if(this.terminal != null)
                this.terminal.set(target, this.terminal.get(source));
            if(this.suffixLink != null)
                this.suffixLink.set(target, this.suffixLink.get(source));
            if(this.length != null){
                this.length.set(target, this.length.get(source));
            }
            if(this.position != null)
                this.position.set(target, this.position.get(source));
        }
        else
            throw new IllegalStateException();
    }

    public void setTerminal(int v){
        if(this.terminal != null && v < this.vertexNumber)
            this.terminal.set(v, 1);
        else
            throw new IllegalStateException();
    }

    public boolean isTerminal(int v){
        if(terminal != null && v < vertexNumber){
            return terminal.get(v) ==  1;
        }
        throw new IllegalStateException();
    }
}