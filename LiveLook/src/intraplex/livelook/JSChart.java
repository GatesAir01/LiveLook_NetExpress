/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intraplex.livelook;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

/**
 *
 * @author jschreiv
 */
public class JSChart extends org.jfree.chart.ChartPanel{

    ZoomListener zoomer;
    boolean showButtons;
    boolean zoomed;
    public JSChart(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips, ZoomListener z) {
        super(chart, properties, save, print, zoom, tooltips);
        super.setRangeZoomable(false);
        zoomer = z;
    }
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent event)
    {
        if (zoomed && showButtons)
        {
            int x = (int)event.getX();
            int width = this.getWidth();
            if (x >= 0 && x < 30)
            {
                zoomer.findFirst();
                return;
            }
            else if (x >= 35 && x < 65)
            {
                zoomer.findPrev();
                return;
            }
            else if (x < width && x > width - 30)
            {
                zoomer.findLast();
                return;
            }
            else if (x < width - 35 && x > width - 65)
            {
                zoomer.findNext();
                return;
            }
           
        }
        super.mouseClicked(event);
    }
    
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e)
    {
        super.mouseDragged(e);
    }

    @Override
    public void	paint(Graphics g1)
    {
        super.paint(g1);
        if (showButtons && zoomed)
        {
            int h = this.getHeight();
            int w = this.getWidth();
            Graphics2D g = (Graphics2D)g1;
            g.setColor(new Color(128,128,128,128));
            g.fillRect(0, 0, 30, h);
            g.fillRect(35, 0, 30, h);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(2));
            g.drawRect(0, 0, 30, h);
            g.drawRect(35, 0, 30, h);
            

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(5, h/2, 15, h/3);
            g.drawLine(5, h/2, 15, 2*h/3);
            g.drawLine(10, h/2, 20, h/3);
            g.drawLine(10, h/2, 20, 2*h/3);
            g.drawLine(15, h/2, 25, h/3);
            g.drawLine(15, h/2, 25, 2*h/3);
            
            g.drawLine(45, h/2, 55, h/3);
            g.drawLine(45, h/2, 55, 2*h/3);
            
            
            //Draw the End Lines
            g.setColor(new Color(128,128,128,128));
            g.fillRect(w-30, 0, 30, h);
            g.fillRect(w-65, 0, 30, h);
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(2));
            g.drawRect(w-30, 0, 30, h);
            g.drawRect(w-65, 0, 30, h);
            
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(w-5,  h/2, w-15, h/3);
            g.drawLine(w-5,  h/2, w-15, 2*h/3);
            g.drawLine(w-10, h/2, w-20, h/3);
            g.drawLine(w-10, h/2, w-20, 2*h/3);
            g.drawLine(w-15, h/2, w-25, h/3);
            g.drawLine(w-15, h/2, w-25, 2*h/3);
            
            g.drawLine(w-45, h/2, w-55, h/3);
            g.drawLine(w-45, h/2, w-55, 2*h/3);
        }
    }
    
    @Override
    public void	mouseEntered(java.awt.event.MouseEvent e)
    {
        showButtons = true;
        super.mouseEntered(e);
        this.repaint();
    }
    @Override
    public void mouseExited(java.awt.event.MouseEvent e)
    {
        showButtons = false;
        super.mouseExited(e);
        this.repaint();
    }

    @Override
    public void	mouseMoved(java.awt.event.MouseEvent e)
    {
        super.mouseMoved(e);
    }
    
    @Override
    public void	mousePressed(java.awt.event.MouseEvent e)
    {
        super.mousePressed(e);
    }

    @Override
    public void	mouseReleased(java.awt.event.MouseEvent e)
    {
        super.mouseReleased(e);
    }
    
    
    public void zoom(java.awt.geom.Rectangle2D selection)
    {
        super.zoom(selection);
        XYPlot plot = super.getChart().getXYPlot();
        long start = (long)plot.getDomainAxis().java2DToValue(selection.getMinX(),selection,plot.getDomainAxisEdge());
        long end = (long)plot.getDomainAxis().java2DToValue(selection.getMaxX(),selection,plot.getDomainAxisEdge());
        super.restoreAutoBounds();
        zoomer.zoom(start, end);
        zoomed = true;
    }
    
    public void setZoomed(boolean z)
    {
        zoomed = z;
    }
    
    
}
