package JavaProject1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Task {
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	private String title;
	private boolean isDone;
	private LocalDate dueDate;
	private String category;
	private Priority level;
	
	public Task(String title, String category, LocalDate dueDate, Priority level) {
		
		this.title = title;
		this.category = category;
		this.dueDate = dueDate;
		setStatus(false);
		this.level = level;
		
		System.out.println("Task added");
	}
	
public Task(String title, String category, LocalDate dueDate, boolean isDone,Priority level) {
		
		this.title = title;
		this.category = category;
		this.dueDate = dueDate;
		this.isDone = isDone;
		this.level = level;
		
		System.out.println("Task added");
	}
	
	
	public void setTitle(String title) {
		
		this.title = title;
		
	}
	
	public String getTitle() {
		
		return title;
		
	}
	
	public void setStatus(boolean isDone) {
		
		this.isDone = isDone;
		
	}
	
	public boolean getStatus() {
		
		return isDone;
		
	}
	
	public void setCategory(String category) {
		
		this.category = category;
		
	}
	
	public String getCategory() {
		
		return category;
		
	}
	
	public void setLevel(Priority level) {
		
		this.level = level;
		
	}
	
	public Priority getLevel() {
		
		return level;
		
	}
	
	public void markDone() {
		
		
		if (getStatus()) {
			
			System.out.println("Task is already done");
			
		} else {
			
			setStatus(true);
			System.out.println("Task marked as done");
			
		}
		
	}
	
	public String toString() {
	
		String availability = getStatus() ? "Done" : "On-going";
		return "Title: " + getTitle() + "\nCategory: " + getCategory() + "\nDue: " + getDueDate().format(format) + "\nPriority: " + getLevel();
		
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public static Comparator<Task> titleComparator = new Comparator<Task>() {
		
		@Override 
		public int compare(Task t1, Task t2) {
			
			return t1.getTitle().toLowerCase().compareTo(t2.getTitle().toLowerCase());
			
		}
		
	};
	
	public static Comparator<Task> dueDateComparator = new Comparator<Task>() {
		
		@Override
		public int compare(Task t1, Task t2) {
			
			return t1.dueDate.compareTo(t2.dueDate);
			
		}
		
	};
	
}
