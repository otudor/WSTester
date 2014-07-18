	package Serialization;

	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;



	public class TestSerialization {
		
		public TestSerialization() { }
		  int number1 = 0;
		  int number2 = 0;
		  public void setNumber1(int value) { number1 = value;}
		  public int getNumber1() { return number1; }
		  public void setNumber2(int value) { number2 = value; }
		  public int getNumber2() {return number2;}
		  
		  public void Run() throws FileNotFoundException{
			 TestSerialization fe = new TestSerialization();
			  fe.setNumber1(12);
			  fe.setNumber1(13);
			  FileOutputStream fos1 = new FileOutputStream("D:\\ser.xml");
			  java.beans.XMLEncoder xe1 = new java.beans.XMLEncoder(fos1);
			  xe1.writeObject(fe);
			  xe1.flush();
			  xe1.close();
		  }
	}


