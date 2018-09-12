public class Triangle extends Shape{

    private double side_A, side_B, side_C;

    //constructor method for Square
    public Triangle(String name){

        super(name);
        System.out.println(super.getName() + " created successfully!");

    }//end constructor

    //set parameters
    public void setParameters(double side_A, double side_B, double side_C){

            this.side_A = side_A;
            this.side_B = side_B;
            this.side_C = side_C;

    }//end set parameter

    @Override
    //returns dimensions of Triangle
    public void printDimensions(){

        System.out.println("Dimensions:\n\tSide A: " + side_A + "\n\tSide B: " + side_B + "\n\tSide C: " + side_C);

    }//end printDimension

    //calculates the semiperimeter of the triangle
    public double semiperimeter(double side_A, double side_B, double side_C){

        return (side_A+side_B+side_C)/2;

    }//end calculate semiperimeter

    @Override
    //returns the area of the triangle
    public double getArea(){

        double s = semiperimeter(side_A, side_B, side_C);
        return Math.sqrt(s*(s-side_A)*(s-side_B)*(s-side_C));

    }//end getArea

}//enc Triangle
