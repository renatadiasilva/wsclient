package dei.uc.pt.aor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunWSClient {

	public static void main(String[] args) {
		menuMain();
	}

	public static void menuMain() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {
			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - Manage Users");
			System.out.println("2 - Manage Playlists");
			System.out.println("3 - Manage Songs");
			System.out.println("0 - Quit");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				// usar log
				e.printStackTrace();
				stop = true;
				answer = "0";
			}

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			switch (op) {
			case 1: 
				menuUser();
				break;
			case 3: 
				menuPlaylist();
				break;
			case 4: 
				menuSong();
				break;
			case 0: stop = true; break;
			default: stop = false;
			}
		}
	}

	public static void menuUser() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";  // ver

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - Check number of users");
			System.out.println("2 - List all users");
			System.out.println("3 - Check user info (by id or email)");
			System.out.println("4 - Check number of logged users");
			System.out.println("5 - List all logged users");
			System.out.println("6 - Create new user");
			System.out.println("7 - Delete user (by id)");
			System.out.println("8 - Change user pass (by id)");
			System.out.println("0 - Quit");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				// usar log
				e.printStackTrace();
				answer = "100";
			}

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			Long id = 0L;
			String data1 = "";
			String data2 = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: WSConsumerUser.totalUsers(); break;
				case 2: WSConsumerUser.listAllUsers(); break;
				case 3: 
					System.out.print("Insert the user id or email: ");
					while (!done) {
						data1 = reader.readLine();
						if (data1.matches("\\d+")) {
							done = true;
							id = Long.parseLong(data1);
							WSConsumerUser.getUserById(id);
						} else if (data1.matches(".+@.+\\.[a-z]+")) {
							done = true;
							WSConsumerUser.getUserByEmail(data1);
						}
						else System.out.print("Please insert a valid id or email: ");
					}
					break;
				case 4: //WSConsumerUser.totalLoggedUsers(); 
					System.out.println("Not implemented yet"); break;
				case 5: //WSConsumerUser.listLoggedUsers(); break;
					System.out.println("Not implemented yet"); break;
				case 6: 
					System.out.println("Insert the name: ");
					data1 = reader.readLine();
					while (!done) {
						System.out.println("Insert the email: ");
						data2 = reader.readLine();
						if (data2.matches(".+@.+\\.[a-z]+")) {
							done = true;
							WSConsumerUser.createUser(data1, data2);
						} else System.out.println("Please insert a valid email: ");
					}
					break;
				case 7: 
					System.out.println("Insert the user id: ");
					while (!done) {
						data1 = reader.readLine();
						if (data1.matches("\\d+")) {
							done = true;
							id = Long.parseLong(data1);
							System.out.println("Are you sure you want to delete user with id "+data1+" (y/n)?");
							data2 = reader.readLine();
							if ( (data2.charAt(0)=='Y') || (data2.charAt(0)=='y') )
								WSConsumerUser.deleteUser(id);
						} else System.out.println("Please insert a valid id: ");
					}
					break;
				case 8: 
					System.out.println("Insert the user id: ");
					while (!done) {
						data1 = reader.readLine();
						if (data1.matches("\\d+")) {
							done = true;
							id = Long.parseLong(data1);
							System.out.println("Insert new password: ");
							data2 = reader.readLine();
							WSConsumerUser.changeUserPass(id, data2);
						} else System.out.println("Please insert a valid id: ");
					}
					break;
				case 0: stop = true; break;
				default: stop = false;
				}
			} catch (Exception e) {
				// usar log
				e.printStackTrace();
				stop = true;
			}

		}

	}

	public static void menuPlaylist() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";  // ver

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - List users ");
			System.out.println("2 - See user (by id)");
			System.out.println("3 - Change user name (by id)");
			System.out.println("4 - Create new user");
			System.out.println("5 - Delete user (by id)");
			System.out.println("7 - Change user pass (by id)");
			System.out.println("8 - Total users");
			System.out.println("9 - See user (by email)");
			System.out.println("0 - Quit");

			menuMain();

			//			try {
			//				answer = reader.readLine();
			//			} catch (IOException e) {
			//				// usar log
			//				e.printStackTrace();
			//				stop = true;
			//				answer = "0";
			//			}
			//
			//			System.out.println("\n");
			//
			//			int op = 1000;
			//			try {
			//				op = Integer.parseInt(answer);
			//			} catch (Exception e) {
			//				op = 1000;
			//			}
			//			// try catch
			//			switch (op) {
			//			case 1: 
			//				System.out.println("Listing all users...\n\n");
			//				listAllUsers();
			//				break;
			//			case 2: 
			//				System.out.println("Checking user data... \n\n");
			//				System.out.println("Insert the user id: ");
			//				Long id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					getUserById(id);
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 3: 
			//				System.out.println("Editing user name... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					//validacoes
			//					id = Long.parseLong(s);
			//					System.out.println("Insert new user name: ");
			//					String name = reader.readLine();
			//					updateUser(id, name, "");
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 4: 
			//				try {
			//					System.out.println("Creating new user... \n\n");
			//					System.out.println("Insert the name: ");
			//					String name = reader.readLine();
			//					System.out.println("Insert the email: ");
			//					String email = reader.readLine();
			//					createUser(name, email);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 5: 
			//				System.out.println("Deleting user... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					//are you sure?
			//					deleteUser(id);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 7: 
			//				System.out.println("Editing user password... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					//validacoes
			//					System.out.println("Insert the new pass: ");
			//					String pass = reader.readLine();
			//					// confirm pass??
			//					updateUser(id, "", pass);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 8:
			//				System.out.print("Total number of users: ");
			//				totalUsers();
			//				break;
			//			case 9:
			//				System.out.println("Checking user data... \n\n");
			//				System.out.println("Insert the user email: ");
			//				String email = "";
			//				try {
			//					email = reader.readLine();
			//					getUserByEmail(email);
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 0: stop = true; break;
			//			default: stop = false;
			//			}

		}

	}

	public static void menuSong() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";  // ver

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - List users ");
			System.out.println("2 - See user (by id)");
			System.out.println("3 - Change user name (by id)");
			System.out.println("4 - Create new user");
			System.out.println("5 - Delete user (by id)");
			System.out.println("7 - Change user pass (by id)");
			System.out.println("8 - Total users");
			System.out.println("9 - See user (by email)");
			System.out.println("0 - Quit");

			menuMain();

			//			try {
			//				answer = reader.readLine();
			//			} catch (IOException e) {
			//				// usar log
			//				e.printStackTrace();
			//				stop = true;
			//				answer = "0";
			//			}
			//
			//			System.out.println("\n");
			//
			//			int op = 1000;
			//			try {
			//				op = Integer.parseInt(answer);
			//			} catch (Exception e) {
			//				op = 1000;
			//			}
			//			// try catch
			//			switch (op) {
			//			case 1: 
			//				System.out.println("Listing all users...\n\n");
			//				listAllUsers();
			//				break;
			//			case 2: 
			//				System.out.println("Checking user data... \n\n");
			//				System.out.println("Insert the user id: ");
			//				Long id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					getUserById(id);
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 3: 
			//				System.out.println("Editing user name... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					//validacoes
			//					id = Long.parseLong(s);
			//					System.out.println("Insert new user name: ");
			//					String name = reader.readLine();
			//					updateUser(id, name, "");
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 4: 
			//				try {
			//					System.out.println("Creating new user... \n\n");
			//					System.out.println("Insert the name: ");
			//					String name = reader.readLine();
			//					System.out.println("Insert the email: ");
			//					String email = reader.readLine();
			//					createUser(name, email);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 5: 
			//				System.out.println("Deleting user... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					//are you sure?
			//					deleteUser(id);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 7: 
			//				System.out.println("Editing user password... \n\n");
			//				System.out.println("Insert the user id: ");
			//				id = 0L;
			//				try {
			//					String s = reader.readLine();
			//					id = Long.parseLong(s);
			//					//validacoes
			//					System.out.println("Insert the new pass: ");
			//					String pass = reader.readLine();
			//					// confirm pass??
			//					updateUser(id, "", pass);
			//				} catch (IOException e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 8:
			//				System.out.print("Total number of users: ");
			//				totalUsers();
			//				break;
			//			case 9:
			//				System.out.println("Checking user data... \n\n");
			//				System.out.println("Insert the user email: ");
			//				String email = "";
			//				try {
			//					email = reader.readLine();
			//					getUserByEmail(email);
			//				} catch (Exception e) {
			//					// usar log
			//					e.printStackTrace();
			//					stop = true;
			//					answer = "0";
			//				}
			//				break;
			//			case 0: stop = true; break;
			//			default: stop = false;
			//			}
			//
		}

	}

}
