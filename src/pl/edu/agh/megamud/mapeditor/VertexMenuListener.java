package pl.edu.agh.megamud.mapeditor;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface VertexMenuListener<V> {
    void setVertexAndView(V v, VisualizationViewer visView);    
}
