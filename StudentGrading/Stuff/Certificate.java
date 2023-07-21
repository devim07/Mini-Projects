 
 
 
 class Certificate{

		String name="Aniket Pattiwar1";
		int roll=2,eng=98,sci=89,math=97,sst=90,rank=3;
		long barcode=500000;
		int total=eng+sci+math+sst;
		double percent=(double)total/4;
		

void printcert(){
	System.out.println("\t\t--------------------------------------------------");
	System.out.println("\t\t|	\tCERTIFICATE OF MERIT	 \t |");
	System.out.println("\t\t|	\t====================	 \t |");
	System.out.println("\t\t|\t      Year of Passing April2022\t\t |");
	System.out.println("\t\t| Name:"+name+"\t"+"\t     barcode No.:|");
	System.out.println("\t\t| School:CDAC-Kharghar\t\t 	"+barcode+"   |");
	System.out.println("\t\t| Roll No.: "+roll+"\t\t\t\t\t |");
	System.out.println("\t\t|\t\t\t\t\t\t |");
	System.out.println("\t\t|\t\t\t\t\t\t |");
	System.out.println("\t\t|=============================================== |");
	System.out.println("\t\t|   	Subject\t\t|  Marks | Out of\t |");
	System.out.println("\t\t|=============================================== |");
	System.out.println("\t\t| English\t\t|  "+eng+"\t |  100		 |");
	System.out.println("\t\t| Maths\t\t\t|  "+math+"\t |  100\t	 |");
	System.out.println("\t\t| Science\t\t|  "+sci+"\t |  100	\t |");
	System.out.println("\t\t| Social-Science\t|  "+sst+"\t |  100\t	 |");
	System.out.println("\t\t|=============================================== |");
	System.out.println("\t\t| Total\t\t\t|  "+total+"\t |  400	\t |");
	System.out.println("\t\t|=============================================== |");
	System.out.println("\t\t| Percentage: "+percent+"%	\tRank: "+rank+"\t\t |");
	System.out.println("\t\t|\t\t\t\t\t\t |");
	System.out.println("\t\t|     Authorised by\t\t\t\t |");
	System.out.println("\t\t|  Government of India\t\tPrincipal 	 |");
	System.out.println("\t\t|\t\t\t\t\t\t |");
	System.out.println("\t\t-------------------------------------------------- ");
	
	}
			
		public static void main(String []args){
		
						Certificate t1=new Certificate();
						t1.printcert();
	}
	}