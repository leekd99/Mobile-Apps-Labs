import java.util.Scanner;

public class UserInput {

    private int parameter1, parameter2, parameter3;

    //Constructor
    public UserInput(){}

    //Mutator
    public void input(Shape currentShape){

        Scanner myParameter = new Scanner(System.in);
        Boolean validInput = false;
        parameter1 = 0;
        parameter2 = 0;
        parameter3 = 0;

        System.out.println("Please put in the dimensions for " + currentShape.getName() + ":");

        if(currentShape.getName().equals("Square")){

            do {

                try {
                    System.out.print("\tlength: ");
                    parameter1 = myParameter.nextInt();
                    System.out.print("\theight: ");
                    parameter2 = myParameter.nextInt();

                    if(parameter1 <= 0 || parameter2 <=0){

                        System.out.println("Invalid input for " + currentShape.getName() + ".\nPlease try again.");

                    } else {validInput = true;}

                }catch(Exception e){

                    System.out.println("Invalid input! Please try again.");

                }

            }while(!validInput);

        }//end if square

        else if(currentShape.getName().equals("Circle")){

            do {

                try {
                    System.out.print("\tradius: ");
                    parameter1 = myParameter.nextInt();

                    if(parameter1 <= 0){

                        System.out.println("Invalid input for " + currentShape.getName() + ".\nPlease try again.");

                    } else {validInput = true;}

                }catch(Exception e){

                    System.out.println("Invalid input! Please try again.");

                }

            }while(!validInput);

        }//end if circle

        else if(currentShape.getName().equals("Triangle")){

            do {

                try {
                    System.out.print("\tside A: ");
                    parameter1 = myParameter.nextInt();
                    System.out.print("\tside B: ");
                    parameter2 = myParameter.nextInt();
                    System.out.print("\tside C: ");
                    parameter3 = myParameter.nextInt();

                    if(parameter1 <= 0 || parameter2 <=0 || parameter3 <=0 ){

                        System.out.println("Invalid input for " + currentShape.getName() + ".\nPlease try again.");

                    }else if (parameter1 == parameter2 && parameter2 == parameter3){

                        validInput = true;

                    }else if( parameter1 + parameter2 <= parameter3 || parameter2 + parameter3 <= parameter1 || parameter3 + parameter2 <= parameter1){

                        System.out.println("Those sides cannot make a " + currentShape.getName() + "\nPlease try again.");

                    }else{ validInput = true; }

                }catch(Exception e){

                    System.out.println("Invalid input! Please try again.");

                }

            }while(!validInput);

        }//end if square

        else if(currentShape.getName().equals("Equilateral Triangle")){

            do {

                try {

                    System.out.print("\tside: ");
                    parameter1 = myParameter.nextInt();

                    if(parameter1 <= 0){

                        System.out.println("Invalid input for " + currentShape.getName() + ".\nPlease try again.");

                    } else {validInput = true;}

                }catch(Exception e){

                    System.out.println("Invalid input! Please try again.");

                }

            }while(!validInput);

        }//end if square

    }//end input

    public int getParameter1(){
        return parameter1;
    }
    public int getParameter2(){
        return parameter2;
    }
    public int getParameter3(){
        return parameter3;
    }

}
