package org.example;

import java.awt.Dimension;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AddScrollPage extends JPanel {
    private JScrollPane scrollPage;

    public AddScrollPage(JPanel components) 
    {
        scrollPage = new JScrollPane(components);
        scrollPage.setPreferredSize(new Dimension(680, 3000));
        scrollPage.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Change to AS_NEEDED if needed
        scrollPage.setBorder(null);
        scrollPage.getViewport().setViewPosition(new java.awt.Point(0, 20));

        scrollPage.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int currentY = scrollPage.getViewport().getViewPosition().y;
                int newY = currentY + e.getWheelRotation() * 70; 

                if (newY < 20) {
                    newY = 20;
                }

                scrollPage.getViewport().setViewPosition(
                    new java.awt.Point(
                        scrollPage.getViewport().getViewPosition().x,
                        newY
                    )
                );
            }
        });

        // Add the JScrollPane to this panel
        this.add(scrollPage);
    }
}