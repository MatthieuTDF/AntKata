package AntKata;

import AntKata.ant.Ant;
import AntKata.ant.Colony;
import AntKata.ant.Status;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;

public class Field extends JPanel {
    private BufferedImage image;
    private Colony c;
    private List<Food> food;
    private List<Point> foodPosition;
    private int widthX;
    private int heightX;
    private JLabel foodLabel;
    private Graphics imageDrawable;
    private int nbFood = 0;

    public Field(int width, int height) {
        this.widthX = width;
        this.heightX = height;

        initColonyAndFood();

        initOnClickListenerFood();

        initUI();
    }

    private void initOnClickListenerFood() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    image.setRGB(e.getX(), e.getY(), Color.green.getRGB());
                    food.add(new Food(e.getX(),e.getY()));
                    repaint();
                } catch (Exception exception) {
                    System.out.println("Invalid click");
                }
            }
        });
    }

    private void initUI() {
        // init gridbaglayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Init image for printing ants
        this.image = new BufferedImage(this.widthX, this.heightX, BufferedImage.TYPE_INT_ARGB);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        add(label, gbc);

        // Init button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            initColonyAndFood();
            nbFood = 0; // On relance tout et on renitialise le nombre de food
        });

        // Init food collected labels
        JPanel j = new JPanel();
        JLabel t = new JLabel("Food collected : ");
        foodLabel = new JLabel("0");

        // Add label and button to panel
        j.add(resetButton);
        j.add(t);
        j.add(foodLabel);

        // Add panel to gridBagLayout
        add(j, gbc);

        // First paint
        repaint();
    }

    private void initColonyAndFood() {
        // TODO
        this.c = new Colony(750, new Point(this.widthX / 2, this.heightX / 2));
        this.food = new ArrayList<>();
        this.foodPosition = new ArrayList<>(); // Initialisation du arrayList foodPosition
    }

    public void nextTurn() {

        foodLabel.setText(Integer.toString(nbFood)); // On ecrit le nombre de food collectés

        this.image = new BufferedImage(widthX, heightX, BufferedImage.TYPE_INT_ARGB);

        for (Ant a : c.getAnts()) {
            this.image.setRGB(a.getPositionX(), a.getPositionY(), Color.blue.getRGB());
            for(Ant b : c.getAnts()){
                if(a.getPosition().equals(b.getPosition()) && a.getStatus() != Status.WANDERING && a.getLastKnownFoodPosition() != null) { //Talk de a à b
                    b.setLastKnownFoodPosition(a.getLastKnownFoodPosition());
                    if(b.getStatus() != Status.RETURNING_COLONY) {
                        b.setStatus(Status.FETCHING_FOOD);
                    }
                }
                if(b.getPosition().equals(a.getPosition()) && b.getStatus() != Status.WANDERING && b.getLastKnownFoodPosition() != null) {//Talk de b à a
                    a.setLastKnownFoodPosition(b.getLastKnownFoodPosition());
                    if(a.getStatus() != Status.RETURNING_COLONY) {
                        a.setStatus(Status.FETCHING_FOOD);
                    }
                }
                for(Point pos:new ArrayList<>(foodPosition)){ // On parcourt le array de foodposition, si la food de notre fourmis n'est pas dedans on repasse en wandering
                    if(a.getLastKnownFoodPosition() == pos) {
                        break;
                    }
                        a.setStatus(Status.WANDERING);
                        a.setLastKnownFoodPosition(null);
                }
                if(foodPosition.isEmpty()) // Si il n'y a plus de food tu repasse en wandering
                    a.setStatus(Status.WANDERING);
            }
            a.newPosition(); // On calcule la position de la fourmis en fonction de son status
            for (Food f : new ArrayList<>(food)) {
                if(!(foodPosition.contains(f.getPosition()))) // On remplit notre tableau de position
                    foodPosition.add(f.getPosition());
                if(a.getPosition().equals(f.getPosition())) {
                    a.setStatus(Status.RETURNING_COLONY); // On change le status si la fourmis passe sur une nourriture
                    a.setLastKnownFoodPosition(f.getPosition());
                    nbFood += f.nextTurn(); // On incremente le nombre de food tout en passant au tour suivant (donc en decrementant la vie de notre food)
                }
                if(a.getPosition().equals(new Point(c.getPositionX(),c.getPositionY())) && a.getLastKnownFoodPosition() != null) {
                    a.setStatus(Status.FETCHING_FOOD); // On retourne cherche une food si il existe une LastKnownPosition
                }
                if (!f.isAlive()) {
                    food.remove(f);//On retire la food
                    foodPosition.remove(f.getPosition());//On retire la position
                }
            }
            a.newPosition();
        }
                for (Point p : food.stream().map(Food::getPosition).collect(Collectors.toList())) {
                    this.image.setRGB(p.x, p.y, Color.green.getRGB());
                }

        this.image.setRGB(c.getPositionX(), c.getPositionY(), Color.red.getRGB());

        repaint();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

}
