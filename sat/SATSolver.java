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
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {

        if (clauses.isEmpty()){
            return env;
        }
        else {
            Clause minClause = clauses.first();
            int minSize = minClause.size();
            for (Clause c : clauses) {
                if (c.size() < minSize) {
                    minSize = c.size();
                    minClause = c;
                }
                if (c.isEmpty())
                    return null;
            }

            Environment e;
            Literal l = minClause.chooseLiteral();
            if (minClause.isUnit()){
                if (l instanceof PosLiteral)
                    e = solve(substitute(clauses,l),env.putTrue(l.getVariable()));
                else
                    e = solve(substitute(clauses,l),env.putFalse(l.getVariable()));
            }else{
                ImList<Clause> smallClauses = substitute(clauses, l);
                if (l instanceof PosLiteral) {
                    e = solve(smallClauses, env.putTrue(l.getVariable()));
//                    if (e != null)
//                        return e;
                    e = solve(smallClauses, env.putFalse(l.getVariable()));
                } else {
                    e = solve(smallClauses, env.putFalse(l.getVariable()));
//                    if (e != null)
//                        return e;
                    e = solve(smallClauses, env.putTrue(l.getVariable()));
                    }
                }
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

        ImList<Clause> sub = new EmptyImList<>();
        for (Clause c : clauses) {
            Clause newClause = c.reduce(l);
            if (newClause != null) {
                sub = sub.add(newClause);
            }
        }
        return sub;

    }

}
