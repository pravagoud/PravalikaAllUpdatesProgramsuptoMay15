package com.ojas.Phase2;

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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class WTERU {
	public static void main(String[] args) {
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

				DefaultCategoryDataset dataset = new DefaultCategoryDataset();

				for (String[] lineArray : lines) {
					try {
						String ageString = lineArray[6];
						int age = Integer.parseInt(ageString);

						String category;
						if (age <= 20) {
							category = "Teen Age";
						} else if (age <= 30) {
							category = "Young Age";
						} else if (age <= 60) {
							category = "Middle Age";
						} else {
							category = "Old Age";
						}

						dataset.addValue(age, "Age", category);
					} catch (NumberFormatException e) {
						System.out.println("Invalid value in line: " + String.join(",", lineArray));
					}
				}

				JFreeChart chart = ChartFactory.createBarChart("Age Distribution", "Category", "Value", dataset,
						org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);

				// Adjust the bar width
				CategoryPlot plot = chart.getCategoryPlot();
				BarRenderer renderer = (BarRenderer) plot.getRenderer();
				renderer.setMaximumBarWidth(0.5); // Set the bar width as needed

				int width = 800; // Width of the image
				int height = 600; // Height of the image
				File barChart = new File("BarChart.jpeg");

				ChartUtils.saveChartAsJPEG(barChart, chart, width, height);

				ChartFrame chartFrame = new ChartFrame("Bar Chart", chart);
				chartFrame.setVisible(true);
				chartFrame.setSize(width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
