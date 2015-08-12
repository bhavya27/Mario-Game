package mario;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
public class Fish{
Main m;
BufferedImage FFU,FFD,FFOU,FFOD;
List<Integer> FFUlist=new CopyOnWriteArrayList<Integer>();
List<Integer> FFDlist=new CopyOnWriteArrayList<Integer>();
Map<Integer,Integer> FFUhashMap=new ConcurrentHashMap<Integer,Integer>();
Map<Integer,Integer> FFDhashMap=new ConcurrentHashMap<Integer,Integer>();
int X=0,Y=240;

Random r=new Random();
Fish(Main m) {
    this.m=m;
    try{
FFU=ImageIO.read(new File("Images/Level1/flyingfish1up.gif"));
FFD=ImageIO.read(new File("Images/Level1/flyingfish1down.gif"));
FFOU=ImageIO.read(new File("Images/Level1/flyingfish2up.gif"));
FFOD=ImageIO.read(new File("Images/Level1/flyingfish2down.gif"));
    }catch(Exception e){
        e.printStackTrace();
    }
        
}
Rectangle rect;
int sumBottom=0,sumTop=0;
int speedOfFishBottom=200,speedOfFishTop=90; //400,100
int locationForBottom=0,locationForTop=0;
int fw,fh,frameWidth,frameHeight;
public void move(){
        fw=FFU.getWidth();
        fh=FFU.getHeight();
        frameWidth=m.getWidth();
        frameHeight=m.getHeight();
    sumBottom++;
    if(sumBottom==speedOfFishBottom){
        locationForBottom=r.nextInt(frameWidth-fw);
        FFUhashMap.put(locationForBottom, Y);
        FFUlist.add(locationForBottom);
        sumBottom=0;
    }
    sumTop++;
    if(sumTop==speedOfFishTop){
        locationForTop=r.nextInt(frameWidth-fw);
        FFDhashMap.put(locationForTop,X);
        FFDlist.add(locationForTop);
        sumTop=0;
    }

    for(Map.Entry<Integer,Integer> me : FFUhashMap.entrySet()){
        FFUhashMap.put(me.getKey(),me.getValue()-2);
        rect=new Rectangle(me.getKey(),me.getValue(),fw-5,fh-5);
        if(rect.intersects(m.mm.getMarioBounds())){
            m.gameOVer();
        }

       }
    for(Map.Entry<Integer,Integer> me:FFDhashMap.entrySet()){
        FFDhashMap.put(me.getKey(), me.getValue()+2);
        rect=new Rectangle(me.getKey(),me.getValue(),fw-5,fh-5);
        if(rect.intersects(m.mm.getMarioBounds()))
        m.gameOVer();
    }
    int i=0;
    while(i<FFUlist.size()){
        int l=FFUhashMap.get(FFUlist.get(i));
        if(l+FFU.getHeight()<X)
        {
            FFUhashMap.remove(FFUlist.get(i));
            FFUlist.remove(i);

        }
        i++;
    }
    int j=0;
    while(j<FFDlist.size()){
        int l=FFDhashMap.get(FFDlist.get(j));
        if(l-FFU.getHeight()>Y)
        {
            FFDhashMap.remove(FFDlist.get(j));
            FFDlist.remove(j);

        }
        j++;
    }

}

public void paint(Graphics g){

       for(Map.Entry<Integer,Integer> me:FFUhashMap.entrySet()){
             g.drawImage(FFU,me.getKey(),me.getValue(),fw,fh,null);
    }
       for(Map.Entry<Integer,Integer> me:FFDhashMap.entrySet()){
             g.drawImage(FFD,me.getKey(),me.getValue(),fw,fh,null);
    }
      


    }

}
