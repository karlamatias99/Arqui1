
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficaLineas {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gráfica de línea");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        // Crear botón para seleccionar archivo
        JButton botonSeleccionar = new JButton("Seleccionar archivo");
        panel.add(botonSeleccionar);
        

        // Agregar listener al botón para mostrar diálogo de selección de archivo
        botonSeleccionar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int opcion = chooser.showOpenDialog(panel);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = chooser.getSelectedFile();
                // Llamar al método que procesa los datos del archivo
                procesarArchivo(archivoSeleccionado);
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void procesarArchivo(File archivo) {
        try {
            Scanner scanner = new Scanner(archivo);
            List<Double> xData = new ArrayList<>();
            List<Double> yData = new ArrayList<>();
            int n = 0;
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");
                if (datos.length == 2) {
                    double x = Double.parseDouble(datos[0]);
                    double y = Double.parseDouble(datos[1]);
                    xData.add(x);
                    yData.add(y);
                    n++;
                } else {
                    System.out.println("Error: línea " + (n + 1) + " tiene un formato inválido");
                }
            }
            scanner.close();

            double[] xValues = xData.stream().mapToDouble(Double::doubleValue).toArray();
            double[] yValues = yData.stream().mapToDouble(Double::doubleValue).toArray();

            XYSeries series = new XYSeries("Datos");
            for (int i = 0; i < xValues.length; i++) {
                series.add(xValues[i], yValues[i]);
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);

            ChartFrame frame = new ChartFrame("Gráfica de línea", ChartFactory.createXYLineChart(
                    "Gráfica de línea", "Tiempo", "Aceleracion", dataset, PlotOrientation.VERTICAL,
                    true, true, false));
            frame.pack();
            frame.setVisible(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
