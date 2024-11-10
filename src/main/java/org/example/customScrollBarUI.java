package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class customScrollBarUI extends BasicScrollBarUI {
    private static final Color THUMB_COLOR = new Color(248, 146, 137);
    private static final Color TRACK_COLOR = new Color(248, 146, 137, 50);
    private static final int THUMB_SIZE = 8;
    private static final int ARC_SIZE = 8;

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = THUMB_COLOR;
        this.trackColor = TRACK_COLOR;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            thumbBounds.x = thumbBounds.x + (thumbBounds.width - THUMB_SIZE) / 2;
            thumbBounds.width = THUMB_SIZE;
        } else {
            thumbBounds.y = thumbBounds.y + (thumbBounds.height - THUMB_SIZE) / 2;
            thumbBounds.height = THUMB_SIZE;
        }

        RoundRectangle2D roundRect = new RoundRectangle2D.Float(
                thumbBounds.x, thumbBounds.y,
                thumbBounds.width, thumbBounds.height,
                ARC_SIZE, ARC_SIZE
        );

        g2.setColor(THUMB_COLOR);
        g2.fill(roundRect);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            trackBounds.x = trackBounds.x + (trackBounds.width - THUMB_SIZE) / 2;
            trackBounds.width = THUMB_SIZE;
        } else {
            trackBounds.y = trackBounds.y + (trackBounds.height - THUMB_SIZE) / 2;
            trackBounds.height = THUMB_SIZE;
        }

        RoundRectangle2D roundRect = new RoundRectangle2D.Float(
                trackBounds.x, trackBounds.y,
                trackBounds.width, trackBounds.height,
                ARC_SIZE, ARC_SIZE
        );

        g2.setColor(TRACK_COLOR);
        g2.fill(roundRect);
        g2.dispose();
    }
}