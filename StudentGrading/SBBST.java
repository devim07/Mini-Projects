import java.util.*;
import java.io.*;

class SBBSTNodes
{
    SBBSTNodes left, right;
    Student s;
    int height;
 
    public SBBSTNodes()
    {
        left = null;
        right = null;
        s=null;
        height = 0;
    }
 
    public SBBSTNodes(Student s)
    {
 
        left = null;
        right = null;
        this.s=s;
        height = 0;
    }
}
 
class SBBST
{
    private SBBSTNodes root;
	static int rank=0;
	static Student change;
 
    public SBBST()
    {
        root = null;
    }
 
    public boolean isEmpty()
    {
        return root == null;
    }
 
    public void clear()
    {
        root = null;
    }
	
	public SBBSTNodes getRoot(){
		return root;
	}
 
    public void insert(Student s)
    {
        root = insert(s, root);
    }
 
    private int height(SBBSTNodes t)
    {
 
        return t == null ? -1 : t.height;
    }
 
    private int max(int lhs, int rhs)
    {
        return lhs > rhs ? lhs : rhs;
    }
 
    private SBBSTNodes insert(Student s, SBBSTNodes t)
    {
        if (t == null)
            t = new SBBSTNodes(s);
        else if (s.getPercent() <= t.s.getPercent())
        {
            t.left = insert(s, t.left);
            if (height(t.left) - height(t.right) == 2)
                if (s.getPercent() <= t.left.s.getPercent())
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
        } else if (s.getPercent() > t.s.getPercent())
        {
            t.right = insert(s, t.right);
            if (height(t.right) - height(t.left) == 2)
                if (s.getPercent() > t.right.s.getPercent())
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
        } else
            ;
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }
 
    private SBBSTNodes rotateWithLeftChild(SBBSTNodes k2)
    {
        SBBSTNodes k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }
 
    private SBBSTNodes rotateWithRightChild(SBBSTNodes k1)
    {
        SBBSTNodes k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }
 
    private SBBSTNodes doubleWithLeftChild(SBBSTNodes k3)
    {
		k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }
 
