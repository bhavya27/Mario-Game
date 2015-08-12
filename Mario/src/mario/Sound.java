package mario;
import java.applet.Applet;
import java.applet.AudioClip;
public class Sound {
public static final AudioClip COIN=
Applet.newAudioClip(Sound.class.getResource("Audios/coin.au"));
public static final AudioClip FIREBALL=
Applet.newAudioClip(Sound.class.getResource("Audios/fireball.au"));
public static final AudioClip JUMP=
Applet.newAudioClip(Sound.class.getResource("Audios/jump.au"));
public static final AudioClip BATTLE=
Applet.newAudioClip(Sound.class.getResource("Audios/Battle.mid"));
public static final AudioClip NEWLIFE=
Applet.newAudioClip(Sound.class.getResource("Audios/newlife.au"));
public static final AudioClip EXPLOSION=
Applet.newAudioClip(Sound.class.getResource("Audios/Explosion.mid"));
public static final AudioClip KICK=
Applet.newAudioClip(Sound.class.getResource("Audios/kick.au"));

}
