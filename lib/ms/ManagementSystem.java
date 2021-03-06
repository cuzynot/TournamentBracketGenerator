/* ManagementSystem.java
 * The UI for the tournament software
 * September 18, 2018
 * Raymond Wang
 */

//Imports

import javax.swing.*;
import javax.swing.border.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.text.DefaultCaret;

import java.io.File;
import java.io.IOException;

import java.util.*;
import java.util.ArrayList;


public class ManagementSystem {

    private GraphicsPanel canvas;
    private BufferedImage background;
    //private Icon buttonImage;

    //JPanels
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JPanel leftMainPanel;
    private JPanel rightMainPanel;

    //JTextFields
    private JTextField nameField;
    private JTextField roundField;
    private JTextField matchField;

    private JTextArea mainField;
    private JScrollPane myScrollPane;
    private DefaultCaret caret;

    //JLabels
    private JLabel titleLabel;
    private JLabel promptLabel;
    private JLabel nameLabel;
    private JLabel roundLabel;
    private JLabel matchLabel;
    private JLabel errorMessage;

    private JLabel leftBlank1;
    private JLabel leftBlank2;
    private JLabel rightBlank1;
    private JLabel rightBlank2;
    private JLabel rightBlank3;

    //JButtons
    private JButton generateUnseededSinglesButton;
    private JButton generateSeededSinglesButton;
    private JButton generateDoublesButton;
    private JButton updateButton;

    private ArrayList<Team> teamList = new ArrayList<Team>();
    private String[] teams;

    public Bracket bracket;

    ManagementSystem() {
        JFrame myWindow = new JFrame("Tournament Maker Pro!");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        canvas = new GraphicsPanel();
        try {
            background = ImageIO.read(new File("background.png"));
        } catch (IOException ex) {
        }

        canvas.setLayout(new BorderLayout());

        //Title Label
        titleLabel = new JLabel("Tournament Maker Pro!", SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(1920, 100));
        titleLabel.setOpaque(false);
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        canvas.add(titleLabel, BorderLayout.NORTH);

        //Border
        westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(250, 1080));
        westPanel.setLayout(new GridLayout(2, 2));
        westPanel.setOpaque(false);
        canvas.add(westPanel, BorderLayout.WEST);

        eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(250, 1080));
        eastPanel.setBorder(new EmptyBorder(175, 0, 175, 25));
        eastPanel.setOpaque(false);
        canvas.add(eastPanel, BorderLayout.EAST);

        southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(1920, 100));
        southPanel.setOpaque(false);
        canvas.add(southPanel, BorderLayout.SOUTH);

        //Center Panel
        centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setOpaque(false);
        canvas.add(centerPanel, BorderLayout.CENTER);

        leftMainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        leftMainPanel.setOpaque(false);
        centerPanel.add(leftMainPanel);

        rightMainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        rightMainPanel.setOpaque(false);
        centerPanel.add(rightMainPanel);

        //Right Main Panel
        //Labels
        nameLabel = new JLabel("<html>" + "Winner Updater" + "<br>"+
                "<br>" + " Enter Winning Team Name:" + "</html>");
        nameLabel.setOpaque(false);
        nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        rightMainPanel.add(nameLabel, c);

        roundLabel = new JLabel("Enter Team Round Number:");
        roundLabel.setOpaque(false);
        roundLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        roundLabel.setForeground(Color.WHITE);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.gridx = 0;
        c.gridy = 3;
        rightMainPanel.add(roundLabel, c);

        matchLabel = new JLabel("Enter Team Match Number:");
        matchLabel.setOpaque(false);
        matchLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        matchLabel.setForeground(Color.WHITE);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.gridx = 0;
        c.gridy = 6;
        rightMainPanel.add(matchLabel, c);

        //Fields
        nameField = new JTextField(20);
        nameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        nameField.setOpaque(false);
        nameField.setCaretColor(Color.WHITE);
        nameField.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 1;
        rightMainPanel.add(nameField, c);

        roundField = new JTextField(20);
        roundField.setCaretColor(Color.WHITE);
        roundField.setOpaque(false);
        roundField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        roundField.setForeground(Color.WHITE);
        roundField.setColumns(8);
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        rightMainPanel.add(roundField, c);

        matchField = new JTextField(20);
        matchField.setCaretColor(Color.WHITE);
        matchField.setOpaque(false);
        matchField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        matchField.setForeground(Color.WHITE);
        matchField.setColumns(8);
        c.gridx = 0;
        c.gridy = 7;
        rightMainPanel.add(matchField, c);

        //Blank Lines
        rightBlank1 = new JLabel("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        rightBlank1.setForeground(Color.WHITE);
        rightBlank1.setOpaque(false);
        c.gridx = 0;
        c.gridy = 2;
        rightMainPanel.add(rightBlank1, c);

        rightBlank2 = new JLabel("¯¯¯¯¯¯¯¯¯¯¯¯");
        rightBlank2.setForeground(Color.WHITE);
        rightBlank2.setOpaque(false);
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        rightMainPanel.add(rightBlank2, c);

        rightBlank3 = new JLabel("¯¯¯¯¯¯¯¯¯¯¯¯");
        rightBlank3.setForeground(Color.WHITE);
        rightBlank3.setOpaque(false);
        c.gridx = 0;
        c.gridy = 8;
        rightMainPanel.add(rightBlank3, c);


        //Left Main Panel
        //Main Prompt
        promptLabel = new JLabel("<html>" + "Enter teams, one per line." +
                "<br>" + "For seeded entries, enter seed, semicolon, then team name." +
                "<br>" + "Example: 1;Raptors" + "</html>");
        promptLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        promptLabel.setForeground(Color.WHITE);
        f.anchor = GridBagConstraints.FIRST_LINE_START;
        f.fill = GridBagConstraints.HORIZONTAL;
        f.gridx = 0;
        f.gridy = 0;
        f.gridwidth = 3;
        leftMainPanel.add(promptLabel, f);

        //Scrolling JText Area
        mainField = new JTextArea(10, 10);
        mainField.setOpaque(false);
        mainField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        caret = (DefaultCaret) mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        mainField.setCaretPosition(mainField.getDocument().getLength());
        mainField.setCaretColor(Color.WHITE);
        mainField.setForeground(Color.WHITE);

        myScrollPane = new JScrollPane(mainField);
        myScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        myScrollPane.getViewport().setOpaque(false);
        myScrollPane.setOpaque(false);
        myScrollPane.setForeground(Color.WHITE);
        f.anchor = GridBagConstraints.CENTER;
        f.fill = GridBagConstraints.HORIZONTAL;
        f.gridx = 0;
        f.gridy = 2;
        f.gridwidth = 3;
        leftMainPanel.add(myScrollPane, f);

        //Blank Lines
        leftBlank1 = new JLabel("V¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯V");
        leftBlank1.setForeground(Color.WHITE);
        f.gridx = 0;
        f.gridy = 1;
        f.gridwidth = 3;
        leftMainPanel.add(leftBlank1, f);

        leftBlank2 = new JLabel("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        leftBlank2.setForeground(Color.WHITE);
        f.gridx = 0;
        f.gridy = 3;
        f.gridwidth = 3;
        leftMainPanel.add(leftBlank2, f);

        //Error Message
        errorMessage = new JLabel("");
        errorMessage.setOpaque(false);
        errorMessage.setFont(new Font("Century Gothic", Font.BOLD, 24));
        errorMessage.setForeground(Color.WHITE);
        southPanel.add(errorMessage);

        /*//Generate Buttons
        try {
            buttonImage = new ImageIcon("buttonImage.png");
        } catch (Exception ex) {
        }*/

        //Buttons
        //Unseeded Single Generator Button
        generateUnseededSinglesButton = new JButton("<html><center>" + "Generate Unseeded" + "<br>" + "Singles Bracket" + "</center></html>");
        generateUnseededSinglesButton.setOpaque(false);
        generateUnseededSinglesButton.setContentAreaFilled(false);
        generateUnseededSinglesButton.setBorderPainted(false);
        generateUnseededSinglesButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        generateUnseededSinglesButton.setForeground(Color.WHITE);
        generateUnseededSinglesButton.setVerticalTextPosition(SwingConstants.CENTER);
        generateUnseededSinglesButton.setHorizontalTextPosition(SwingConstants.CENTER);
        f.anchor = GridBagConstraints.LINE_START;
        f.gridx = 0;
        f.gridy = 4;
        f.gridwidth = 1;
        leftMainPanel.add(generateUnseededSinglesButton, f);

        generateUnseededSinglesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorMessage.setText(null);
                teamList.clear();

                teams = mainField.getText().split("\n");

                if (!hasDuplicates(teams)) {
                    for (int i = 0; i < teams.length; i++) {
                        Team newTeam = new Team(teams[i]);
                        teamList.add(newTeam);
                    }

                    System.out.println("hi");

                    SingleGenerator singleUGen = new SingleGenerator(teamList, false);
                    bracket=singleUGen.getBracket();
                    Display bracketDisplay = new Display(bracket);

                } else {
                    errorMessage.setText("Make sure there are no duplicate team names.");
                }
            }
        });

        //Seeded Single Generator Button
        generateSeededSinglesButton = new JButton("<html><center>" + "Generate Seeded" + "<br>" + "Singles Bracket" + "</center></html>");
        generateSeededSinglesButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        generateSeededSinglesButton.setForeground(Color.WHITE);
        generateSeededSinglesButton.setVerticalTextPosition(SwingConstants.CENTER);
        generateSeededSinglesButton.setHorizontalTextPosition(SwingConstants.CENTER);
        generateSeededSinglesButton.setOpaque(false);
        generateSeededSinglesButton.setContentAreaFilled(false);
        generateSeededSinglesButton.setBorderPainted(false);
        f.gridx = 1;
        f.gridy = 4;
        f.anchor = GridBagConstraints.CENTER;
        leftMainPanel.add(generateSeededSinglesButton, f);

        generateSeededSinglesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorMessage.setText(null);
                teamList.clear();

                teams = mainField.getText().split("\n");


                for (int i = 0; i < teams.length; i++) {
                    if (!(teams[i].indexOf(";") == -1)) {
                        if (canConvertInt(teams[i].substring(0, teams[i].indexOf(";")))) {
                            if (!hasDuplicates(teams)) {
                                int seed = Integer.parseInt(teams[i].substring(0, teams[i].indexOf(";")));
                                teams[i] = teams[i].substring(teams[i].indexOf(';') + 1);

                                Team newTeam = new Team(teams[i], seed);
                                teamList.add(newTeam);

                                //SingleGenerator singleSGen = new SingleGenerator(teamList, true);

                                //Display bracketDisplay = new Display(singleSGen.getBracket());

                            } else {
                                errorMessage.setText("Make sure there are no duplicate team names.");
                            }
                        } else {
                            errorMessage.setText("Make sure there is a seed before the semicolon.");
                        }
                    } else if (teams[i].indexOf(";") == -1) {
                        errorMessage.setText("Make sure there is a semicolon (after the seed).");
                    }
                }
                Collections.sort(teamList);
            }
        });

        //Double Generator Button
        generateDoublesButton = new JButton("<html><center>" + "Generate" + "<br>" + "Doubles Bracket" + "</center></html>");
        generateDoublesButton.setOpaque(false);
        generateDoublesButton.setContentAreaFilled(false);
        generateDoublesButton.setBorderPainted(false);
        generateDoublesButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        generateDoublesButton.setForeground(Color.WHITE);
        generateDoublesButton.setVerticalTextPosition(SwingConstants.CENTER);
        generateDoublesButton.setHorizontalTextPosition(SwingConstants.CENTER);
        f.gridx = 2;
        f.gridy = 4;
        f.anchor = GridBagConstraints.LINE_END;
        leftMainPanel.add(generateDoublesButton, f);

        generateDoublesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorMessage.setText(null);
                teamList.clear();
                teams = mainField.getText().split("\n");

                if (!hasDuplicates(teams)) {
                    for (int i = 0; i < teams.length; i++) {
                        Team newTeam = new Team(teams[i]);
                        teamList.add(newTeam);

                        //DoubleGenerator doubleGen = new DoubleGenerator(teamList);
                        //Display bracketDisplay = new Display(doubleGen.getBracket());
                    }

                } else {
                    errorMessage.setText("Make sure there are no duplicate team names.");
                }

            }
        });

        //Update Button
        updateButton = new JButton("<html><center>" + "Update Bracket" + "</center></html>");
        updateButton.setOpaque(false);
        updateButton.setContentAreaFilled(false);
        updateButton.setBorderPainted(false);
        updateButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        updateButton.setForeground(Color.WHITE);
        updateButton.setVerticalTextPosition(SwingConstants.CENTER);
        updateButton.setHorizontalTextPosition(SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 9;
        rightMainPanel.add(updateButton, c);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                nameField.setText(null);
                matchField.setText(null);
                roundField.setText(null);

               // update(bracketDisplay);
            }
        });


        myWindow.add(canvas);
        myWindow.setVisible(true);
    }


    //GraphicsPanel
    class GraphicsPanel extends JPanel {
        public GraphicsPanel() {
            super();
            setFocusable(true);
            requestFocusInWindow();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, this);
            setDoubleBuffered(true);
        }
    }

    //canConvertInt
    private boolean canConvertInt(String seedEntry) {
        try {
            Integer.parseInt(seedEntry);
            return true;
        } catch (Exception NumberFormatException) {
            return false;
        }
    }

    //hasDuplicates
    private boolean hasDuplicates(String[] list) {
        for (int i = 0; i < list.length; i++) {
            for (int j = 1; j < (list.length - i); j++) {
                if (list[i].equals(list[i + j])) {
                    return true;
                }
            }
        }
        return false;
    }

}

