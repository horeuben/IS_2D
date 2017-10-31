package com.example.IS_2D.sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.*;
import sat.formula.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();



	
    public static void main(String[] args) {
        Formula formula = new Formula();
        int variables = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Main/SUTD/50.001/Project-2D/Project-2D-starting/sampleCNF/smallSat.cnf"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!(line.substring(0,1).equals("p")) && !(line.substring(0,1).equals("c"))) {
                    Clause clause = new Clause();
                    for (String linesplit : line.split("\\s+")) {
                        if (!linesplit.equals("0")) {
                            if (linesplit.substring(0, 1).equals("-"))
                                clause = clause.add(NegLiteral.make(linesplit.substring(1)));
                            else
                                clause = clause.add(PosLiteral.make(linesplit));
                        }
                    }
                    formula = formula.addClause(clause);
                }
                else if (line.substring(0,1).equals("p")) {
                    String[] linesplit = line.split("\\s+");
                    variables = Integer.parseInt(linesplit[2]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(formula.toString());

        Environment env;
        env = SATSolver.solve(formula);
        if (env == null) {
            System.out.println("not satisfiable");
        }
        else {
            System.out.println("satisfiable");
            try {
                PrintWriter out = new PrintWriter(new FileWriter("C:/Main/SUTD/50.001/Project-2D/Project-2D-starting/sampleCNF/BoolAssignment.txt"));
                for (int varnum = 1; varnum <= variables; varnum++) {
                    Bool bool = env.get(new Variable(Integer.toString(varnum)));
                    if (bool == Bool.TRUE) {
                        out.println(Integer.toString(varnum) + ":TRUE");
                    }
                    else {
                        out.println(Integer.toString(varnum) + ":FALSE");
                    }
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}
