package MarioGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/*
 * Created on 2005/06/06
 *
 */

/**
 * @author mori
 *  
 */
public class Player extends GameObject{
	// ��
    public static final int WIDTH = 32;
    // ����
    public static final int HEIGHT = 32;
	// �X�s�[�h
    private static final int SPEED = 6;
    // �W�����v��
    private static final int JUMP_SPEED = 20;

    // �ʒu
    private double x;
    private double y;

    // ���x
    private double vx;
    private double vy;
    
    // ���n���Ă��邩
    private boolean onGround;
    
    // �}�b�v�ւ̎Q��
    private Map map;
    
    public Player(double x, double y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
        vx = SPEED;
        vy = 0;
        onGround = false;
    }
    
    /**
     * ��~����
     
    public void stop() {
        vx = 0;
    }
     */
    /**
     * ���ɉ�������
     
    public void accelerateLeft() {
        vx = -SPEED;
    }*/

    /**
     * �E�ɉ�������
     
    public void accelerateRight() {
        vx = SPEED;
    }*/

    /**
     * �W�����v����
     */
    public void jump() {
        if (onGround) {
            // ������ɑ��x��������
            vy = -JUMP_SPEED;
            onGround = false;
        }
    }
    
    /**
     * �v���C���[�̏�Ԃ��X�V����
     */
    public void update() {
        // �d�͂ŉ������ɉ����x��������
        vy += Map.GRAVITY;

        // x�����̓����蔻��
        // �ړ�����W�����߂�
        double newX = x + vx;
        // �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
        // x���������l����̂�y���W�͕ω����Ȃ��Ɖ���
        Point tile = map.getTileCollision(this, newX, y);
        if (tile == null) {
            // �Փ˂���^�C�����Ȃ���Έړ�
            x = newX;
        } else {
            // �Փ˂���^�C��������ꍇ
        	System.exit(0);
            /*if (vx > 0) { // �E�ֈړ����Ȃ̂ŉE�̃u���b�N�ƏՓ�
                // �u���b�N�ɂ߂肱�� or ���Ԃ��Ȃ��悤�Ɉʒu����
                //x = Map.tilesToPixels(tile.x) - WIDTH;
            } else if (vx < 0) { // ���ֈړ����Ȃ̂ō��̃u���b�N�ƏՓ�
                // �ʒu����
                x = Map.tilesToPixels(tile.x + 1);
            }*/
            //vx = 0;
        }

        // y�����̓����蔻��
        // �ړ�����W�����߂�
        double newY = y + vy;
        //���ɗ�������Q�[���I�[�o�[
        //if (newY >= 609)System.exit(0);
        //System.out.println(newY);
        // �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
        // y���������l����̂�x���W�͕ω����Ȃ��Ɖ���
        tile = map.getTileCollision(this, x, newY);
        if (tile == null) {
            // �Փ˂���^�C�����Ȃ���Έړ�
            y = newY;
            // �Փ˂��ĂȂ��Ƃ������Ƃ͋�
            onGround = false;
        } else {
            // �Փ˂���^�C��������ꍇ
            if (vy > 0) { // ���ֈړ����Ȃ̂ŉ��̃u���b�N�ƏՓˁi���n�j
                // �ʒu����
                y = Map.tilesToPixels(tile.y) - HEIGHT;
                // ���n�����̂�y�������x��0��
                vy = 0;
                // ���n
                onGround = true;
            } else if (vy < 0) { // ��ֈړ����Ȃ̂ŏ�̃u���b�N�ƏՓˁi�V�䂲��I�j
                // �ʒu����
                y = Map.tilesToPixels(tile.y + 1);
                // �V��ɂԂ������̂�y�������x��0��
                vy = 0;
            }
        }
    }

    /**
     * �v���C���[��`��
     * 
     * @param g �`��I�u�W�F�N�g
     * @param offsetX X�����I�t�Z�b�g
     * @param offsetY Y�����I�t�Z�b�g
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.fillRect((int) x + offsetX, (int) y + offsetY, WIDTH, HEIGHT);
    }

    /**
     * @return Returns the x.
     */
    public double getX() {
        return x;
    }
    /**
     * @return Returns the y.
     */
    public double getY() {
        return y;
    }
}
