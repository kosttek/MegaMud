package pl.edu.agh.megamud.mapeditor;

import org.apache.commons.collections15.Factory;

public class GraphElements {
    
    public GraphElements() {
    }
    
    public static class MyVertex {
        private String name;
        private String description;
        
        public MyVertex(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public static class MyEdge {
        private String name;

        public MyEdge(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }             
        
        public String toString() {
            return name;
        }
    }
    
    public static class MyVertexFactory implements Factory<MyVertex> {
        private static int nodeCount = 0;
        private static MyVertexFactory instance = new MyVertexFactory();
        
        private MyVertexFactory() {            
        }
        
        public static MyVertexFactory getInstance() {
            return instance;
        }
        
        public GraphElements.MyVertex create() {
            String name = "Location " + nodeCount++;
            MyVertex v = new MyVertex(name);
            return v;
        }        
    }
    
    public static class MyEdgeFactory implements Factory<MyEdge> {
        private static int linkCount = 0;

        private static MyEdgeFactory instance = new MyEdgeFactory();
        
        private MyEdgeFactory() {            
        }
        
        public static MyEdgeFactory getInstance() {
            return instance;
        }
        
        public GraphElements.MyEdge create() {
            String name = "Portal " + linkCount++;
            MyEdge link = new MyEdge(name);
            return link;
        }      
    }

}
