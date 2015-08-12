package mario;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
public class MarioMov {
BufferedImage MSR,MWR,MSL,MWL,MJL,MJR,SHOT;
int SIZEH=28,SIZEW=16;
Main m;
int pos=0;
int mov=0;
boolean flag=true,start=true,move=false;
boolean Right=false,Left=false,Jump=false;
boolean forShot=false;
boolean shotPosition;
boolean spaceOpenForShot=true; //spacebar control,so that only one fire ball can move at a tym.
int Y=0;int X=0;
int jumpLimit;
boolean Up=true,Down=false;
int fw=0,fh=0;
int shotPosX=0,shotPosY=0;
Rectangle rect;
JLabel jlabel;

public MarioMov(Main m) {
//    Sound.NEWLIFE.play();
       this.m=m;
       Y=m.GroundH-SIZEH-1;
       jumpLimit=100;
       try{
MSR=ImageIO.read(new File("Images/Mario/MarioStandingRight.gif"));
MWR=ImageIO.read(new File("Images/Mario/MarioWalkingRight.gif"));
MSL=ImageIO.read(new File("Images/Mario/MarioStandingLeft.gif"));
MWL=ImageIO.read(new File("Images/Mario/MarioWalkingLeft.gif"));
MJL=ImageIO.read(new File("Images/Mario/MarioJumpingLeft.gif"));
MJR=ImageIO.read(new File("Images/Mario/MarioJumpingRight.gif"));
SHOT=ImageIO.read(new File("Images/Level1/shot.gif"));
       }catch(IOException e){
        e.printStackTrace();
        }
       }
   public void move(){
    if(move){
        if(Up)
        Y-=3;
        if(Y<jumpLimit || Down){
            Up=false;
            Down=true;
            Y+=3;
            if(Y==m.GroundH-SIZEH-1){
               move=false;
                Up=true;
                Down=false;
            }
        }
    }
    
    if(X>m.getWidth()-SIZEW) X=m.getWidth()-SIZEW;
    if(X<0) X=0;
    
    if(forShot){
        if(shotPosition){
      shotPosX+=3;}
        else{
        shotPosX-=3;}
      spaceOpenForShot=false;
      if(shotPosX>500 || shotPosX<0){
          spaceOpenForShot=true;}
      else if(m.gooba.IndicatorForShot){
        if(shotPosition) shotPosX+=500;
        else shotPosX-=500;
        // just to make the shot Invisible.Too much time wasted.
        spaceOpenForShot=true;
        m.gooba.IndicatorForShot=false;

      }
    }
   }
   public Rectangle getFireShotBounds(){
       return new Rectangle(shotPosX,shotPosY,SHOT.getWidth(),SHOT.getHeight());
   }
   public Rectangle getMarioBounds(){
    return new Rectangle(X,Y,SIZEW-2,SIZEH-2);
}
   public void setFireShotBounds(int x,int y,boolean p){
       shotPosX=x;
       shotPosY=y+11;
       shotPosition=p;
   }
   public void keyPressed(KeyEvent ke) {
       int key=ke.getKeyCode();
     switch(key){
         case KeyEvent.VK_RIGHT:
             Right=true;
             Left=Jump=start=false;
             if(flag) flag=false;
             else     flag=true;
             X+=5;
             break;
         case KeyEvent.VK_LEFT:
             Left=true;
             Right=Jump=false;
             if(flag) flag=false;
             else     flag=true;
             X-=5;
             break;
         case KeyEvent.VK_UP:
             Sound.JUMP.play();
             Jump=true;
             if(Right){
                 flag=true;
                 }
             else if(Left)      {
                 flag=false;
             }
             Right=Left=false;
             move=true;
             break;
        case KeyEvent.VK_SPACE:
            if(spaceOpenForShot){
            Sound.FIREBALL.play();
            forShot=true;
            rect=getMarioBounds();
            boolean shotPosition;
            if(Right) shotPosition=true;
            else if(Left)  shotPosition=false;
            else if(Jump && flag) shotPosition=true;
            else shotPosition=false;
            setFireShotBounds(rect.x,rect.y,shotPosition);
            break;
             }
     }
     }
   public void paint(Graphics g){
      if(flag && Right || start){
      g.drawImage(MSR,X,Y,SIZEW,SIZEH,null);
      Left=false;
      Jump=false;
      }
      else if(!flag && Right){
      g.drawImage(MWR,X,Y,SIZEW,SIZEH,null);
      Left=false;
      Jump=false;
      }
        if(flag && Left){
      g.drawImage(MSL, X, Y, SIZEW, SIZEH, null);
      Right=false;
      Jump=false;
        }
       else if(!flag && Left){
      g.drawImage(MWL, X, Y, SIZEW, SIZEH, null);
        Right=false;
      Jump=false;
        }
      if(flag && Jump){
        g.drawImage(MJR, X, Y, SIZEW, SIZEH, null);
        Right=false;
      Left=false;
        }
        else if(!flag && Jump){
        g.drawImage(MJL,X,Y,SIZEW,SIZEH,null);
        Right=false;
      Left=false;
        }
      fw=SHOT.getWidth();
      fh=SHOT.getHeight();
      
      if(forShot){
      g.drawImage(SHOT,shotPosX,shotPosY,fw,fh,null);
      }


}
}
