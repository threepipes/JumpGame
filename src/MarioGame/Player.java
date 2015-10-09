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
public class Player extends Sprite{
	// スピード
    private static final int SPEED = 8;
    // ジャンプ力
    private static final int JUMP_SPEED = 20;

    // 速度
    private double vx;
    private double vy;
    
    // 着地しているか
    private boolean onGround;
    
    // 再ジャンプできるか
    private boolean forceJump;
    
    // マップへの参照
    private Map map;
    
    public Player(double x, double y, Map map) {
    	super(x,y,map);
        this.x = x;
        this.y = y;
        this.map = map;
        vx = SPEED;
        vy = 0;
        onGround = false;
        forceJump = false;
        exist = true;
    }
    
    /**
     * 停止する
     
    public void stop() {
        vx = 0;
    }
     */
    /**
     * 左に加速する
     
    public void accelerateLeft() {
        vx = -SPEED;
    }*/

    /**
     * 右に加速する
     
    public void accelerateRight() {
        vx = SPEED;
    }*/

    /**
     * ジャンプする
     */
    public void jump() {
        if (onGround || forceJump) {
            // 上向きに速度を加える
            vy = -JUMP_SPEED;
            onGround = false;
            forceJump = false;
        }
    }
    public void jump2(){
    	vy = -JUMP_SPEED * 2;
    	onGround = false;
    }
    
    public void setForceJump(boolean forceJump) {
        this.forceJump = forceJump;
    }
    
    /**
     * プレイヤーの状態を更新する
     */
    public void update() {
        // 重力で下向きに加速度がかかる
        vy += Map.GRAVITY;

        // x方向の当たり判定
        // 移動先座標を求める
        double newX = x + vx;
        // 移動先座標で衝突するタイルの位置を取得
        // x方向だけ考えるのでy座標は変化しないと仮定
        Point tile = map.getTileCollision(this, newX, y);
        if(!exist) return;
        if (tile == null) {
            // 衝突するタイルがなければ移動
            x = newX;
        } else {
            // 衝突するタイルがある場合
        	map.gameover();
            /*if (vx > 0) { // 右へ移動中なので右のブロックと衝突
                // ブロックにめりこむ or 隙間がないように位置調整
                //x = Map.tilesToPixels(tile.x) - WIDTH;
            } else if (vx < 0) { // 左へ移動中なので左のブロックと衝突
                // 位置調整
                x = Map.tilesToPixels(tile.x + 1);
            }*/
            //vx = 0;
        }

        // y方向の当たり判定
        // 移動先座標を求める
        double newY = y + vy;
        //穴に落ちたらゲームオーバー
        //if (newY >= 609)System.exit(0);
        //System.out.println(newY);
        // 移動先座標で衝突するタイルの位置を取得
        // y方向だけ考えるのでx座標は変化しないと仮定
        tile = map.getTileCollision(this, x, newY);
        if (tile == null) {
            // 衝突するタイルがなければ移動
            y = newY;
            // 衝突してないということは空中
            onGround = false;
        } else {
            // 衝突するタイルがある場合
            if (vy > 0) { // 下へ移動中なので下のブロックと衝突（着地）
                // 位置調整
                y = Map.tilesToPixels(tile.y) - HEIGHT;
                // 着地したのでy方向速度を0に
                vy = 0;
                // 着地
                onGround = true;
            } else if (vy < 0) { // 上へ移動中なので上のブロックと衝突（天井ごん！）
                // 位置調整
                y = Map.tilesToPixels(tile.y + 1);
                // 天井にぶつかったのでy方向速度を0に
                vy = 0;
            }
        }
    }
    
    @Override
    public void death() {
    	exist = false;
    	map.gameover();
    }
    
    public boolean onGround(){
    	return onGround;
    }

    /**
     * プレイヤーを描画
     * 
     * @param g 描画オブジェクト
     * @param offsetX X方向オフセット
     * @param offsetY Y方向オフセット
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
    public double getVX() {
        return vx;
    }
    /**
     * @return Returns the y.
     */
    public double getY() {
        return y;
    }
}
