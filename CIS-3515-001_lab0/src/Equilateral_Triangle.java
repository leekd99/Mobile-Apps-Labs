public class Equilateral_Triangle extends Triangle{

    private double side;

    //constructor method for Square
    public Equilateral_Triangle(String name){

        super(name);
        //System.out.println(super.getName() + " created successfully!");

    }//end constructor

    //set parameters
    public void setParameters(double side){

        this.side = side;

    }//end set parameter

    @Override
    //returns dimensions of Equilateral_Triangle
    public void printDimensions(){

        System.out.println("Dimensions:\n\tAll three sides: " + side);

    }//end printDimension

    @Override
    //returns the area of the Equilateral_Triangle
    public double getArea(){

        double s = semiperimeter(side, side, side);
        return Math.sqrt(s*Math.pow((s-side),3));

    }//end getArea


}//end Equilateral_Triangle
