package MarioGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/*
 * Created on 2005/06/24
 *
 */

/**
 * @author mori
 *
 */
public abstract class Sprite implements Comparable<Sprite>{
    // 位置
    protected double x;
    protected double y;
    
    // 幅
    protected int WIDTH;
    // 高さ
    protected int HEIGHT;
    
    // スプライト画像
    protected Image image;

    // アニメーション用カウンタ
    protected int count;

    // マップへの参照
    protected Map map;
    
    protected boolean exist;

    public Sprite(double x, double y,/* String fileName,*/ Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
        exist = true;
        WIDTH = 32;
        HEIGHT = 32;

        // イメージをロードする
//        loadImage(fileName);

//        count = 0;
        
        // アニメーション用スレッドを開始
//        AnimationThread thread = new AnimationThread();
//        thread.start();
    }
    
    public void init(){
    	exist = true;
    }

    /**
     * スプライトの状態を更新する
     */
    public abstract void update();

    /**
     * スプライトを描画
     * 
     * @param g 描画オブジェクト
     * @param offsetX X方向オフセット
     * @param offsetY Y方向オフセット
     */
    public void draw(int i,Graphics g, int offsetX, int offsetY) {
    	if(!exist) return;
    	if(i==2)
    		g.setColor(Color.BLUE);
    	if(i==3)
    		g.setColor(Color.WHITE);
    	if(i==4)
    		g.setColor(Color.GREEN);
        g.fillRect((int)x + offsetX, (int)y + offsetY, 32, 32);
        /*g.drawImage(image,
                (int) x + offsetX, (int) y + offsetY, 
                (int) x + offsetX + width, (int) y + offsetY + height,
                count * width, 0,
                count * width + width, height,
                null);*/
    }

    /**
     * 他のスプライトと接触しているか
     * @param sprite スプライト
     */
    public boolean isCollision(Sprite sprite) {
        Rectangle playerRect = new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
        Rectangle spriteRect = new Rectangle((int)sprite.getX(), (int)sprite.getY(), sprite.getWidth(), sprite.getHeight());
        // 自分の矩形と相手の矩形が重なっているか調べる
        if (playerRect.intersects(spriteRect)) {
            return true;
        }
        
        return false;
    }
    
    public void death(){
    	exist = false;
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
    /**
     * @return Returns the width.
     */
    public int getWidth() {
        return WIDTH;
    }
    /**
     * @return Returns the height.
     */
    public int getHeight() {
        return HEIGHT;
    }
    
    @Override
    public int compareTo(Sprite o) {
    	return (int)(x - o.x);
    }

    /**
     * イメージをロードする
     * @param filename イメージファイル名
     */
    private void loadImage(String filename) {
        /*ImageIcon icon = new ImageIcon(getClass().getResource(
                "image/" + filename));
        image = icon.getImage();*/
    }

    // アニメーション用スレッド
    private class AnimationThread extends Thread {
       /* public void run() {
            while (true) {
                // countを切り替える
                if (count == 0) {
                    count = 1;
                } else if (count == 1) {
                    count = 0;
                }

                // 300ミリ秒休止＝300ミリ秒おきに勇者の絵を切り替える
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}