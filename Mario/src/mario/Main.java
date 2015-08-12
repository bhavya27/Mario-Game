package mario;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Formatter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
public class Main extends JPanel{
BufferedImage Ground,Grass,Flower,ExitStone;
int ExitStoneX,ExitStoneY,ExitStoneW,ExitStoneH;
int GroundH=200;
Rectangle rec;
boolean flag=true;
static Formatter fmt,HighScorefmt,yourHighScorefmt;
JButton exitButton;
Icon icon,end;

static String entered;
MarioMov mm=new MarioMov(this);
Fish fish=new Fish(this);
Gooba gooba=new Gooba(this);
Coins coins=new Coins(this);
static Database db;
static String HighScore,yourHighScore;
    public Main(){
    try{
    Ground=ImageIO.read(new File("Images/Level1/ground.gif"));
    Grass=ImageIO.read(new File("Images/Level1/platform.gif"));
    Flower=ImageIO.read(new File("Images/Level1/flower.gif"));
    ExitStone=ImageIO.read(new File("Images/Level1/exitstone.gif"));
    }catch(IOException e){
    e.printStackTrace();
    }
    ExitStoneX=475;
    ExitStoneY=5;
    ExitStoneW=ExitStone.getWidth();
    ExitStoneH=ExitStone.getHeight();
    Color blue=new Color(153,255,255);
    setBackground(blue);
    rec=new Rectangle(ExitStoneX,ExitStoneY,ExitStoneW,ExitStoneH);
    icon=new ImageIcon("Images/Level1/exitstone.gif");
    exitButton=new JButton(icon);
    exitButton.setBounds(rec);
    add(exitButton);
    
    setLayout(null); //compulsory to use,if we have to set the location of a button.
    exitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            flag=false;
            gameOVer();
        }
    });

    addKeyListener(new KeyAdapter(){
        public void keyPressed(KeyEvent ke){
            mm.keyPressed(ke);
        }
    });    
    Sound.BATTLE.loop();
    setFocusable(true);
    }

    public void motion(){
        mm.move();
        fish.move();
        gooba.move();
        coins.move();
    }

    public void gameOVer() {
       Sound.EXPLOSION.play();
       Sound.BATTLE.stop();
       try{
       db.storeYourHigh(Integer.valueOf(fmt.toString()), entered);
        }catch(SQLException e){}
       StringBuilder go=new StringBuilder();
       int limit=Integer.valueOf(fmt.toString());
       if(limit<100){
       end=new ImageIcon("Images/chimp.gif");
       go.append(" Even a chimp is better than you"); }
       else if(limit>=100 && limit<200){
       end=new ImageIcon("Images/owl.jpg");
       go.append(" A loser");    }
       else if(limit>=200 && limit<300){
       end=new ImageIcon("Images/batman.gif");
       go.append(" A beginner");}
       else if(limit>=300 && limit<400){
       end=new ImageIcon("Images/haha.gif");
       go.append(" Ok Ok");}
       else if(limit>=400 && limit<500){
       end=new ImageIcon("Images/loki.gif");
       go.append(" Very Good,Now Loki's eyes are on you");}
       else if(limit>=500 && limit<600){
       end=new ImageIcon("Images/minions.gif");
       go.append(" PERFECT !!! \n CHEERS");}
       else if(limit>=600){
       end=new ImageIcon("Images/TheEnd.gif");
       go.append(" MARVELLOUS...Here's your reward."); }
       go.append("\n         Your Score: "+fmt);
       go.append("\n\n Want to Continue ?");
       int response=JOptionPane.showConfirmDialog(null,go,"Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,end);
       if(response==JOptionPane.NO_OPTION || response==JOptionPane.CLOSED_OPTION)
       {
           System.exit(ABORT);
           flag=false;
        } else{
           try{
           
           main(new String[0]);
        }catch(Exception e){
            e.printStackTrace();}
        }

    }

    @Override
    public void paint(Graphics g){
      super.paint(g);
      
      int iw=Ground.getWidth();
      int ih=Ground.getHeight();
      for(int i=0;i<=480;i+=iw){
          for(int j=GroundH;j<=300;j+=ih){
              g.drawImage(Ground,i,j,iw,ih,this);
          }
      }
      int gw=Grass.getWidth();
      int gh=Grass.getHeight();
      for(int i=0;i<=480;i+=gw){
      for(int j=GroundH;j<=(GroundH+gh-5);j+=gh){
              g.drawImage(Grass,i,j,gw,gh,null);
          }
      }
      int fh=Flower.getHeight();
      int fw=Flower.getWidth();
      g.drawImage(Flower,50,GroundH-fh,fw,fh,null );
      g.drawImage(Flower,210,GroundH-fh,fw,fh,null );
      g.drawImage(Flower,450,GroundH-fh,fw,fh,null );
      mm.paint(g);
      fish.paint(g);
      gooba.paint(g);
      coins.paint(g);
      g.setColor(Color.white);
      g.setFont(new Font("SansSerif",Font.BOLD,11));
      g.drawString(entered.toUpperCase(),30,15);
      g.drawString("YOUR HIGH",210,15);
      g.drawString("HIGH SCORE",400,15);
      g.setFont(new Font("SansSerif",Font.BOLD,15));
      g.drawString(fmt.toString(),30,35);
      g.drawString(yourHighScorefmt.toString(),210,35);
      g.drawString(HighScorefmt.toString(),400,35);
      g.setFont(new Font("SansSerif",Font.ITALIC,12));
      g.drawString("\u00a9"+"bhavya joshi",410,260);

    }
static long diff=0;
public static void main(String[] a) throws Exception{
JFrame jfrm=new JFrame("Mario");
Main p=new Main();
jfrm.add(p);
db=new Database(p);
Object[] type={"I'm a newbie","Your old mate"};
int result=JOptionPane.showOptionDialog(jfrm,"Who the hell are You ?", "Welcome",
JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,type,null);
if(result==0){
entered=JOptionPane.showInputDialog(jfrm,"Hmm.A New One...Just Enter your name.");
db.registerName(entered);
}else{    
Object[] selectionValues=db.getAllPlayersName();
String initialSelection="player1";
Object selected=JOptionPane.showInputDialog(jfrm,"Hello Mate,Welcome back !","An Old Friend",
JOptionPane.QUESTION_MESSAGE,null,selectionValues,initialSelection);
entered=selected.toString();
}
HighScore=db.getHighScore();
HighScorefmt=new Formatter();
HighScorefmt.format("%05d", Integer.valueOf(HighScore));
yourHighScore=db.getyourHighScore(entered);
yourHighScorefmt=new Formatter();
yourHighScorefmt.format("%05d", Integer.valueOf(yourHighScore));
jfrm.setSize(500,300);
jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jfrm.setVisible(true);
jfrm.setResizable(false);
diff=0;
int scoreIncrmnt=0;
while(true && p.flag){
    fmt=new Formatter();
    if(scoreIncrmnt==80){
    diff+=1;
    scoreIncrmnt=0;
    }
    fmt.format("%05d",diff);
    p.repaint();
    Thread.sleep(20);
    p.motion();
    scoreIncrmnt++;
}
  }
}

