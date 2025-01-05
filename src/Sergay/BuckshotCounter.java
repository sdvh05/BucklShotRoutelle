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

    public BuckshotCounter() {
        // Configuración inicial del marco
        frame = new JFrame("Buckshot Roulette");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400); // Tamaño reducido
        frame.setLayout(new BorderLayout());

        undoStack = new Stack<>();

        // Crear los paneles principales
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        mainPanel = new JPanel(new BorderLayout());

        // Estilizar los paneles
        leftPanel.setBackground(Color.BLACK);
        rightPanel.setBackground(Color.BLACK);
        leftPanel.setLayout(new GridLayout(0, 1, 2, 2));
        rightPanel.setLayout(new GridLayout(0, 1, 2, 2));

        // Ajustar tamaños
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));

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

        // Crear el botón de deshacer
        JButton undoButton = new JButton("Deshacer");
        undoButton.setBackground(Color.DARK_GRAY);
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusPainted(false);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deshacer();
            }
        });

        // Panel inferior para los botones
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(resetButton);
        bottomPanel.add(undoButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(Color.BLACK);

        reiniciar();

        frame.setVisible(true);
    }

    private void reiniciar() {
        // Pedir al usuario las balas azules y rojas
        int azul = obtenerNumeroBalas("azules");
        int rojo = obtenerNumeroBalas("rojas");

        // Limpiar los paneles y la pila de deshacer
        leftPanel.removeAll();
        rightPanel.removeAll();
        undoStack.clear();

        // Generar los botones azules
        for (int i = 0; i < azul; i++) {
            JButton blueButton = new JButton();
            blueButton.setBackground(Color.BLUE);
            blueButton.setFocusPainted(false);
            blueButton.setPreferredSize(new Dimension(80, 30));
            blueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    leftPanel.remove(blueButton);
                    leftPanel.revalidate();
                    leftPanel.repaint();
                    undoStack.push(blueButton);
                }
            });
            leftPanel.add(blueButton);
        }

        // Generar los botones rojos
        for (int i = 0; i < rojo; i++) {
            JButton redButton = new JButton();
            redButton.setBackground(Color.RED);
            redButton.setFocusPainted(false);
            redButton.setPreferredSize(new Dimension(80, 30));
            redButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rightPanel.remove(redButton);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                    undoStack.push(redButton);
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
        if (!undoStack.isEmpty()) {
            JButton lastButton = undoStack.pop();
            if (lastButton.getBackground() == Color.BLUE) {
                leftPanel.add(lastButton);
                leftPanel.revalidate();
                leftPanel.repaint();
            } else if (lastButton.getBackground() == Color.RED) {
                rightPanel.add(lastButton);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No hay acciones para deshacer.", "Deshacer", JOptionPane.INFORMATION_MESSAGE);
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
