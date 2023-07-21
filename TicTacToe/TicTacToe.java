//Game of Tic-Tac-Toe
import java.util.*;

class TicTacToe{
	static char arr[][]=new char[3][3];
	static int count=0;
	static int score1=0, score2=0; 
	
	static int insert(int p){
		Scanner sc = new Scanner(System.in);
		int b=sc.nextInt();
		int r=(b-1)/3;
		int c=(b-1)%3;
		if(p==0 && arr[r][c]!='O' && arr[r][c]!='X'){
			arr[r][c]='O';
			count++;
			return 0;
		}
		else if (p==1 && arr[r][c]!='O' && arr[r][c]!='X'){
			arr[r][c]='X';
			count++;
			return 0;
		}		
		return -1;
	}
	
	static boolean check(){
		while(count>=5){
		for(int i=0; i<3; i++){
			if(arr[i][1]== arr[i][2] && arr[i][2]==arr[i][0]){
				winner(i,1);
				return true;
			}else if(arr[1][i]== arr[2][i] && arr[0][i]==arr[2][i]){
				winner(1, i);
				return true;
			}
		}
		if(arr[1][1]==arr[2][2] && arr[2][2]==arr[0][0]){
			winner(1,1);
			return true;
		}else if(arr[0][2]==arr[1][1] && arr[1][1]==arr[2][0]){
			winner(1,1);
			return true;
		}else
			return false;
		}return false;
	}
	
	static void display(){
		int x=1;
		for (int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				System.out.printf("  %d[%c]",x++, arr[i][j]);
			}
			System.out.println();
		}
	}
	
	static void winner(int i, int j){
		if(arr[i][j]=='X'){
			System.out.println("\nPlayer 2 is the WINNER");
			score2++;
		}
		else{
			System.out.println("\nPlayer 1 is the WINNER");
			score1++;
		}
		showScore();
	} 
	
	static void showScore(){
		System.out.println("==============================================");
		System.out.println("Score of Player 1: "+score1+"\t Score of Player 2: "+score2);
		System.out.println("==============================================\n");
	}
	
	static void initialise(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				arr[i][j]=' ';
			}
		}
	}
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in); 
		char choice;
		do{
			int i=0, done=0;
			count=0;
			initialise();
			display();
			do{
				do{try{
					System.out.print("\nPlayer "+(i%2+1)+", enter box number: ");
					done=insert(i%2);
					display();
					}catch(ArrayIndexOutOfBoundsException e){done=-1;}
				}while(done==-1);
				i++;
				if(i==9){
					System.out.println("Game Draw!!!");
					break;
				}
			}while(!check());
			
			System.out.print("Do you wish to continue? (Y/N): ");
			choice=sc.next().charAt(0);
		}while(choice=='y' || choice== 'Y');
		if(score1>score2)
			System.out.print("********Player 1 is the FINAL WINNER*********");
		else if(score1<score2)
			System.out.print("********Player 2 is the FINAL WINNER*********");
		else if(score1==score2)
			System.out.print("********THE SERIES IS A DRAW*********");
	}
}