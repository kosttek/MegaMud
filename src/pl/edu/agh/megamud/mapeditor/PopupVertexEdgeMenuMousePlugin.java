package pl.edu.agh.megamud.mapeditor;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;

public class PopupVertexEdgeMenuMousePlugin<V, E> extends AbstractPopupGraphMousePlugin {
    private JPopupMenu edgePopup, vertexPopup;

    public PopupVertexEdgeMenuMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }
    
    public PopupVertexEdgeMenuMousePlugin(int modifiers) {
        super(modifiers);
    }
    
    protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)e.getSource();
        Point2D p = e.getPoint();
        
        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
            final V v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                updateVertexMenu(v, vv, p);
                vertexPopup.show(vv, e.getX(), e.getY());
            } else {
                final E edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                if(edge != null) {
                    updateEdgeMenu(edge, vv, p);
                    edgePopup.show(vv, e.getX(), e.getY());
                  
                }
            }
        }
    }
    
    private void updateVertexMenu(V v, VisualizationViewer vv, Point2D point) {
        if (vertexPopup == null) return;
        Component[] menuComps = vertexPopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof VertexMenuListener) {
                ((VertexMenuListener)comp).setVertexAndView(v, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
        
    }
    
    public JPopupMenu getEdgePopup() {
        return edgePopup;
    }
    
    public void setEdgePopup(JPopupMenu edgePopup) {
        this.edgePopup = edgePopup;
    }
    
    public JPopupMenu getVertexPopup() {
        return vertexPopup;
    }
    
    public void setVertexPopup(JPopupMenu vertexPopup) {
        this.vertexPopup = vertexPopup;
    }
    
    private void updateEdgeMenu(E edge, VisualizationViewer vv, Point2D point) {
        if (edgePopup == null) return;
        Component[] menuComps = edgePopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof EdgeMenuListener) {
                ((EdgeMenuListener)comp).setEdgeAndView(edge, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
    }
    
}
