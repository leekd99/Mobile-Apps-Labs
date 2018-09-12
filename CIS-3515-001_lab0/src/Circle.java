public class Circle extends Shape{

    private double radius;

    //constructor method for Square
    public Circle(String name){

        super(name);
        System.out.println(super.getName() + " created successfully!");

    }//end constructor

    //set parameters
    public void setParameters(double radius){

        this.radius = radius;

    }//end set parameter

    @Override
    //returns dimensions of circle
    public void printDimensions(){

        System.out.println("Dimensions:\n\tRadius: " + radius);

    }//end printDimension

    @Override
    //returns the area of the circle
    public double getArea(){ return Math.pow(radius,2)*Math.PI; }//end getArea

}//end Circle
