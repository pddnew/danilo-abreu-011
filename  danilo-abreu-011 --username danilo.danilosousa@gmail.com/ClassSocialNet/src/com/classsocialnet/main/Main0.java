package com.classsocialnet.main;

import java.util.ArrayList;
import java.util.List;

import com.classsocialnet.gui.Window;
import com.classsocialnet.model.Link;
import com.classsocialnet.model.Student;
import com.classsocialnet.util.LoadData;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;


/**
 * This is main execute Java Program
 * <BR/>
 * No argments is required.
 * 
 * @author Camilla Calcagno
 * @since November 2012
 * 
 * */
public class Main0 {
	
	
	public static void main(String[] args){
				
		Window w = new Window( init());
		System.out.println(w);
	}
	

	public static Graph<Student, Link> init(){
		
		Graph<Student, Link> g = new SparseMultigraph<Student, Link>();//DirectedSparseMultigraph
		
		// Get Students from text File
		String[][] rms = LoadData.readTextFiles();
		
		// Add all Students to List
		List<Student> list = new ArrayList<Student>();
		for (int i = 0; i < rms.length; i++)
			list.add(new Student(rms[i][0]));
			
		//Add Vertex and respectives Edges
		for (int i = 0; i < rms.length; i++) {
			g.addVertex(list.get(i));
			for (int j = 1; j < rms[i].length; j++) {
				try{
					Student s = find(list, new Student(rms[i][j]));
					
					g.addEdge(new Link(i+":"+j), list.get(i), s);
					
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}		
		}
		return g;
	}

	public static Student find(List<Student> l, Student s){
		for (int i = 0; i < l.size(); i++) {
			if(l.get(i).equals(s))
				return l.get(i);
		}
		throw new RuntimeException(s + " not found!");
	}
}
