/**
 * @Author Curtis Hohl
 * @Date 1/19/17
 * Models a guitar with 37 different strings
 */
public class Guitar37 implements Guitar {

    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public int time;
    public GuitarString[] guitS;

    //Constructs Guitar37 based on frequency formula and passed in boolean
    public Guitar37(boolean useKP) {
       guitS  = new GuitarString[37];
       if(useKP == true){
            for (int i = 0; i < KEYBOARD.length(); i++) {
                guitS[i] = new KPGuitarString(Math.pow(2, ((i - 24.0) / 12.0)) * 440.0);
            }
        }
       else{
           for (int i = 0; i < KEYBOARD.length(); i++) {
               guitS[i] = new SimpleGuitarString(Math.pow(2, ((i - 24.0) / 12.0)) * 440.0);
           }
       }

    }

    // Specifies pitch of played note
    @Override
    public void playNote(int pitch) {
        int n = pitch + 12;

        if(n < KEYBOARD.length() && n >= 0){
            guitS[n].pluck();
        }
    }

    //Verifies that a particular character has a corresponding string for this guitar
    @Override
    public boolean hasString(char key) {
        return (KEYBOARD.indexOf(key) != -1);
    }

    //If guitar hasString, then the appropriate key is played or "plucked"
    @Override
    public void pluck(char key) throws IllegalArgumentException {
        if (hasString(key))
            guitS[KEYBOARD.indexOf(key)].pluck();
        else
            throw new IllegalArgumentException("illegal key: " + key);
    }

    //Returns sum sample of current guitar string
    @Override
    public double sample() {
        double sum = 0;
        for (GuitarString guitarString : guitS){
            sum += guitarString.sample();
        }
        return sum;
    }

    //Moves time forward one tic
    @Override
    public void tic() {
        for (GuitarString gs : guitS) {
            gs.tic();
        }
        time += 1;
    }

    //Returns current time value
    @Override
    public int time() {
        return time;
    }
}