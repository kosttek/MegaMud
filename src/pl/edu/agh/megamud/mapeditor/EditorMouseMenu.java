package pl.edu.agh.megamud.mapeditor;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

public class EditorMouseMenu {
	
	public static DirectedSparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge> g;
	
    public static void main(String[] args) {        
        JFrame frame = new JFrame("MUD Map Editor");
        g = new DirectedSparseMultigraph<GraphElements.MyVertex, GraphElements.MyEdge>();
        Layout<GraphElements.MyVertex, GraphElements.MyEdge> layout = new StaticLayout(g);
        layout.setSize(new Dimension(300,300));
        VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge> vv = 
                new VisualizationViewer<GraphElements.MyVertex,GraphElements.MyEdge>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
                 GraphElements.MyVertexFactory.getInstance(),
                GraphElements.MyEdgeFactory.getInstance()); 
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        JPopupMenu edgeMenu = new MyMouseMenus.EdgeMenu(frame);
        JPopupMenu vertexMenu = new MyMouseMenus.VertexMenu(frame);
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        gm.remove(gm.getPopupEditingPlugin());
        gm.add(myPlugin);
        
        vv.setGraphMouse(gm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null);
        modeMenu.setPreferredSize(new Dimension(80,20));
        
        menuBar.add(modeMenu);
        frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.EDITING);
        frame.pack();
        frame.setSize(300,300);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);    
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	try {
					Export.saveToDatabase();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	System.exit(0);
            }});
    }
    
}
