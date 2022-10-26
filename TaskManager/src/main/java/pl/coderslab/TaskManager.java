package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class TaskManager {

    static final String [] options = {"add", "remove", "list", "exit"};
    static final String fileName = "src/main/java/pl/coderslab/tasks.csv";
    static String [][] tasks;

    static Scanner scan = new Scanner(System.in);

    public static void printOptions(String [] args){
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for(String arg : args){
            System.out.println(arg);
        }


    }



    public static void main(String[] args){

        tasks = loadData(fileName);
        printOptions(options);
        while(scan.hasNextLine()) {
            switch (scan.nextLine()) {
                case "add":
                    taskAdd();
                    break;
                case "remove":
                    remove(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted");
                    break;
                case "list":
                    list(tasks);
                    break;
                case "exit":
                    save(fileName, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            printOptions(options);
        }
    }

    public static String[][] loadData(String fileName)  {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("File not exists.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                 String[] split = strings.get(i).split(",");
                     for (int j = 0; j < split.length; j++) {
                           tab[i][j] = split[j];
             }
         }
        }catch(IOException e){e.printStackTrace();}
        return tab;
         }

         public static void list(String [][] args){
             System.out.println("list");
             for(int i = 0; i<args.length; i++){
                 System.out.println();
                 System.out.print(i + ": ");
                 for(int j = 0; j<args[i].length; j++){
                     System.out.print(args[i][j] + " ");
                 }
             }
         }

         public static void taskAdd(){

             System.out.println("Please add task description");
             String description = scan.nextLine();
             System.out.println("Please add task due date");
             String date = scan.nextLine();
             System.out.println("Is your task is important: true/false ");
             String important = scan.nextLine();
             tasks = Arrays.copyOf(tasks, tasks.length+1);
             tasks[tasks.length-1] = new String[3];
             tasks[tasks.length-1][0] = description;
             tasks[tasks.length-1][1] = date;
             tasks[tasks.length-1][2] = important;
         }

         public static void save(String fileName, String[][] tab){
            Path path = Paths.get(fileName);
            String [] lines = new String[tab.length];
            for(int i =0; i< lines.length; i++){
                lines[i]= String.join(",", tab[i]);
            }
            try{
                Files.write(path, Arrays.asList(lines));
            }catch (IOException e){
                e.printStackTrace();
            }
         }

         public static boolean isNumberGreaterOrEqualZero(String input){
             if(NumberUtils.isParsable(input)){
                 return Integer.parseInt(input) >=0;
             }
                return false;
         }

         public static int getTheNumber(){
             System.out.println("Please select number to remove.");
             String number = scan.nextLine();
             while(!isNumberGreaterOrEqualZero(number)){
                 System.out.println("Incorect argument passed. please give number greater or equal 0");
                 scan.nextLine();
             }
            return Integer.parseInt(number);
         }

         public static void remove(String [][] tab, int number){
        try {
           if( number < tab.length){
               tasks = ArrayUtils.remove(tab, number);
           }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Element not exists in tab");
        }
         }

}