package Utility;

class MyException extends Exception
{
	String str1;
	/* Constructor of custom exception class
	 * here I am copying the message that we are passing while
	 * throwing the exception to a string and then displaying 
	 * that string along with the message.
	 */
	MyException(String str2) 
	{
		str1=str2;
	}
	public String toString()
	{ 
		return ("MyException Occurred: "+str1) ;
	}
}

