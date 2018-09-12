abstract class Shape {

    private String name;

    //Constructor
    public Shape(String name){

        this.name = name;

    }//end constructor

    //returns name of the shape
    public String getName(){
        return name;
    }//end get String

    //returns the area of the shape
    public double getArea(){
        return 0.0;
    }//end getArea

    //returns dimensions of Shape
    public void printDimensions(){

        //prints dimensions of shape
        System.out.println("No dimensions");

    }//end printDimensions

}//end class Shape
