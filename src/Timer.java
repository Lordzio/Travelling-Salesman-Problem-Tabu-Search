import java.text.DecimalFormat;

public class Timer 
{
	long start;
	long stop;


	public void start() 
	{
		start = System.nanoTime();
	}

	public void stop() 
	{
		stop = System.nanoTime();
	}
	
	public long getTime() 
	{
		return stop - start;
	}

	public String formatTime() 
	{
		DecimalFormat format = new DecimalFormat("#.####");
		long time = getTime();
		return format.format((time / Math.pow(10, 9))) + "s"; 
	}
}
