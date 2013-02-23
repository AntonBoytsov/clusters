/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Double;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author anton
 */


public class TreeCreator {
    
    private class Edge {
        
        int v, u;
        double dist;
        
        Edge(int toV, int toU, double toDist) {
            v = toV;
            u = toU;
            dist = toDist;
        }
        
    
    }
    
    private class EdgeComparator implements Comparator<Edge> {
        
        @Override
        public int compare(Edge a, Edge b) {
            
            Double distA = new Double(a.dist);
            Double distB = new Double(b.dist);
            return distA.compareTo(distB);
            
        }
        
    }
    
    int n, m, clusterNumber;
    double maxDist, eps = 0.00000001;
    ArrayList<CPoint> pts;
    ArrayList<Integer> treeId;
    ArrayList<Edge> all, tree;
    ArrayList<ArrayList<Integer>> g;
    ArrayList<Boolean> used;
    
    public ArrayList<Color> getColors(int count) {
        
        ArrayList<Color> c = new ArrayList<Color>(ColorGetter.getColors(count));
        
        return c;
        
    }
    
    int dsuGet(int a) {
        
        if (treeId.get(a) != a)
            treeId.set(a, dsuGet(treeId.get(a)));
        return treeId.get(a);
        
    }
    
    void dsuUnite(int a, int b) {
        
        a = dsuGet(a);
        b = dsuGet(b);
        
        if (a == b)
            return;
        
        if (new Random().nextInt(2) == 0) {
            int c = a;
            a = b;
            b = c;
        }
        treeId.set(b, a);
        
        
    }
    
    void dfs(int v, int color) {
        
        used.set(v, Boolean.TRUE);
        treeId.set(v, color);
        for (int i = 0; i < g.get(v).size(); i++) {
            
            if (!used.get(g.get(v).get(i).intValue())) {
                dfs(g.get(v).get(i).intValue(), color);
            }
            
        }
        
    }
    
    double countDist(CPoint v, CPoint u) {
        
        return Math.sqrt(Math.pow(v.x - u.x, 2) + Math.pow(v.y - u.y, 2));
        
    }
    
    TreeCreator(double toDist, ArrayList<CPoint> toPts) {
        
        maxDist = toDist;
        pts = new ArrayList<CPoint>(toPts);
        n = pts.size();
        
        all = new ArrayList<Edge>();
        for (int i = 0; i < pts.size(); i++) {
            for (int j = i; j < pts.size(); j++) {
                all.add(new Edge(i, j, countDist(pts.get(i), pts.get(j))));
            }
        }
        m = all.size();
        clusterNumber = n;
        
        
    }
    
    void makeTree() {
        
        tree = new ArrayList<Edge>();
        Collections.sort(all, new EdgeComparator());
        
        treeId = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            treeId.add(i);
        }
        
        for (int i = 0; i < m; i++) {
            
            int a = all.get(i).u;
            int b = all.get(i).v;
            
            if (dsuGet(a) != dsuGet(b)) {
                
                tree.add(all.get(i));
                dsuUnite(a, b);
                
            }
            
        }
        
        treeId = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            treeId.add(0);
        }
        
        g = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<Integer>());
        }
        
        for (int i = 0; i < tree.size(); i++) {
            
            if (tree.get(i).dist > maxDist + eps)
                continue;
            g.get(tree.get(i).u).add(new Integer(tree.get(i).v));
            g.get(tree.get(i).v).add(new Integer(tree.get(i).u));
            
        }
        
        int vertexId = 0;
        used = new ArrayList<Boolean>();
        for (int i = 0; i < n; i++) {
            used.add(false);
        }
        
        for (int i = 0; i < n; i++) {
            if (!used.get(i)) {
                dfs(i, vertexId);
                vertexId++;
            }
        }
        
        clusterNumber = vertexId;
        
        ArrayList<Color> colors = new ArrayList<Color>(getColors(vertexId));
        
        
        for (int i = 0; i < n; i++) {
            
            pts.set(i, new CPoint(pts.get(i).x, pts.get(i).y, colors.get(treeId.get(i))));
            
        }
        
    }
    
    ArrayList<CPoint> getPoints() {
        
        return pts;
        
    }
    
}
