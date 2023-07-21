import java.util.*;
import java.io.*;

class Student{
	static Scanner sc= new Scanner(System.in);
	static Random rand = new Random();
	static String subject[]={"ENGLISH","MATHEMATICS","SCIENCE","SOCIAL SCIENCE"};
	static int outOf=100;
	static int totalStudents=0;
	private String name;
	private int rollNo;
	private int marks[]=new int[subject.length];
	private double percent;
	private int rank;
	private int total;
	private int barCode;
	
	Student(String name, int... marks){
		totalStudents++;
		this.name=name.toUpperCase();
		this.rollNo=totalStudents;
		this.marks=marks;
		for(int i=0; i<marks.length; i++)
			total+=marks[i];
		percent=(total*100.00)/(marks.length*outOf);
		barCode=rand.nextInt(100000);
	}
	
	public void display(){
		System.out.printf("Roll No: %03d \t\t\t Name: %s\n",this.rollNo,this.name);
		System.out.printf("Percentage: %.2f%%\t\t Certificate No: %05d\t\t Rank: %02d\n\n", this.percent, this.barCode,this.rank);
	}
	
	
	public int getRank(){
		return this.rank;
	}
	
	public int getRollNo(){
		return this.rollNo;
	}
	
	public double getPercent(){
		return percent;
	}
	
	public void setRank(int rank){
		this.rank=rank;
	}
	
	
//Function to change the name of an existing student
	public void setName(){
		System.out.print("Enter new name: ");
		String name;
		do{
			name=sc.nextLine();
		}while(name.length()==0);
		if(name.matches("^[a-zA-Z ]*$")){}            //matches 0 or n time till end of line
		else{
			System.out.println("XXXXX Improper Name XXXXX");
			return;
		}
		this.generateBarcode();
		System.out.println("Name of Roll No. "+this.rollNo+" changed from '"+this.name+"' to '"+name+"'");
		this.name=name;
	}
	
	
//Function to change the marks of an existing student
	public void setMarks(){
		int tempMark[]=new int[subject.length], newtotal=0,temp;
		try{
			for(int i=0; i<subject.length; i++){
				System.out.print("Marks obtained in '"+subject[i]+"' = ");
				temp=sc.nextInt();
				if(temp>=0 && temp<=outOf){
					tempMark[i]=temp;
					newtotal+=tempMark[i];
				}
				else{
					System.out.println("XXXXX You entered a wrong mark. Try again XXXXX");
					return;
				}
			}
		}catch(Exception e){
			sc.nextLine();                       
			System.out.println("XXXXX You entered a wrong mark. Try again XXXXX");
			return;
		}
		this.total=newtotal;
		this.marks=tempMark;
		this.percent=(newtotal*100.00)/(marks.length*outOf);
		this.generateBarcode();
		return;
	}
	
	
	public void generateBarcode(){
		this.barCode=rand.nextInt(100000);
	}
	

//Function to insert new student	
	static Student insertStudent(){
		System.out.print("Enter name: ");
		String name;
		int marks[]=new int[subject.length];
		do{
			name=sc.nextLine();
		}while(name.length()==0);
		if(name.matches("^[a-zA-Z ]*$")){}
		else{
			System.out.println("XXXXX Improper Name XXXXX");
				return null;
		}
		try{
			System.out.print("Enter Marks (seperated by space): ");
			for(int i=0; i<subject.length; i++){
				marks[i]=sc.nextInt();
				if(marks[i]<0 || marks[i]>outOf){
					System.out.println("Format not proper. Data not added");
					return null;
				}
			}
		}catch(Exception e){
			System.out.println("Format not proper. Data not added");
			return null;
		}
		Student newStudent=new Student(name, marks);
		return newStudent;
	}
	
	
//function to print certificate
	void printCertificate(){
		System.out.println("\n\t\t--------------------------------------------------");
		System.out.println("\t\t|\t\tCERTIFICATE OF MERIT	 \t |");
		System.out.println("\t\t|\t\t====================	 \t |");
		System.out.println("\t\t|\t     Year of Passing April 2022\t\t |");
		System.out.printf("\t\t| Name:%-40s  |\n",name);
		System.out.printf("\t\t| School:CDAC (Kharghar)\t Certificate No. |\n");
		System.out.printf("\t\t| Roll No.: %02d\t\t\t\t  %05d  |\n",rollNo,barCode);
		System.out.println("\t\t|\t\t\t\t\t\t |");
		System.out.println("\t\t|\t\t\t\t\t\t |");
		System.out.println("\t\t|=============================================== |");
		System.out.println("\t\t|   	Subject\t\t|  Marks | Out of\t |");
		System.out.println("\t\t|=============================================== |");
		for(int i=0; i<subject.length; i++)
			System.out.printf("\t\t| %-22s|  %3d   |  %-13d|\n",subject[i],marks[i], outOf); 
		System.out.println("\t\t|=============================================== |");
		System.out.printf("\t\t| Total\t\t\t|  %3d   |  %3d	\t |\n",total,(outOf*subject.length));
		System.out.println("\t\t|=============================================== |");
		System.out.printf("\t\t| Percentage: %.2f%%	\tRank: %02d\t |\n",percent,this.rank);
		System.out.println("\t\t|\t\t\t\t\t\t |");
		System.out.println("\t\t|     Authorised by\t\t\t\t |");
		System.out.println("\t\t|  Government of India\t\tPrincipal 	 |");
		System.out.println("\t\t|\t\t\t\t\t\t |");
		System.out.println("\t\t-------------------------------------------------- ");
	}
	
//main function for checking Student class	
/*
	public static void main(String args[])throws FileNotFoundException{
		File file = new File("D:\\projects\\StudentGrading\\StudentDetails.txt");
		Scanner sf = new Scanner(file);
		int noOfStudents=sf.nextInt();
		Student s[]=new Student[noOfStudents];
		try{sf.nextLine();
		for(int i=0; i<noOfStudents; i++){
			s[i]=new Student(sf.nextLine(), sf.nextInt(), sf.nextInt(),sf.nextInt(), sf.nextInt());
			sf.nextLine();
		}
		}catch(Exception e){};
		for(int i=0; i<noOfStudents; i++)
			s[i].display();
	}
	*/
}