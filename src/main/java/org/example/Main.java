package org.example;

import javax.swing.SwingUtilities;

/* FOR EDWELL
mvn exec:java -Dexec.mainClass=org.example.Main
*/

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater(LoginPage::new);
//        SwingUtilities.invokeLater(MainFrameEmployee::new);
         SwingUtilities.invokeLater(InventoryCRUD::new);
    }
}
