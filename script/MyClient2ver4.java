import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;//画像処理に必要
import java.awt.geom.*;//画像処理に必要
import java.awt.Graphics;
import java.applet.Applet;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import java.io.File;//音楽再生時に必要
import javax.sound.sampled.AudioFormat;//音楽再生時に必要
import javax.sound.sampled.AudioSystem;//音楽再生時に必要
import javax.sound.sampled.Clip;//音楽再生時に必要
import javax.sound.sampled.DataLine;//音楽再生時に必要

public class MyClient2ver4 extends JFrame implements MouseListener,MouseMotionListener,KeyListener{
	private JButton theMyBarButton = new JButton();
	private JButton theYourBarButton = new JButton();
	private JButton theStartButton = new JButton();
	private JButton RuleBtn = new JButton();
	private JButton BGMBtn = new JButton();
	private JButton SEBtn = new JButton();
	private JButton Rule_return_Btn = new JButton();
	private JButton EndBtn = new JButton();
	private JButton BlockArray[][];
	private JTextField theTextField_IP, theTextField_Name;
	private ImageIcon IPicon,NAMEicon, titleicon, waitflameicon, waitguruguruicon,waitmsg_search_icon, waitmsg_nomatch_icon, waitmsg_match_icon, waitmsg_noconect_icon, BGM_ONicon, BGM_OFFicon, SE_ONicon, SE_OFFicon;
	private JLabel IPLabel, NAMELabel, titleLabel, waitflameLabel, waitguruguruLabel, waitmsgLabel;
	//private Container c;
	private ImageIcon bollicon ,player1_bollicon, player2_bollicon,player1_baricon, player2_baricon;
	private ImageIcon blockicon ,stageicon, campasicon;
	private ImageIcon startbtn_mouseout_icon,startbtn_mousein_icon;//スタートボタン用
	private ImageIcon rulebtn_mouseout_icon,rulebtn_mousein_icon;//ルールボタン用
	private Image boll2;
	private JLabel mybolllabel, yourbolllabel;
	private int mybollnumber = 0, yourbollnumber = 0;
	private int [] startcount = { 0, 0};//スタート準備を管理
	private int[] playerbollcount = {0,0};
	private int block[] = new int[48];
	private int blockwidth = 60, blockheight = 40;//ブロックの設定
	private JLabel stageLabel;
	private final int stageposition_x = 100, stageposition_y = 100;//ステージを移動に対応するための変数　デザイン時に調整
	private final int stagewidth = 600,stageheight = 800;
	private int BollStartPositionx = 290,BollStartPositiony = 390;//Bollの初期値
	private int playernum;//1or2
	private int player1_point = 0,player2_point = 0;//得点
	private int player1_lifepoint = 3, player2_lifepoint = 3;
	private final int MyBarPositionY = 770,YourBarPositionY = 20;//Barの設定
	private int Barwidth = 120;//Barのヨコ　デフォルト
	private int mybarhitnum = 0, yourberhitnum = 0;//連続当たり判定の防止
	private JLabel mypointLabel, yourpointLabel, PTLabel_1,PTLabel_2;
	private JLabel myNameLabel, yourNameLabel;
	private ImageIcon player1_life,player1_nolife,player2_life,player2_nolife;
	private JLabel mylifeLabel_1,mylifeLabel_2,mylifeLabel_3, yourlifeLabel_1,yourlifeLabel_2,yourlifeLabel_3;
	private ImageIcon winicon, loseicon, Ruleicon, Rule_return_icon, Endicon;
	private JLabel winLabel, loseLabel, RuleLabel;
	private String myName, yourName, ip;
	private int myNumberInt = 0;;
	private boolean SEflag = true, BGMflag = true;
	private int vector[][] = {
		{-3, -2, -1,  1,  2,  3},//x座標
		{-1, -2, -3, -3, -2, -1},//ｙ座標
	};
	private int[] BollThred = {0,0};//スレッドの管理　重複防止　スレッドが動いていたら１、動いていなかったら０
	SoundPlayer SE_Btn, SE_Boll, BGM_Start;//どこからでもアクセスできるように，クラスのメンバとして宣言
	PrintWriter out;//出力用のライター
	Graphics g;
	JPanel c = new JPanel();//Containerでが強制描画機能が使えないのでJPanelを使う．使い方はほとんどContainerと同じ


