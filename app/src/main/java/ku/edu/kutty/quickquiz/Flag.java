package ku.edu.kutty.quickquiz;

import android.graphics.Bitmap;

class Flag
{
	private String name;
	private Bitmap bitmap;
	
	Flag(String name, Bitmap bitmap)
	{
		this.name = name;
		this.bitmap = bitmap;
	}
	
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	
	public String getName()
	{
		return name;
	}
}
