package mario;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
public class Coins {
BufferedImage coin;
Main m;
Rectangle rect;
int cw=0,ch=0;
int X=0,Y=100;
int sum=0;
int density=20;
boolean points=false;
List<Integer> al=new CopyOnWriteArrayList<Integer>(); //Use this Class Always in case of ConcurrentModificationException.
List<Integer> PointsGained=new CopyOnWriteArrayList<Integer>();
List<Integer> PointsLost=new CopyOnWriteArrayList<Integer>();
//In case of CopyOnWriteArrayList, iterator doesn’t accomodate the changes in the list and works on the original list.
Random r=new Random();
int pointsCount=0;

Coins(Main m) {
    this.m=m;
    try{
    coin=ImageIO.read(new File("Images/Level1/money.gif"));
    }catch(Exception e){}
}

public void move(){
sum++;
if(sum==density){
al.add(r.nextInt(m.getWidth()-cw));
sum=0;
}
for(Integer li:al){ //Remove coins on collision
    rect=new Rectangle(li,Y,cw,ch);
    if(rect.intersects(m.mm.getMarioBounds())){
    m.diff+=10;
    Sound.COIN.play();
    al.remove(li);
    PointsGained.add(li);
    }
    }
for(Integer li:PointsGained){
if(sum==density-5){
    PointsGained.remove(li);
    break;
}
}
for(Integer li:al){ //Remove coins upon age
    if(al.size()>10){
        PointsLost.add(li);
        al.remove(li);
        m.diff-=2;
        break;
    }
}
for(Integer li:PointsLost){
    if(sum==density-10){
        PointsLost.remove(li);
        break;
    }
}
/*
 Another way,without ConcurrentMdificationException.
 * Iterator itr=al.iterator();
 * while(itr.hasNext()){
 * if(condition){
 * al.remove(li);
 */

}
public void paint(Graphics g){
  cw=coin.getWidth();
  ch=coin.getHeight();
  for(Integer li:al){
   g.drawImage(coin,li,Y,cw,ch,null);
}
  for(Integer li:PointsGained){
   g.setColor(Color.YELLOW);
   g.setFont(new Font("SansSerif",Font.BOLD,10));
   g.drawString("+10", li, Y-10);
  }
  for(Integer li:PointsLost){
      g.setColor(Color.YELLOW);
      g.setFont(new Font("SansSerif",Font.BOLD,10));
      g.drawString("-2",li,Y-10);
  }
}
}
