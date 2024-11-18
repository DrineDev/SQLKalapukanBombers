package org.example;

import org.example.Classes.Order;

import javax.swing.SwingUtilities;

/* FOR EDWELL
mvn exec:java -Dexec.mainClass=org.example.Main
*/

public class Main {
    public static void main(String[] args) {
       //SwingUtilities.invokeLater(LoginPage::new);
    // SwingUtilities.invokeLater(InventoryCRUDEmployee::new);
        //SwingUtilities.invokeLater(MainFrameEmployee::new);
//       SwingUtilities.invokeLater(MainFrameManager::new);
        //SwingUtilities.invokeLater(InventoryCRUD::new);
//       SwingUtilities.invokeLater(UserCRUD::new);
        SwingUtilities.invokeLater(OrderCrud::new);
    }
}
