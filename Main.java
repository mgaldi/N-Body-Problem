import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main  {

    private static final String DATA_FILE = "/resources/bodies.dat";

    private static final int DEFAULT_WIDTH = 768;
    private static final int DEFAULT_HEIGHT = 768;

    private static List<CelestialBody> bodies;
    private static double scale;    // the distance in meters represented by one pixel

    // GUI elements
    private static JFrame frame;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        try {
            Main.loadData(Main.class.getResource(DATA_FILE));
            Main.initGui();
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                Main.updateAcceleration(bodies);
                Main.updatePosition(bodies);
                Main.updateVelocity(bodies);
                frame.repaint();
            }, 0, 15, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    private static void loadData(URL resource) throws IOException, NumberFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
            switch (reader.readLine().toLowerCase()) {
                case "arraylist":
                    bodies = new ArrayList<>();
                    break;
                case "linkedlist":
                    bodies = new LinkedList<>();
                    break;
                default:
                    // @fixme
                    break;
            }
            scale = Double.parseDouble(reader.readLine());
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] bodyValues = line.split(",");
                bodies.add(new CelestialBody()
                        .setName(bodyValues[0])
                        .setMass(Double.parseDouble(bodyValues[1]))
                        .setXCoord(Integer.parseInt(bodyValues[2]))
                        .setYCoord(Integer.parseInt(bodyValues[3]))
                        .setXVel(Double.parseDouble(bodyValues[4]))
                        .setYVel(Double.parseDouble(bodyValues[5]))
                        .setSize(Integer.parseInt(bodyValues[6]))
                        .setColor(new Color(ThreadLocalRandom.current().nextInt(0, 256),
                                        ThreadLocalRandom.current().nextInt(0, 256),
                                        ThreadLocalRandom.current().nextInt(0, 256)).brighter()));
            }
        }
    }

    private static void initGui() {
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                bodies.forEach(body -> Main.drawCelestialBody(body, graphics));
            }
        };
        panel.setBackground(new Color(33, 33, 33));

        frame = new JFrame();
        frame.add(panel);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

        private static void updateAcceleration(List<CelestialBody> bodies) {


            for (CelestialBody toUpdate : bodies) {
                toUpdate.setXAcc(0);
                toUpdate.setYAcc(0);
                for (CelestialBody body: bodies){
                    if (!toUpdate.equals(body)) {
                        double force = (CelestialBody.G * ( toUpdate.getMass() * body.getMass() / Math.pow(
                                toUpdate.distanceTo(body), 2)));
                        double forceX = force * ((body.getXCoord() - toUpdate.getXCoord()));///toUpdate.distanceTo(body));
                        double forceY = force * ((body.getYCoord() - toUpdate.getYCoord()));///toUpdate.distanceTo(body));
                        double accX = (forceX/toUpdate.getMass()) + toUpdate.getXAcc();
                        double accY = (forceY/toUpdate.getMass()) + toUpdate.getYAcc();
                        toUpdate.setXAcc(accX);
                        toUpdate.setYAcc(accY);
                    }
                }
            }
        }
        private static void updatePosition(List<CelestialBody> bodies) {
            for (CelestialBody toUpdate : bodies) {
                toUpdate.setXCoord((int)(toUpdate.getXCoord() + toUpdate.getXAcc() + toUpdate.getXVel()));
                toUpdate.setYCoord((int)(toUpdate.getYCoord() + toUpdate.getYAcc() + toUpdate.getYVel()));
            }
        }

        private static void updateVelocity(List<CelestialBody> bodies) {
            for (CelestialBody toUpdate : bodies) {
                toUpdate.setXVel(toUpdate.getXVel() + toUpdate.getXAcc());
                toUpdate.setYVel(toUpdate.getYVel() + toUpdate.getYAcc());

            }
    }

    private static void drawCelestialBody(CelestialBody body, Graphics g) {
        g.setColor(body.getColor());
        g.fillOval(body.getXCoord(), body.getYCoord(), body.getSize(), body.getSize());
    }
}
