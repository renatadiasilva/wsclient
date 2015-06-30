package dei.uc.pt.aor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dei.uc.pt.aor.wsconsumer.WSConsumerPlaylist;
import dei.uc.pt.aor.wsconsumer.WSConsumerSong;
import dei.uc.pt.aor.wsconsumer.WSConsumerUser;

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
			System.out.println("\n***** MAIN MENU *****");
			System.out.println("1 - Manage Users");
			System.out.println("2 - Manage Playlists");
			System.out.println("3 - Manage Songs");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				break;
			
			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			switch (op) {
			case 1: // Manage users
				stop = menuUser();
				break;
			case 2: // Manage playlists
				stop = menuPlaylist();
				break;
			case 3: // Manage songs
				stop = menuSong();
				break;
			default: // Repeat menu
				stop = false;
			}
		}

		// Exit application
		System.out.println("Exiting...");
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println("Error closing BufferedReader.");
		}
		System.out.println("Goodbye!\n");
		
	}

	public static boolean menuUser() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n***** USERS MENU *****");
			System.out.println("1 - Total number of users");
			System.out.println("2 - List all users");
			System.out.println("3 - Consult user info");
			System.out.println("4 - Total number of logged users");
			System.out.println("5 - List all logged users");
			System.out.println("6 - Create new user");
			System.out.println("7 - Delete user");
			System.out.println("8 - Change user pass");
			System.out.println("G - Go back");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			// Go back
			if ( (answer.charAt(0)=='g') || (answer.charAt(0)=='G') )
				return false;
			
			// Quit
			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				return true;

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			String data1 = "";
			String data2 = "";
			String WSpath = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: // Total number of users
					int total = WSConsumerUser.totalUsers();
					if (total >= 0) {
						System.out.print("Total number of users: ");
						System.out.println(total);
					}
					break;
				case 2: // List all users
					WSConsumerUser.listAllUsers();
					break;
				case 3: // Consult user info
					total = WSConsumerUser.totalUsers();
					if (total > 0) {
						//list all users, choose one
						System.out.print("Insert the user id or email: ");
						while (!done) {
							data1 = reader.readLine();
							if (data1.matches("\\d+")) {
								done = true;
								WSpath = "/id";
							} else if (data1.matches(".+@.+\\.[a-z]+")) {
								done = true;
								WSpath = "/email";
							}
							else System.out.print("Please insert a valid id or email: ");
						}
						WSConsumerUser.userInfo(data1, WSpath);
					} else if (total == 0) System.out.println("***** There are no users *****");
					break;
				case 4: // Total number of logged users //check first
					WSConsumerUser.totalLoggedUsers(); break;
				case 5: // List all logged users
					WSConsumerUser.listAllLoggedUsers(); break;
				case 6: // Create new user
					System.out.print("Insert the name: ");
					data1 = reader.readLine();
					while (!done) {
						System.out.print("Insert the email: ");
						data2 = reader.readLine();
						if (data2.matches(".+@.+\\.[a-z]+")) {
							done = true;
							WSConsumerUser.createUser(data1, data2);
						} else System.out.println("Please insert a valid email: ");
					}
					break;
				case 7: // Delete user
					total = WSConsumerUser.totalUsers();
					if (total > 0) {
						//list all users, choose one
						System.out.print("Insert the user id or email: ");
						while (!done) {
							data1 = reader.readLine();
							if (data1.matches("\\d+")) {
								done = true;
								WSpath = "/id";
							} else if (data1.matches(".+@.+\\.[a-z]+")) {
								done = true;
								WSpath = "/email";
							}
							else System.out.print("Please insert a valid id or email: ");
						}
						System.out.println("Are you sure you want to delete user with id "+data1+" (y/n)?");
						data2 = reader.readLine();
						if ( (data2.charAt(0)=='Y') || (data2.charAt(0)=='y') )
							WSConsumerUser.deleteUser(data1, WSpath);
					} else if (total == 0) System.out.println("***** There are no users *****");
					break;
				case 8: // Change user pass
					total = WSConsumerUser.totalUsers();
					if (total > 0) {
						System.out.print("Insert the user id or email: ");
						while (!done) {
							data1 = reader.readLine();
							if (data1.matches("\\d+")) {
								done = true;
								WSpath = "/id";
							} else if (data1.matches(".+@.+\\.[a-z]+")) {
								done = true;
								WSpath = "/email";
							}
							else System.out.print("Please insert a valid id or email: ");
						}
						System.out.println("Insert new password: ");
						data2 = reader.readLine();
						WSConsumerUser.changeUserPass(data1, data2, WSpath);
					} else if (total == 0) System.out.println("***** There are no users *****");
					break;
				default: // Repeat menu
					stop = false;
				}
			} catch (Exception e) {
				System.out.println("Error reading input.");
				stop = true;
			}

		}
		
		return true;

	}

	public static boolean menuPlaylist() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n***** PLAYLISTS MENU *****");
			System.out.println("1 - Total number of playlists");
			System.out.println("2 - List all playlists");
			System.out.println("3 - List playlists of user");
			System.out.println("4 - Add/Remove songs to playlist");
			System.out.println("G - Go back");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			// Go back
			if ( (answer.charAt(0)=='g') || (answer.charAt(0)=='G') )
				return false;
			
			// Quit
			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				return true;

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			Long pid = 0L;
			String data = "";
			String WSpath = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: // Total number of playlists
					int total =  WSConsumerPlaylist.totalPlaylists();
					if (total >= 0) {
						System.out.print("Total number of playlists: ");
						System.out.println(total);
					}
					break;
				case 2: // List all playlists
					WSConsumerPlaylist.listAllPlaylists();
					break;
				case 3: // List playlists of user
					total = WSConsumerUser.totalUsers();
					if (total > 0) {
						if (WSConsumerUser.listAllUsers()) {
							//list all users and choose one
							System.out.print("Insert the user id or email to show playlists: ");
							while (!done) {
								data = reader.readLine();
								if (data.matches("\\d+")) {
									done = true;
									WSpath = "/id";
								} else if (data.matches(".+@.+\\.[a-z]+")) {
									done = true;
									WSpath = "/email";
								}
								else System.out.print("Please insert a valid id or email: ");
							}
							if (WSConsumerUser.userInfo(data, WSpath))
								WSConsumerPlaylist.playlistsOfUser(data, WSpath);
						}
					} else if (total == 0) System.out.println("***** There are no users *****");
					break;
				case 4: // Add/Remove songs to playlist
					total = WSConsumerPlaylist.totalPlaylists();
					if (total > 0) {
						//list all playlists, choose one
						if (WSConsumerPlaylist.listAllPlaylists()) {
							System.out.print("Insert the playlist id to add/remove songs: ");
							while (!done) {
								data = reader.readLine();
								if (data.matches("\\d+")) {
									done = true;
									pid = Long.parseLong(data);
								}
								else System.out.print("Please insert a valid id: ");
							}
							if (WSConsumerPlaylist.playlistInfo(pid))
								stop = menuPlaylist(pid);
						}
					} else if (total == 0) System.out.println("***** There are no playlists *****");
					break;
				default: // Repeat menu
					stop = false;
				}
			} catch (Exception e) {
				System.out.println("Error reading input.");
				stop = true;
			}

		}
		
		return true;

	}

	public static boolean menuPlaylist(Long pid) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n***** PLAYLIST ("+pid+") MENU *****");
			System.out.println("1 - List songs of playlist "+ pid);
			System.out.println("2 - Add song to playlist "+ pid);
			System.out.println("3 - Remove song from playlist "+ pid);
			System.out.println("G - Go back");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			// Go back
			if ( (answer.charAt(0)=='g') || (answer.charAt(0)=='G') )
				return false;
			
			// Quit
			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				return true;

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			Long sid = 0L;
			String data = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: // List songs of playlist
					WSConsumerSong.songsOfPlaylist(pid);
					break;
				case 2: // Add song to playlist
					int total = WSConsumerSong.totalSongs();
					if (total > 0) {
						//list all songs, choose one to add
						if (WSConsumerSong.listAllSongs()) {
							System.out.print("Insert the song id to add: ");
							while (!done) {
								data = reader.readLine();
								if (data.matches("\\d+")) {
									done = true;
									sid = Long.parseLong(data);
								}
								else System.out.print("Please insert a valid id: ");
							}
							if (WSConsumerSong.songInfo(sid)) {
								WSConsumerPlaylist.updateSongPlaylist(sid, pid, "/addsongtoplaylist");				
								WSConsumerSong.songsOfPlaylist(pid);
							}
						}
					} else if (total == 0) System.out.println("***** There are no songs *****");
					break;
				case 3: // Remove song from playlist
					total = WSConsumerSong.totalSongsOfPlaylist(pid);
					if (total > 0) {
						//list all songs of playlist, choose one to remove
						if (WSConsumerSong.songsOfPlaylist(pid)) {
							System.out.print("Insert the song id to remove: ");
							while (!done) {
								data = reader.readLine();
								if (data.matches("\\d+")) {
									done = true;
									sid = Long.parseLong(data);
								}
								else System.out.print("Please insert a valid id: ");
							}
							WSConsumerPlaylist.updateSongPlaylist(sid, pid, "/removesongfromplaylist");				
							WSConsumerSong.songsOfPlaylist(pid);
						}
					} else if (total == 0) System.out.println("***** There are no songs in the playlist *****");
					break;
				default: // Repeat menu
					stop = false;
				}
			} catch (Exception e) {
				System.out.println("Error reading input.");
				stop = true;
			}

		}
		
		return true;

	}

	public static boolean menuSong() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n***** SONGS MENU *****");
			System.out.println("1 - Total number of songs");
			System.out.println("2 - List all songs");
			System.out.println("3 - Consult song info");
			System.out.println("4 - Delete songs of user");
			System.out.println("G - Go back");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			// Go back
			if ( (answer.charAt(0)=='g') || (answer.charAt(0)=='G') )
				return false;
			
			// Quit
			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				return true;

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			Long id = 0L;
			String data = "";
			String WSpath = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: // Total number of songs
					int total = WSConsumerSong.totalSongs();
					if (total >= 0) {
						System.out.print("Total number of songs: ");
						System.out.println(total);
					}
					break;
				case 2: // List all songs
					WSConsumerSong.listAllSongs(); break;
				case 3: // Consult song info
					total = WSConsumerSong.totalSongs();
					if (total > 0) {
						System.out.print("Insert the song id: ");
						while (!done) {
							data = reader.readLine();
							if (data.matches("\\d+")) {
								done = true;
								id = Long.parseLong(data);
							}
							else System.out.print("Please insert a valid id: ");
						}
						WSConsumerSong.songInfo(id);
					} else if (total == 0) System.out.println("***** There are no songs *****");
					break;
				case 4: // Delete songs of user
					total = WSConsumerUser.totalUsers();
					if (total > 0) {
						//list all users and choose one
						if (WSConsumerUser.listAllUsers()) {
							System.out.print("Insert the user id/email to remove songs: ");
							while (!done) {
								data = reader.readLine();
								if (data.matches("\\d+")) {
									done = true;
									WSpath = "/id";
								} else if (data.matches(".+@.+\\.[a-z]+")) {
									done = true;
									WSpath = "/email";
								}
								else System.out.print("Please insert a valid id or email: ");
							}

							if (WSConsumerUser.userInfo(data, WSpath)) {
								int totals = WSConsumerSong.totalSongsOfUser(data, WSpath);
								if (totals > 0)
									stop = menuUser(data, WSpath);
								else if (totals == 0) System.out.println("***** The user has no songs *****");
							}
						}
					} else if (total == 0) System.out.println("***** There are no users *****");
					break;
				default: // Repeat menu
					stop = false;
				}
			} catch (Exception e) {
				System.out.println("Error reading input.");
				stop = true;
			}

		}
		
		return true;

	}

	public static boolean menuUser(String idemail, String WSpath) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n***** USER ("+idemail+") MENU *****");
			System.out.println("1 - List songs of user "+ idemail);
			System.out.println("2 - Delete song of user "+ idemail);
			System.out.println("G - Go back");
			System.out.println("Q - Quit");
			System.out.print("\nChoose an option: ");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error reading input.");
				answer = "100";
			}

			// Go back
			if ( (answer.charAt(0)=='g') || (answer.charAt(0)=='G') )
				return false;
			
			// Quit
			if ( (answer.charAt(0)=='q') || (answer.charAt(0)=='Q') )
				return true;

			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}

			Long sid = 0L;
			String data = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: // List songs of user
					WSConsumerSong.songsOfUser(idemail, WSpath); break;
				case 2: // Delete song of user
					//list all songs of user, choose one to remove
					if (WSConsumerSong.songsOfUser(idemail, WSpath)) {
						//choose song to delete
						System.out.print("Insert the song id to delete: ");
						while (!done) {
							data = reader.readLine();
							if (data.matches("\\d+")) {
								done = true;
								sid = Long.parseLong(data);
							}
							else System.out.print("Please insert a valid id: ");
						}
						if (WSConsumerSong.deleteSong(sid, idemail, WSpath))
							WSConsumerSong.songsOfUser(idemail, WSpath);
					}
					break;
				default: // Repeat menu
					stop = false;
				}
			} catch (Exception e) {
				System.out.println("Error reading input.");
				stop = true;
			}

		}
		
		return true;

	}

}