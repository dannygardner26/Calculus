import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        String temp = "3x^2 + SIN(4x^2) + 5x + 6";
        Equation eq = Equation.toEquation(temp);
        System.out.println("The equation is: " + eq.toString());


        // Scanner in = new Scanner(System.in);
        // String input;
        // do{
        // System.out.println("What would you like to do?");
        // System.out.println("(1) term by term input");
        // System.out.println("(2) full equation input");
        // input = in.nextLine();
        // }while(!input.equals("1") && !input.equals("2"));
        
        // if(input.equals("1")){
        //     System.out.println("How many terms would you like to differentiate?");
        //     int numTerms = in.nextInt();
        //     in.nextLine();
        //     ArrayList<Term> terms = new ArrayList<>();
        //     for(int i = 0; i < numTerms; i++){
        //         System.out.println("Enter the coefficient of term " + (i+1));
        //         double coefficient = in.nextDouble();
        //         System.out.println("Enter the degree of term " + (i+1));
        //         double degree = in.nextDouble();
        //         in.nextLine();
        //         terms.add(new Term(coefficient, degree));
        //     }
            
        // }
        // else{
        //     System.out.println("Enter your equation:");
        //     String equation = in.nextLine();
        //     Equation eq = Equation.toEquation(equation);
        //     System.out.println("The equation is: " + eq.toString());
        // }

































        
        // ArrayList<String> terms = new ArrayList<>();
        // StringBuilder currentTerm = new StringBuilder();
        
        // ArrayList<Term> termList = new ArrayList<>();
        // Term term1 = new Term(3,2);
        // Term term2 = new Term(4,2,term1, Trig.SIN);

        // System.out.println(term2.toString());

        // termList = term2.differentiate();
       
        // System.out.println(Term.toString(termList));
        // ArrayList<Term> simplifiedTermList = Term.simplify(termList);
        // System.out.println(Term.toString(simplifiedTermList));
        
    }
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
}