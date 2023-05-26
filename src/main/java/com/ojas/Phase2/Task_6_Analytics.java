package com.ojas.Phase2;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;



public class Task_6_Analytics {
	public static void main(String[] args) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Select Input File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getPath();

			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				List<String[]> lines = new ArrayList<>();

				String line;
				while ((line = br.readLine()) != null) {
					String[] lineArray = line.split(",");
					lines.add(lineArray);
					
				}

				
				int index = 1;
				for (String[] lineArray : lines) {
					try {
						
						String Ages=lineArray[6];
						System.out.println(lineArray[6]);
						String[] age = Ages.split(" ");

						String agecrit = age[0];

						System.out.println(agecrit);
						int Age1=Integer.parseInt(agecrit);
						
						//int age = Integer.parseInt(lineArray[6]);
						if (Age1 <= 20) {
							//category = "Teen Age";
							dataset.addValue(Age1, "Teens","Teen Age");

						} else if (Age1 > 20 && Age1 <= 30) {
							//category = "Young Age";
							dataset.addValue(Age1, "Youths","Young Age");
						} else if (Age1 > 30 && Age1 <= 60) {
							//category = "Middle Age";
							dataset.addValue(Age1, "Middlers","Middle Age");
						} else {
							//category = "Old Age";
							dataset.addValue(Age1, "Oldies", "Old Age");
						}
						
						
					} catch (NumberFormatException e) {
						System.out.println("Invalid value in line: " + String.join(",", lineArray));
					}
					index++;
				}
			

				// Create the chart
				JFreeChart chart = ChartFactory.createBarChart("Age Distribution", // Chart title
						"Category", // X-axis label
						"Value", // Y-axis label
						dataset, PlotOrientation.VERTICAL, false, // Include legend
						true, // Include tooltips
						false // Include URLs
				);
				
				
				// Customize chart appearance
				chart.setBackgroundPaint(Color.white);
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
				plot.setBackgroundPaint(Color.lightGray);
				plot.setDomainGridlinePaint(Color.white);
				plot.setRangeGridlinePaint(Color.white);

				// Adjust the margins to provide space for wider bars
				CategoryAxis domainAxis = plot.getDomainAxis();
				domainAxis.setCategoryMargin(0.1);

				// Set the range axis upper margin to leave space for labels
				NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
				rangeAxis.setUpperMargin(0.15);

				// Set the plot insets to provide space for wider bars
				plot.setInsets(new RectangleInsets(10, 10, 10, 20)); // Adjust the values as per your preference								
				
				// Adjust the bar width
				CategoryPlot plotz = (CategoryPlot) chart.getPlot();
				BarRenderer renderer = (BarRenderer) plotz.getRenderer();
				renderer.setMaximumBarWidth(0.5); // Adjust the value as per your preference

				// Save the chart as an image
				int width = 800; // Width of the image
				int height = 600; // Height of the image
				String outputPath = "BarChart.jpeg";
				try {
					ChartUtils.saveChartAsJPEG(new File(outputPath), chart, width, height);
					System.out.println("Chart saved successfully.");
				} catch (IOException e) {
					e.printStackTrace();
				}

				// Display the chart in a chart frame
				ChartFrame chartFrame = new ChartFrame("Bar Chart", chart);
				chartFrame.pack();
				chartFrame.setVisible(true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
