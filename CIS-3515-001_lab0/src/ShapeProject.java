public class ShapeProject {

    public static void main(String[] args){

        //instantiate shapes
        Square mySquare = new Square("Square");
        Circle myCircle = new Circle("Circle");
        Triangle myTriangle = new Triangle("Triangle");
        Equilateral_Triangle myEqTriangle = new Equilateral_Triangle("Equilateral Triangle");

        //instantiate userInput object
        UserInput myInput = new UserInput();

        //take input for Square
        myInput.input(mySquare);
        mySquare.setParameters(myInput.getParameter1(),myInput.getParameter2());

        //take input for Cirlce
        myInput.input(myCircle);
        myCircle.setParameters(myInput.getParameter1());

        //take input for Triangle
        myInput.input(myTriangle);
        myTriangle.setParameters(myInput.getParameter1(), myInput.getParameter2(), myInput.getParameter3());

        //take input for Equilateral Triangle
        myInput.input(myEqTriangle);
        myEqTriangle.setParameters(myInput.getParameter1());

        //Print information for shapes
        System.out.println(mySquare.getName());
        mySquare.printDimensions();
        System.out.println("Area:\n\t" + mySquare.getArea());

        System.out.println(myCircle.getName());
        myCircle.printDimensions();
        System.out.println("Area:\n\t" + myCircle.getArea());

        System.out.println(myTriangle.getName());
        myTriangle.printDimensions();
        System.out.println("Area:\n\t" + myTriangle.getArea());

        System.out.println(myEqTriangle.getName());
        myEqTriangle.printDimensions();
        System.out.println("Area:\n\t" + myEqTriangle.getArea());

    }//end main method

}//end class Shape Project
