package MarioGame;

import java.awt.Container;

import javax.swing.JFrame;

/*
 * Created on 2005/06/06
 *
 */

/**
 * @author mori
 *
 */
public class MarioLike extends JFrame {
    public MarioLike() {
        // �^�C�g����ݒ�
        setTitle("�S�[���ł�����_");
        // �T�C�Y�ύX�s��
        setResizable(false);

        // ���C���p�l�����쐬���ăt���[���ɒǉ�
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        // �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y�������ݒ�
        pack();
    }

    public static void main(String[] args) {
        MarioLike frame = new MarioLike();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
