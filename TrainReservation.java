package application2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TrainReservation 
{
	public static String encrypt(String s)
	{
		String res="";
		
		for(int i=0;i<s.length();i++)
		{
			res+=(char)(((int)s.charAt(i))+1);
		}
		return res;
	}

	public static void main(String[] args) 
	{
		
		List<Passenger> passenger = new ArrayList<Passenger>();
		
		List<Compartment> comList = new ArrayList<Compartment>();//compartment List
		
		comList.add(new Compartment("AC"));     //Adding compartment
		comList.add(new Compartment("AC"));
		comList.add(new Compartment("NonAC"));
		comList.add(new Compartment("NonAC"));
		comList.add(new Compartment("NonAC"));
		
		
		List<User> userList = new ArrayList<User>();//UserList

		Scanner sc = new Scanner(System.in);		
		int choice;
		do
		{
			System.out.println("1.Register");
			System.out.println("2.Login");
			System.out.println("0.Exit");
			
			System.out.println("Enter your choice:");
			choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:
				System.out.println("New User Registration");
				System.out.print("Enter user name:");
				String name = sc.next();
				
				System.out.print("Enter new password:");
				String pass = sc.next();
				String password = encrypt(pass);
				userList.add(new User(name,password));
				
				System.out.println("Account created successfully...");
				break;
				
			case 2:
				System.out.println("---Log In---");
				
				System.out.print("Enter user name:");
				String usrname = sc.next();
				
				System.out.print("Enter password:");
				String lPass = sc.next();
				String epass = encrypt(lPass);
				
				boolean userFound = false;
				for(User u : userList)
				{
					if(u.name.equals(usrname))
					{
						userFound = true;
						if(u.password.equals(epass))
						{
							System.out.println("Logged In successfully...");
							int c;
							do
							{
								System.out.println("1.Display No of seats avaliable");
								System.out.println("2.Book Seats");
								System.out.println("0.Exit");
								System.out.print("Enter your choice:");
								c = sc.nextInt();
								switch(c)
								{
								case 1:
									int cnt = 0;
									int acSeatCount = 0;
									int nonAcSeatCount = 0;
									for(Compartment com:comList)
									{
										System.out.println("Compartment "+ ++cnt);
										System.out.println("Available seats : "+com.noOfFreeSeats);
										System.out.println("Booked Seats in Compartment : "+com.noOfPassengers);
										System.out.println("Compartment type: "+com.coachName);
										if(com.coachName.equalsIgnoreCase("AC"))
										{
											acSeatCount += com.noOfFreeSeats;
										}
										else
										{
											nonAcSeatCount += com.noOfFreeSeats;
										}
									}
									
									int total = acSeatCount+nonAcSeatCount;
									System.out.println("Total avaliable AC seats: "+ acSeatCount);
									System.out.println("Total avaliable Non AC seats: "+ nonAcSeatCount);
									System.out.println("Total no of avaliable seats: "+ total);
									break;
									
								case 2:
									//Booking seats
									
									System.out.print("Enter number of seats:");
									int reqSeat = sc.nextInt();
									int acSeat = 0;
									int nonAcSeat = 0;
									int totalAvlSeat = 0;
									for(Compartment com : comList)
									{
										if(com.coachName.equalsIgnoreCase("AC"))
										{
											acSeat += com.noOfFreeSeats;
										}
										else
										{
											nonAcSeat += com.noOfFreeSeats;
										}
										
									}
									totalAvlSeat = acSeat + nonAcSeat;
									boolean booked = false;
									int totalFair = 0;
									
									if(reqSeat <= totalAvlSeat)
									{
										for(int i = 0;i < reqSeat;i++)
										{
											System.out.print("Enter passenger name:");
											String psgName = sc .next();
											System.out.print("Enter gender:");
											String psgGender = sc.next();
											System.out.print("Enter coach(AC/NonAC):");
											String coach = sc.next();
											for(Compartment com:comList)
											{
											
												if(coach.equalsIgnoreCase(com.coachName))
												{
													if(com.coachName.equalsIgnoreCase("AC"))
													{
														if(acSeat>0)
														{
															if(com.noOfFreeSeats>0)
															{
																com.noOfFreeSeats--;
																com.noOfPassengers++;
																acSeat--;
																com.seat[com.b++]=1;
																passenger.add(new Passenger(psgName,psgGender,coach));
																totalFair += com.fair;
																booked = true;
																
																break;
															}
														}
														else
														{
															System.out.println("There are no seats in AC.");// ");
															if(nonAcSeat>0)
															{
																System.out.println("Select seats from non AC");
																System.out.println("Enter coach(AC/Non AC):");
																coach = sc.next();
															}
															else
															{
																System.out.println("There are no seats  in Non AC");
																break;
															}
															
														}
													}
													else //if(com.coachName.equalsIgnoreCase("Non AC"))
													{
														if(nonAcSeat>0)
														{
															if(com.noOfFreeSeats>0)
															{
																com.noOfPassengers++;
																com.noOfFreeSeats--;
																nonAcSeat--;
																com.seat[com.b++]=1;
																totalFair += com.fair;
																booked = true;
																passenger.add(new Passenger(psgName,psgGender,coach));
																break;
															}
														}
														else
														{
															System.out.print("There are no seats in Non AC.");
															if(acSeat>0)
															{
																System.out.println("Select seats from AC.");
																System.out.print("Enter your choice(AC/Non AC):");
																coach = sc.next();
															}
															else
															{
																System.out.println("No seats avaliable in AC:(");
																break;
															}
														}
													}
													
												}
												/*else
												{
													System.out.println("No coaches found");
													break;
												}*/
											}
											if(!booked)
											{
												System.out.println("No seats Avaliable");
												break;
											}
										}
										if(booked)
										{
											System.out.println("Total cost: " + totalFair);
										}
									}
									else
									{
										System.out.println("Not enough seats found...");
									}
									break;
								case 0:
									System.out.println("Exiting--->");
									break;
									
								default:
									System.out.println("Invalid Option");
									break;
								}
							}while(c != 0);
							System.out.println("Logged out->>>");
							
						}
						else 
						{
							System.out.println("Invalid password...");
							break;
						}
					}
				}
				if(!userFound)
				{
					System.out.println("Invalid username");
				}
				break;
			case 0:
				System.out.println("Exiting-->");
				break;
			default:
				System.out.println("Invalid option");
			}
			
		}while(choice != 0);
		System.out.println("(:***Thank You***:)");

		sc.close();
	}

}

class Passenger
{
	String name;
	String gender;
	String pCoach;
	public Passenger(String name,String gender,String pCoach)
	{
		this.name = name;
		this.gender = gender;
		this.pCoach = pCoach;
	}
}

class Compartment
{
	String coachName;
	int noOfPassengers;
	int noOfFreeSeats;
	static int[] seat = new int[60];
	int b=0;
	//int acFair = 500;
	//int nonACFair = 250;
	int fair;
	public Compartment(String coachName)
	{
		noOfPassengers=0;
		noOfFreeSeats=60;
		this.coachName=coachName;
		if(this.coachName.equalsIgnoreCase("AC"))
		{
			fair = 500;
		}
		else
		{
			fair = 250;
		}
		
	}
		
}



class User
{
	static final AtomicInteger id = new AtomicInteger(0); ;
	String name;
	String password;
	String gender;
	int usrid;
	//String coach;
	
	public User(String name,String password)
	{
		usrid = id.incrementAndGet();
		this.name = name;
		this.password = password;
		//this.gender = gender;
	}
}