	public MyClient2ver4() {
		//ウィンドウを作成する
		this.add(c,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
		setTitle("MyClient2ver4");//ウィンドウのタイトルを設定する
		setSize(1220,1000);//ウィンドウのサイズを設定する
		//c = getContentPane();//フレームのペインを取得する
		c.setLayout(null);//自動レイアウトの設定を行わない
		
		
		//初期画面---------------------------------------------------------------------------------------------
		//title
		titleicon = new ImageIcon("title2.png");
		titleLabel = new JLabel(titleicon);
		c.add(titleLabel);
		titleLabel.setBounds(50,40,900,296);
		titleLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//startbtnの作成
		startbtn_mouseout_icon = new ImageIcon("startbtn_mouseout.png");
		startbtn_mousein_icon = new ImageIcon("startbtn_mousein.png");
		theStartButton = new JButton(startbtn_mouseout_icon);//ボタンを作成，テキストの表示
		c.add(theStartButton);//ペインに貼り付ける
		theStartButton.setBounds(200,700,800,100);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		theStartButton.setContentAreaFilled(false);//btnの背景をなくしている
		theStartButton.setBorderPainted(false);//btnの枠線をなくしている
		theStartButton.addMouseListener(this);
		theStartButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//ルールボタン
		rulebtn_mouseout_icon = new ImageIcon("rule_mouseout.png");
		rulebtn_mousein_icon = new ImageIcon("rule_mousein.png");
		RuleBtn = new JButton(rulebtn_mouseout_icon);//ボタンを作成，テキストの表示
		c.add(RuleBtn);//ペインに貼り付ける
		RuleBtn.setBounds(200,550,400,100);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		RuleBtn.setContentAreaFilled(false);//btnの背景をなくしている
		RuleBtn.setBorderPainted(false);//btnの枠線をなくしている
		RuleBtn.addMouseListener(this);
		RuleBtn.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//ルール画面から戻るボタン
		Rule_return_icon = new ImageIcon("Rule_return.png");
		Rule_return_Btn = new JButton(Rule_return_icon);
		c.add(Rule_return_Btn);
		Rule_return_Btn.setBounds(930,120,150,80);
		Rule_return_Btn.setContentAreaFilled(false);//btnの背景をなくしている
		Rule_return_Btn.setBorderPainted(false);//btnの枠線をなくしている
		Rule_return_Btn.addMouseListener(this);
		Rule_return_Btn.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		Rule_return_Btn.setVisible(false);
		
		
		//ルール画面 ルールボタンを押したら表示
		Ruleicon = new ImageIcon("Rule_2.png");
		RuleLabel = new JLabel(Ruleicon);
		c.add(RuleLabel);
		RuleLabel.setBounds(100,80,1000,828);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		RuleLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		RuleLabel.setVisible(false);
		
		
		//BGM
		BGM_ONicon = new ImageIcon("BGM_ONbtn.png");
		BGM_OFFicon = new ImageIcon("BGM_OFFbtn.png");
		
		BGMBtn = new JButton(BGM_ONicon);
		c.add(BGMBtn);//ペインに貼り付ける
		BGMBtn.setBounds(650,550,150,100);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		BGMBtn.setContentAreaFilled(false);//btnの背景をなくしている
		BGMBtn.setBorderPainted(false);//btnの枠線をなくしている
		BGMBtn.addMouseListener(this);
		BGMBtn.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//SE
		SE_ONicon = new ImageIcon("SE_ONbtn.png");
		SE_OFFicon = new ImageIcon("SE_OFFbtn.png");
		
		SEBtn = new JButton(SE_ONicon);
		c.add(SEBtn);//ペインに貼り付ける
		SEBtn.setBounds(850,550,150,100);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		SEBtn.setContentAreaFilled(false);//btnの背景をなくしている
		SEBtn.setBorderPainted(false);//btnの枠線をなくしている
		SEBtn.addMouseListener(this);
		SEBtn.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//ipアドレス
		IPicon = new ImageIcon("IP.png");
		IPLabel = new JLabel(IPicon);
		c.add(IPLabel);
		IPLabel.setBounds(200,400,150,50);
		theTextField_IP = new JTextField("localhost");
		c.add(theTextField_IP);
		theTextField_IP.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		theTextField_IP.setBounds(350,400,250,50);
		
		//名前
		NAMEicon = new ImageIcon("NAME.png");
		NAMELabel = new JLabel(NAMEicon);
		c.add(NAMELabel);
		NAMELabel.setBounds(200,470,150,50);
		theTextField_Name = new JTextField("");
		c.add(theTextField_Name);
		theTextField_Name.setBounds(350,470,250,50);
		theTextField_Name.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		
		
		//待機画面---------------------------------------------------------------------------------------------
		//待機画面フレーム
		waitflameicon = new ImageIcon("waitflame.png");
		waitflameLabel = new JLabel(waitflameicon);
		c.add(waitflameLabel);
		waitflameLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		waitflameLabel.setBounds(200,400,800,400);
		waitflameLabel.setVisible(false);
		
		//グルグルなるアイコン
		waitguruguruicon = new ImageIcon("wait_guruguru.gif");
		waitguruguruLabel = new JLabel(waitguruguruicon);
		c.add(waitguruguruLabel);
		waitguruguruLabel.setBounds(520,450,160,160);
		waitguruguruLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		waitguruguruLabel.setVisible(false);
		
		//待機画面メッセージ
		waitmsg_search_icon = new ImageIcon("waitmsg_search.png");//対戦相手を探しています
		waitmsg_nomatch_icon = new ImageIcon("waitmsg_nomatch.png");//みつかりませんでした
		waitmsg_match_icon = new ImageIcon("waitmsg_match.png");//みつかりました
		waitmsg_noconect_icon = new ImageIcon("waitmsg_noconect.png");//接続に失敗しました
		waitmsgLabel = new JLabel();
		c.add(waitmsgLabel);
		waitmsgLabel.setBounds(265,650,670,106);
		waitmsgLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		waitmsgLabel.setVisible(false);
		
		
		
		//対戦画面---------------------------------------------------------------------------------------------
		//やめるボタン
		Endicon = new ImageIcon("End.png");
		EndBtn = new JButton(Endicon);
		c.add(EndBtn);//ペインに貼り付ける
		EndBtn.setBounds(260,700,700,163);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		EndBtn.setContentAreaFilled(false);//btnの背景をなくしている
		EndBtn.setBorderPainted(false);//btnの枠線をなくしている
		EndBtn.addMouseListener(this);
		EndBtn.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		EndBtn.setVisible(false);
		
		//結果表示の設定
		winicon = new ImageIcon("win.png");
		loseicon = new ImageIcon("lose.png");
		winLabel = new JLabel(winicon);
		c.add(winLabel);
		winLabel.setBounds(210,150,800,771);
		winLabel.setVisible(false);
		winLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		loseLabel = new JLabel(loseicon);
		c.add(loseLabel);
		loseLabel.setBounds(210,150,800,771);
		loseLabel.setVisible(false);
		loseLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//ブロックの設定
		BlockArray = new JButton[6][8];//タテ6、ヨコ8のブロック
		blockicon = new ImageIcon("block_gray.png");
		for(int i = 0; i < 6; i++){
			for(int j = 0;j < 8; j++){
				BlockArray[i][j] = new JButton(blockicon);//ボタンにアイコンを設定する
				BlockArray[i][j].setContentAreaFilled(false);//btnの背景をなくしている
				BlockArray[i][j].setBorderPainted(false);//btnの枠線をなくしている
				c.add(BlockArray[i][j]);//ペインに貼り付ける
				BlockArray[i][j].setBounds(j*(blockwidth + 2) + 52 + stageposition_x, i*(blockheight + 2) + 275 + stageposition_y,blockwidth,blockheight);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
				BlockArray[i][j].setActionCommand(Integer.toString(6*i + j));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
				block[8*i + j] = 1;
				BlockArray[i][j].addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
				BlockArray[i][j].setVisible(false);
			}
		}
		
		
		//bollの設定
		player1_bollicon = new ImageIcon("player1_boll.png");
		player2_bollicon = new ImageIcon("player2_boll.png");
		mybolllabel = new JLabel();
		yourbolllabel = new JLabel();
		c.add(mybolllabel);
		c.add(yourbolllabel);
		mybolllabel.setBounds(BollStartPositionx + stageposition_x,BollStartPositiony + stageposition_y,20,20);
		yourbolllabel.setBounds(BollStartPositionx + stageposition_x,BollStartPositiony + stageposition_y,20,20);
		mybolllabel.setVisible(false);
		mybolllabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		yourbolllabel.setVisible(false);
		yourbolllabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//berを設定
		player1_baricon = new ImageIcon("player1_bar.png");
		player2_baricon = new ImageIcon("player2_bar.png");
		theMyBarButton = new JButton();
		theMyBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		theYourBarButton = new JButton();
		theYourBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		c.add(theMyBarButton);
		theMyBarButton.setBounds(240 + stageposition_x,770 + stageposition_y,Barwidth,10);
		theMyBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		theMyBarButton.setBorderPainted(false);//btnの枠線をなくしている
		c.add(theYourBarButton);
		theYourBarButton.setBounds(240 + stageposition_x,20 + stageposition_y,Barwidth,10);
		theYourBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		theYourBarButton.setBorderPainted(false);//btnの枠線をなくしている
		
		theMyBarButton.setVisible(false);
		theYourBarButton.setVisible(false);
		
		//ステージ背景色付きラベルの作成
		stageicon = new ImageIcon("stageflame.png");
		stageLabel = new JLabel(stageicon);
		c.add(stageLabel);
		stageLabel.setBounds(stageposition_x, stageposition_y, stagewidth, stageheight);
		stageLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		stageLabel.setVisible(false);
		
		
		//名前表示
		myNameLabel = new JLabel();
		c.add(myNameLabel);
		myNameLabel.setBounds(650 + stageposition_x , stageposition_y,200,50);
		myNameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		myNameLabel.setForeground(Color.BLACK);
		myNameLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		myNameLabel.setVisible(false);
		
		yourNameLabel = new JLabel();
		c.add(yourNameLabel);
		yourNameLabel.setBounds(650  + stageposition_x,400 + stageposition_y,200,50);
		yourNameLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		yourNameLabel.setForeground(Color.BLACK);
		yourNameLabel.setVisible(false);
		yourNameLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		
		//得点表示
		mypointLabel = new JLabel("0");
		c.add(mypointLabel);
		mypointLabel.setBounds(800  + stageposition_x,50 + stageposition_y,150,150);
		mypointLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 100));
		mypointLabel.setForeground(Color.BLACK);
		mypointLabel.setBackground(Color.pink); //文字の背景色の設定．
		mypointLabel.setVerticalAlignment(JLabel.BOTTOM);
		mypointLabel.setVisible(false);
		mypointLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		yourpointLabel = new JLabel("0");
		c.add(yourpointLabel);
		yourpointLabel.setBounds(800  + stageposition_x,450 + stageposition_y,150,150);
		yourpointLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 100));
		yourpointLabel.setForeground(Color.BLACK);
		yourpointLabel.setVerticalAlignment(JLabel.BOTTOM);
		yourpointLabel.setVisible(false);
		yourpointLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//Pt文字表示
		PTLabel_1 = new JLabel("Pt");
		PTLabel_2 = new JLabel("Pt");
		c.add(PTLabel_1);
		PTLabel_1.setBounds(950  + stageposition_x,150 + stageposition_y,50,50);
		PTLabel_1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 40));
		PTLabel_1.setForeground(Color.BLACK);
		PTLabel_1.setVerticalAlignment(JLabel.BOTTOM);
		PTLabel_1.setVisible(false);
		PTLabel_1.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		c.add(PTLabel_2);
		PTLabel_2.setBounds(950  + stageposition_x,550 + stageposition_y,50,50);
		PTLabel_2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 40));
		PTLabel_2.setForeground(Color.BLACK);
		PTLabel_2.setVerticalAlignment(JLabel.BOTTOM);
		PTLabel_2.setVisible(false);
		PTLabel_2.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//ライフの管理
		player1_life = new ImageIcon("player1_life.png");
		player1_nolife = new ImageIcon("player1_nolife.png");
		player2_life = new ImageIcon("player2_life.png");
		player2_nolife = new ImageIcon("player2_nolife.png");
		mylifeLabel_1 = new JLabel();
		mylifeLabel_2 = new JLabel();
		mylifeLabel_3 = new JLabel();
		yourlifeLabel_1 = new JLabel();
		yourlifeLabel_2 = new JLabel();
		yourlifeLabel_3 = new JLabel();
		c.add(mylifeLabel_1);
		c.add(mylifeLabel_2);
		c.add(mylifeLabel_3);
		c.add(yourlifeLabel_1);
		c.add(yourlifeLabel_2);
		c.add(yourlifeLabel_3);
		mylifeLabel_1.setBounds(660  + stageposition_x,250 + stageposition_y,75,65);
		mylifeLabel_2.setBounds(755  + stageposition_x,250 + stageposition_y,75,65);
		mylifeLabel_3.setBounds(840  + stageposition_x,250 + stageposition_y,75,65);
		yourlifeLabel_1.setBounds(660  + stageposition_x,650 + stageposition_y,75,65);
		yourlifeLabel_2.setBounds(745  + stageposition_x,650 + stageposition_y,75,65);
		yourlifeLabel_3.setBounds(840  + stageposition_x,650 + stageposition_y,75,65);
		mylifeLabel_1.setVisible(false);
		mylifeLabel_2.setVisible(false);
		mylifeLabel_3.setVisible(false);
		yourlifeLabel_1.setVisible(false);
		yourlifeLabel_2.setVisible(false);
		yourlifeLabel_3.setVisible(false);
		mylifeLabel_1.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		mylifeLabel_2.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		mylifeLabel_3.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		yourlifeLabel_1.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		yourlifeLabel_2.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		yourlifeLabel_3.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		
		//背景
		campasicon = new ImageIcon("campas_2.png");
		JLabel campasLabel = new JLabel(campasicon);
		c.add(campasLabel);
		campasLabel.setBounds(10, 0, 1200, 1000);
		campasLabel.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
		//キー入力の有効化
		addKeyListener(this);//Formでキー入力を受けるようにする＜KeyListerner関係＞
		requestFocus();//Formにフォーカスが移動する＜KeyListerner関係＞
		
		BGM_Start();
	}
	
	//メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {
			Socket socket;
			String myName;
			public MesgRecvThread(Socket s, String n){
				socket = s;
				myName = n;
			}
			//通信状況を監視し，受信データによって動作する
			public void run() {
				try{
					InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
					BufferedReader br = new BufferedReader(sisr);
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println(myName);//接続の最初に名前を送る
					String myNumberStr = br.readLine();
					myNumberInt = Integer.parseInt(myNumberStr);
					//playernumの決定
					if(myNumberInt % 2 == 0){//playerを1と2で区別
						playernum = 1;
						//System.out.println("あなたはplayer1です");
					} else {
						playernum = 2;
						//System.out.println("あなたはplayer2です");
					}
					
					while(true) {
						String inputLine = br.readLine();//データを一行分だけ読み込んでみる
						if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
							System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
							String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
							String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す
							//スタート準備
							if(cmd.equals("RedyStart")){
								int theplayernum = Integer.parseInt(inputTokens[1]);
								String TheName = inputTokens[2];
								if(playernum != theplayernum){
									yourName = TheName;
								}
								//start準備ができたら１
								startcount[theplayernum - 1] = 1;
							}
							//実際にスタート
							if(cmd.equals("LetStart")){
								String player1_Name = inputTokens[1];
								String player2_Name = inputTokens[2];
								if(playernum == 1){
									myName = player1_Name;
									yourName = player2_Name;
									mylifeLabel_1.setIcon(player1_life);
									mylifeLabel_2.setIcon(player1_life);
									mylifeLabel_3.setIcon(player1_life);
									yourlifeLabel_1.setIcon(player2_life);
									yourlifeLabel_2.setIcon(player2_life);
									yourlifeLabel_3.setIcon(player2_life);
								}else{
									myName = player2_Name;
									yourName = player1_Name;
									mylifeLabel_1.setIcon(player2_life);
									mylifeLabel_2.setIcon(player2_life);
									mylifeLabel_3.setIcon(player2_life);
									yourlifeLabel_1.setIcon(player1_life);
									yourlifeLabel_2.setIcon(player1_life);
									yourlifeLabel_3.setIcon(player1_life);
								}
								//System.out.println("LetStartを受信したお");
								startcount[0] = 1;
								startcount[1] = 1;
								PlayWindow();
							}
							//Barを動かす
							if(cmd.equals("BarMOVE")){//cmdの文字と"BarMOVE"が同じか調べる．同じ時にtrueとなる
								int theplayernum = Integer.parseInt(inputTokens[1]);
								int BarLocationX = Integer.parseInt(inputTokens[2]);
								if(playernum == theplayernum){
									theMyBarButton.setLocation(BarLocationX,MyBarPositionY + stageposition_y);
								}else{
									theYourBarButton.setLocation(stagewidth + (2 * stageposition_x) - BarLocationX - Barwidth,YourBarPositionY + stageposition_y);
								}
							}
							if(cmd.equals("BollPosition")){//cmdの文字と"StartBoll"が同じか調べる．同じ時にtrueとなる
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2用の座標とベクトル設定
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								BollThred[bollplayernum - 1] += 1;//スレッドが動くため1にする
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
							}
							if(cmd.equals("HitBarPosition")){//Barに対する跳ね返り処理
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2用の座標とベクトル設定
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								dy = (-1)*dy;//ｄｙを反転させる
								x = x + dx;
								y = y + dy;
								BollThred[bollplayernum - 1] += 1;//スレッドが動くため1にする
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("HitSidePosition")){//ヨコに対する跳ね返り処理
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2用の座標とベクトル設定
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								dx = (-1) * dx;//dxを反転させる
								x = x + dx;
								y = y + dy;
								BollThred[bollplayernum - 1] += 1;//スレッドが動くため1にする
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("HitBlock")){//ブロックに当たったときの処理
								int HitBlocknum = Integer.parseInt(inputTokens[1]);//当たったブロックの番号
								int bollplayernum = Integer.parseInt(inputTokens[2]);
								int x = Integer.parseInt(inputTokens[3]);
								int y = Integer.parseInt(inputTokens[4]);
								int dx = Integer.parseInt(inputTokens[5]);
								int dy = Integer.parseInt(inputTokens[6]);
								int i = HitBlocknum / 8; //yのやつ
								int j = HitBlocknum % 8; //xのやつ
								if(playernum == 2){//player2用の座標とベクトル設定
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
									i = 5 - i;
									j = 7 - j;
									block[i * 8 + j] = 0;
								}else if(playernum == 1){
									block[HitBlocknum] = 0;
								}
								
								countpoint(bollplayernum);
								BlockArray[i][j].setVisible(false);
								x = x + dx;
								y = y + dy;
								BollThred[bollplayernum - 1] += 1;//スレッドが動くため1にする
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("BollDead")){//ボールが場外の時の処理
								int deadbollplayernum = Integer.parseInt(inputTokens[1]);//場外のボールの番号
								//System.out.println("BollDead受信　" + deadbollplayernum);
								//System.out.println("playernum　" + playernum);
								if(playernum == deadbollplayernum){
									//System.out.println("マイボールが消えます");
									mybolllabel.setVisible(false);
								}else{
									//System.out.println("ゆあぼーるが消えます");
									yourbolllabel.setVisible(false);
								}
								c.repaint();
								c.paintImmediately(c.getBounds());
								playerbollcount[deadbollplayernum - 1] = 0; //ボールが場内にあるかどうあか
								BollThred[deadbollplayernum - 1] -= 1;//スレッドが止まるため０にする
								Lifecount(deadbollplayernum);
							}
						}else{
							break;
						}
					}
					socket.close();
				} catch (IOException e) {
					System.err.println("エラーが発生しました: " + e);
				}
			}
		}
		
	//repaintをするスレッド
	public class RepaintThread extends Thread {
			boolean repaintflag;
			public void stoprepaint(){
				repaintflag = false;
			}
			public void run(){
				repaintflag = true;
				while(true){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//repaint();
					c.repaint();
					c.paintImmediately(c.getBounds());
					if(!repaintflag){//接続ができたら終了
						break;
					}
				}
			}
	}
		
	//ボールを動かすスレッド 直線だけ
	public class BallMoveThread extends Thread {
		int bollplayernum;//1:player1, 2:player2
		int x,y,dx,dy;
		int HitBlocknum;//当たったブロックの番号
		int blockjudge;//ブロックの跳ね返り判定　x方向の反射：１、y方向の反射：０
		
		public BallMoveThread(int playernum,int x,int y,int dx,int dy){
			this.bollplayernum = playernum;
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
		}
		
		public void run(){
			
			while(true){
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(bollplayernum == playernum){//mybolllabelの処理
					mybolllabel.setVisible(true);
					mybolllabel.setLocation(x, y);
				}else{//yourbolllabelの処理
					yourbolllabel.setVisible(true);
					yourbolllabel.setLocation(x, y);
				}
				
				if((x + dx < stageposition_x)||(stagewidth + stageposition_x < (x + 20) + dx)){//ヨコの跳ね返り
					mybarhitnum = 0;
					yourberhitnum = 0;
					if(playernum == 1){
						SendHitSidePosition(bollplayernum,x,y,dx,dy);//跳ね返りがあると同期
					}
					//System.out.println("ヨコとの跳ね返りによりbreak" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//スレッドが止まるため０にする
					break;
				}
				if(judgehitbar(x,y,dx,dy)){//Barとの跳ね返り
					if(playernum == 1){
						SendHitBarPosition(bollplayernum,x,y,dx,dy);//跳ね返りがあると同期
					}
					//System.out.println("Bar都の跳ね返りよりbreak" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//スレッドが止まるため０にする
					break;
				}
				
				
				String[] BlockHitjudgeStr = BlockHitjudge(bollplayernum,x,y,dx,dy).split(" ");	//入力データを解析するために、スペースで切り分ける
				HitBlocknum = Integer.parseInt(BlockHitjudgeStr[0]);//ブロックとの跳ね返りがある場合、当たったブロックの番号を返し、跳ね返りがない場合、-1を返す
				if(HitBlocknum >= 0){//blockの跳ね返りがある場合
					String blockvct = BlockHitjudgeStr[1];
					//System.out.println(blockvct);
					if((blockvct.equals("DOWN"))||(blockvct.equals("UP"))){
						dy = -dy;
					}else if((blockvct.equals("LEFT"))||(blockvct.equals("RIGHT"))){
						dx = -dx;
					}else if(blockvct.equals("UP_LEFT")){
						dx = -2;
						dy = -2;
					}else if(blockvct.equals("UP_RIGHT")){
						dx = 2;
						dy = -2;
					}else if(blockvct.equals("DOWN_LEFT")){
						dx = -2;
						dy = 2;
					}else if(blockvct.equals("DOWN_RIGHT")){
						dx = 2;
						dy = 2;
					}
					mybarhitnum = 0;
					yourberhitnum = 0;
					if(playernum == 1){//player1が当たったことを送信
						SendHitBlockPosition(HitBlocknum, bollplayernum, x, y, dx, dy);
					}
					//System.out.println("Blockとの跳ね返りよりbreak" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//スレッドが止まるため０にする
					break;
				}
				
				if((y + dy < stageposition_y)||(stageheight + stageposition_y < (y + 20) + dy)){//ボールが場外へ出たときの処理
					BollThred[bollplayernum - 1] -= 0;//スレッドが止まるため０にする
					if(playernum == 1){
						SendBollDead(bollplayernum);
					}
					break;
				}
				
				if(BollThred[bollplayernum - 1] > 1){//同じボールに対して重複してスレッドを立てている場合 
					//System.out.println("スレッドが重複した" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;
					break;
				}
				
					x += dx;
					y += dy;
			}
		}
	}
	
	
	//下記を利用しています．一部書き換えています(停止機能を付加)    ．
	//http://nautilus2580.hatenablog.com/entry/2015/11/07/223457
	//mp3をwavに変換は，下記のサイトでできます
	//http://online-audio-converter.com/ja/
	public class SoundPlayer{
		private AudioFormat format = null;
		private DataLine.Info info = null;
		private Clip clip = null;
		boolean stopFlag = false;
		Thread soundThread = null;
		private boolean loopFlag = false;

		public SoundPlayer(String pathname){
			File file = new File(pathname);
			try{
				format = AudioSystem.getAudioFileFormat(file).getFormat();
				info = new DataLine.Info(Clip.class, format);
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(AudioSystem.getAudioInputStream(file));
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		public void SetLoop(boolean flag){
			loopFlag = flag;
		}

		public void play(){
			soundThread = new Thread(){
				public void run(){
					long time = (long)clip.getFrameLength();//44100で割ると再生時間（秒）がでる
					long endTime = System.currentTimeMillis()+time*1000/44100;
					clip.start();
					while(true){
						if(stopFlag){//stopFlagがtrueになった終了
							//System.out.println("PlaySound stop by stopFlag");
							clip.stop();
							return;
						}
						if(endTime < System.currentTimeMillis()){//曲の長さを過ぎたら終了
							if(loopFlag) {
								clip.loop(1);//無限ループとなる
							} else {
								clip.stop();
								return;
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			soundThread.start();
		}
		
		public void stop(){
			stopFlag = true;
			System.out.println("StopSound");
		}
	}
	
	//ボタンの効果音
	public void SE_Btn(){
		if(SEflag){
			SE_Btn = new SoundPlayer("SE_Btn.wav");
			SE_Btn.play();
		}
	}
	
	//ボールが跳ね返るときの効果音
	public void SE_Boll(){
		if(SEflag){
			SE_Boll = new SoundPlayer("SE_Boll.wav");
			SE_Boll.play();
		}
	}
	
	//スタート画面のBGM
	public void BGM_Start(){
		if(BGMflag){
			BGM_Start = new SoundPlayer("BGM_Start.wav");
			BGM_Start.SetLoop(true);
			BGM_Start.play();
		}
	}
	
	//BGMをOFFする
	public void BGM_OFF(){
		BGM_Start.stop();
	}
	
	public static void main(String[] args) {
			MyClient2ver4 net = new MyClient2ver4();
			net.setVisible(true);
	}
	
	public void BarSet(int playernum){//Barの設定を行う関数
		player1_baricon = new ImageIcon("player1_bar.png");
		player2_baricon = new ImageIcon("player2_bar.png");
		/*if(playernum == 1){
			theMyBarButton.setIcon(player1_baricon);
			theYourBarButton.setIcon(player2_baricon);
			mybolllabel.setIcon(player1_bollicon);
			yourbolllabel.setIcon(player2_bollicon);
		}else{
			theMyBarButton.setIcon(player2_baricon);
			theYourBarButton.setIcon(player1_baricon);
			mybolllabel.setIcon(player2_bollicon);
			yourbolllabel.setIcon(player1_bollicon);
		}*/
		c.add(theMyBarButton);
		theMyBarButton.setBounds(240 + stageposition_x,770 + stageposition_y,Barwidth,10);
		theMyBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		c.add(theYourBarButton);
		theYourBarButton.setBounds(240 + stageposition_x,20 + stageposition_y,Barwidth,10);
		theYourBarButton.addKeyListener(this);//ボタンにフォーカスがあってもKey入力を受け付けるようにする＜KeyListerner関係＞
		
	}
	
	//スタート画面を表示
	public void StartWindow(){
		//スタート画面の情報を表示
		titleLabel.setVisible(true);
		theStartButton.setVisible(true);
		RuleBtn.setVisible(true);
		BGMBtn.setVisible(true);
		SEBtn.setVisible(true);
		IPLabel.setVisible(true);
		theTextField_IP.setVisible(true);
		NAMELabel.setVisible(true);
		theTextField_Name.setVisible(true);
		
		//待機画面の情報を非表示
		waitflameLabel.setVisible(false);
		waitmsgLabel.setVisible(false);
		waitguruguruLabel.setVisible(false);
		
		//プレイ画面を非表示
		for(int i = 0; i < 6; i++){
			for(int j = 0;j < 8; j++){
				BlockArray[i][j].setVisible(false);
			}
		}
		theMyBarButton.setVisible(false);
		theYourBarButton.setVisible(false);
		stageLabel.setVisible(false);
		myNameLabel.setVisible(false);
		myNameLabel.setText(myName);
		yourNameLabel.setVisible(false);
		yourNameLabel.setText(yourName);
		mypointLabel.setVisible(false);
		yourpointLabel.setVisible(false);
		PTLabel_1.setVisible(false);
		PTLabel_2.setVisible(false);
		mylifeLabel_1.setVisible(false);
		mylifeLabel_2.setVisible(false);
		mylifeLabel_3.setVisible(false);
		yourlifeLabel_1.setVisible(false);
		yourlifeLabel_2.setVisible(false);
		yourlifeLabel_3.setVisible(false);
		EndBtn.setVisible(false);
		winLabel.setVisible(false);
		loseLabel.setVisible(false);
		mybolllabel.setVisible(false);
		yourbolllabel.setVisible(false);
	}
	
	
	//待機画面を表示する
	public void WaitWindow(){
		//スタート画面を非表示
		theStartButton.setVisible(false);
		RuleBtn.setVisible(false);
		BGMBtn.setVisible(false);
		SEBtn.setVisible(false);
		NAMELabel.setVisible(false);
		IPLabel.setVisible(false);
		theTextField_IP.setVisible(false);
		theTextField_Name.setVisible(false);
		
		//待機画面を表示
		waitflameLabel.setVisible(true);
		waitmsgLabel.setVisible(true);
		waitguruguruLabel.setVisible(true);
		//待機メッセージを「対戦相手を探しています」に設定
		waitmsgLabel.setIcon(waitmsg_search_icon);
		//c.repaint();
		c.repaint();
		c.paintImmediately(c.getBounds());
	}
	
	//プレイ画面を設定
	public void PlayWindow(){
		//スタート画面の情報を非表示
		titleLabel.setVisible(false);
		theStartButton.setVisible(false);
		RuleBtn.setVisible(false);
		BGMBtn.setVisible(false);
		SEBtn.setVisible(false);
		IPLabel.setVisible(false);
		theTextField_IP.setVisible(false);
		NAMELabel.setVisible(false);
		theTextField_Name.setVisible(false);
		
		//待機画面の情報を非表示
		waitflameLabel.setVisible(false);
		waitmsgLabel.setVisible(false);
		waitguruguruLabel.setVisible(false);
		
		//プレイ画面を表示
		for(int i = 0; i < 6; i++){
			for(int j = 0;j < 8; j++){
				BlockArray[i][j].setVisible(true);
			}
		}
		theMyBarButton.setVisible(true);
		theYourBarButton.setVisible(true);
		stageLabel.setVisible(true);
		if(playernum == 1){//player1の場合
			theMyBarButton.setIcon(player1_baricon);
			theYourBarButton.setIcon(player2_baricon);
			mybolllabel.setIcon(player1_bollicon);
			yourbolllabel.setIcon(player2_bollicon);
		}else{//player2の場合
			theMyBarButton.setIcon(player2_baricon);
			theYourBarButton.setIcon(player1_baricon);
			mybolllabel.setIcon(player2_bollicon);
			yourbolllabel.setIcon(player1_bollicon);
		}
		myNameLabel.setVisible(true);
		myNameLabel.setText(myName);
		yourNameLabel.setVisible(true);
		yourNameLabel.setText(yourName);
		mypointLabel.setVisible(true);
		yourpointLabel.setVisible(true);
		PTLabel_1.setVisible(true);
		PTLabel_2.setVisible(true);
		mylifeLabel_1.setVisible(true);
		mylifeLabel_2.setVisible(true);
		mylifeLabel_3.setVisible(true);
		yourlifeLabel_1.setVisible(true);
		yourlifeLabel_2.setVisible(true);
		yourlifeLabel_3.setVisible(true);
	}
	
	
	public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理
		JButton theBtnName = (JButton)e.getComponent();
			//スタートボタンが押されたとき
			SE_Btn();
			if(theBtnName == theStartButton){
				myName = theTextField_Name.getText();//名前の取得
				if(myName.equals("")){
					myName = "Noname";//名前がないときは，"No name"とする
				}
				ip = theTextField_IP.getText();//ipアドレスの取得
				if(ip.equals("")){
					ip = "localhost";//入力がない場合は自分のPCサーバに接続
				}
				WaitWindow();
				ConectServer();
			}
			
			//ルールボタンが押されたとき
			if(theBtnName == RuleBtn){
				SE_Btn();
				//スタート画面を非表示にする
				titleLabel.setVisible(false);
				theStartButton.setVisible(false);
				RuleBtn.setVisible(false);
				BGMBtn.setVisible(false);
				SEBtn.setVisible(false);
				NAMELabel.setVisible(false);
				IPLabel.setVisible(false);
				theTextField_IP.setVisible(false);
				theTextField_Name.setVisible(false);
				//ルール画面とルールから戻るボタンを表示
				RuleLabel.setVisible(true);
				Rule_return_Btn.setVisible(true);
				c.repaint();
				c.paintImmediately(c.getBounds());
			}
			if(theBtnName == Rule_return_Btn){//ルール画面から戻るボタン
				SE_Btn();
				//ルール画面とボタンを非表示
				RuleLabel.setVisible(false);
				Rule_return_Btn.setVisible(false);
				//スタート画面を表示
				titleLabel.setVisible(true);
				theStartButton.setVisible(true);
				RuleBtn.setVisible(true);
				BGMBtn.setVisible(true);
				SEBtn.setVisible(true);
				IPLabel.setVisible(true);
				theTextField_IP.setVisible(true);
				NAMELabel.setVisible(true);
				theTextField_Name.setVisible(true);
				c.repaint();
				c.paintImmediately(c.getBounds());
			}
			if(theBtnName == SEBtn){//SEボタンが押された処理
				if(SEBtn.getIcon() == SE_ONicon){
					//SEをOFFにする
					SEflag = false;
					SEBtn.setIcon(SE_OFFicon);
				}else{
					//SEをONにする
					SEflag = true;
					SEBtn.setIcon(SE_ONicon);
				}
			}
			if(theBtnName == BGMBtn){//BGMボタンが押された処理
				if(BGMBtn.getIcon() == BGM_ONicon){
					//BGMをOFFにする
					BGMflag = false;
					BGM_OFF();
					BGMBtn.setIcon(BGM_OFFicon);
				}else{
					//BGMをONにする
					BGMflag = true;
					BGM_Start();
					BGMBtn.setIcon(BGM_ONicon);
				}
			}
			
			if(theBtnName == EndBtn){//Ednボタンが押された処理
				Reset();
				SendBye();
				StartWindow();
			}
	}
	
		public void mouseEntered(MouseEvent e) {//マウスがオブジェクトに入ったときの処理
			JButton theBtnName = (JButton)e.getComponent();
			if(theBtnName == theStartButton){
				theStartButton.setIcon(startbtn_mousein_icon);
			}
			if(theBtnName == RuleBtn){
				RuleBtn.setIcon(rulebtn_mousein_icon);
			}
		}
		
		public void mouseExited(MouseEvent e) {//マウスがオブジェクトから出たときの処理
			JButton theBtnName = (JButton)e.getComponent();
			if(theBtnName == theStartButton){
				theStartButton.setIcon(startbtn_mouseout_icon);
			}
			if(theBtnName == RuleBtn){
				RuleBtn.setIcon(rulebtn_mouseout_icon);
			}
		}
		
		public void mousePressed(MouseEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
			//
		}
		
		public void mouseReleased(MouseEvent e) {//マウスで押していたオブジェクトを離したときの処理
			//System.out.println("マウスを放した");
		}
		
		public void mouseDragged(MouseEvent e) {//マウスでオブジェクトとをドラッグしているときの処理
			//
		}

		public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
			//System.out.println("マウス移動");
		}
		
		@Override //＜KeyListerner関係＞
		public void keyTyped(KeyEvent e) { //　文字入力のときに使う部分だけど，多分ゲームのときには利用しない
			//使用しないので空にしておきます。
		}
			
		@Override //＜KeyListerner関係＞
		public void keyPressed(KeyEvent e) { //Keyを押したとき
		Point theMyBarButtonLocation = theMyBarButton.getLocation();//自分のバーのポジション
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_RIGHT://右キー
				if(theMyBarButtonLocation.x + 10 < (stagewidth - Barwidth)  + stageposition_x){
					theMyBarButtonLocation.x += 10;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}else{
					theMyBarButtonLocation.x = (stagewidth - Barwidth) + stageposition_x;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}
				SendMyBarLocation(theMyBarButtonLocation.x);
				break;
			case KeyEvent.VK_LEFT://左キー
				if(theMyBarButtonLocation.x - 10 > stageposition_x){
					theMyBarButtonLocation.x -= 10;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}else{
					theMyBarButtonLocation.x = stageposition_x;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}
				SendMyBarLocation(theMyBarButtonLocation.x);
				break;
			case KeyEvent.VK_SPACE://スペースキー bollのスタート
				if((playernum == 1)&&(playerbollcount[0] == 0)){
					StartBoll(playernum);
					playerbollcount[0] = 1;
					break;
				}else if((playernum == 2)&&(playerbollcount[1] == 0)){
					StartBoll(playernum);
					playerbollcount[1] = 1;
					break;
				}else{
					//System.out.println("playerbollcount[0] = " + playerbollcount[0]);
					//System.out.println("playerbollcount[1] = " + playerbollcount[1]);
				}
		}
	}
	
		@Override //＜KeyListerner関係＞
		public void keyReleased(KeyEvent e) { //Keyを離したとき
			
		}
		
		//サーバー接続を行う
		public void ConectServer(){
			Socket socket = null;
			try {
				//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
				//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
				socket = new Socket(ip, 10000);
			} catch (UnknownHostException e) {
				System.err.println("ホストの IP アドレスが判定できません: " + e);
				waitmsgLabel.setIcon(waitmsg_noconect_icon);//「接続に失敗しました」
				c.repaint();
				c.paintImmediately(c.getBounds());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException er) {
					er.printStackTrace();
				}
				StartWindow();
				return;
			} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
				waitmsgLabel.setIcon(waitmsg_noconect_icon);//「接続に失敗しました」
				c.repaint();
				c.paintImmediately(c.getBounds());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException er) {
					er.printStackTrace();
				}
				return;
			}
			
			MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
			mrt.start();//スレッドを動かす（Runが動く）
			RepaintThread repain = new RepaintThread();//再描写ようのスレッドを作成する
			repain.start();//スレッドを動かす(repainが動く)
			
			//相手の接続を待つ 15秒
			for(int i = 0; i < 15; i++){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SendRedyStartinfo(playernum, myName);
				if((startcount[0] == 1)&&(startcount[1] == 1)){
					waitguruguruLabel.setVisible(false);
					waitmsgLabel.setIcon(waitmsg_match_icon);
					c.repaint();
					c.paintImmediately(c.getBounds());
					repain.stoprepaint();//repainのスレッドを止める
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if((myName != "")&&(yourName != "")){
						SendLetStart(myName, yourName);
					}
					return;
				}
			}
			
			//接続できなかった場合
			waitguruguruLabel.setVisible(false);
			waitmsgLabel.setIcon(waitmsg_nomatch_icon);
			c.repaint();
			c.paintImmediately(c.getBounds());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//サーバーとの接続を切断する
			SendBye();
			try{
				socket.shutdownOutput();
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
					socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			repain.stoprepaint();//repainのスレッドを止める
			startcount[playernum-1] = 0;
			//待機画面を非表示
			waitflameLabel.setVisible(false);
			waitmsgLabel.setVisible(false);
			//スタート画面を表示
			theStartButton.setVisible(true);
			RuleBtn.setVisible(true);
			BGMBtn.setVisible(true);
			SEBtn.setVisible(true);
			IPLabel.setVisible(true);
			NAMELabel.setVisible(true);
			theTextField_IP.setVisible(true);
			theTextField_Name.setVisible(true);
		}
	
		//barに当たったかどうかを判断する
		public boolean judgehitbar(int x,int y,int dx,int dy){
			int nextX = x + dx;
			int nextY = y + dy;
			Point theMyBarButtonLocation = theMyBarButton.getLocation();
			Point theYourBarButtonLocation = theYourBarButton.getLocation();
			int MyBarX = theMyBarButtonLocation.x;
			int MyBarY = theMyBarButtonLocation.y;
			int YourBarX = theYourBarButtonLocation.x;
			int YourBarY = theYourBarButtonLocation.y;
			//mybarが跳ね返す
			if((mybarhitnum == 0)&&((MyBarX < nextX)&&(nextX < MyBarX + 120))&&((MyBarY - 20 < nextY)&&(nextY < MyBarY + 10))){
				mybarhitnum = 1;
				yourberhitnum = 0;
				return true;
			}
			//YourBarが跳ね返す
			if((yourberhitnum == 0)&&(YourBarX < nextX)&&(nextX < YourBarX + 120)&&(YourBarY < nextY)&&(nextY < YourBarY + 20)){
				mybarhitnum = 0;
				yourberhitnum = 1;
				return true;
			}
			return false;
		}
		
		//MyBarのx座標を送信
		public void SendMyBarLocation(int MyBarX){
			String msg = "BarMOVE"+" "+ playernum +" " + MyBarX;
			out.println(msg);
			out.flush();
		}
		
		//bollのスタート設定
		public void StartBoll(int playernum){
			int dx,dy, randamnum;
			Point theMyBarButtonLocation = theMyBarButton.getLocation();
			Point theYourBarButtonLocation = theYourBarButton.getLocation();
			int MyBarX = theMyBarButtonLocation.x;
			int YourBarX = theYourBarButtonLocation.x;
			Random rand = new Random();
			randamnum = rand.nextInt(6);//ランダムな0~6までの整数を生成
			dx = vector[0][randamnum];
			dy = vector[1][randamnum];
			if(playernum == 1){
				SendBollPosition(playernum, MyBarX + (Barwidth/2) - 10, MyBarPositionY - 30 + stageposition_y ,dx ,dy);
			}else if(playernum == 2){
				dy = (-1) * dy;//←pleyer2画面ようの変更に対する処理↓
				SendBollPosition(playernum, stagewidth - 20 - (MyBarX + (Barwidth/2) - 10) + 2 * stageposition_x, YourBarPositionY + 30 - 20 + stageposition_y,dx,dy);
			}
		}
		
		public void SendBollPosition(int playernum,int x,int y,int dx,int dy){
			String msg = "BollPosition"+" " + playernum + " " + x + " " + y + " " + dx + " " + dy;
			out.println(msg);
			out.flush();
		}
		
		//public void SendHitBarPosition(int bollplayernum, int bollnum,int x,int y,int dx,int dy){
		public void SendHitBarPosition(int bollplayernum,int x,int y,int dx,int dy){
			Point theMyBarButtonLocation = theMyBarButton.getLocation();
			Point theYourBarButtonLocation = theYourBarButton.getLocation();
			int MyBarX = theMyBarButtonLocation.x;
			int YourBarX = theYourBarButtonLocation.x;
			int Barcenter, bolldistance;
			int barjudgebtnY, barjudgebtnX;
			if(y <= 300 + stageposition_y){//YourBarの跳ね返り
				Barcenter = YourBarX + (Barwidth / 2);
				bolldistance = Math.abs(x - Barcenter);
				barjudgebtnY = -1;
			}else{//MyBarの跳ね返り
				Barcenter = MyBarX + (Barwidth / 2);
				bolldistance = Math.abs(x - Barcenter);
				barjudgebtnY = 1;
			}
			if((x - Barcenter) < 0){
				barjudgebtnX = -1;
			}else{
				barjudgebtnX = 1;
			}
			
			//Barの当たった場所によってベクトルを変更する
			if(bolldistance <= (Barwidth / 6)){//中心から0~1/3
				//System.out.println("1 : " + dx + " , " + dy);
				dx = barjudgebtnX * 1;//±１
				dy = barjudgebtnY * 3;//±３
			}else if(bolldistance <= (Barwidth / 4)){//中心から1/3~2/3
				//System.out.println("2 : " + dx + " , " + dy);
				dx = barjudgebtnX * 2;//±2
				dy = barjudgebtnY * 2;//±2
			}else{//中心から2/3~
				//System.out.println("3 : " + dx + " , " + dy);
				dx = barjudgebtnX * 3;//±3
				dy = barjudgebtnY * 1;//±1
			}
			//String msg = "HitBarPosition"+" " + bollplayernum + " "+ bollnum + " " + x + " " + y + " " + dx + " " + dy;
			String msg = "HitBarPosition"+" " + bollplayernum + " " + x + " " + y + " " + dx + " " + dy;
			out.println(msg);
			out.flush();
		}
		
		public void SendHitSidePosition(int bollplayernum,int x,int y,int dx,int dy){
			String msg = "HitSidePosition"+" " + bollplayernum + " " + x + " " + y + " " + dx + " " + dy;
			out.println(msg);
			out.flush();
		}
		
		//ブロックに当たったときに点数をカウントする関数
		public void countpoint(int bollplayernum){
			int mypoint, yourpoint;
			if(bollplayernum == 1){
				player1_point++;
			}else if(bollplayernum == 2){
				player2_point++;
			}
			if(playernum == 1){
				mypoint = player1_point;
				yourpoint = player2_point;
			}else{
				mypoint = player2_point;
				yourpoint = player1_point;
			}
			mypointLabel.setText(""+mypoint);
			yourpointLabel.setText(""+yourpoint);
			if(mypoint + yourpoint == 48){//終了判定
				if(mypoint > yourpoint){
					Result(playernum);
				}else{
					if(playernum == 1){
						Result(2);
					}else{
						Result(1);
					}
				}
			}
		}
		
		public String BlockHitjudge(int bollplayernum, int x, int y, int dx, int dy){
			int blockX,blockY;
			int nextX = x + dx;
			int nextY = y + dy;
			//ボールの左上の座標
			int nextX_up_left = nextX;
			int nextY_up_left = nextY;
			//ボールの右上の座標
			int nextX_up_right = nextX + 20;
			int nextY_up_right = nextY;
			//ボールの左下の座標
			int nextX_down_left = nextX;
			int nextY_down_left = nextY + 20;
			//ボールの右下の座標
			int nextX_down_right = nextX + 20;
			int nextY_down_right = nextY + 20;
			
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 8; j++){
					if(block[8 * i + j] == 1){//そのブロックが存在している場合
						blockX = 62 * j + 52  + stageposition_x;
						blockY = 42 * i + 275 + stageposition_y;
						if(((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight))&&((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight))){//下向き反射↓
							return (8 * i + j) + " " + "DOWN";
						}else	if(((blockX < nextX_down_left)&&(nextX_down_left < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight))&&((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight))){//上向き反射↑
							return (8 * i + j) + " " + "UP";
						}else if(((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight))&&((blockX < nextX_down_left)&&(nextX_down_left) < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight)){//右向き反射→
							return (8 * i + j) + " " + "RIGHT";
						}else	if(((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight))&&((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight))){//左向き反射←
							return (8 * i + j) + " " + "LEFT";
						}else	if((blockX < nextX_down_left)&&(nextX_down_left < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight)){//右上向き反射?
							return (8 * i + j) + " " + "UP_RIGHT";
						}else	if((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight)){//左上向き反射?
							return (8 * i + j) + " " + "UP_LEFT";
						}else	if((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight)){//右下向き反射?
							return (8 * i + j) + " " + "DOWN_RIGHT";
						}else	if((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight)){//左下向き反射?
							return (8 * i + j) + " " + "DOWN_LEFT";
						}
					}
				}
			}
			return -1 + " "; 
		}
		
		public void SendHitBlockPosition(int HitBlocknum, int bollplayernum,int x, int y, int dx, int dy){
			String msg = "HitBlock"+" " + HitBlocknum + " " + bollplayernum +" " + x + " " + y + " " + dx + " " + dy;
			out.println(msg);
			out.flush();
		}
		
		//ライフの計算
		public void Lifecount(int deadbollplayernum){
			int mylifepoint, yourlifepoint;
			ImageIcon mynolife,yournolife;
			/*if(boll_y >= stageheight + stageposition_y - 30){//自分側でボールが場外に出た
				if(playernum == 1){
					player1_lifepoint--;
				}else{
					player2_lifepoint--;
				}
			}else{//相手側でボールが場外に出た場合
				if(playernum == 1){
					player2_lifepoint--;
				}else{
					player1_lifepoint--;
				}
			}
			if(playernum == 1){
				mylifepoint = player1_lifepoint;
				yourlifepoint = player2_lifepoint;
				mynolife = player1_nolife;
				yournolife = player2_nolife;
			}else{
				mylifepoint = player2_lifepoint;
				yourlifepoint = player1_lifepoint;
				mynolife = player2_nolife;
				yournolife = player1_nolife;
			}
			*/
			if(playernum == deadbollplayernum){//自分のボールが場外
				if(playernum == 1){
					player1_lifepoint--;
				}else{
					player2_lifepoint--;
				}
			}else{//相手のボールが場外
				if(playernum == 1){
					player2_lifepoint--;
				}else{
					player1_lifepoint--;
				}
			}
			if(playernum == 1){
				mylifepoint = player1_lifepoint;
				yourlifepoint = player2_lifepoint;
				mynolife = player1_nolife;
				yournolife = player2_nolife;
			}else{
				mylifepoint = player2_lifepoint;
				yourlifepoint = player1_lifepoint;
				mynolife = player2_nolife;
				yournolife = player1_nolife;
			}
			
			switch(mylifepoint){
				case 2:
					mylifeLabel_3.setIcon(mynolife);
					break;
				case 1 :
					mylifeLabel_2.setIcon(mynolife);
					break;
				case 0 :
					mylifeLabel_1.setIcon(mynolife);
					break;
			}
			switch(yourlifepoint){
				case 2:
					yourlifeLabel_3.setIcon(yournolife);
					break;
				case 1 :
					yourlifeLabel_2.setIcon(yournolife);
					break;
				case 0 :
					yourlifeLabel_1.setIcon(yournolife);
					break;
			}
			if((player1_lifepoint == 0)||(player2_lifepoint == 0)){//どちらかのライフポイントが0になった場合
				//終了判定
				if(player1_lifepoint == 0){
					Result(2);
				}else{
					Result(1);
				}
			}
			return;
		}
		
		public void SendRedyStartinfo(int playernum, String myName){
			String msg = "RedyStart"+" " + playernum + " " + myName;
			out.println(msg);
			out.flush();
			return;
		}
		
		public void SendBye(){
			String msg = "BYE";
			out.println(msg);
			out.flush();
			return;
		}
		
		public void SendLetStart(String myName, String yourName){
			String player1_Name;
			String player2_Name;
			if(playernum == 1){
				player1_Name = myName;
				player2_Name = yourName;
			}else{
				player1_Name = yourName;
				player2_Name = myName;
			}
			String msg = "LetStart" + " " + player1_Name + " " + player2_Name;
			out.println(msg);
			out.flush();
			return;
		}
		
		public void Result(int winnunber){//結果発表
			if(playernum == winnunber){
				winLabel.setVisible(true);
			}else{
				loseLabel.setVisible(true);
			}
			EndBtn.setVisible(true);
			return;
		}
		
		public void SendBollDead(int bollplayernum){
			String msg = "BollDead" + " " + bollplayernum;
			out.println(msg);
			out.flush();
			return;
		}
		
		public void Reset(){
			player1_lifepoint = 3;
			player2_lifepoint = 3;
			
			for(int i = 0; i < 48; i++){
				block[i] = 1;
			}
			
			theMyBarButton.setLocation(240 + stageposition_x,770 + stageposition_y);
			theYourBarButton.setLocation(240 + stageposition_x,20 + stageposition_y);
			player1_point = 0;
			player2_point = 0;
			startcount[0] = 0;
			startcount[1] = 0;
			playerbollcount[0] = 0;
			playerbollcount[1] = 0;
			
			mypointLabel.setText(""+ 0);
			yourpointLabel.setText(""+ 0);
			
			BollThred[0] = 0;
			BollThred[1] = 0;
		}
		
}