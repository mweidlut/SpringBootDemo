package org.test.web.Bezier.Curve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Using 4 points to draw Bezier Curve
 * http://blog.csdn.net/ytlcainiao/article/details/45267791
 * User: ROOT
 * Date: 2018/1/13 17:32
 */
public class BezierCurve extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private static final double SIDE_LENGTH = 8;

    private Point2D[] controlPoint;
    private Ellipse2D.Double[] ellipse;
    private int numPoints;
    private double t = 0.002;

    public BezierCurve() {
        numPoints = 0;
        controlPoint = new Point2D[4];
        ellipse = new Ellipse2D.Double[4];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public static void main(String[] agrs) {
        JFrame f = new JFrame();
        BezierCurve t2 = new BezierCurve();
        f.add(t2);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);
        for (int i = 0; i < numPoints; i++) {
            if (i > 0 && i < (numPoints - 1)) {
                g2.fill(ellipse[i]);
            } else {
                g2.draw(ellipse[i]);
            }
            if (numPoints > 1 && i < (numPoints - 1))
                g2.drawLine((int) controlPoint[i].getX(), (int) controlPoint[i].getY(), (int) controlPoint[i + 1].getX(), (int) controlPoint[i + 1].getY());
        }
        if (numPoints == 4) {
            double x, y;
            g2.setColor(Color.RED);
            for (double k = t; k <= 1 + t; k += t) {
                x = (1 - k) * (1 - k) * (1 - k) * controlPoint[0].getX() + 3 * k * (1 - k) * (1 - k) * controlPoint[1].getX()
                        + 3 * k * k * (1 - k) * controlPoint[2].getX() + k * k * k * controlPoint[3].getX();
                y = (1 - k) * (1 - k) * (1 - k) * controlPoint[0].getY() + 3 * k * (1 - k) * (1 - k) * controlPoint[1].getY()
                        + 3 * k * k * (1 - k) * controlPoint[2].getY() + k * k * k * controlPoint[3].getY();

                g2.drawLine((int) x, (int) y, (int) x, (int) y);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(600, 600);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (numPoints < 4) {
            double x = e.getX();
            double y = e.getY();
            controlPoint[numPoints] = new Point2D.Double(x, y);
            Ellipse2D.Double current = new Ellipse2D.Double(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
            ellipse[numPoints] = current;

            numPoints++;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    private int find(Point2D p) {
        int flag = 5;
        for (int i = 0; i < numPoints; i++) {
            if (ellipse[i].contains(p)) {
                flag = i;
                return flag;
            }
        }
        return flag;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int flag = find(e.getPoint());
        if (flag < 5) {
            double x = e.getX();
            double y = e.getY();
            controlPoint[flag] = new Point2D.Double(x, y);
            Ellipse2D.Double current = new Ellipse2D.Double(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
            ellipse[flag] = current;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}