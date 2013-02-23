package clusters;

import java.util.Hashtable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Anton
 */
public class Clusters extends JFrame {

    /**
     * @param args the command line arguments
     */
    private class ButtonAction extends AbstractAction {

        TreeCreator tc;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            tc = new TreeCreator(dist, pts);
            tc.makeTree();
            pts = tc.getPoints();
            canvas.repaint();
            componentsLabel.setText("Components count: " + tc.clusterNumber);

        }
    }

    private class RefreshButtonAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            refresh();

        }
    }

    private class PointCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {

            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, w, h);


            for (int i = 0; i < pts.size(); i++) {
                g.setColor(pts.get(i).color);
                g.fillOval(pts.get(i).x, pts.get(i).y, pointRadius, pointRadius);
            }

        }
    }

    private class ChangePointSliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent event) {

            count = slidePoints.getValue();
            pointsLabel.setText("Points count: " + count);
            refresh();

        }
    }

    private class ChangeDistSliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent event) {

            dist = slideDist.getValue();
            distLabel.setText("Distance between clusters: " + dist);

        }
    }
    private ArrayList<CPoint> pts;
    private int count = 100, w = 1000, h = 500, ny = 190, pointRadius = 7;
    public double dist = 80;
    JLabel pointsLabel, distLabel, componentsLabel;
    JButton ok, refreshButton;
    JPanel panel;
    JSlider slidePoints, slideDist;
    PointCanvas canvas;

    void generate() {

        ArrayList<Color> colors = ColorGetter.getColors(count);


        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(w - 2 * pointRadius) + pointRadius;
            int y = rand.nextInt(h - 2 * pointRadius) + pointRadius;
            pts.add(new CPoint(x, y, colors.get(i)));
        }
        
    }

    void refresh() {

        pts = new ArrayList<CPoint>();
        generate();
        canvas.repaint();
        componentsLabel.setText("Components count: " + count);

    }

    public static void main(String[] args) {
        new Clusters();
    }

    public Clusters() {

        super("Clusterization");

        ColorGetter.make();

        JFrame.setDefaultLookAndFeelDecorated(true);

        canvas = new PointCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setBounds(0, 0, w, h);
        canvas.setVisible(true);

        ok = new JButton("Ok");
        ok.setAction(new ButtonAction());
        ok.setBounds(30, h + 15, 200, 50);
        ok.setVisible(true);
        ok.setText("Run");
        ok.setToolTipText("Run clusterization");

        refreshButton = new JButton("Refresh");
        refreshButton.setAction(new RefreshButtonAction());
        refreshButton.setBounds(30, h + 75, 200, 50);
        refreshButton.setVisible(true);
        refreshButton.setText("Refresh");
        refreshButton.setToolTipText("Refresh picture");

        componentsLabel = new JLabel("Components count: " + count);
        componentsLabel.setBounds(300, h + 15, 400, 20);
        

        pointsLabel = new JLabel("Points count: " + count);
        pointsLabel.setBounds(w / 2 + 140, h + 15, 400, 10);
        slidePoints = new JSlider(JSlider.HORIZONTAL, 1, 1000, 100);
        slidePoints.setBounds(w / 2, h + 30, 400, 40);
        Hashtable labelTablePoints = new Hashtable();
        labelTablePoints.put(new Integer(1), new JLabel("1"));
        for (int scale = 100; scale <= 1000; scale += 100) {
            labelTablePoints.put(new Integer(scale), new JLabel("" + scale));
        }
        slidePoints.setLabelTable(labelTablePoints);
        slidePoints.setMajorTickSpacing(100);
        slidePoints.setMinorTickSpacing(50);
        slidePoints.setPaintTicks(true);
        slidePoints.setPaintLabels(true);
        slidePoints.setToolTipText("Points count");
        slidePoints.setVisible(true);
        slidePoints.addChangeListener(new ChangePointSliderListener());

        distLabel = new JLabel("Distance between clusters: " + dist);
        distLabel.setBounds(w / 2 + 100, h + 85, 400, 20);
        slideDist = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)dist);
        slideDist.setBounds(w / 2, h + 105, 400, 40);
        Hashtable labelTableDist = new Hashtable();
        for (int scale = 0; scale <= 100; scale += 10) {
            labelTableDist.put(new Integer(scale), new JLabel("" + scale));
        }
        slideDist.setLabelTable(labelTableDist);
        slideDist.setMajorTickSpacing(10);
        slideDist.setMinorTickSpacing(5);
        slideDist.setPaintTicks(true);
        slideDist.setPaintLabels(true);
        slideDist.setToolTipText("Maximum distance between clusters");
        slideDist.setVisible(true);
        slideDist.addChangeListener(new ChangeDistSliderListener());

        
        panel = new JPanel();
        panel.setBounds(0, 0, w, h + ny);
        panel.setLayout(null);
        panel.add(canvas);
        panel.setVisible(true);
        panel.add(ok);
        panel.add(refreshButton);
        panel.add(componentsLabel);
        panel.add(pointsLabel);
        panel.add(distLabel);
        panel.add(slidePoints);
        panel.add(slideDist);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setSize(w, h + ny);
        this.setResizable(false);
        this.setLayout(null);
        this.add(panel);

        refresh();
        
        this.setVisible(true);


    }
}
