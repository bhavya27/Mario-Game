package mario;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
public class Gooba{
BufferedImage GSR,GWR,GSL,GWL,GD;
Main m;
Random r=new Random();
Map<Integer,Integer> hm=new ConcurrentHashMap<Integer,Integer>();
Map<Integer,Integer> deadGooba=new ConcurrentHashMap<Integer,Integer>();
List<Integer> PointsGain=new CopyOnWriteArrayList<Integer>();
//Use ConcurrentHashMap Instead of HashMap.
int gw,gh;
int X=0;
int Y=0;
int sum=0;
int t=1;
int name=0;
int density;
Rectangle rect;
boolean flag=false;
boolean IndicatorForShot=false;
//int GoobaScoreAge=0;

public Gooba(Main m) {
       this.m=m;
try{
GSR=ImageIO.read(new File("Images/Level1/goobaStandingright.gif"));
GWR=ImageIO.read(new File("Images/Level1/goobaWalkingright.gif"));
GSL=ImageIO.read(new File("Images/Level1/goobaStandingleft.gif"));
GWL=ImageIO.read(new File("Images/Level1/goobaWalkingleft.gif"));
GD=ImageIO.read(new File("Images/Level1/goobadead.gif"));

       }catch(IOException e){
        e.printStackTrace();
      }
       }


public void move(){
   gw=GSR.getWidth();
   gh=GSR.getHeight();
   Y=m.GroundH-gh;
   sum++;
   //GoobaScoreAge++;
   density=randNumber(50,101); //100,150
   if(sum%density==0){
    ++name;
    hm.put(name, X);
    sum=0;
  }
   for(Map.Entry<Integer,Integer> me:hm.entrySet()){
       hm.put(me.getKey(), me.getValue()+2);
     rect=new Rectangle(me.getValue(),Y,gw-1,gh-1);
     if(m.mm.getMarioBounds().intersects(rect))
      m.gameOVer();
     else if(m.mm.getFireShotBounds().intersects(rect)){
         Sound.KICK.play();
         flag=true;
         m.diff+=15;
         deadGooba.put(me.getValue(),Y);
         PointsGain.add(me.getValue());
         hm.remove(me.getKey());
         IndicatorForShot=true;
       }
   }
   //Removing the offScreen Gooba's.
   for(Map.Entry<Integer,Integer> me:hm.entrySet()){
   if(me.getValue()>m.getWidth()){
       hm.remove(me.getKey());
       break;
   }
   }
  for(Map.Entry<Integer,Integer> me:deadGooba.entrySet()){
     deadGooba.put(me.getKey(),me.getValue()+2);
     if(me.getValue()>300){
         deadGooba.remove(me.getKey());
     }
   }
for(Integer li:PointsGain){
if(sum==density-10){
    PointsGain.remove(li);
 //   GoobaScoreAge=0;
    sum=0;
    break;
}
}

    }
public int randNumber(int Low,int High){
    return r.nextInt(High-Low)+Low;
}

   public void paint(Graphics g){
    
        for(Map.Entry<Integer,Integer> me:hm.entrySet()){
                   g.drawImage(GSR, me.getValue(), Y, gw, gh, null);
                }
        for(Map.Entry<Integer,Integer> me:deadGooba.entrySet()){
            g.drawImage(GD,me.getKey(),me.getValue(),gw,gh,null);
        }
     for(Integer li:PointsGain){
      g.setColor(Color.YELLOW);
      g.setFont(new Font("SansSerif",Font.BOLD,10));
      g.drawString("+15",li,Y-10);
  }

   }
}
