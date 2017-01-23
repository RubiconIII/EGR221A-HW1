import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *  @Author Curtis Hohl
 * @Date 1/19/17
 */
public class KPGuitarString implements GuitarString{

    private final int SAMPLERATE = StdAudio.SAMPLE_RATE;
    private final double ENERGYDECAY = 0.996;
    Queue<Double> ringBuffer;

    // Constructor for guitar string of given frequency
    KPGuitarString(double frequency) throws IllegalArgumentException{
        int cap = (int) Math.round(SAMPLERATE/frequency);
        if (frequency <=0 || cap < 2) throw new IllegalArgumentException();
        ringBuffer = new LinkedList<>();
        for (int i = 0; i < cap; i++){
            ringBuffer.add(0.0);
        }
    }

    // Constructs guitar string and initializes ring buffer to values in array
    KPGuitarString(double[] init) throws IllegalArgumentException {
        if (init.length < 2)
            throw new IllegalArgumentException();

        ringBuffer = new LinkedList<>();

        for (int i = 0; i < init.length; i++){
            ringBuffer.add(init[i]);
        }
    }

    //Replaces values in ring buffer with n random values between -0.5 inclusive and +0.5 exclusive
    @Override
    public void pluck(){
    Random rand = new Random();
        for (int i = 1; i < ringBuffer.size(); i++) {
        ringBuffer.remove();
        double r = rand.nextDouble() - .5;
        ringBuffer.add(r);
    }
}

    // Applies Karplus-Strong update once
    @Override
    public void tic(){
        double num = ringBuffer.remove();
        double num2 = ringBuffer.peek();

        double finNum = ((num + num2)/2)* ENERGYDECAY;
        ringBuffer.add(finNum);
    }

    // Returns the current sample
    @Override
    public double sample(){
        return ringBuffer.peek();
    }
}