    private SBBSTNodes doubleWithRightChild(SBBSTNodes k1)
    {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
 
    public int countNodes()
    {
        return countNodes(root);
    }
 
    private int countNodes(SBBSTNodes r)
    {
        if (r == null)
            return 0;
        else
        {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }
	
	
	public void findRollNo(SBBSTNodes n, int rollNo,int choice){
		if(n==null){
			return;
		}
		findRollNo(n.left,rollNo, choice);
		if(n.s.getRollNo()==rollNo){
			if(choice==2)
				delete(this.root, n.s.getPercent(), n.s.getRollNo());
			else if(choice== 4)
				n.s.setName();
			else if(choice==5){
				System.out.println("Updating marks for Roll No: "+ n.s.getRollNo());
				change=n.s;
				this.delete(this.root, n.s.getPercent(), n.s.getRollNo());
				change.setMarks();
				System.out.println("Marks Updated!");
				return;				
			}
			else if(choice==6)
				n.s.printCertificate();
			return;
		}
		findRollNo(n.right,rollNo,choice);
		return;
	}
	
	SBBSTNodes delete(SBBSTNodes root, double key, int roll){
		if(root==null)
			return null;
		if(key<root.s.getPercent())
			root.left=delete(root.left, key,roll);
		else if(key>root.s.getPercent())
			root.right=delete(root.right, key,roll);
		else{
			if(root.s.getRollNo()==roll){
				if(root.left==null)
					return root.right;
				else if(root.right==null)
					return root.left;
				root.s=minValue(root.right);
				root.right=delete(root.right, root.s.getPercent(),roll);
			}else{
				root.left=delete(root.left, key,roll);
			}
		}
		return root;
	}
	
	Student minValue(SBBSTNodes root){
		Student x= root.s;
		while(root.left!=null){
			x=root.left.s;
			root=root.left;
		}
		return x;
	}
	
	
//Function to update ranks when a new student is added or marks of an existing student is changed
	public void giveRank(SBBSTNodes n, int choice){
		if(n==null)
			return;
		int tempRank;
		giveRank(n.right,choice);
		tempRank=n.s.getRank();              //storing previously value of static variable rank, to check if BarCode needs to be updated
		n.s.setRank(++rank);
		if(tempRank!=n.s.getRank())          //checking previously assigned rank with current rank
			n.s.generateBarcode();
		if(choice==3)                        //choice selected is to display the details of all students
			n.s.display();
		if(n.left!=null  && n.s.getPercent()==n.left.s.getPercent())                   //if two students have same perscentage adjust ranks
			rank--;
		if(choice==7)                        //choice selected is to display certificates of all students
			n.s.printCertificate();
		giveRank(n.left,choice);
	}
	

    static public void menu(){
		System.out.println("\n\t1. Insert data of a new student.");
		System.out.println("\t2. Delete data of an existing student.");
		System.out.println("\t3. Display the data of all students.");
		System.out.println("\t4. Change Name of a student.");
		System.out.println("\t5. Change marks of a student.");
		System.out.println("\t6. Display the certificate of a particular student.");
		System.out.println("\t7. Display the certificate of all students.");
		System.out.print("Enter your choice: ");
		
	}
	
	public static void main(String[] args)throws FileNotFoundException
    {
        Scanner sc = new Scanner(System.in);
 
        SBBST sbbst = new SBBST();
        File file = new File("D:\\projects\\StudentGrading\\StudentDetails.txt");
		Scanner sf = new Scanner(file);
		int noOfStudents=sf.nextInt();
		Student s;
		SBBSTNodes n;
		String tempName;
		int tempMarks[]=new int[Student.subject.length];
		try{
			sf.nextLine();
			for(int i=0; i<=noOfStudents; i++){
				tempName=sf.nextLine();
				for(int j=0; j<Student.subject.length; j++)
					tempMarks[j]=sf.nextInt();
				s=new Student(tempName, tempMarks);
				sf.nextLine();
				n=new SBBSTNodes(s);
				sbbst.insert(s);
			}
		}catch(Exception e){};
		sbbst.giveRank(sbbst.getRoot(), 3);
		char cont='y';
		int choice=0,tempRollNo;
		while(cont=='y' || cont=='Y'){
			menu();
			try{
				choice=sc.nextInt();
			switch(choice){
				case 1:
					Student newStudent=Student.insertStudent();
					if(newStudent!=null){
						sbbst.insert(newStudent);
						System.out.println("New Student inserted successfully!");
					}
					break;
				case 2:
					System.out.print("Enter the Roll Number of the student to be deleted: ");
					tempRollNo=sc.nextInt();
					sbbst.findRollNo(sbbst.getRoot(), tempRollNo, 2);
					System.out.println("Student deleted successfully!");
					break;
				case 3:
					sbbst.rank=0;
					sbbst.giveRank(sbbst.getRoot(),3);
					break;
				case 4:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					sbbst.findRollNo(sbbst.getRoot(), tempRollNo, 4);
					break;
				case 5:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					sbbst.findRollNo(sbbst.getRoot(), tempRollNo, 5);
					sbbst.insert(change);
					break;
				case 6:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					sbbst.findRollNo(sbbst.getRoot(), tempRollNo, 6);
					break;
				case 7:
					sbbst.rank=0;
					sbbst.giveRank(sbbst.getRoot(),7);
					break;
				default:
					System.out.println("You entered a wrong choice");
			}
			}catch(Exception e){
				System.out.println("XXXXX Incorrect Input XXXXX");
				sc.nextLine();
			}
			System.out.print("Do you wish to continue? (Y/N): ");
			cont=sc.next().charAt(0);
		}
	}
}




























/*

class BSTest{
	static Scanner sc=new Scanner(System.in);
	static Student change;
	
	Node root;
	static int rank=0;
	
	static class Node{
		Student s;
		Node left;
		Node right;
		
		Node(Student s){
			this.s=s;	
		}
	}
	
	
//Function to create a new node	
	public Node insertInTree(Node n, Student newStudent){
		if(this.root==null)
			this.root=new Node(newStudent);
		if(n==null)
			return n=new Node(newStudent);
		if(newStudent.getPercent()<=n.s.getPercent())
			n.left=insertInTree(n.left, newStudent);
		else 
			n.right=insertInTree(n.right, newStudent);
		return n;
	}
	
	public void findRollNo(Node n, int rollNo,int choice){
		if(n==null){
			return;
		}
		findRollNo(n.left,rollNo, choice);
		if(n.s.getRollNo()==rollNo){
			if(choice==2)
				delete(this.root, n.s.getPercent(), n.s.getRollNo());
			else if(choice== 4)
				n.s.setName();
			else if(choice==5){
				System.out.println("Updating marks for Roll No: "+ n.s.getRollNo());
				change=n.s;
				this.delete(this.root, n.s.getPercent(), n.s.getRollNo());
				change.setMarks();
				this.insertInTree(this.root, change);
				this.rank=0;
				this.giveRank(this.root,0);
				System.out.println("Marks Updated!");
				return;				
			}
			else if(choice==6)
				n.s.printCertificate();
			return;
		}
		findRollNo(n.right,rollNo,choice);
		return;
	}
	
	Node delete(Node root, double key, int roll){
		if(root==null)
			return null;
		if(key<root.s.getPercent())
			root.left=delete(root.left, key,roll);
		else if(key>root.s.getPercent())
			root.right=delete(root.right, key,roll);
		else{
			if(root.s.getRollNo()==roll){
				if(root.left==null)
					return root.right;
				else if(root.right==null)
					return root.left;
				root.s=minValue(root.right);
				root.right=delete(root.right, root.s.getPercent(),roll);
			}else{
				root.left=delete(root.left, key,roll);
			}
		}
		return root;
	}
	
	Student minValue(Node root){
		Student x= root.s;
		while(root.left!=null){
			x=root.left.s;
			root=root.left;
		}
		return x;
	}
	
	
//Function to update ranks when a new student is added or marks of an existing student is changed
	public void giveRank(Node n, int choice){
		if(n==null)
			return;
		int tempRank;
		giveRank(n.right,choice);
		tempRank=n.s.getRank();              //storing previously value of static variable rank, to check if BarCode needs to be updated
		n.s.setRank(++rank);
		System.out.println(n.s.getPercent()+"   "+n.s.getRank());
		if(tempRank!=n.s.getRank())          //checking previously assigned rank with current rank
			n.s.generateBarcode();
		if(choice==3)                        //choice selected is to display the details of all students
			n.s.display();
		if(n.left!=null  && n.s.getPercent()==n.left.s.getPercent())                   //if two students have same perscentage adjust ranks
			rank--;
		if(choice==7)                        //choice selected is to display certificates of all students
			n.s.printCertificate();
		giveRank(n.left,choice);
	}
	
	
	static public void menu(){
		System.out.println("\n\t1. Insert data of a new student.");
		System.out.println("\t2. Delete data of an existing student.");
		System.out.println("\t3. Display the data of all students.");
		System.out.println("\t4. Change Name of a student.");
		System.out.println("\t5. Change marks of a student.");
		System.out.println("\t6. Display the certificate of a particular student.");
		System.out.println("\t7. Display the certificate of all students.");
		System.out.print("Enter your choice: ");
		
	}
	
	
//The main function
	public static void main(String args[])throws FileNotFoundException{
		//To insert the data of students already present
		File file = new File("D:\\projects\\StudentGrading\\StudentDetails.txt");
		Scanner sf = new Scanner(file);
		BSTest rankTree=new BSTest();
		int noOfStudents=sf.nextInt();
		Student s;
		Node n;
		String tempName;
		int tempMarks[]=new int[Student.subject.length];
		try{
			sf.nextLine();
			for(int i=0; i<=noOfStudents; i++){
				tempName=sf.nextLine();
				for(int j=0; j<Student.subject.length; j++)
					tempMarks[j]=sf.nextInt();
				s=new Student(tempName, tempMarks);
				sf.nextLine();
				n=new Node(s);
				rankTree.insertInTree(rankTree.root, s);
			}
		}catch(Exception e){};
		rankTree.giveRank(rankTree.root, 3);
		char cont='y';
		int choice=0,tempRollNo;
		while(cont=='y' || cont=='Y'){
			menu();
			try{
				choice=sc.nextInt();
			switch(choice){
				case 1:
					Student newStudent=Student.insertStudent();
					if(newStudent!=null){
						rankTree.insertInTree(rankTree.root, newStudent);
						System.out.println("New Student inserted successfully!");
					}
					break;
				case 2:
					System.out.print("Enter the Roll Number of the student to be deleted: ");
					tempRollNo=sc.nextInt();
					rankTree.findRollNo(rankTree.root, tempRollNo, 2);
					System.out.println("Student deleted successfully!");
					break;
				case 3:
					rankTree.rank=0;
					rankTree.giveRank(rankTree.root,3);
					break;
				case 4:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					rankTree.findRollNo(rankTree.root, tempRollNo, 4);
					break;
				case 5:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					rankTree.findRollNo(rankTree.root, tempRollNo, 5);
					rankTree.insertInTree(rankTree.root, change);
					break;
				case 6:
					System.out.print("Enter the Roll Number of the student: ");
					tempRollNo=sc.nextInt();
					rankTree.findRollNo(rankTree.root, tempRollNo, 6);
					break;
				case 7:
					rankTree.rank=0;
					rankTree.giveRank(rankTree.root,7);
					break;
				default:
					System.out.println("You entered a wrong choice");
			}
			}catch(Exception e){
				System.out.println("XXXXX Incorrect Input XXXXX");
				sc.nextLine();
			}
			System.out.print("Do you wish to continue? (Y/N): ");
			cont=sc.next().charAt(0);
		}
	}
}

*/