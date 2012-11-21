package pl.edu.agh.megamud.mapeditor;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MyMouseMenus {
    
    public static class EdgeMenu extends JPopupMenu {        
        public EdgeMenu(final JFrame frame) {
            super("Edge Menu");
            this.add(new DeleteEdgeMenuItem<GraphElements.MyEdge>());
            this.addSeparator();
            this.add(new EdgePropItem(frame));           
        }
        
    }
    
    public static class EdgePropItem extends JMenuItem implements EdgeMenuListener<pl.edu.agh.megamud.mapeditor.GraphElements.MyEdge>,
            MenuPointListener {
        GraphElements.MyEdge edge;
        VisualizationViewer visComp;
        Point2D point;
        
        public void setEdgeAndView(GraphElements.MyEdge edge, VisualizationViewer visComp) {
            this.edge = edge;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }
        
        public  EdgePropItem(final JFrame frame) {            
            super("Edit Edge Properties...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EdgePropertyDialog dialog = new EdgePropertyDialog(frame, edge);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }
                
            });
        }
        
    }
    
    public static class VertexMenu extends JPopupMenu {
        public VertexMenu(final JFrame frame) {
            super("Vertex Menu");
            this.add(new DeleteVertexMenuItem<GraphElements.MyVertex>());
            this.addSeparator();
            this.add(new VertexPropItem(frame));
        }
    }
    
    public static class VertexPropItem extends JMenuItem implements VertexMenuListener<GraphElements.MyVertex>,
            MenuPointListener {
        GraphElements.MyVertex vertex;
        VisualizationViewer visComp;
        Point2D point;
        
        public void setVertexAndView(GraphElements.MyVertex vertex, VisualizationViewer visComp) {
            this.vertex = vertex;
            this.visComp = visComp;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }
        
        public  VertexPropItem(final JFrame frame) {            
            super("Edit location...");
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    VertexPropertyDialog dialog = new VertexPropertyDialog(frame, vertex);
                    dialog.setLocation((int)point.getX()+ frame.getX(), (int)point.getY()+ frame.getY());
                    dialog.setVisible(true);
                }
                
            });
        }
        
    } 
}
