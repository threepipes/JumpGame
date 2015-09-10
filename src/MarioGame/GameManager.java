package MarioGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;


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
        map = new Map(this);

        // プレイヤーを作成
        player = new Player(192, 890, map);
	}
	
    /**
     * ゲームオーバー
     */
    public void gameOver() {
        // マップを作成
        map = new Map(this);

        // プレイヤーを作成
        player = new Player(100, 890, map);
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
     
     // マップにいるスプライトを取得
        LinkedList sprites = map.getSprites();            
        Iterator iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = (Sprite)iterator.next();
            
            // スプライトの状態を更新する
            sprite.update();

            // プレイヤーと接触してたら
            if (player.isCollision(sprite)) {
                if (sprite instanceof Needle) {  // 針
                    Needle needle = (Needle)sprite;
                    gameOver();
                    break;
                 }
                 else if (sprite instanceof Spring) {  //　ばね
                        Spring spring = (Spring)sprite;
                        // コインは消える
                        player.jump2();
                        break;
                 }else if (sprite instanceof Kuribo) {  // 栗ボー
                     Kuribo kuribo = (Kuribo)sprite;
                     // 上から踏まれてたら
                     if ((int)player.getY() < (int)kuribo.getY()) {
                         // 栗ボーは消える
                         sprites.remove(kuribo);
                         // 踏むとプレイヤーは再ジャンプ
                         player.setForceJump(true);
                         player.jump();
                         break;
                     } else {
                         // ゲームオーバー
                         gameOver();
                     }
                 }
                /*} else if (sprite instanceof Accelerator) {  // 加速アイテム
                    // アイテムは消える
                    sprites.remove(sprite);
                    Accelerator accelerator = (Accelerator)sprite;
                    // サウンド
                    accelerator.play();
                    // アイテムをその場で使う
                    accelerator.use(player);
                    break;
                } else if (sprite instanceof JumperTwo) {  // 二段ジャンプアイテム
                    // アイテムは消える
                    sprites.remove(sprite);
                    JumperTwo jumperTwo = (JumperTwo)sprite;
                    // サウンド
                    jumperTwo.play();
                    // アイテムをその場で使う
                    jumperTwo.use(player);
                    break;                        
                }*/
            }
        }
	}
	
	public void draw(Graphics g){

        // 背景を黒で塗りつぶす
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        

        // マップを描画
        map.draw(g, offsetX, offsetY);

        // プレイヤーを描画
        player.draw(g, offsetX, offsetY);
        
        // スプライトを描画
        // マップにいるスプライトを取得
        LinkedList sprites = map.getSprites();            
        Iterator iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = (Sprite)iterator.next();
            if(sprite instanceof Needle){
            	sprite.draw(2,g, offsetX, offsetY);
            }else if(sprite instanceof Spring){
            	sprite.draw(3,g, offsetX, offsetY);
            }else if(sprite instanceof Kuribo){
            	sprite.draw(4, g, offsetX, offsetY);
            }
        }
	}
	
	public static final int KEY_PRESSED = 1;
	public static final int KEY_RELEASED = 2;
	public void setKeyEvent(int keytype){
		if((keytype|KEY_PRESSED) > 0){
			player.jump();
		}
	}
	
	public void exitRequest(){
		
	}

}
