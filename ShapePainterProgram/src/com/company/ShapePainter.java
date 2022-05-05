package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class ShapePainter extends JFrame {
             JLabel surface;
             String shape="Oval";
             String text ="<html>"+"0"+"px"+"<sup><font size=\"+2\">2</font></sup></html>";

    public ShapePainter() {

        // Initialize Window

        this.setTitle("Projekt GUI zad 2");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2,8,8));
        this.setSize(800, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(true);
        this.setBackground(Color.white);
        this.getContentPane().add(new ShapeStroke());

        // Inicjacia Jpanels For buttons and canvas
        JPanel optionsArea = new JPanel();
        optionsArea.setLayout(new GridLayout(2,1,8,8));
        optionsArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel showSurface = new JPanel();
        showSurface.setLayout(new GridBagLayout());

        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(3,1,8,8));
        buttonArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Jlabel displaying surface size

        surface=new JLabel();
        surface.setText(text);
        surface.setFont(new Font("Arial", Font.BOLD, 40));
        showSurface.add(surface);

        // Lista button initialize

        List<JButton> shapeSelectButtonList = Arrays.asList(
                new JButton("Oval"),
                new JButton("Rectangle"),
                new JButton("Triangle")
        );
        shapeSelectButtonList.forEach(one -> {
            one.setBorder(BorderFactory.createBevelBorder(1));
            one.setOpaque(true);
        });

        shapeSelectButtonList.get(0).setBackground(Color.RED);

        // button action listener
        shapeSelectButtonList
                .forEach(button -> button.addActionListener(e -> {
                    shape=(button.getText());
                    shapeSelectButtonList.forEach(one -> one.setBackground(null));
                    button.setBackground(Color.RED);

                }));

        // adding buttons to jpanel

        for (JButton jButton : shapeSelectButtonList) {
            buttonArea.add(jButton);
        }

        optionsArea.add(showSurface);
        optionsArea.add(buttonArea);
        this.getContentPane().add(optionsArea);


    }




    // Painiting

    public class ShapeStroke extends JPanel {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        public ShapeStroke() {

            // Adapters

            this.addMouseListener(new PaintingPanelMouseAdapter());
            this.addMouseMotionListener(new PaintingPanelMouseAdapter());
            this.setSize(800,400);


        }

        // Painting and calculating surface

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics g2d = (Graphics2D) g;
            ((Graphics2D) g2d).setStroke(new BasicStroke(4));
            g2d.setColor(Color.red);

            if(shape.equals("Oval")){

                // Oval

                int px = Math.min(x1,x2);
                int py = Math.min(y1,y2);
                int pw=Math.abs(x1-x2);
                int ph=Math.abs(y1-y2);

                g2d.drawOval(px, py, pw, ph);

                int calcSurface = (int) (Math.PI*(pw/2)*(ph/2));
                surface.setText("<html>"+calcSurface+"px"+"<sup><font size=\"+2\">2</font></sup></html>");



            }else if (shape.equals("Triangle")){

                // Triangle

                int pw=Math.abs(x1-x2);
                int ph=Math.abs(y1-y2);

                int ch= ((x2-x1)/2)+x1;

                g2d.drawLine(x1,y1,x2,y1);
                g2d.drawLine(x1,y1,ch,y2);
                g2d.drawLine(ch,y2,x2,y1);

                int calcSurface= (int) ((pw/2)*ph);

                surface.setText("<html>"+calcSurface+"px"+"<sup><font size=\"+2\">2</font></sup></html>");

            }else if (shape.equals("Rectangle")){

                // Square

                int px = Math.min(x1,x2);
                int py = Math.min(y1,y2);
                int pw=Math.abs(x1-x2);
                int ph=Math.abs(y1-y2);

                g2d.drawRect(px, py,pw,ph);

                int calcSurface= (int) (pw*ph);
                surface.setText("<html>"+calcSurface+"px"+"<sup><font size=\"+2\">2</font></sup></html>");

            }
        }


        // Adapter for painting

        class PaintingPanelMouseAdapter extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                repaint();
            }

        }
    }

}


