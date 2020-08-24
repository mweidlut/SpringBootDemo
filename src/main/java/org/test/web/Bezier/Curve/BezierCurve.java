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
    private int countOfPoint;
    private double delta = 0.002;

    public BezierCurve() {
        countOfPoint = 0;
        controlPoint = new Point2D[4];
        ellipse = new Ellipse2D.Double[4];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    //main
    public static void main(String[] agrs) {
        BezierCurve bezierCurve = new BezierCurve();

        JFrame frame = new JFrame();
        frame.add(bezierCurve);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.MAGENTA);

        for (int i = 0; i < countOfPoint; i++) {
            if (i > 0 && i < (countOfPoint - 1)) {
                g2d.fill(ellipse[i]);
            } else {
                g2d.draw(ellipse[i]);
            }
            if (countOfPoint > 1 && i < (countOfPoint - 1))
                g2d.drawLine((int) controlPoint[i].getX(), (int) controlPoint[i].getY(), (int) controlPoint[i + 1].getX(), (int) controlPoint[i + 1].getY());
        }

        if (countOfPoint == 4) {
            g2d.setColor(Color.BLUE);

            double x, y;
            for (double k = delta; k <= 1 + delta; k += delta) {
                //x
                x = (1 - k) * (1 - k) * (1 - k) * controlPoint[0].getX()
                        + 3 * k * (1 - k) * (1 - k) * controlPoint[1].getX()
                        + 3 * k * k * (1 - k) * controlPoint[2].getX()
                        + k * k * k * controlPoint[3].getX();

                //y
                y = (1 - k) * (1 - k) * (1 - k) * controlPoint[0].getY()
                        + 3 * k * (1 - k) * (1 - k) * controlPoint[1].getY()
                        + 3 * k * k * (1 - k) * controlPoint[2].getY()
                        + k * k * k * controlPoint[3].getY();

                g2d.drawLine((int) x, (int) y, (int) x, (int) y);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(600, 600);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (countOfPoint < 4) {
            double x = e.getX();
            double y = e.getY();
            controlPoint[countOfPoint] = new Point2D.Double(x, y);
            Ellipse2D.Double current = new Ellipse2D.Double(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
            ellipse[countOfPoint] = current;

            countOfPoint++;
            repaint();
        }
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

    private int find(Point2D p) {
        int flag = 5;
        for (int i = 0; i < countOfPoint; i++) {
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
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
}