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
		String answer = "1";

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - Total number of users");
			System.out.println("2 - List all users");
			System.out.println("3 - Consult user info");
			System.out.println("4 - Total number of logged users");
			System.out.println("5 - List all logged users");
			System.out.println("6 - Create new user");
			System.out.println("7 - Delete user");
			System.out.println("8 - Change user pass");
			System.out.println("0 - Go back");

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

			String data1 = "";
			String data2 = "";
			String WSpath = "";
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
							WSpath = "/id";
						} else if (data1.matches(".+@.+\\.[a-z]+")) {
							done = true;
							WSpath = "/email";
						}
						else System.out.print("Please insert a valid id or email: ");
					}
					WSConsumerUser.userInfo(data1, WSpath);
					break;
				case 4: //WSConsumerUser.totalLoggedUsers(); 
					System.out.println("Not implemented yet"); break;
				case 5: //WSConsumerUser.listLoggedUsers(); break;
					System.out.println("Not implemented yet"); break;
				case 6: 
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
				case 7: 
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
					break;
				case 8: 
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
		String answer = "1";

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - Total number of playlists");
			System.out.println("2 - List all playlists");
			System.out.println("3 - List playlists of user");
			System.out.println("4 - Add/Remove songs to playlist");
			System.out.println("0 - Go back");

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

			Long pid = 0L;
			String data = "";
			String WSpath = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: WSConsumerPlaylist.totalPlaylists(); break;
				case 2: WSConsumerPlaylist.listAllPlaylists(); break;
				case 3: 
					System.out.print("Insert the user id or email: ");
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
					WSConsumerPlaylist.playlistsOfUser(data, WSpath);
					break;
				case 4: 
					//list all playlists, choose one
					WSConsumerPlaylist.listAllPlaylists();
					System.out.print("Insert the playlist id to add/remove songs: ");
					while (!done) {
						data = reader.readLine();
						if (data.matches("\\d+")) {
							done = true;
							pid = Long.parseLong(data);
						}
						else System.out.print("Please insert a valid id: ");
					}
					menuPlaylist(pid);
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
	
	public static void menuPlaylist(Long pid) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - List songs of playlist "+ pid);
			System.out.println("2 - Add song to playlist "+ pid);
			System.out.println("3 - Remove song from playlist "+ pid);
			System.out.println("0 - Go back");

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

			Long sid = 0L;
			String data = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: WSConsumerSong.songsOfPlaylist(pid); break;
				case 2:
					//list all songs, choose one to add
					WSConsumerSong.listAllSongs();
					System.out.print("Insert the song id to add: ");
					while (!done) {
						data = reader.readLine();
						if (data.matches("\\d+")) {
							done = true;
							sid = Long.parseLong(data);
						}
						else System.out.print("Please insert a valid id: ");
					}
					WSConsumerPlaylist.updateSongPlaylist(sid, pid, "/addsongtoplaylist");				
					WSConsumerSong.songsOfPlaylist(pid);
					break;
				case 3: 
					//list all songs of playlist, choose one to remove
					WSConsumerSong.songsOfPlaylist(pid);
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

	public static void menuSong() {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - Total number of songs");
			System.out.println("2 - List all songs");
			System.out.println("3 - Consult song info");
			System.out.println("4 - Delete songs of user");
			System.out.println("0 - Go back");

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
			String data = "";
			String WSpath = ""; //?
			boolean done = false;

			try {
				switch (op) {
				case 1: WSConsumerSong.totalSongs(); break;
				case 2: WSConsumerSong.listAllSongs(); break;
				case 3:
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
					break;
				case 4: 
					//list all users and choose one
					WSConsumerUser.listAllUsers();
					System.out.print("Insert the user id to remove songs: ");
					System.out.print("Insert the user id or email: ");
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
					menuUser(data, WSpath);
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

	public static void menuUser(String idemail, String WSpath) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - List songs of user "+ idemail);
			System.out.println("2 - Delete song of user "+ idemail);
			System.out.println("0 - Go back");

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

			Long sid = 0L;
			String data = "";
			boolean done = false;

			try {
				switch (op) {
				case 1: WSConsumerSong.songsOfUser(idemail, WSpath); break;
				case 2:
					//list all songs of user, choose one to remove
					WSConsumerSong.songsOfUser(idemail, WSpath);
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
					//deletesongfor good?
					WSConsumerSong.deleteSong(sid);
					WSConsumerSong.songsOfUser(idemail, WSpath);
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

}
