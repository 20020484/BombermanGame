package animation;

import floor.Floor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class BombermanFrame extends JFrame
{
    private Floor floor;
    private BombermanAnimation bombermanAnimation;

    public BombermanFrame(final String title, Floor floor) throws HeadlessException {
        super(title);
        this.floor = floor;
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        bombermanAnimation = new BombermanAnimation(floor);
        floor.createPlayer(bombermanAnimation, floor);
        setKeyStrokes();

        this.setLayout(new BorderLayout());
        this.add(bombermanAnimation, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public BombermanAnimation getBombermanComponent() {
        return bombermanAnimation;
    }

    private boolean askUser(String question) {
        return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void setKeyStrokes() {

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        bombermanAnimation.getInputMap().put(stroke, "q");
        bombermanAnimation.getActionMap().put("q", quit);

    }

    private final Action quit = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e) {
            dispose();

        }
    };

}
