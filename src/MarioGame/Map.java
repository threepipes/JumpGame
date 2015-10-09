package MarioGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.LinkedList;

/*
 * Created on 2005/06/16
 *
 */

/**
 * @author mori
 *  
 */
public class Map {
    // タイルサイズ
    public static final int TILE_SIZE = 32;
    // 行数
    public static int ROW;
    // 列数
    public static int COL;
    // 幅
    public static int WIDTH;
    // 高さ
    public static int HEIGHT;
    // 重力
    public static final double GRAVITY = 2.0;
    
    // スプライトリスト
    private LinkedList<Sprite> sprites;

    // マップ
    private int[][] map;
    private int[][] initmap;
    
    private GameManager manager;

    public Map(GameManager manager) {
        sprites = new LinkedList<>();
    	load("Map/map.map");
    	Collections.sort(sprites);
    	this.manager = manager;
    	initmap = new int[map.length][map[0].length];
    	for(int i=0; i<map.length; i++){
    		for(int j=0; j<map[i].length; j++){
    			initmap[i][j] = map[i][j];
    		}
    	}
    }
    
    public void resetMap(){
       	for(int i=0; i<map.length; i++){
    		for(int j=0; j<map[i].length; j++){
    			map[i][j] = initmap[i][j];
    		}
    	}
       	sprites.stream().forEach(e->e.init());
    }

    /**
     * マップを描画する
     * 
     * @param g 描画オブジェクト
     * @param offsetX X方向オフセット
     * @param offsetY Y方向オフセット
     */
    public void draw(Graphics g, int offsetX, int offsetY) {
        // オフセットを元に描画範囲を求める
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(GameManager.WIDTH) + 2;
        // 描画範囲がマップの大きさより大きくならないように調整
        lastTileX = Math.min(lastTileX, COL);

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(GameManager.HEIGHT) + 1;
        // 描画範囲がマップの大きさより大きくならないように調整
        lastTileY = Math.min(lastTileY, ROW);

        //g.setColor(Color.ORANGE);
        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                // mapの値に応じて画像を描く
                switch (map[i][j]) {
                    case 1 : // ブロック
                    	g.setColor(Color.ORANGE);
                        g.fillRect(tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, TILE_SIZE, TILE_SIZE);
                        break;
                }
            }
        }
    }
    
    /**
     * (newX, newY)で衝突するブロックの座標を返す
     * @param player プレイヤーへの参照
     * @param newX X座標
     * @param newY Y座標
     * @return 衝突するブロックの座標
     */
    public Point getTileCollision(Sprite sprite, double newX, double newY) {
        // 小数点以下切り上げ
        // 浮動小数点の関係で切り上げしないと衝突してないと判定される場合がある
        newX = Math.ceil(newX);
        newY = Math.ceil(newY);
        
        double fromX = Math.min(sprite.getX(), newX);
        double fromY = Math.min(sprite.getY(), newY);
        double toX = Math.max(sprite.getX(), newX);
        double toY = Math.max(sprite.getY(), newY);
        
        int fromTileX = pixelsToTiles(fromX);
        int fromTileY = pixelsToTiles(fromY);
        int toTileX = pixelsToTiles(toX + sprite.WIDTH - 1);
        int toTileY = pixelsToTiles(toY + sprite.HEIGHT - 1);

        // 衝突しているか調べる
        out:for (int x = fromTileX; x <= toTileX; x++) {
            for (int y = fromTileY; y <= toTileY; y++) {
                // 画面外は衝突
                if (x < 0 || x >= COL) {
                    return new Point(x, y);
                }
                if (y < 0 || y >= ROW) {
                	//穴に落ちたらゲームオーバー
                	sprite.death();
                	break out;
                }
                // ブロックがあったら衝突
                if (map[y][x] == 1) {
                    return new Point(x, y);
                }
            }
        }
        
        return null;
    }
    
    public void gameover(){
    	manager.gameOver();
    }
    
    /**
     * ファイルからマップを読み込む
     * 
     * @param filename 読み込むマップデータのファイル名
     */
    private void load(String filename) {
        try {
        	FileInputStream in = new FileInputStream(new File(filename));
            ROW = in.read();
            COL = in.read()<<8 | in.read();
            // マップサイズを設定
            WIDTH = TILE_SIZE * COL;
            HEIGHT = TILE_SIZE * ROW;
            // マップを作成
            map = new int[ROW][COL];
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    map[i][j] = in.read();
                    switch (map[i][j]) {
                    case 2:  // 針
                        sprites.add(new Needle(tilesToPixels(j), tilesToPixels(i),/* "coin.gif",*/ this));
                        break;
                    case 3:  // ばね
                    	sprites.add(new Spring(tilesToPixels(j),tilesToPixels(i),this));
                    	break;
                    case 4:	//クリボー
                    	sprites.add(new Kuribo(tilesToPixels(j),tilesToPixels(i),this));
                    	break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * ピクセル単位をタイル単位に変更する
     * @param pixels ピクセル単位
     * @return タイル単位
     */
    public static int pixelsToTiles(double pixels) {
        return (int)Math.floor(pixels / TILE_SIZE);
    }
    
    /**
     * タイル単位をピクセル単位に変更する
     * @param tiles タイル単位
     * @return ピクセル単位
     */
    public static int tilesToPixels(int tiles) {
        return tiles * TILE_SIZE;
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
    /**
     * @return Returns the sprites.
     */
    public LinkedList getSprites() {
        return sprites;
    }
}
