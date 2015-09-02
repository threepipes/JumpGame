package MarioGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, KeyListener {


    // �Q�[�����[�v�p�X���b�h
    private Thread gameLoop;
    private GameManager gamemanager;
//    private boolean upPressed;

    public MainPanel() {
        // �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // �p�l�����L�[���͂��󂯕t����悤�ɂ���
        setFocusable(true);

        
        //�Q�[���}�l�[�W���[�̍쐬
        gamemanager = new GameManager();

        // �L�[�C�x���g���X�i�[��o�^
        addKeyListener(this);
//        upPressed = false;
        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * �Q�[�����[�v
     */
    public void run() {
        while (true) {
/*            if (leftPressed) {
                // ���L�[��������Ă���΍������ɉ���
                player.accelerateLeft();
            } else if (rightPressed) {
                // �E�L�[��������Ă���ΉE�����ɉ���
                player.accelerateRight();
            } else {
                // ����������ĂȂ��Ƃ��͒�~
                player.stop();
            }
*/
//            if (upPressed) {
//                // �W�����v����
//                player.jump();
//            }
            
            gamemanager.update();
            // �ĕ`��
            repaint();

            // �x�~
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �`�揈��
     * 
     * @param �`��I�u�W�F�N�g
     */
    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
        gamemanager.draw(g);
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        /*
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        */
        if (key == KeyEvent.VK_UP) {
            gamemanager.setKeyEvent(GameManager.KEY_PRESSED);
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        /*
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        */
        if (key == KeyEvent.VK_UP) {
            gamemanager.setKeyEvent(GameManager.KEY_RELEASED);
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}