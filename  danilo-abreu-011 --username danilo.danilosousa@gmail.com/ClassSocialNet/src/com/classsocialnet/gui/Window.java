package com.classsocialnet.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import com.classsocialnet.model.Link;
import com.classsocialnet.model.Student;
import com.classsocialnet.util.ListUtils;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class Window extends JFrame {

	private static final long serialVersionUID = 3652714867990399965L;
	
	private Container c;
	private Graph<Student, Link> g;
	
	private final List<Student> ls;
	
	public Window(Graph<Student, Link> g){
		super("CLASS SOCIAL NETWORK - GRAPH ANALYSIS");
		
		this.g = g;
		this.ls = getStudentAll();
		
		c = getContentPane();		
		c.setLayout( new BorderLayout());		
		
		
		addNortPanel();
		addMasterPanel();
		addSouthPanel();
        
        this.pack();
        this.setSize(1280, 640);        
        this.setResizable(true);        
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);
	}
	
	public void addMasterPanel(){
		
		//Create Layout. Choosen ISOMLayout. Add Graph<V, E>
		Layout<Student, Link> layout = new ISOMLayout<Student, Link>(g);		
		layout.setSize(new Dimension(1280, 680));
		
		// Create ModalGraph Mouse - To interactive mode
		DefaultModalGraphMouse<Student, Link> gm = new DefaultModalGraphMouse<Student, Link>();
//				PluggableGraphMouse gm = new PluggableGraphMouse();
//		gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
		gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
		
		// Visualization Viewer. Choosen PICKING Modal Graph
		VisualizationViewer<Student, Link> vv = new VisualizationViewer<Student, Link>(layout );
		vv.setPreferredSize(new Dimension(1280,680));
		gm.setMode(ModalGraphMouse.Mode.PICKING);
//				gm.setMode(ModalGraphMouse.Mode.EDITING);
//				gm.add(new TranslatingGraphMousePlugin());
//				gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
		
		vv.setGraphMouse(gm);
		
		// Style for Edges to improve the visualization.
		float[] dash = {10.0f};		
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 10.0f, dash, 0.0f);
		Transformer<Link, Stroke> transformer = new Transformer<Link, Stroke> (){
			@Override
			public Stroke transform(Link arg0) {
				return edgeStroke;
			}			
		};
		
		//Style Vertex Geom form to improve the visualization.
		final Transformer<Student, Shape> vertexShape = new Transformer<Student, Shape>() {			
			@Override
			public Shape transform(Student arg0) {
				return new Ellipse2D.Double(-5, -5, 15, 10);				
			}
		}; 
		
		 //Style Vertex Paint color to improve the visualization.
		final Transformer<Student, Paint> vertexPaint = new Transformer<Student, Paint>(){
			@Override
			public Paint transform(Student arg0) {
				if(arg0.getRm().endsWith("1"))
					return Color.WHITE;
				if(arg0.getRm().endsWith("2"))
					return Color.BLACK;
				if(arg0.getRm().endsWith("3"))
					return Color.BLUE;
				if(arg0.getRm().endsWith("4"))
					return Color.CYAN;
				if(arg0.getRm().endsWith("5"))
					return Color.DARK_GRAY;
				if(arg0.getRm().endsWith("6"))
					return Color.GRAY;
				if(arg0.getRm().endsWith("7"))
					return Color.YELLOW;
				if(arg0.getRm().endsWith("8"))
					return Color.LIGHT_GRAY;
				if(arg0.getRm().endsWith("9"))
					return Color.MAGENTA;
				if(arg0.getRm().endsWith("0"))
					return Color.ORANGE;
				else
					return Color.GREEN;
			}
		};
				
		vv.getRenderContext().setVertexShapeTransformer(vertexShape);
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setVertexLabelTransformer( new ToStringLabeller<Student>());
		vv.getRenderContext().setEdgeStrokeTransformer(transformer);
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<Link>());
		
		
		vv.setBackground(Color.LIGHT_GRAY);
		
		vv.repaint();
		
		c.add(vv, BorderLayout.CENTER);
	}
	
	public void addSouthPanel(){
		
		JLabel lbl0 = new JLabel("STUDENTS (FROM) " );
		lbl0.setForeground(Color.WHITE);
		
		JLabel lbl1 = new JLabel("(TO) " );
		lbl1.setForeground(Color.WHITE);
		
		JLabel lbl2 = new JLabel(" DISTANCE " );
		lbl2.setForeground(Color.WHITE);
		
		final JComboBox<String> select0 = new JComboBox<String>();
		final JComboBox<String> select1 = new JComboBox<String>();
		final JComboBox<Integer> select2 = new JComboBox<Integer>();
		
		for(Student desc : ls){
        	select0.addItem(desc.toString());
        	select1.addItem(desc.toString());
        }	
        
		select2.addItem(1);select2.addItem(2);select2.addItem(3);
		select2.addItem(4);select2.addItem(5);
		
        JButton btn0 = new JButton(" >> Shortest Path << ");
		btn0.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Student s0 = new Student(select0.getSelectedItem().toString());
				Student s1 = new Student(select1.getSelectedItem().toString());
				
				JOptionPane.showMessageDialog(null, ListUtils.toString(getShortestPath(s0, s1 )));
			}
		});
        
		
        JButton btn1 = new JButton(" >> Max Possible contact << ");
		btn1.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Student s0 = new Student(select0.getSelectedItem().toString());
				
				int level = Integer.parseInt(select2.getSelectedItem().toString());
				
				JOptionPane.showMessageDialog(null, ListUtils.toString(getNeighborhood(s0, level)));
			}
		});
		
        JButton btn2 = new JButton(" >> Not Connected << ");
		btn2.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				Student s0 = new Student(select0.getSelectedItem().toString());
				String out = ListUtils.toString(getNotConnected(s0));
				JOptionPane.showMessageDialog(null, out);
			}
		});
		
        JPanel panel0 = new JPanel();
        panel0.add(lbl0);
        panel0.add(select0);
        panel0.add(lbl1);
        panel0.add(select1);
        panel0.add(btn0);
        panel0.add(lbl2);
        panel0.add(select2);
        panel0.add(btn1);
        panel0.add(btn2);
        panel0.setBackground(Color.DARK_GRAY);
        c.add(panel0, BorderLayout.SOUTH);
	}
	
	public void addNortPanel(){
		JLabel lbl0 = new JLabel("ANALISE EM GRAFO - REDE SOCIAL " );
		lbl0.setForeground(Color.WHITE);
		

        JButton btn0 = new JButton(" >> Generate << ");
		btn0.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
        
		
		JPanel panel0 = new JPanel();
	    panel0.add(lbl0);		
	    panel0.setBackground(Color.DARK_GRAY);
	    
//	    panel0.add(btn0);
        c.add(panel0, BorderLayout.NORTH);
	}

	public List<Student> getNeighborhood(Student s0, int level){
		
		KNeighborhoodFilter<Student, Link> n = new KNeighborhoodFilter<Student, Link>(s0, level,  KNeighborhoodFilter.EdgeType.OUT);
		
		Graph<Student, Link> subg = n.transform(g);
		
		return new ArrayList<Student>(subg.getVertices());
	}
	
	public Graph<Student, Link> getNeighborhood(Student s0, int level, boolean is){
		KNeighborhoodFilter<Student, Link> n = new KNeighborhoodFilter<Student, Link>(s0, level,  KNeighborhoodFilter.EdgeType.OUT);
		
		return n.transform(g);
	}
	
	public List<Student> getNotConnected(Student s0){
		WeakComponentClusterer<Student, Link> wcc = new WeakComponentClusterer<>();
		
		List<Student> nonconnectedList = new ArrayList<Student>();
		
		Set<Set<Student>> clusters = wcc.transform(g);
		
		Iterator<Set<Student>> it = clusters.iterator();
		
		while(it.hasNext()){
			Set<Student> cl0 = it.next();
			
			if(!cl0.contains(s0))
				nonconnectedList.addAll(cl0);
		}	
		
		return nonconnectedList;
	}
	
	public List<Link> getShortestPath(Student s0, Student s1){
		
		DijkstraShortestPath<Student, Link> dijkstra = new DijkstraShortestPath<Student, Link>(g);
		
		return dijkstra.getPath(s0, s1);
	}

	public List<Student> getStudentAll(){
		return new ArrayList<Student>(g.getVertices());
	}
}
