package com.ojas.Phase2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class  Barchart {

	public static void main( String[ ] args )throws Exception {
	      
	      String data[ ] = {    
	         "Name" ,   
	         "Email" ,   
	         "Country" ,    
	         "Phone" ,
	         "Email2",
	         "Dob"
	      };
	      
	      InputStream in = new FileInputStream( new File( "menDataIndianfull.txt" ) );          
	      BufferedReader reader = new BufferedReader(new InputStreamReader(in ) );          
	      StringBuilder out = new StringBuilder();          
	      String line;          
	      DefaultPieDataset dataset = new DefaultPieDataset();          

	      while (( line = reader.readLine() ) != null ) {
	         out.append( line );
	      }
	      
	      StringTokenizer s = new StringTokenizer( out.toString(), "," );
	      int i = 0;      
	      
	      while( s.hasMoreTokens( ) && ( data [i] != null ) ) {
	         dataset.setValue(data[i], Double.parseDouble( s.nextToken( ) ));
	         i++;
	      }
	      
	      JFreeChart chart = ChartFactory.createPieChart( 
	         "data",    // chart title           
	         dataset,           // data           
	         true,              // include legend           
	         true,           
	         false);
	      
	      int width = 560;    /* Width of the image */          
	      int height = 370;   /* Height of the image */                          
	      File outputFile = new File("barchart.png");                        
	      ChartUtils.saveChartAsJPEG( outputFile, chart, width, height); 
	   }
	}

