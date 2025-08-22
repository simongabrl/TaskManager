package JavaProject1;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		menu(scan, tasks);
		
		scan.close();
		
	}
	
	public static void menu(Scanner scan, ArrayList<Task> tasks) {
		
		boolean menu = true;
		int choice = 0;
		
		do {
			
			System.out.println("\n===============================================");
			System.out.println("		TASK MANAGER							");
			System.out.println("===============================================");
			System.out.println("[1] Add New Task");
			System.out.println("[2] View Tasks");
			System.out.println("[3] Mark Task as Completed");
			System.out.println("[4] Delete Task");
			System.out.println("[5] Search Task");
			System.out.println("[6] Save Tasks");
			System.out.println("[7] Load Tasks");
			System.out.println("[8] Exit");
			
			
			while (true) {
			
				System.out.print("Enter choice: ");
				
				if (scan.hasNextInt()) {
					
					choice = scan.nextInt();
					scan.nextLine();
					
					if (choice > 0 && choice <= 8) {
						
						break;
						
					} else {
						
						System.out.println("Please select a number between 1-6 Only.");
						
					}
					
					
				} else {
					
					System.out.println("Please select a number between 1-5 Only.");
					scan.nextLine();
					
				}
			
			} 
			
			switch (choice) {
			
			case 1:
				
				addTask(scan, tasks);
				
			break;
			
			case 2:

				if (!checkTasks(tasks)) {
					
					System.out.println("There are no tasks at the moment.");
					
				} else {
				
					chooseSort(scan, tasks);
					
				}
				
			break;
			
			case 3: 
				
				markDone(scan, tasks);
				
			break;
			
			case 4:
				
				deleteTask(scan, tasks);
			
			break;
			
			case 5:
				
				searchTask(scan, tasks);
				
			break;
			
			case 6:
				
				saveTasks(tasks);
				
			break;
			
			case 7:
				
				loadTasks(tasks);
				
			break;
			
			case 8:	
				
				menu = false;
				
				
			break;
			
			default:
				
				System.out.println("Please select a number between 1-6 Only");
			
			break;
			}
			
		} while (menu);
		
		
		System.out.println("Thank you, and Goodbye!");
		
	}

	public static void addTask(Scanner scan, ArrayList<Task> tasks) {
		
		String taskName;
		String category;
		LocalDate dueDate = LocalDate.MIN;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate today = LocalDate.now();		
		
		
		while (true) {
			
			System.out.print("Enter category (Work/Personal/etc.): ");
			category = scan.nextLine();
			
			if (category.matches("[a-zA-Z0-9 ]+")) {
				
				break;
				
			} else {
				
				System.out.println("Letters and Spaces Only.");
				
			}
		
		}
		
		while (true) {
		
			System.out.print("Enter task: ");
			taskName = scan.nextLine();
			
			if (taskName.matches("[a-zA-Z ]+")) {
				
				break;
				
			} else {
				
				System.out.println("No special characters.");
				
			}
		
		}
		
		while (true) {
			
			System.out.print("Enter Due Date (MM-dd-yyyy): ");
			String dateInput = scan.nextLine();
			try {
			dueDate = LocalDate.parse(dateInput, format); }
			catch(DateTimeParseException e) {
				System.out.println("Invalid Date Format Follow MM-dd-yyyy");
			}
			
			if (!dueDate.isBefore(today)) {
				
				
				break;
				
			} else {
				
				System.out.println("Please enter current or future date");
				
			}
		
		}
		
		while (true) {
			
			System.out.println("1. LOW");
			System.out.println("2. MEDIUM");
			System.out.println("3. HIGH");
			System.out.print("Enter Priority Level: ");
			
			if (scan.hasNextInt()) {
				
				int choice = scan.nextInt();
				scan.nextLine();
				
				if (choice > 0 && choice <= 6) {
					
					Priority level = priorityLevel(scan, tasks, choice);
					tasks.add(new Task(taskName, category, dueDate, level));
					break;
					
				} else {
					
					System.out.println("Please select a number between 1-3 Only.");
					
				}
				
				
			} else {
				
				System.out.println("Please select a number between 1-3 Only.");
				scan.nextLine();
				
			}
		
		}
		
	}
	
	public static Priority priorityLevel(Scanner scan, ArrayList<Task> tasks, int choice) {
		
		
		switch (choice) {
		
		case 1:
			
			return Priority.LOW;
			
		case 2:
			
			return Priority.MEDIUM;
			
		case 3:
	
			return Priority.HIGH;
		
		default:
			
			return null;
		
		}
		
		
	}
	
	public static void chooseSort(Scanner scan, ArrayList<Task> tasks) {
		
		int choice = 0;
		
		while (true) {
		
			System.out.println("\n[1] Show as entered");
			System.out.println("[2] Show sorted by title");
			System.out.println("[3] Show sorted by deadline");
			System.out.println("[4] Show Ongoing Tasks");
			System.out.println("[5] Show Completed Tasks");
			System.out.print("Choose between 1-5: ");
			
			if (scan.hasNextInt()) {
				
				choice = scan.nextInt();
				scan.nextLine();
				
				if (choice >= 1 && choice <= 5) {
					
					break;
					
				} else {
					
					System.out.println("Only numbers between 1-5");
					
				}
				
			} else {
				
				System.out.println("Only numbers between 1-5");
				scan.nextLine();
				
			}
		
		}
		
		if (noTasks(tasks)) return;
		
		switch (choice) {
		
		case 1 -> displayInfo(tasks);
			
		case 2, 3, 4, 5 -> generalSort(tasks, choice);
		
		default -> System.out.println("Only numbers between 1-5");
			
		}
	}
	
	public static void markDone(Scanner scan, ArrayList<Task> tasks) {
		
		int choice = 0;
		
		if (noTasks(tasks)) {
			
			System.out.println("There are no tasks at the moment.");
			return;
			
		} else {
			
			displayInfo(tasks);
			
			while (true) {

				System.out.print("Enter task index: ");
				System.out.println("Press 0 to go back to menu");
				
				if (scan.hasNextInt()) {
					
					choice = scan.nextInt();
					scan.nextLine();
					
					if (choice > 0 && choice <= tasks.size()) {
						
						int index = choice - 1;
						tasks.get(index).markDone();
						break;
						
					} else if (choice == 0) {
					
						return;
						
					} else {
						
						System.out.println("Please select a number between 1-" + tasks.size() + " Only");
						
					}
					
					
				} else {
					
					System.out.println("Please select a number between 1-" + tasks.size() + " Only");
					scan.nextLine();
					
				}
			
			} 
				
				
			}	
			
		}
	
	public static void deleteTask(Scanner scan, ArrayList<Task> tasks) {
		
		int choice = 0;
		
		if (!checkTasks(tasks)) {
			
			System.out.println("There are no tasks at the moment.");
			
		} else {
			
			displayInfo(tasks);
			
			while (true) {
				
				
				System.out.print("Enter task index: ");
				System.out.println("Press 0 to go back to menu");
				
				if (scan.hasNextInt()) {
					
					choice = scan.nextInt();
					scan.nextLine();
					
					if (choice >= 1 && choice <= tasks.size()) {
						
						int index = choice - 1;
						tasks.remove(index);
						break;
						
					} else if (choice == 0) {
					
						return;
						
					} else {
						
						System.out.println("Please select a number between 1-" + tasks.size() + " Only");
						
					}
					
					
				} else {
					
					System.out.println("Please select a number between 1-" + tasks.size() + " Only");
					scan.nextLine();
					
				}
			
			} 
				
				
			}	
		
	}
	
	public static void searchTask(Scanner scan, ArrayList<Task> tasks) {
		if (!checkTasks(tasks)) {
			
			System.out.println("There are no tasks at the moment.");
			
		} else {
			
			while (true) {
				
				boolean found = false;
				System.out.println("Press 0 to go back to menu");
				System.out.print("Search a task: ");
				String search = scan.nextLine();
				
				if (search.equals("0")) {
					break;
				}
				
				if (search.matches("[a-zA-Z0-9 ]+")) {
					
					for (int i =0; i < tasks.size(); i++) {
						
						Task task = tasks.get(i);
						System.out.println("===============================================");
						System.out.println("		TASK LIST							");
						System.out.println("===============================================");
						if (task.getTitle().toLowerCase().contains(search.toLowerCase())) {
							
							found = true;
							
							System.out.println();
							System.out.println(task.toString());
							
							
						} 
						
					}
					
					System.out.print("Do you want to search again? (y/n)");
					String again = scan.nextLine();
					
					if (again.equalsIgnoreCase("n")) {
						break;
					}
					
				} else {
					
					System.out.println("No special characters");
					
				}
				
				if (!found) {
					System.out.println("Nothing found.");
				}
				
			}
			
		}
		
	}
		
	
	public static boolean checkTasks(ArrayList<Task> tasks) {
		
		if (tasks.size() > 0) {
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean noTasks(ArrayList<Task> tasks) {
		
		if (!checkTasks(tasks)) {
			
			System.out.println("No tasks at this moment");
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static void displayInfo(ArrayList<Task> tasks) {
		
		System.out.println("===============================================");
		System.out.println("		TASK LIST							");
		System.out.println("===============================================");
		
		for (int i = 0; i < tasks.size(); i++) {
				System.out.print("\n[" + (i+1) + "] ");
				System.out.print("[" + (tasks.get(i).getStatus() ? "X" : " ") + "] ");
				System.out.println(tasks.get(i).toString());
				
			}	
			
		}
	
	public static void generalSort(ArrayList<Task> tasks, int choice) {
		
		switch (choice) {
		
		case 2: 
			
			Collections.sort(tasks, Task.titleComparator);
			System.out.println("===============================================");
			System.out.println("		TASK LIST							");
			System.out.println("===============================================");
			for (int i = 0; i < tasks.size(); i++) {
				System.out.print("\n[" + (i+1) + "] ");
				System.out.print("[" + (tasks.get(i).getStatus() ? "X" : " ") + "] ");
				System.out.println(tasks.get(i).toString());
				
			}
		
		break;
		
		case 3:
			
			Collections.sort(tasks, Task.dueDateComparator);
			System.out.println("===============================================");
			System.out.println("		TASK LIST							");
			System.out.println("===============================================");
			for (int i = 0; i < tasks.size(); i++) {
				System.out.print("\n[" + (i+1) + "] ");
				System.out.print("[" + (tasks.get(i).getStatus() ? "X" : " ") + "] ");
				System.out.println(tasks.get(i).toString());
				
			}
		
		break;
		
		case 4:
			
			System.out.println("===============================================");
			System.out.println("		TASK LIST							");
			System.out.println("===============================================");
			
			Collections.sort(tasks, Task.dueDateComparator);
			
			for (int i =0; i < tasks.size(); i++) {
				
				if (!tasks.get(i).getStatus()) {
					
					System.out.print("\n[" + (i+1) + "] ");
					System.out.print("[" + (tasks.get(i).getStatus() ? "X" : " ") + "] ");
					System.out.println(tasks.get(i).toString());
					
				}
				
			}
		
		break;
		
		case 5:
			
			System.out.println("===============================================");
			System.out.println("		TASK LIST							");
			System.out.println("===============================================");
		
			if (checkCompletedTask(tasks)) {
				
				Collections.sort(tasks, Task.dueDateComparator);
				
				for (int i =0; i < tasks.size(); i++) {
					
					if (tasks.get(i).getStatus()) {
		
						System.out.print("\n[" + (i+1) + "] ");
						System.out.print("[" + (tasks.get(i).getStatus() ? "X" : " ") + "] ");
						System.out.println(tasks.get(i).toString());
						
					}
					
				
			    }
			} else {
				
				System.out.println("No completed tasks yet");
				
			}
			
		
		break;
		
		default:
		
			System.out.println("Invalid Input");
			
		break;
		}
		
		
	}
	
	public static boolean checkCompletedTask(ArrayList<Task> tasks) {
		
		for (Task task : tasks) {
			
			if (task.getStatus()) return true;
			
		}
		return false;
		
	}
	
	
	public static void saveTasks(ArrayList<Task> tasks) {
	    if (noTasks(tasks)) return;

	    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");

	    try (FileWriter writer = new FileWriter("Task.txt")) {
	        for (Task task : tasks) {
	            writer.write(
	                task.getTitle() + ";" +
	                task.getCategory() + ";" +
	                task.getDueDate().format(format) + ";" +
	                task.getStatus() + ";" +
	                task.getLevel() + "\n"
	            );
	        }
	        System.out.println("Tasks saved successfully to Task.txt");
	    } catch (IOException e) {
	        System.out.println("Error saving file");
	    }
	}

	
	public static void loadTasks(ArrayList<Task> tasks) {
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");

	    try {
	        File file = new File("Task.txt");
	        Scanner scan = new Scanner(file);

	        tasks.clear();

	        while (scan.hasNextLine()) {
	            String line = scan.nextLine();
	            String[] parts = line.split(";");

	            if (parts.length == 5) {
	                String title = parts[0];
	                String category = parts[1];
	                LocalDate dueDate = LocalDate.parse(parts[2], format);
	                boolean status = Boolean.parseBoolean(parts[3]);
	                Priority level = Priority.valueOf(parts[4]);

	                Task task = new Task(title, category, dueDate, status, level);
	                tasks.add(task);
	            }
	        }

	        scan.close();
	        System.out.println("Tasks successfully loaded from file!");

	    } catch (FileNotFoundException e) {
	        System.out.println("File not found: Task.txt");
	    }
	}



}


