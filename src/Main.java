import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(900, 900);
        frame.add(new Canvas());
        frame.setVisible(true);
    }
}
