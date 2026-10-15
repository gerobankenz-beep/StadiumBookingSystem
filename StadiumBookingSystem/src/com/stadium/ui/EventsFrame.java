package com.stadium.ui;

import com.stadium.dao.EventDAO;
import com.stadium.model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.EmptyBorder;

public class EventsFrame extends JFrame {

    private JTable eventsTable;
    private DefaultTableModel tableModel;
    private int customerId;

    public EventsFrame(int customerId) {
        this.customerId = customerId;

        setTitle("GMT Sports Arena");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        StadiumTheme.prepareFrame(this);

        String[] columns = {
                "Event ID",
                "Type",
                "Event Name",
                "Date",
                "Time"
        };

        tableModel = new DefaultTableModel(columns, 0);

        eventsTable = new JTable(tableModel);
        StadiumTheme.styleTable(eventsTable);

        JScrollPane scrollPane = new JScrollPane(eventsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(18, 22, 20, 22));
        content.add(StadiumTheme.title("Upcoming Events"), BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        JButton selectEventButton = StadiumTheme.button("Select Event", true);
        selectEventButton.setPreferredSize(new Dimension(150, 40));
        actionPanel.add(selectEventButton);
        content.add(actionPanel, BorderLayout.SOUTH);
        add(content);
        selectEventButton.addActionListener(e->{
            int selectedRow = eventsTable.getSelectedRow();

            if(selectedRow == -1){
                JOptionPane.showMessageDialog(this, "Please select an event");
                return;
            }

            int eventId = (int) tableModel.getValueAt(selectedRow, 0);
            String eventName = tableModel.getValueAt(selectedRow, 2).toString();

            new SeatSelectionFrame(eventId, customerId, eventName);
        });

        loadEvents();

        setVisible(true);
    }

    private void loadEvents() {

        EventDAO eventDAO = new EventDAO();

        List<Event> events = eventDAO.getAllEvents();

        for (Event event : events) {

            tableModel.addRow(new Object[]{
                    event.getEventId(),
                    event.getEventType(),
                    event.getEventName(),
                    event.getEventDate(),
                    event.getEventTime()
            });
        }
    }
}