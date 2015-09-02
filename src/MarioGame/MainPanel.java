package MarioGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, KeyListener {


    // ゲームループ用スレッド
    private Thread gameLoop;
    private GameManager gamemanager;
//    private boolean upPressed;

    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(GameManager.WIDTH, GameManager.HEIGHT));
        // パネルがキー入力を受け付けるようにする
        setFocusable(true);

        
        //ゲームマネージャーの作成
        gamemanager = new GameManager();

        // キーイベントリスナーを登録
        addKeyListener(this);
//        upPressed = false;
        // ゲームループ開始
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * ゲームループ
     */
    public void run() {
        while (true) {
/*            if (leftPressed) {
                // 左キーが押されていれば左向きに加速
                player.accelerateLeft();
            } else if (rightPressed) {
                // 右キーが押されていれば右向きに加速
                player.accelerateRight();
            } else {
                // 何も押されてないときは停止
                player.stop();
            }
*/
//            if (upPressed) {
//                // ジャンプする
//                player.jump();
//            }
            
            gamemanager.update();
            // 再描画
            repaint();

            // 休止
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 描画処理
     * 
     * @param 描画オブジェクト
     */
    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
        gamemanager.draw(g);
    }

    /**
     * キーが押されたらキーの状態を「押された」に変える
     * 
     * @param e キーイベント
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
     * キーが離されたらキーの状態を「離された」に変える
     * 
     * @param e キーイベント
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