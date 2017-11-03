package com.example.IS_2D.sat;
/*
*
import immutable.ImList;
import sat.env.*;
import sat.formula.*;
*/
import com.example.IS_2D.immutable.EmptyImList;
import com.example.IS_2D.immutable.ImList;
import com.example.IS_2D.sat.env.*;
import com.example.IS_2D.sat.formula.*;


/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     *
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        return solve(formula.getClauses(), new Environment());
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     *
     *
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        Clause minClause = new Clause();
        int smallestClauseSize = Integer.MAX_VALUE;
        if (clauses.isEmpty()){
            return env;
        }
        else {

            for (Clause c : clauses){
                if (c.isEmpty()){
                    return null;
                }
                else {
                    if (c.size() < smallestClauseSize){
                        smallestClauseSize = c.size();
                        if (c.isUnit()){
                            Environment e;
                            minClause = c;
                            Literal l = minClause.chooseLiteral();
                            ImList<Clause> subC;
                            if (l instanceof PosLiteral){
                                e = env.putTrue(l.getVariable());
                                subC = substitute(clauses, l);
                            }
                            else {
                                e = env.putFalse(l.getVariable());
                                subC = substitute(clauses, l);
                            }
                             return solve(subC, e);
                        }
                    }
                }
            }
            for (Clause c :clauses){
                if (c.size() == smallestClauseSize){
                    minClause = c;
                }
            }
            Literal l = minClause.chooseLiteral();
            if (l instanceof NegLiteral) {
                l = l.getNegation();
            }

            Environment e = solve(substitute(clauses, l), env.put(l.getVariable(), Bool.TRUE));
            if (e == null)
                return solve(substitute(clauses, l.getNegation()), env.put(l.getVariable(), Bool.FALSE));
            else
                return e;


        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     *
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses, Literal l) {
        Clause tempClause;
        for (Clause c : clauses) {
            if (c.contains(l) || c.contains(l.getNegation())) {
                tempClause = c.reduce(l);
                clauses = clauses.remove(c);
                if (tempClause != null) {
                    clauses = clauses.add(tempClause);
                }
            }
        }
        return clauses;
    }

}
