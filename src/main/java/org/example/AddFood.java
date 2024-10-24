// package org.example;

// import java.awt.Dimension;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// import javax.swing.ImageIcon;
// import javax.swing.JButton;
// import javax.swing.Timer;

// public class AddFood extends JButton {
//     private ImageIcon foodImage;
//     private ImageIcon hoverImage;
//     private Timer hoverTimer;

//     public AddFood(ImageIcon foodImage, ImageIcon hoverImage) 
//     {
//         this.setPreferredSize(new Dimension(300, 250));
//         this.setIcon(foodImage);
//         this.setContentAreaFilled(false);
//         this.setBorderPainted(false);

//         // Initialize the hover timer
//         hoverTimer = new Timer(500, new ActionListener() 
//         {
//             @Override
//             public void actionPerformed(ActionEvent e) 
//             {
//                 setIcon(hoverImage); // Change the icon to the hover image
//                 hoverTimer.stop(); // Stop the timer
//             }
//         });

//         this.addMouseListener(new java.awt.event.MouseAdapter() {
//             @Override
//             public void mouseEntered(java.awt.event.MouseEvent evt) {
//                 hoverTimer.start(); // Start the timer on hover
//             }

//             @Override
//             public void mouseExited(java.awt.event.MouseEvent evt) {
//                 hoverTimer.stop(); // Stop the timer if mouse exits before delay
//                 setIcon(foodImage); // Reset to original image
//             }
//         });
//     }
// }


package org.example;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class AddFood extends JLabel {
    private ImageIcon foodImage;
    private ImageIcon hoverImage;
    private Timer hoverTimer;

    public AddFood(ImageIcon foodImage, ImageIcon hoverImage) 
    {
        this.setPreferredSize(new Dimension(300, 250));
        this.setIcon(foodImage);
        // this.setContentAreaFilled(false);
        // this.setBorderPainted(false);

        // Initialize the hover timer
        hoverTimer = new Timer(500, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                AddFood.this.setIcon(hoverImage); // Change the icon to the hover image
                hoverTimer.stop(); // Stop the timer
            }
        });

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hoverTimer.start(); // Start the timer on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hoverTimer.stop(); // Stop the timer if mouse exits before delay
                setIcon(foodImage); // Reset to original image
            }
        });
    }
}
