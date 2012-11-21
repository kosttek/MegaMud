package pl.edu.agh.megamud.mapeditor;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class DeleteEdgeMenuItem<E> extends JMenuItem implements EdgeMenuListener<E> {
    private E edge;
    private VisualizationViewer visComp;
    
    public DeleteEdgeMenuItem() {
        super("Delete Edge");
        this.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                visComp.getPickedEdgeState().pick(edge, false);
                visComp.getGraphLayout().getGraph().removeEdge(edge);
                visComp.repaint();
            }
        });
    }

    public void setEdgeAndView(E edge, VisualizationViewer visComp) {
        this.edge = edge;
        this.visComp = visComp;
        this.setText("Delete Edge " + edge.toString());
    }
    
}
