public class Square extends Shape{

    public double length;
    public double height;

    //constructor method for Square
    public Square(String name){

        super(name);

        System.out.println(super.getName() + " created successfully!");

    }//end constructor

    //set parameters
    public void setParameters(double length, double height){

        this.length = length;
        this.height = height;

    }//end set parameter

    @Override
    //returns dimensions of Square
    public void printDimensions(){

        System.out.println("Dimensions:\n\tLength: " + length + "\n" + "\tHeight: " + height);

    }//end printDimension

    @Override
    //returns the area of the shape
    public double getArea(){
        return length*height;
    }//end getArea

}//end class Square
