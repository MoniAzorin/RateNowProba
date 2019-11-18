package com.RateNowProba.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RateNowProba.model.Chart;
import com.RateNowProba.persistence.ChartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class chartController {
	
	
//	@Autowired
	private ChartRepository repository;
	
			
	// http://localhost:8080/Chart
	
	@ResponseBody
	@GetMapping("/Chart")
	public void Chart(HttpServletResponse response) throws IOException {
		
//	 	Coger los datos para hacer el grafico de BD:
//		long id= 1;
//		repository.findById(id);

		
		Chart grafico =new Chart("My test Graph", new int[]{ 3,5,8,1});
		
		// Crear Grafico 
		
		int[] values =grafico.getValuestoShow();
			
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		for(int i=0; i<values.length;i++) {
			
			dataset.addValue(values[i], "","Indice "+String.valueOf(i) );
			
		}
				        
        JFreeChart barChart = ChartFactory.createBarChart(
                				grafico.getTitle(),           
                				"Values to Show",            
                				"Score",            
                				dataset,          
                				PlotOrientation.VERTICAL,           
                				false, true, true);
        
        // Exportar grafico en una imagen:
        
        
        int width = 640;    
        int height = 480;   
        File jpegChart = new File( "/Users/azorin/Desktop/BarChart.jpeg" ); 
        try {
			ChartUtils.saveChartAsJPEG( jpegChart , barChart , width , height );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        final CategoryPlot categoryPlot = (CategoryPlot) barChart.getPlot();
        final CategoryItemRenderer categoryItemRenderer = categoryPlot.getRenderer();
        categoryItemRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        categoryItemRenderer.setDefaultItemLabelsVisible(true);

        final ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
        categoryItemRenderer.setDefaultPositiveItemLabelPosition(position);
        
        writeChartAsPNGImage(barChart, width, height, response);

        
	}
	
    private void writeChartAsPNGImage(final JFreeChart chart, final int width, final int height, HttpServletResponse response) throws IOException {
        final BufferedImage bufferedImage = chart.createBufferedImage(width, height);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        ChartUtils.writeBufferedImageAsPNG(response.getOutputStream(), bufferedImage);
    }



	@ResponseBody
	@PostMapping(value="/", produces = "application/json;charset=UTF-8")
	public String register(@RequestBody String jchart)  {
				
		
		ObjectMapper objectMapper = new ObjectMapper();

		// Guardar json en un fitxer de text:

		try {
		objectMapper.writeValue(new File("/Users/azorin/Desktop/jchart.txt"), jchart);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Guardar JSON a la base de datos (Falta hacer!)
		//1- COnvertir JSON en Objeto Chart
		
		try {
			Chart chart = objectMapper.readValue(jchart, Chart.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		

				
		
//	 	Guardar los datos a la base de datos		
//			repository.save(chart);
		
		return jchart;
						
	}

}
