
package pl.workshop1;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static String[][] data = new String[100][3];
    static int countLine = 0;

    public static void main(String[] args) {
        loadTasks();

        String[] commands = {"Add", "Remove", "List", "Exit"};

        System.out.println("Commands: ");

        for(int i = 0; i < commands.length; i++){
            System.out.println(ConsoleColors.GREEN + commands[i]);
        }

        Scanner io = new Scanner(System.in);
        String command;

        do {
            System.out.println(ConsoleColors.RESET + "Enter command: ");
            command = io.nextLine().trim();
            System.out.println(ConsoleColors.BLUE + command + ":");
            //add Switch here
             switch(command){
                 case "List":
                     list();
                     break;
                 case "Add":
                     System.out.println("Enter name: ");
                     String name = io.nextLine();
                     System.out.println("Enter date: (YYYY-MM-DD) ");
                     String date = io.nextLine();
                     System.out.println("Is active? (true/false)) ");
                     boolean active = Boolean.parseBoolean(io.nextLine());
                     add(name, date, active);
                     break;
                 case "Remove":
                     System.out.println("Enter the index of task you want to remove(0 to " + (countLine-1) + "): ");
                     int indexToRemove = io.nextInt();
                     io.nextLine();
                     remove(indexToRemove);
                     break;
                 case "Exit":
                     System.out.println(ConsoleColors.RED + "Program finished!");
                     break;
             }
        } while (!command.equals("Exit"));
    }

    public static void loadTasks(){
        File file = new File("./tasks.csv");

        try {
            Scanner scan = new Scanner(file);

            int i = 0;

            while(scan.hasNextLine()){
                String line= scan.nextLine();
                data[i] = line.split(",");
                i++;
            }

            countLine = i;
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu exista!");
        }

    }
    public static boolean add(String name, String date, boolean active){

        if(countLine < data.length){
            data[countLine][0] = name;
            data[countLine][1] = date;
//            valueOf() in Java is used to convert any non-String variable or Object such as int,
//            boolean, char, and others to a newly created String object
            data[countLine][2] = String.valueOf(active);
            countLine++;
            System.out.println("Task added successfully");
            return true;
        }else{
            System.out.println("Data store is full! Unable to add more tasks");
            return false;
        }

    }

    public static boolean list(){
        if(countLine == 0){
            System.out.println("No available data to show.");
            return false;
        }

        for(int i = 0; i < countLine; i++){
            System.out.println(ConsoleColors.YELLOW + StringUtils.joinWith(",", data[i]));
        }

        return true;
    }

    public static void remove(int index){
        if(index < 0 || index >= countLine){
            System.out.println(ConsoleColors.RED + "Invalid index. Please enter a valid task index.");
            return;
        }

        for(int i = index; i < countLine - 1; i++){
            data[i] = data[i+1];
        }

        data[countLine-1] = null;
        countLine--;

        System.out.println("Task removed successfully.");
    }
}