package MarioGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
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
    
    Replay rep;
    
    // キーの状態（押されているか、押されてないか）
//    private boolean leftPressed;
//    private boolean rightPressed;
//    private boolean upPressed;
    
    private static int goal = 700*32 - 8*32;
	private static final int OFFSET_POS = WIDTH / 5;
	private static final int Player_init_x = OFFSET_POS;
	private static final int Player_init_y = HEIGHT - 32*3;
	public GameManager(){

        // マップを作成
        map = new Map(this);
        goal = map.getWidth() - 8*32;

        // プレイヤーを作成
        player = new Player(Player_init_x, Player_init_y, map);
        rep = new Replay("48,98,122,146,170,249,269,326,433,462,482,499,517,554,577,601,649,704,740,761,781,902,954,978,998,1018,1094,1118,1156,1188,1267,1306,1358,1498,1580,1602,1621,1636,1653,1678,1701,1721,1736,1754,1894,1929,1957,2001,2054,2074,2094,2114,2142,2166,2233,2249,2265");
//        rep = new Replay("26,50,74,173,193,234,254,274,325,350,414,461,478,504,525,546,570,593,621,646,677,697,736,833,884,914,938,957,1006,1034,1086,1134,1160,1178,1201,1234,1258,1282,1302,1368,1402,1421,1442,1466,1490,1545,1573,1597,1630,1673,1692,1707,1725,1740,1916,1934,1958,2018,2098,2134,2154,2178,2198,2218,2281,2374,2437,2457,2490,2512,2534,2550,2572,2642,2662,2700,2738,2762,2793,2810,2830,2878,2920,2962,3002,3124,3146,3165,3186,3216,3238,3268,3290,3317,3359,3403,3442,3472,3494,3522,3542,3569,3649,3825");
	}
	
    /**
     * ゲームオーバー
     */
	boolean gmov = false;
	int count = 0;
    public void gameOver() {
//    	System.out.println(rep);
//        // マップを作成
//        map.resetMap();
//
//        // プレイヤーを作成
//        player = new Player(Player_init_x, Player_init_y, map);
//        rep.init();
    	System.out.println(++count);
        gmov = true;
    }
    
    private void reset(){
    	System.out.println(rep);
        // マップを作成
        map.resetMap();

        // プレイヤーを作成
        System.out.println(player.x - rep.frame*player.getVX());
        player = new Player(Player_init_x, Player_init_y, map);
        rep.init();
    }
	
	private void culcOffset(){


        // X方向のオフセットを計算
        offsetX = WIDTH / 5 - (int)player.getX();
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
		if(gmov){
			reset();
			gmov = false;
		}

		culcOffset();
		
		if(player.onGround()){
			rep.lastOnGround = rep.frame;
			rep.ong.set(rep.frame);
		}
		if(rep.checkJump())
			setKeyEvent(KEY_PRESSED);
        
        player.update();
//        if(gmov){
//        	gmov = false;
//        	return;
//        }
        
        rep.frame++;
        if(player.x > goal)	exitRequest();
        
     
        if(gmov) return;
     // マップにいるスプライトを取得
        LinkedList sprites = map.getSprites();            
        Iterator iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = (Sprite)iterator.next();
            
            // スプライトの状態を更新する
            sprite.update();
            // プレイヤーより一画面以上先にいるスプライトと衝突判定しても仕方ないのでbreak
            if(sprite.getX() > player.getX() + WIDTH)
            	break;

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
                	 if(!sprite.exist) continue;
                     Kuribo kuribo = (Kuribo)sprite;
                     // 上から踏まれてたら
                     if ((int)player.getY() < (int)kuribo.getY()) {
                         // 栗ボーは消える
//                         sprites.remove(kuribo);
                         kuribo.death();
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
		save(new File("replay.rp"), rep.toString());
		System.exit(0);
	}
	
	private void save(File file, String buffer){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(buffer);
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Replay{
	int frame;
	
	int deathFrame;
	int lastOnGround;
	ArrayList<Integer> list;
	BitSet lastJump = new BitSet();
	BitSet ong = new BitSet();
	int idx;
	Replay(){
		frame = 0;
		list = new ArrayList<>();
		idx = 0;
	}
	
	Replay(String str){
		frame = 0;
		list = new ArrayList<>();
		idx = 0;
		String[] data = str.split(",");
		for(String d: data){
			list.add(Integer.parseInt(d));
		}
	}
	
	boolean checkJump(){
		if(idx >= list.size() || list.get(idx) != frame) return false;
		idx++;
		return true;
	}
	
	void init(){
		System.out.println(frame+" "+idx+" "+lastOnGround);
		frame = 0;
		idx = 0;
		if(list.isEmpty()){
			list.add(lastOnGround);
			return;
		}
		int last = list.get(list.size()-1);
		int oldlast = last;
		if(last == lastOnGround || lastJump.get(lastOnGround)){
			lastJump.set(last);
			last = lastOnGround;
//			last -= 1;
			while(!ong.get(last) || lastJump.get(last)) last--;
			if(last <= oldlast)	list.set(list.size()-1, last);
			else list.add(last);
		}
		else list.add(lastOnGround);
		int prev = Integer.MAX_VALUE;
		for(int i=list.size()-1; i>=0; i--){
			if(list.get(i) >= prev) list.set(i, 0);
			else prev = list.get(i);
		}
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){
			if(it.next()==0) it.remove();
		}
		lastOnGround = 0;
		ong.clear();
	}
	
	@Override
	public String toString() {
		if(list.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for(int i: list){
			sb.append(i+",");
		}
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
}
