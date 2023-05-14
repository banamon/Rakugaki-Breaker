import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;//�摜�����ɕK�v
import java.awt.geom.*;//�摜�����ɕK�v
import java.awt.Graphics;
import java.applet.Applet;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import java.io.File;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioFormat;//���y�Đ����ɕK�v
import javax.sound.sampled.AudioSystem;//���y�Đ����ɕK�v
import javax.sound.sampled.Clip;//���y�Đ����ɕK�v
import javax.sound.sampled.DataLine;//���y�Đ����ɕK�v

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
	private ImageIcon startbtn_mouseout_icon,startbtn_mousein_icon;//�X�^�[�g�{�^���p
	private ImageIcon rulebtn_mouseout_icon,rulebtn_mousein_icon;//���[���{�^���p
	private Image boll2;
	private JLabel mybolllabel, yourbolllabel;
	private int mybollnumber = 0, yourbollnumber = 0;
	private int [] startcount = { 0, 0};//�X�^�[�g�������Ǘ�
	private int[] playerbollcount = {0,0};
	private int block[] = new int[48];
	private int blockwidth = 60, blockheight = 40;//�u���b�N�̐ݒ�
	private JLabel stageLabel;
	private final int stageposition_x = 100, stageposition_y = 100;//�X�e�[�W���ړ��ɑΉ����邽�߂̕ϐ��@�f�U�C�����ɒ���
	private final int stagewidth = 600,stageheight = 800;
	private int BollStartPositionx = 290,BollStartPositiony = 390;//Boll�̏����l
	private int playernum;//1or2
	private int player1_point = 0,player2_point = 0;//���_
	private int player1_lifepoint = 3, player2_lifepoint = 3;
	private final int MyBarPositionY = 770,YourBarPositionY = 20;//Bar�̐ݒ�
	private int Barwidth = 120;//Bar�̃��R�@�f�t�H���g
	private int mybarhitnum = 0, yourberhitnum = 0;//�A�������蔻��̖h�~
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
		{-3, -2, -1,  1,  2,  3},//x���W
		{-1, -2, -3, -3, -2, -1},//�����W
	};
	private int[] BollThred = {0,0};//�X���b�h�̊Ǘ��@�d���h�~�@�X���b�h�������Ă�����P�A�����Ă��Ȃ�������O
	SoundPlayer SE_Btn, SE_Boll, BGM_Start;//�ǂ�����ł��A�N�Z�X�ł���悤�ɁC�N���X�̃����o�Ƃ��Đ錾
	PrintWriter out;//�o�͗p�̃��C�^�[
	Graphics g;
	JPanel c = new JPanel();//Container�ł������`��@�\���g���Ȃ��̂�JPanel���g���D�g�����͂قƂ��Container�Ɠ���


	public MyClient2ver4() {
		//�E�B���h�E���쐬����
		this.add(c,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("MyClient2ver4");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(1220,1000);//�E�B���h�E�̃T�C�Y��ݒ肷��
		//c = getContentPane();//�t���[���̃y�C�����擾����
		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�
		
		
		//�������---------------------------------------------------------------------------------------------
		//title
		titleicon = new ImageIcon("title2.png");
		titleLabel = new JLabel(titleicon);
		c.add(titleLabel);
		titleLabel.setBounds(50,40,900,296);
		titleLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//startbtn�̍쐬
		startbtn_mouseout_icon = new ImageIcon("startbtn_mouseout.png");
		startbtn_mousein_icon = new ImageIcon("startbtn_mousein.png");
		theStartButton = new JButton(startbtn_mouseout_icon);//�{�^�����쐬�C�e�L�X�g�̕\��
		c.add(theStartButton);//�y�C���ɓ\��t����
		theStartButton.setBounds(200,700,800,100);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		theStartButton.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		theStartButton.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		theStartButton.addMouseListener(this);
		theStartButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//���[���{�^��
		rulebtn_mouseout_icon = new ImageIcon("rule_mouseout.png");
		rulebtn_mousein_icon = new ImageIcon("rule_mousein.png");
		RuleBtn = new JButton(rulebtn_mouseout_icon);//�{�^�����쐬�C�e�L�X�g�̕\��
		c.add(RuleBtn);//�y�C���ɓ\��t����
		RuleBtn.setBounds(200,550,400,100);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		RuleBtn.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		RuleBtn.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		RuleBtn.addMouseListener(this);
		RuleBtn.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//���[����ʂ���߂�{�^��
		Rule_return_icon = new ImageIcon("Rule_return.png");
		Rule_return_Btn = new JButton(Rule_return_icon);
		c.add(Rule_return_Btn);
		Rule_return_Btn.setBounds(930,120,150,80);
		Rule_return_Btn.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		Rule_return_Btn.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		Rule_return_Btn.addMouseListener(this);
		Rule_return_Btn.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		Rule_return_Btn.setVisible(false);
		
		
		//���[����� ���[���{�^������������\��
		Ruleicon = new ImageIcon("Rule_2.png");
		RuleLabel = new JLabel(Ruleicon);
		c.add(RuleLabel);
		RuleLabel.setBounds(100,80,1000,828);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		RuleLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		RuleLabel.setVisible(false);
		
		
		//BGM
		BGM_ONicon = new ImageIcon("BGM_ONbtn.png");
		BGM_OFFicon = new ImageIcon("BGM_OFFbtn.png");
		
		BGMBtn = new JButton(BGM_ONicon);
		c.add(BGMBtn);//�y�C���ɓ\��t����
		BGMBtn.setBounds(650,550,150,100);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		BGMBtn.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		BGMBtn.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		BGMBtn.addMouseListener(this);
		BGMBtn.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//SE
		SE_ONicon = new ImageIcon("SE_ONbtn.png");
		SE_OFFicon = new ImageIcon("SE_OFFbtn.png");
		
		SEBtn = new JButton(SE_ONicon);
		c.add(SEBtn);//�y�C���ɓ\��t����
		SEBtn.setBounds(850,550,150,100);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		SEBtn.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		SEBtn.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		SEBtn.addMouseListener(this);
		SEBtn.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//ip�A�h���X
		IPicon = new ImageIcon("IP.png");
		IPLabel = new JLabel(IPicon);
		c.add(IPLabel);
		IPLabel.setBounds(200,400,150,50);
		theTextField_IP = new JTextField("localhost");
		c.add(theTextField_IP);
		theTextField_IP.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		theTextField_IP.setBounds(350,400,250,50);
		
		//���O
		NAMEicon = new ImageIcon("NAME.png");
		NAMELabel = new JLabel(NAMEicon);
		c.add(NAMELabel);
		NAMELabel.setBounds(200,470,150,50);
		theTextField_Name = new JTextField("");
		c.add(theTextField_Name);
		theTextField_Name.setBounds(350,470,250,50);
		theTextField_Name.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		
		
		//�ҋ@���---------------------------------------------------------------------------------------------
		//�ҋ@��ʃt���[��
		waitflameicon = new ImageIcon("waitflame.png");
		waitflameLabel = new JLabel(waitflameicon);
		c.add(waitflameLabel);
		waitflameLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		waitflameLabel.setBounds(200,400,800,400);
		waitflameLabel.setVisible(false);
		
		//�O���O���Ȃ�A�C�R��
		waitguruguruicon = new ImageIcon("wait_guruguru.gif");
		waitguruguruLabel = new JLabel(waitguruguruicon);
		c.add(waitguruguruLabel);
		waitguruguruLabel.setBounds(520,450,160,160);
		waitguruguruLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		waitguruguruLabel.setVisible(false);
		
		//�ҋ@��ʃ��b�Z�[�W
		waitmsg_search_icon = new ImageIcon("waitmsg_search.png");//�ΐ푊���T���Ă��܂�
		waitmsg_nomatch_icon = new ImageIcon("waitmsg_nomatch.png");//�݂���܂���ł���
		waitmsg_match_icon = new ImageIcon("waitmsg_match.png");//�݂���܂���
		waitmsg_noconect_icon = new ImageIcon("waitmsg_noconect.png");//�ڑ��Ɏ��s���܂���
		waitmsgLabel = new JLabel();
		c.add(waitmsgLabel);
		waitmsgLabel.setBounds(265,650,670,106);
		waitmsgLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		waitmsgLabel.setVisible(false);
		
		
		
		//�ΐ���---------------------------------------------------------------------------------------------
		//��߂�{�^��
		Endicon = new ImageIcon("End.png");
		EndBtn = new JButton(Endicon);
		c.add(EndBtn);//�y�C���ɓ\��t����
		EndBtn.setBounds(260,700,700,163);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		EndBtn.setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
		EndBtn.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		EndBtn.addMouseListener(this);
		EndBtn.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		EndBtn.setVisible(false);
		
		//���ʕ\���̐ݒ�
		winicon = new ImageIcon("win.png");
		loseicon = new ImageIcon("lose.png");
		winLabel = new JLabel(winicon);
		c.add(winLabel);
		winLabel.setBounds(210,150,800,771);
		winLabel.setVisible(false);
		winLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		loseLabel = new JLabel(loseicon);
		c.add(loseLabel);
		loseLabel.setBounds(210,150,800,771);
		loseLabel.setVisible(false);
		loseLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//�u���b�N�̐ݒ�
		BlockArray = new JButton[6][8];//�^�e6�A���R8�̃u���b�N
		blockicon = new ImageIcon("block_gray.png");
		for(int i = 0; i < 6; i++){
			for(int j = 0;j < 8; j++){
				BlockArray[i][j] = new JButton(blockicon);//�{�^���ɃA�C�R����ݒ肷��
				BlockArray[i][j].setContentAreaFilled(false);//btn�̔w�i���Ȃ����Ă���
				BlockArray[i][j].setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
				c.add(BlockArray[i][j]);//�y�C���ɓ\��t����
				BlockArray[i][j].setBounds(j*(blockwidth + 2) + 52 + stageposition_x, i*(blockheight + 2) + 275 + stageposition_y,blockwidth,blockheight);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
				BlockArray[i][j].setActionCommand(Integer.toString(6*i + j));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
				block[8*i + j] = 1;
				BlockArray[i][j].addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
				BlockArray[i][j].setVisible(false);
			}
		}
		
		
		//boll�̐ݒ�
		player1_bollicon = new ImageIcon("player1_boll.png");
		player2_bollicon = new ImageIcon("player2_boll.png");
		mybolllabel = new JLabel();
		yourbolllabel = new JLabel();
		c.add(mybolllabel);
		c.add(yourbolllabel);
		mybolllabel.setBounds(BollStartPositionx + stageposition_x,BollStartPositiony + stageposition_y,20,20);
		yourbolllabel.setBounds(BollStartPositionx + stageposition_x,BollStartPositiony + stageposition_y,20,20);
		mybolllabel.setVisible(false);
		mybolllabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		yourbolllabel.setVisible(false);
		yourbolllabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//ber��ݒ�
		player1_baricon = new ImageIcon("player1_bar.png");
		player2_baricon = new ImageIcon("player2_bar.png");
		theMyBarButton = new JButton();
		theMyBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		theYourBarButton = new JButton();
		theYourBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		c.add(theMyBarButton);
		theMyBarButton.setBounds(240 + stageposition_x,770 + stageposition_y,Barwidth,10);
		theMyBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		theMyBarButton.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		c.add(theYourBarButton);
		theYourBarButton.setBounds(240 + stageposition_x,20 + stageposition_y,Barwidth,10);
		theYourBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		theYourBarButton.setBorderPainted(false);//btn�̘g�����Ȃ����Ă���
		
		theMyBarButton.setVisible(false);
		theYourBarButton.setVisible(false);
		
		//�X�e�[�W�w�i�F�t�����x���̍쐬
		stageicon = new ImageIcon("stageflame.png");
		stageLabel = new JLabel(stageicon);
		c.add(stageLabel);
		stageLabel.setBounds(stageposition_x, stageposition_y, stagewidth, stageheight);
		stageLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		stageLabel.setVisible(false);
		
		
		//���O�\��
		myNameLabel = new JLabel();
		c.add(myNameLabel);
		myNameLabel.setBounds(650 + stageposition_x , stageposition_y,200,50);
		myNameLabel.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 30));
		myNameLabel.setForeground(Color.BLACK);
		myNameLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		myNameLabel.setVisible(false);
		
		yourNameLabel = new JLabel();
		c.add(yourNameLabel);
		yourNameLabel.setBounds(650  + stageposition_x,400 + stageposition_y,200,50);
		yourNameLabel.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 30));
		yourNameLabel.setForeground(Color.BLACK);
		yourNameLabel.setVisible(false);
		yourNameLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		
		//���_�\��
		mypointLabel = new JLabel("0");
		c.add(mypointLabel);
		mypointLabel.setBounds(800  + stageposition_x,50 + stageposition_y,150,150);
		mypointLabel.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 100));
		mypointLabel.setForeground(Color.BLACK);
		mypointLabel.setBackground(Color.pink); //�����̔w�i�F�̐ݒ�D
		mypointLabel.setVerticalAlignment(JLabel.BOTTOM);
		mypointLabel.setVisible(false);
		mypointLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		yourpointLabel = new JLabel("0");
		c.add(yourpointLabel);
		yourpointLabel.setBounds(800  + stageposition_x,450 + stageposition_y,150,150);
		yourpointLabel.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 100));
		yourpointLabel.setForeground(Color.BLACK);
		yourpointLabel.setVerticalAlignment(JLabel.BOTTOM);
		yourpointLabel.setVisible(false);
		yourpointLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//Pt�����\��
		PTLabel_1 = new JLabel("Pt");
		PTLabel_2 = new JLabel("Pt");
		c.add(PTLabel_1);
		PTLabel_1.setBounds(950  + stageposition_x,150 + stageposition_y,50,50);
		PTLabel_1.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 40));
		PTLabel_1.setForeground(Color.BLACK);
		PTLabel_1.setVerticalAlignment(JLabel.BOTTOM);
		PTLabel_1.setVisible(false);
		PTLabel_1.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		c.add(PTLabel_2);
		PTLabel_2.setBounds(950  + stageposition_x,550 + stageposition_y,50,50);
		PTLabel_2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 40));
		PTLabel_2.setForeground(Color.BLACK);
		PTLabel_2.setVerticalAlignment(JLabel.BOTTOM);
		PTLabel_2.setVisible(false);
		PTLabel_2.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//���C�t�̊Ǘ�
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
		mylifeLabel_1.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		mylifeLabel_2.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		mylifeLabel_3.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		yourlifeLabel_1.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		yourlifeLabel_2.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		yourlifeLabel_3.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		
		//�w�i
		campasicon = new ImageIcon("campas_2.png");
		JLabel campasLabel = new JLabel(campasicon);
		c.add(campasLabel);
		campasLabel.setBounds(10, 0, 1200, 1000);
		campasLabel.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
		//�L�[���̗͂L����
		addKeyListener(this);//Form�ŃL�[���͂��󂯂�悤�ɂ��遃KeyListerner�֌W��
		requestFocus();//Form�Ƀt�H�[�J�X���ړ����遃KeyListerner�֌W��
		
		BGM_Start();
	}
	
	//���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {
			Socket socket;
			String myName;
			public MesgRecvThread(Socket s, String n){
				socket = s;
				myName = n;
			}
			//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
			public void run() {
				try{
					InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
					BufferedReader br = new BufferedReader(sisr);
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
					String myNumberStr = br.readLine();
					myNumberInt = Integer.parseInt(myNumberStr);
					//playernum�̌���
					if(myNumberInt % 2 == 0){//player��1��2�ŋ��
						playernum = 1;
						//System.out.println("���Ȃ���player1�ł�");
					} else {
						playernum = 2;
						//System.out.println("���Ȃ���player2�ł�");
					}
					
					while(true) {
						String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
						if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
							System.out.println(inputLine);//�f�o�b�O�i����m�F�p�j�ɃR���\�[���ɏo�͂���
							String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
							String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��
							//�X�^�[�g����
							if(cmd.equals("RedyStart")){
								int theplayernum = Integer.parseInt(inputTokens[1]);
								String TheName = inputTokens[2];
								if(playernum != theplayernum){
									yourName = TheName;
								}
								//start�������ł�����P
								startcount[theplayernum - 1] = 1;
							}
							//���ۂɃX�^�[�g
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
								//System.out.println("LetStart����M������");
								startcount[0] = 1;
								startcount[1] = 1;
								PlayWindow();
							}
							//Bar�𓮂���
							if(cmd.equals("BarMOVE")){//cmd�̕�����"BarMOVE"�����������ׂ�D��������true�ƂȂ�
								int theplayernum = Integer.parseInt(inputTokens[1]);
								int BarLocationX = Integer.parseInt(inputTokens[2]);
								if(playernum == theplayernum){
									theMyBarButton.setLocation(BarLocationX,MyBarPositionY + stageposition_y);
								}else{
									theYourBarButton.setLocation(stagewidth + (2 * stageposition_x) - BarLocationX - Barwidth,YourBarPositionY + stageposition_y);
								}
							}
							if(cmd.equals("BollPosition")){//cmd�̕�����"StartBoll"�����������ׂ�D��������true�ƂȂ�
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2�p�̍��W�ƃx�N�g���ݒ�
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								BollThred[bollplayernum - 1] += 1;//�X���b�h����������1�ɂ���
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
							}
							if(cmd.equals("HitBarPosition")){//Bar�ɑ΂��钵�˕Ԃ菈��
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2�p�̍��W�ƃx�N�g���ݒ�
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								dy = (-1)*dy;//�����𔽓]������
								x = x + dx;
								y = y + dy;
								BollThred[bollplayernum - 1] += 1;//�X���b�h����������1�ɂ���
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("HitSidePosition")){//���R�ɑ΂��钵�˕Ԃ菈��
								int bollplayernum = Integer.parseInt(inputTokens[1]);
								int x = Integer.parseInt(inputTokens[2]);
								int y = Integer.parseInt(inputTokens[3]);
								int dx = Integer.parseInt(inputTokens[4]);
								int dy = Integer.parseInt(inputTokens[5]);
								if(playernum == 2){//player2�p�̍��W�ƃx�N�g���ݒ�
									x = stagewidth - 20 - x + 2 * stageposition_x;
									y = stageheight - 20 - y + 2 * stageposition_y;
									dx = - dx;
									dy = - dy;
								}
								dx = (-1) * dx;//dx�𔽓]������
								x = x + dx;
								y = y + dy;
								BollThred[bollplayernum - 1] += 1;//�X���b�h����������1�ɂ���
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("HitBlock")){//�u���b�N�ɓ��������Ƃ��̏���
								int HitBlocknum = Integer.parseInt(inputTokens[1]);//���������u���b�N�̔ԍ�
								int bollplayernum = Integer.parseInt(inputTokens[2]);
								int x = Integer.parseInt(inputTokens[3]);
								int y = Integer.parseInt(inputTokens[4]);
								int dx = Integer.parseInt(inputTokens[5]);
								int dy = Integer.parseInt(inputTokens[6]);
								int i = HitBlocknum / 8; //y�̂��
								int j = HitBlocknum % 8; //x�̂��
								if(playernum == 2){//player2�p�̍��W�ƃx�N�g���ݒ�
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
								BollThred[bollplayernum - 1] += 1;//�X���b�h����������1�ɂ���
								BallMoveThread boll = new BallMoveThread(bollplayernum,x,y,dx,dy);
								boll.start();
								SE_Boll();
							}
							if(cmd.equals("BollDead")){//�{�[������O�̎��̏���
								int deadbollplayernum = Integer.parseInt(inputTokens[1]);//��O�̃{�[���̔ԍ�
								//System.out.println("BollDead��M�@" + deadbollplayernum);
								//System.out.println("playernum�@" + playernum);
								if(playernum == deadbollplayernum){
									//System.out.println("�}�C�{�[���������܂�");
									mybolllabel.setVisible(false);
								}else{
									//System.out.println("�䂠�ځ[�邪�����܂�");
									yourbolllabel.setVisible(false);
								}
								c.repaint();
								c.paintImmediately(c.getBounds());
								playerbollcount[deadbollplayernum - 1] = 0; //�{�[��������ɂ��邩�ǂ�����
								BollThred[deadbollplayernum - 1] -= 1;//�X���b�h���~�܂邽�߂O�ɂ���
								Lifecount(deadbollplayernum);
							}
						}else{
							break;
						}
					}
					socket.close();
				} catch (IOException e) {
					System.err.println("�G���[���������܂���: " + e);
				}
			}
		}
		
	//repaint������X���b�h
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
					if(!repaintflag){//�ڑ����ł�����I��
						break;
					}
				}
			}
	}
		
	//�{�[���𓮂����X���b�h ��������
	public class BallMoveThread extends Thread {
		int bollplayernum;//1:player1, 2:player2
		int x,y,dx,dy;
		int HitBlocknum;//���������u���b�N�̔ԍ�
		int blockjudge;//�u���b�N�̒��˕Ԃ蔻��@x�����̔��ˁF�P�Ay�����̔��ˁF�O
		
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
				
				if(bollplayernum == playernum){//mybolllabel�̏���
					mybolllabel.setVisible(true);
					mybolllabel.setLocation(x, y);
				}else{//yourbolllabel�̏���
					yourbolllabel.setVisible(true);
					yourbolllabel.setLocation(x, y);
				}
				
				if((x + dx < stageposition_x)||(stagewidth + stageposition_x < (x + 20) + dx)){//���R�̒��˕Ԃ�
					mybarhitnum = 0;
					yourberhitnum = 0;
					if(playernum == 1){
						SendHitSidePosition(bollplayernum,x,y,dx,dy);//���˕Ԃ肪����Ɠ���
					}
					//System.out.println("���R�Ƃ̒��˕Ԃ�ɂ��break" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//�X���b�h���~�܂邽�߂O�ɂ���
					break;
				}
				if(judgehitbar(x,y,dx,dy)){//Bar�Ƃ̒��˕Ԃ�
					if(playernum == 1){
						SendHitBarPosition(bollplayernum,x,y,dx,dy);//���˕Ԃ肪����Ɠ���
					}
					//System.out.println("Bar�s�̒��˕Ԃ���break" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//�X���b�h���~�܂邽�߂O�ɂ���
					break;
				}
				
				
				String[] BlockHitjudgeStr = BlockHitjudge(bollplayernum,x,y,dx,dy).split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
				HitBlocknum = Integer.parseInt(BlockHitjudgeStr[0]);//�u���b�N�Ƃ̒��˕Ԃ肪����ꍇ�A���������u���b�N�̔ԍ���Ԃ��A���˕Ԃ肪�Ȃ��ꍇ�A-1��Ԃ�
				if(HitBlocknum >= 0){//block�̒��˕Ԃ肪����ꍇ
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
					if(playernum == 1){//player1�������������Ƃ𑗐M
						SendHitBlockPosition(HitBlocknum, bollplayernum, x, y, dx, dy);
					}
					//System.out.println("Block�Ƃ̒��˕Ԃ���break" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;//�X���b�h���~�܂邽�߂O�ɂ���
					break;
				}
				
				if((y + dy < stageposition_y)||(stageheight + stageposition_y < (y + 20) + dy)){//�{�[������O�֏o���Ƃ��̏���
					BollThred[bollplayernum - 1] -= 0;//�X���b�h���~�܂邽�߂O�ɂ���
					if(playernum == 1){
						SendBollDead(bollplayernum);
					}
					break;
				}
				
				if(BollThred[bollplayernum - 1] > 1){//�����{�[���ɑ΂��ďd�����ăX���b�h�𗧂ĂĂ���ꍇ 
					//System.out.println("�X���b�h���d������" + BollThred[bollplayernum - 1]);
					BollThred[bollplayernum - 1] -= 1;
					break;
				}
				
					x += dx;
					y += dy;
			}
		}
	}
	
	
	//���L�𗘗p���Ă��܂��D�ꕔ���������Ă��܂�(��~�@�\��t��)    �D
	//http://nautilus2580.hatenablog.com/entry/2015/11/07/223457
	//mp3��wav�ɕϊ��́C���L�̃T�C�g�łł��܂�
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
					long time = (long)clip.getFrameLength();//44100�Ŋ���ƍĐ����ԁi�b�j���ł�
					long endTime = System.currentTimeMillis()+time*1000/44100;
					clip.start();
					while(true){
						if(stopFlag){//stopFlag��true�ɂȂ����I��
							//System.out.println("PlaySound stop by stopFlag");
							clip.stop();
							return;
						}
						if(endTime < System.currentTimeMillis()){//�Ȃ̒������߂�����I��
							if(loopFlag) {
								clip.loop(1);//�������[�v�ƂȂ�
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
	
	//�{�^���̌��ʉ�
	public void SE_Btn(){
		if(SEflag){
			SE_Btn = new SoundPlayer("SE_Btn.wav");
			SE_Btn.play();
		}
	}
	
	//�{�[�������˕Ԃ�Ƃ��̌��ʉ�
	public void SE_Boll(){
		if(SEflag){
			SE_Boll = new SoundPlayer("SE_Boll.wav");
			SE_Boll.play();
		}
	}
	
	//�X�^�[�g��ʂ�BGM
	public void BGM_Start(){
		if(BGMflag){
			BGM_Start = new SoundPlayer("BGM_Start.wav");
			BGM_Start.SetLoop(true);
			BGM_Start.play();
		}
	}
	
	//BGM��OFF����
	public void BGM_OFF(){
		BGM_Start.stop();
	}
	
	public static void main(String[] args) {
			MyClient2ver4 net = new MyClient2ver4();
			net.setVisible(true);
	}
	
	public void BarSet(int playernum){//Bar�̐ݒ���s���֐�
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
		theMyBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		c.add(theYourBarButton);
		theYourBarButton.setBounds(240 + stageposition_x,20 + stageposition_y,Barwidth,10);
		theYourBarButton.addKeyListener(this);//�{�^���Ƀt�H�[�J�X�������Ă�Key���͂��󂯕t����悤�ɂ��遃KeyListerner�֌W��
		
	}
	
	//�X�^�[�g��ʂ�\��
	public void StartWindow(){
		//�X�^�[�g��ʂ̏���\��
		titleLabel.setVisible(true);
		theStartButton.setVisible(true);
		RuleBtn.setVisible(true);
		BGMBtn.setVisible(true);
		SEBtn.setVisible(true);
		IPLabel.setVisible(true);
		theTextField_IP.setVisible(true);
		NAMELabel.setVisible(true);
		theTextField_Name.setVisible(true);
		
		//�ҋ@��ʂ̏����\��
		waitflameLabel.setVisible(false);
		waitmsgLabel.setVisible(false);
		waitguruguruLabel.setVisible(false);
		
		//�v���C��ʂ��\��
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
	
	
	//�ҋ@��ʂ�\������
	public void WaitWindow(){
		//�X�^�[�g��ʂ��\��
		theStartButton.setVisible(false);
		RuleBtn.setVisible(false);
		BGMBtn.setVisible(false);
		SEBtn.setVisible(false);
		NAMELabel.setVisible(false);
		IPLabel.setVisible(false);
		theTextField_IP.setVisible(false);
		theTextField_Name.setVisible(false);
		
		//�ҋ@��ʂ�\��
		waitflameLabel.setVisible(true);
		waitmsgLabel.setVisible(true);
		waitguruguruLabel.setVisible(true);
		//�ҋ@���b�Z�[�W���u�ΐ푊���T���Ă��܂��v�ɐݒ�
		waitmsgLabel.setIcon(waitmsg_search_icon);
		//c.repaint();
		c.repaint();
		c.paintImmediately(c.getBounds());
	}
	
	//�v���C��ʂ�ݒ�
	public void PlayWindow(){
		//�X�^�[�g��ʂ̏����\��
		titleLabel.setVisible(false);
		theStartButton.setVisible(false);
		RuleBtn.setVisible(false);
		BGMBtn.setVisible(false);
		SEBtn.setVisible(false);
		IPLabel.setVisible(false);
		theTextField_IP.setVisible(false);
		NAMELabel.setVisible(false);
		theTextField_Name.setVisible(false);
		
		//�ҋ@��ʂ̏����\��
		waitflameLabel.setVisible(false);
		waitmsgLabel.setVisible(false);
		waitguruguruLabel.setVisible(false);
		
		//�v���C��ʂ�\��
		for(int i = 0; i < 6; i++){
			for(int j = 0;j < 8; j++){
				BlockArray[i][j].setVisible(true);
			}
		}
		theMyBarButton.setVisible(true);
		theYourBarButton.setVisible(true);
		stageLabel.setVisible(true);
		if(playernum == 1){//player1�̏ꍇ
			theMyBarButton.setIcon(player1_baricon);
			theYourBarButton.setIcon(player2_baricon);
			mybolllabel.setIcon(player1_bollicon);
			yourbolllabel.setIcon(player2_bollicon);
		}else{//player2�̏ꍇ
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
	
	
	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
		JButton theBtnName = (JButton)e.getComponent();
			//�X�^�[�g�{�^���������ꂽ�Ƃ�
			SE_Btn();
			if(theBtnName == theStartButton){
				myName = theTextField_Name.getText();//���O�̎擾
				if(myName.equals("")){
					myName = "Noname";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
				}
				ip = theTextField_IP.getText();//ip�A�h���X�̎擾
				if(ip.equals("")){
					ip = "localhost";//���͂��Ȃ��ꍇ�͎�����PC�T�[�o�ɐڑ�
				}
				WaitWindow();
				ConectServer();
			}
			
			//���[���{�^���������ꂽ�Ƃ�
			if(theBtnName == RuleBtn){
				SE_Btn();
				//�X�^�[�g��ʂ��\���ɂ���
				titleLabel.setVisible(false);
				theStartButton.setVisible(false);
				RuleBtn.setVisible(false);
				BGMBtn.setVisible(false);
				SEBtn.setVisible(false);
				NAMELabel.setVisible(false);
				IPLabel.setVisible(false);
				theTextField_IP.setVisible(false);
				theTextField_Name.setVisible(false);
				//���[����ʂƃ��[������߂�{�^����\��
				RuleLabel.setVisible(true);
				Rule_return_Btn.setVisible(true);
				c.repaint();
				c.paintImmediately(c.getBounds());
			}
			if(theBtnName == Rule_return_Btn){//���[����ʂ���߂�{�^��
				SE_Btn();
				//���[����ʂƃ{�^�����\��
				RuleLabel.setVisible(false);
				Rule_return_Btn.setVisible(false);
				//�X�^�[�g��ʂ�\��
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
			if(theBtnName == SEBtn){//SE�{�^���������ꂽ����
				if(SEBtn.getIcon() == SE_ONicon){
					//SE��OFF�ɂ���
					SEflag = false;
					SEBtn.setIcon(SE_OFFicon);
				}else{
					//SE��ON�ɂ���
					SEflag = true;
					SEBtn.setIcon(SE_ONicon);
				}
			}
			if(theBtnName == BGMBtn){//BGM�{�^���������ꂽ����
				if(BGMBtn.getIcon() == BGM_ONicon){
					//BGM��OFF�ɂ���
					BGMflag = false;
					BGM_OFF();
					BGMBtn.setIcon(BGM_OFFicon);
				}else{
					//BGM��ON�ɂ���
					BGMflag = true;
					BGM_Start();
					BGMBtn.setIcon(BGM_ONicon);
				}
			}
			
			if(theBtnName == EndBtn){//Edn�{�^���������ꂽ����
				Reset();
				SendBye();
				StartWindow();
			}
	}
	
		public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
			JButton theBtnName = (JButton)e.getComponent();
			if(theBtnName == theStartButton){
				theStartButton.setIcon(startbtn_mousein_icon);
			}
			if(theBtnName == RuleBtn){
				RuleBtn.setIcon(rulebtn_mousein_icon);
			}
		}
		
		public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
			JButton theBtnName = (JButton)e.getComponent();
			if(theBtnName == theStartButton){
				theStartButton.setIcon(startbtn_mouseout_icon);
			}
			if(theBtnName == RuleBtn){
				RuleBtn.setIcon(rulebtn_mouseout_icon);
			}
		}
		
		public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
			//
		}
		
		public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
			//System.out.println("�}�E�X�������");
		}
		
		public void mouseDragged(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g�Ƃ��h���b�O���Ă���Ƃ��̏���
			//
		}

		public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���
			//System.out.println("�}�E�X�ړ�");
		}
		
		@Override //��KeyListerner�֌W��
		public void keyTyped(KeyEvent e) { //�@�������͂̂Ƃ��Ɏg�����������ǁC�����Q�[���̂Ƃ��ɂ͗��p���Ȃ�
			//�g�p���Ȃ��̂ŋ�ɂ��Ă����܂��B
		}
			
		@Override //��KeyListerner�֌W��
		public void keyPressed(KeyEvent e) { //Key���������Ƃ�
		Point theMyBarButtonLocation = theMyBarButton.getLocation();//�����̃o�[�̃|�W�V����
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_RIGHT://�E�L�[
				if(theMyBarButtonLocation.x + 10 < (stagewidth - Barwidth)  + stageposition_x){
					theMyBarButtonLocation.x += 10;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}else{
					theMyBarButtonLocation.x = (stagewidth - Barwidth) + stageposition_x;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}
				SendMyBarLocation(theMyBarButtonLocation.x);
				break;
			case KeyEvent.VK_LEFT://���L�[
				if(theMyBarButtonLocation.x - 10 > stageposition_x){
					theMyBarButtonLocation.x -= 10;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}else{
					theMyBarButtonLocation.x = stageposition_x;
					theMyBarButton.setLocation(theMyBarButtonLocation);
				}
				SendMyBarLocation(theMyBarButtonLocation.x);
				break;
			case KeyEvent.VK_SPACE://�X�y�[�X�L�[ boll�̃X�^�[�g
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
	
		@Override //��KeyListerner�֌W��
		public void keyReleased(KeyEvent e) { //Key�𗣂����Ƃ�
			
		}
		
		//�T�[�o�[�ڑ����s��
		public void ConectServer(){
			Socket socket = null;
			try {
				//"localhost"�́C���������ւ̐ڑ��Dlocalhost��ڑ����IP Address�i"133.42.155.201"�`���j�ɐݒ肷��Ƒ���PC�̃T�[�o�ƒʐM�ł���
				//10000�̓|�[�g�ԍ��DIP Address�Őڑ�����PC�����߂āC�|�[�g�ԍ��ł���PC�㓮�삷��v���O��������肷��
				socket = new Socket(ip, 10000);
			} catch (UnknownHostException e) {
				System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
				waitmsgLabel.setIcon(waitmsg_noconect_icon);//�u�ڑ��Ɏ��s���܂����v
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
				System.err.println("�G���[���������܂���: " + e);
				waitmsgLabel.setIcon(waitmsg_noconect_icon);//�u�ڑ��Ɏ��s���܂����v
				c.repaint();
				c.paintImmediately(c.getBounds());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException er) {
					er.printStackTrace();
				}
				return;
			}
			
			MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
			mrt.start();//�X���b�h�𓮂����iRun�������j
			RepaintThread repain = new RepaintThread();//�ĕ`�ʂ悤�̃X���b�h���쐬����
			repain.start();//�X���b�h�𓮂���(repain������)
			
			//����̐ڑ���҂� 15�b
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
					repain.stoprepaint();//repain�̃X���b�h���~�߂�
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
			
			//�ڑ��ł��Ȃ������ꍇ
			waitguruguruLabel.setVisible(false);
			waitmsgLabel.setIcon(waitmsg_nomatch_icon);
			c.repaint();
			c.paintImmediately(c.getBounds());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//�T�[�o�[�Ƃ̐ڑ���ؒf����
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
			
			repain.stoprepaint();//repain�̃X���b�h���~�߂�
			startcount[playernum-1] = 0;
			//�ҋ@��ʂ��\��
			waitflameLabel.setVisible(false);
			waitmsgLabel.setVisible(false);
			//�X�^�[�g��ʂ�\��
			theStartButton.setVisible(true);
			RuleBtn.setVisible(true);
			BGMBtn.setVisible(true);
			SEBtn.setVisible(true);
			IPLabel.setVisible(true);
			NAMELabel.setVisible(true);
			theTextField_IP.setVisible(true);
			theTextField_Name.setVisible(true);
		}
	
		//bar�ɓ����������ǂ����𔻒f����
		public boolean judgehitbar(int x,int y,int dx,int dy){
			int nextX = x + dx;
			int nextY = y + dy;
			Point theMyBarButtonLocation = theMyBarButton.getLocation();
			Point theYourBarButtonLocation = theYourBarButton.getLocation();
			int MyBarX = theMyBarButtonLocation.x;
			int MyBarY = theMyBarButtonLocation.y;
			int YourBarX = theYourBarButtonLocation.x;
			int YourBarY = theYourBarButtonLocation.y;
			//mybar�����˕Ԃ�
			if((mybarhitnum == 0)&&((MyBarX < nextX)&&(nextX < MyBarX + 120))&&((MyBarY - 20 < nextY)&&(nextY < MyBarY + 10))){
				mybarhitnum = 1;
				yourberhitnum = 0;
				return true;
			}
			//YourBar�����˕Ԃ�
			if((yourberhitnum == 0)&&(YourBarX < nextX)&&(nextX < YourBarX + 120)&&(YourBarY < nextY)&&(nextY < YourBarY + 20)){
				mybarhitnum = 0;
				yourberhitnum = 1;
				return true;
			}
			return false;
		}
		
		//MyBar��x���W�𑗐M
		public void SendMyBarLocation(int MyBarX){
			String msg = "BarMOVE"+" "+ playernum +" " + MyBarX;
			out.println(msg);
			out.flush();
		}
		
		//boll�̃X�^�[�g�ݒ�
		public void StartBoll(int playernum){
			int dx,dy, randamnum;
			Point theMyBarButtonLocation = theMyBarButton.getLocation();
			Point theYourBarButtonLocation = theYourBarButton.getLocation();
			int MyBarX = theMyBarButtonLocation.x;
			int YourBarX = theYourBarButtonLocation.x;
			Random rand = new Random();
			randamnum = rand.nextInt(6);//�����_����0~6�܂ł̐����𐶐�
			dx = vector[0][randamnum];
			dy = vector[1][randamnum];
			if(playernum == 1){
				SendBollPosition(playernum, MyBarX + (Barwidth/2) - 10, MyBarPositionY - 30 + stageposition_y ,dx ,dy);
			}else if(playernum == 2){
				dy = (-1) * dy;//��pleyer2��ʂ悤�̕ύX�ɑ΂��鏈����
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
			if(y <= 300 + stageposition_y){//YourBar�̒��˕Ԃ�
				Barcenter = YourBarX + (Barwidth / 2);
				bolldistance = Math.abs(x - Barcenter);
				barjudgebtnY = -1;
			}else{//MyBar�̒��˕Ԃ�
				Barcenter = MyBarX + (Barwidth / 2);
				bolldistance = Math.abs(x - Barcenter);
				barjudgebtnY = 1;
			}
			if((x - Barcenter) < 0){
				barjudgebtnX = -1;
			}else{
				barjudgebtnX = 1;
			}
			
			//Bar�̓��������ꏊ�ɂ���ăx�N�g����ύX����
			if(bolldistance <= (Barwidth / 6)){//���S����0~1/3
				//System.out.println("1 : " + dx + " , " + dy);
				dx = barjudgebtnX * 1;//�}�P
				dy = barjudgebtnY * 3;//�}�R
			}else if(bolldistance <= (Barwidth / 4)){//���S����1/3~2/3
				//System.out.println("2 : " + dx + " , " + dy);
				dx = barjudgebtnX * 2;//�}2
				dy = barjudgebtnY * 2;//�}2
			}else{//���S����2/3~
				//System.out.println("3 : " + dx + " , " + dy);
				dx = barjudgebtnX * 3;//�}3
				dy = barjudgebtnY * 1;//�}1
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
		
		//�u���b�N�ɓ��������Ƃ��ɓ_�����J�E���g����֐�
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
			if(mypoint + yourpoint == 48){//�I������
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
			//�{�[���̍���̍��W
			int nextX_up_left = nextX;
			int nextY_up_left = nextY;
			//�{�[���̉E��̍��W
			int nextX_up_right = nextX + 20;
			int nextY_up_right = nextY;
			//�{�[���̍����̍��W
			int nextX_down_left = nextX;
			int nextY_down_left = nextY + 20;
			//�{�[���̉E���̍��W
			int nextX_down_right = nextX + 20;
			int nextY_down_right = nextY + 20;
			
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 8; j++){
					if(block[8 * i + j] == 1){//���̃u���b�N�����݂��Ă���ꍇ
						blockX = 62 * j + 52  + stageposition_x;
						blockY = 42 * i + 275 + stageposition_y;
						if(((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight))&&((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight))){//���������ˁ�
							return (8 * i + j) + " " + "DOWN";
						}else	if(((blockX < nextX_down_left)&&(nextX_down_left < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight))&&((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight))){//��������ˁ�
							return (8 * i + j) + " " + "UP";
						}else if(((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight))&&((blockX < nextX_down_left)&&(nextX_down_left) < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight)){//�E�������ˁ�
							return (8 * i + j) + " " + "RIGHT";
						}else	if(((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight))&&((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight))){//���������ˁ�
							return (8 * i + j) + " " + "LEFT";
						}else	if((blockX < nextX_down_left)&&(nextX_down_left < blockX + blockwidth)&&(blockY < nextY_down_left)&&(nextY_down_left < blockY + blockheight)){//�E���������?
							return (8 * i + j) + " " + "UP_RIGHT";
						}else	if((blockX < nextX_down_right)&&(nextX_down_right < blockX + blockwidth)&&(blockY < nextY_down_right)&&(nextY_down_right < blockY + blockheight)){//�����������?
							return (8 * i + j) + " " + "UP_LEFT";
						}else	if((blockX < nextX_up_left)&&(nextX_up_left < blockX + blockwidth)&&(blockY < nextY_up_left)&&(nextY_up_left < blockY + blockheight)){//�E����������?
							return (8 * i + j) + " " + "DOWN_RIGHT";
						}else	if((blockX < nextX_up_right)&&(nextX_up_right < blockX + blockwidth)&&(blockY < nextY_up_right)&&(nextY_up_right < blockY + blockheight)){//������������?
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
		
		//���C�t�̌v�Z
		public void Lifecount(int deadbollplayernum){
			int mylifepoint, yourlifepoint;
			ImageIcon mynolife,yournolife;
			/*if(boll_y >= stageheight + stageposition_y - 30){//�������Ń{�[������O�ɏo��
				if(playernum == 1){
					player1_lifepoint--;
				}else{
					player2_lifepoint--;
				}
			}else{//���葤�Ń{�[������O�ɏo���ꍇ
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
			if(playernum == deadbollplayernum){//�����̃{�[������O
				if(playernum == 1){
					player1_lifepoint--;
				}else{
					player2_lifepoint--;
				}
			}else{//����̃{�[������O
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
			if((player1_lifepoint == 0)||(player2_lifepoint == 0)){//�ǂ��炩�̃��C�t�|�C���g��0�ɂȂ����ꍇ
				//�I������
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
		
		public void Result(int winnunber){//���ʔ��\
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