/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rotatingtext;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author UP732011 <UP732011@myport.ac.uk>
 */
public class GUI extends JFrame {

    private GraphicsPanel _graphicsPanel;
    private Shape _square = new Rectangle2D.Double(250, 250, 100, 100);
    private String[] _colors = {"BLUE", "RED", "YELLOW", "CYAN", "ORANGE"};
    private String _currentColour, _stringToWrite;
    private float _currentAlpha = 1;
    private synchronized float getAlpha()
    {
        return _currentAlpha;
    }
    private synchronized void setAlpha(float alpha)
    {
        _currentAlpha = alpha;
    }
    private class GraphicsPanel extends JPanel {

        protected GraphicsPanel() {
            super();

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                rotateObject(g, _currentColour);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public GUI() {
        super();
        JButton rotate = new JButton("Rotate");
        this.setLayout(new BorderLayout());
        rotate.addActionListener(e -> {

            ///System.out.println("sent request");
            new Thread(() -> {
                try {
                    float quotient = 1/100;
                    for (int i = 0; i < 100; i++) {

                        //System.out.println("Rotating");
                        _graphicsPanel.repaint();
                        _currentColour = _colors[i % 5];
                        this.setAlpha(this.getAlpha()-0.01f);
                        Thread.sleep(100);
                    }
                    this.setAlpha(1);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }).start();
        });
        this.add(rotate, BorderLayout.SOUTH);
        _graphicsPanel = new GraphicsPanel();
        this.add(_graphicsPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setVisible(true);
    }

    private void rotateObject(Graphics g, String colour) throws Exception {
        //System.out.println("Drawing");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color newColour = new Color(1, 0, 0, this.getAlpha());
        System.out.println(_currentAlpha);
        g2d.setColor(newColour);
        //g2d.draw(_square);

        AffineTransform af = AffineTransform.getRotateInstance(1, 290, 290);
        
        _square = af.createTransformedShape(_square);
        g2d.fill(_square);

    }

}
