package AntKata;

import AntKata.Random.RNG;
import AntKata.ant.Ant;
import AntKata.ant.Colony;
import AntKata.ant.CellType;
//import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.Arrays;

public class Field extends JPanel {
    private BufferedImage image;
    private Colony c;
    private Colony c2;
    private List<Food> food;
    private int widthX;
    private int heightX;
    private JLabel foodLabel;
    private CellType[][] cellArray;
    //static final long SerialVersionUID;

    public Field(int width, int height) {
        this.widthX = width;
        this.heightX = height;
        this.cellArray = new CellType[width][height];

        this.initColonyAndFood();

        this.initOnClickListenerFood();

        this.initUI();
    }

    private void initOnClickListenerFood() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    image.setRGB(e.getX(), e.getY(), Color.green.getRGB());
                    // TODO : set clicked position as FOOD
                    food.add(new Food(e.getX(), e.getY()));

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
            this.initColonyAndFood();
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

    private void syncCellArrayAndFood() {
        for (int i=0; i<cellArray.length; i++) {
            Arrays.fill(this.cellArray[i], CellType.NORMAL);
        }
        this.cellArray[this.c.getPositionX()][this.c.getPositionY()] = CellType.COLONY;
        this.cellArray[this.c2.getPositionX()][this.c2.getPositionY()] = CellType.COLONY;
        for (Food f: this.food) {
            Point foodPos = f.getPosition();
            this.cellArray[foodPos.x][foodPos.y] = CellType.FOOD;
        }
    }

    private void initColonyAndFood() {
        this.c = new Colony(1, new Point(this.widthX / 2, this.heightX / 2));
        this.c2 = new Colony(1, new Point(RNG.random(0, widthX), RNG.random(0, heightX)));
        while (c2.getPosition().equals(c.getPosition())) {
            this.c2 = new Colony(1, new Point(RNG.random(0, widthX), RNG.random(0, heightX)));
        }
        this.food = new ArrayList<>();
        this.food.add(new Food(3,3));
        syncCellArrayAndFood();
    }

    public void nextTurn() {
        foodLabel.setText(String.valueOf(c.getFoodCollected()) + "/" + String.valueOf(c2.getFoodCollected()));

        this.image = new BufferedImage(widthX, heightX, BufferedImage.TYPE_INT_ARGB);

        if  (c.getHp() > 0) {
            for (Ant a : c.getAnts())
                this.image.setRGB(a.getPositionX(), a.getPositionY(), Color.blue.getRGB());
            if (c2.getHp() > 0) {
                for (Ant a : c2.getAnts())
                    this.image.setRGB(a.getPositionX(), a.getPositionY(), Color.blue.getRGB());
                c.next(cellArray, c2);
            }
            else c.next(cellArray);
        }
        else if (c2.getHp() > 0) {
            for (Ant a : c2.getAnts())
                this.image.setRGB(a.getPositionX(), a.getPositionY(), Color.blue.getRGB());
            c2.next(cellArray);
        }

        for (Ant a : c2.getAnts())
            this.image.setRGB(a.getPositionX(), a.getPositionY(), Color.blue.getRGB());

        for (Point p : food.stream().map(Food::getPosition).collect(Collectors.toList()))
            this.image.setRGB(p.x, p.y, Color.green.getRGB());

        this.image.setRGB(c.getPositionX(), c.getPositionY(), Color.red.getRGB());
        this.image.setRGB(c2.getPositionX(), c2.getPositionY(), Color.black.getRGB());

        repaint();

        // On itère sur une autre liste pour pouvoir retirer un élement sans risquer d'erreurs
        for (Food f : new ArrayList<>(this.food)) {
            f.nextTurn();
            if (!f.isAlive())
                food.remove(f);
        }
        this.syncCellArrayAndFood();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
