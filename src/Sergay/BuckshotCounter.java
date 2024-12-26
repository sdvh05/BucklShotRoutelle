/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Sergay;

/**
 *
 * @author Hp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BuckshotCounter {

    private JFrame frame;
    private JPanel leftPanel, rightPanel;
    private JPanel mainPanel;
    private Stack<JButton> undoStack;
    private Stack<JPanel> panelStack;

    public BuckshotCounter() {
        // Inicializar pilas para undo
        undoStack = new Stack<>();
        panelStack = new Stack<>();

        // Configuración inicial del marco
        frame = new JFrame("Buckshot Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Crear los paneles principales
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        mainPanel = new JPanel(new BorderLayout(10, 10));

        // Estilizar los paneles
        leftPanel.setBackground(Color.BLACK);
        rightPanel.setBackground(Color.BLACK);
        leftPanel.setLayout(new GridLayout(0, 1, 5, 5));
        rightPanel.setLayout(new GridLayout(0, 1, 5, 5));

        // Hacer los paneles más anchos
        leftPanel.setPreferredSize(new Dimension(300, frame.getHeight()));
        rightPanel.setPreferredSize(new Dimension(300, frame.getHeight()));

        // Agregar paneles al frame
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.setBackground(Color.BLACK);
        frame.add(mainPanel, BorderLayout.CENTER);

        // Crear el botón de reiniciar
        JButton resetButton = new JButton("Reiniciar");
        resetButton.setBackground(Color.DARK_GRAY);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciar();
            }
        });

        // Crear el botón de Error (Undo)
        JButton undoButton = new JButton("Error");
        undoButton.setBackground(Color.DARK_GRAY);
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusPainted(false);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deshacer();
            }
        });

        // Panel para botones de control
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.add(resetButton);
        controlPanel.add(undoButton);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(Color.BLACK);

        reiniciar();

        frame.setVisible(true);
    }

    private void reiniciar() {
        // Pedir al usuario las balas azules y rojas
        int azul = obtenerNumeroBalas("azules");
        int rojo = obtenerNumeroBalas("rojas");

        // Limpiar los paneles y pilas
        leftPanel.removeAll();
        rightPanel.removeAll();
        undoStack.clear();
        panelStack.clear();

        // Generar los botones azules
        for (int i = 0; i < azul; i++) {
            JButton blueButton = new JButton("Azul " + (i + 1));
            blueButton.setBackground(Color.BLUE);
            blueButton.setForeground(Color.WHITE);
            blueButton.setFocusPainted(false);
            blueButton.setPreferredSize(new Dimension(250, 40));
            blueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    leftPanel.remove(blueButton);
                    leftPanel.revalidate();
                    leftPanel.repaint();
                    undoStack.push(blueButton);
                    panelStack.push(leftPanel);
                }
            });
            leftPanel.add(blueButton);
        }

        // Generar los botones rojos
        for (int i = 0; i < rojo; i++) {
            JButton redButton = new JButton("Rojo " + (i + 1));
            redButton.setBackground(Color.RED);
            redButton.setForeground(Color.WHITE);
            redButton.setFocusPainted(false);
            redButton.setPreferredSize(new Dimension(250, 40));
            redButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightPanel.remove(redButton);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                    undoStack.push(redButton);
                    panelStack.push(rightPanel);
                }
            });
            rightPanel.add(redButton);
        }

        // Actualizar la vista
        leftPanel.revalidate();
        leftPanel.repaint();
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void deshacer() {
        if (!undoStack.isEmpty() && !panelStack.isEmpty()) {
            JButton lastButton = undoStack.pop();
            JPanel lastPanel = panelStack.pop();
            lastPanel.add(lastButton);
            lastPanel.revalidate();
            lastPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Ya Wey", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int obtenerNumeroBalas(String color) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            String input = JOptionPane.showInputDialog(frame, "Ingrese el número de balas " + color + ":", "0");
            if (input == null) {
                System.exit(0); // Salir si el usuario cancela
            }
            try {
                numero = Integer.parseInt(input);
                if (numero >= 0) {
                    valido = true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return numero;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BuckshotCounter::new);
    }
}