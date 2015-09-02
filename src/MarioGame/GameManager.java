package MarioGame;

import java.awt.Color;
import java.awt.Graphics;

public class GameManager{
    // �p�l���T�C�Y
    public static final int WIDTH = 540;
    public static final int HEIGHT = 960;

    // �}�b�v
    private Map map;

    // �v���C���[
    private Player player;
    
    private int offsetX;
    private int offsetY;

    // �L�[�̏�ԁi������Ă��邩�A������ĂȂ����j
//    private boolean leftPressed;
//    private boolean rightPressed;
//    private boolean upPressed;
    
	public GameManager(){

        // �}�b�v���쐬
        map = new Map();

        // �v���C���[���쐬
        player = new Player(192, 32, map);
	}
	
	public void update(){


        // X�����̃I�t�Z�b�g���v�Z
        offsetX = MainPanel.WIDTH / 2 - (int)player.getX();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);

        // Y�����̃I�t�Z�b�g���v�Z
        offsetY = MainPanel.HEIGHT / 2 - (int)player.getY();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);

	}
	
	public void draw(Graphics g){

        // �w�i�����œh��Ԃ�
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        

        // �}�b�v��`��
        map.draw(g, offsetX, offsetY);

        // �v���C���[��`��
        player.draw(g, offsetX, offsetY);
	}
	
	public static final int KEY_PRESSED = 1;
	public static final int KEY_RELEASED = 2;
	public void setKeyEvent(int keytype){
		if((keytype|KEY_PRESSED) > 0){
			player.jump();
		}
	}

}
