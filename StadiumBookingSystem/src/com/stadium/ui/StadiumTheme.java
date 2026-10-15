package com.stadium.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

final class StadiumTheme {

    static final Color NAVY = new Color(24, 48, 83);
    static final Color BLUE = new Color(35, 117, 194);
    static final Color BACKGROUND = new Color(238, 242, 247);
    static final Color MUTED = new Color(95, 108, 125);
    static final Color BORDER = new Color(205, 214, 225);

    private StadiumTheme() {
    }

    static void prepareFrame(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
    }

    static JLabel title(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Sans-serif", Font.BOLD, 24));
        label.setForeground(NAVY);
        label.setBorder(new EmptyBorder(18, 10, 14, 10));
        return label;
    }

    static JButton button(String text, boolean primary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans-serif", Font.BOLD, 14));
        button.setForeground(primary ? Color.WHITE : NAVY);
        button.setBackground(primary ? BLUE : new Color(232, 238, 246));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        return button;
    }

    static JTextField field() {
        JTextField field = new JTextField();
        field.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(230, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    static JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans-serif", Font.BOLD, 13));
        label.setForeground(NAVY);
        return label;
    }

    static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Sans-serif", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(214, 231, 247));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Sans-serif", Font.BOLD, 13));
        header.setForeground(Color.WHITE);
        header.setBackground(NAVY);
        header.setOpaque(true);
    }
}
