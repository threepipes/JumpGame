package MarioGame;

import java.awt.Color;
import java.awt.Graphics;

public class GameManager{
    // パネルサイズ
    public static final int WIDTH = 540;
    public static final int HEIGHT = 960;

    // マップ
    private Map map;

    // プレイヤー
    private Player player;
    
    private int offsetX;
    private int offsetY;

    // キーの状態（押されているか、押されてないか）
//    private boolean leftPressed;
//    private boolean rightPressed;
//    private boolean upPressed;
    
	public GameManager(){

        // マップを作成
        map = new Map();

        // プレイヤーを作成
        player = new Player(192, 32, map);
	}
	
	private void culcOffset(){


        // X方向のオフセットを計算
        offsetX = WIDTH / 2 - (int)player.getX();
        // マップの端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, WIDTH - Map.WIDTH);

        // Y方向のオフセットを計算
        offsetY = HEIGHT / 2 - (int)player.getY();
        // マップの端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, HEIGHT - Map.HEIGHT);	
	}
	
	public void update(){

		culcOffset();
        
        player.update();
	}
	
	public void draw(Graphics g){

        // 背景を黒で塗りつぶす
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        

        // マップを描画
        map.draw(g, offsetX, offsetY);

        // プレイヤーを描画
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
