package com.example.IS_2D.sat;

/**
 * Created by reube on 2/11/2017.
 */

/**
 * Author: dnj
 * Date: Mar 5, 2008, 5:02:48 PM
 * 6.005 Elements of Software Construction
 * (c) 2008, MIT and Daniel Jackson
 */

import com.example.IS_2D.immutable.ImListMap;
import com.example.IS_2D.immutable.ImMap;
import com.example.IS_2D.sat.env.Bool;
import com.example.IS_2D.sat.env.Environment;
import com.example.IS_2D.sat.env.Variable;
import com.example.IS_2D.immutable.ImListMap;
import com.example.IS_2D.immutable.ImMap;

import java.util.HashMap;

/**
 * An environment is an immutable mapping from variables to boolean values.
 * A special 3-valued Bool type is used to handle the case
 * in which a variable is to be evaluated that has no binding.
 *
 * Typically, clients are expected to bind variables explicitly only
 * to Bool.TRUE and Bool.FALSE, and Bool.UNDEFINED is used only
 * to return a boolean value for an unbound variable. But this
 * implementation does not prevent a variable from being explicitly
 * bound to UNDEFINED.
 */
public class REnvironment{
    /*
     * Rep invariant
     *     bindings != null
     */
    private HashMap<Variable, Bool> bindings;
    public HashMap<Variable,Bool> getBindings(){return bindings;}


    public REnvironment() {
        bindings = new HashMap<>();
    }

    /**
     * @return a new environment in which l has the value b
     * if a binding for l already exists, overwrites it
     */
    public void put(Variable v, Bool b) {
        bindings.put (v, b);
    }

    /**
     * @return a new environment in which l has the value Bool.TRUE
     * if a binding for l already exists, overwrites it
     */
    public void putTrue(Variable v) {
        bindings.put (v, Bool.TRUE);

    }

    /**
     * @return a new environment in which l has the value Bool.FALSE
     * if a binding for l already exists, overwrites it
     */
    public void putFalse(Variable v) {
      bindings.put (v, Bool.FALSE);
    }

    /**
     * @return the boolean value that l is bound to, or
     * the special UNDEFINED value of it is not bound
     */
    public Bool get(Variable v){
        Bool b = bindings.get(v);
        if (b==null) return Bool.UNDEFINED;
        else return b;
    }

    @Override
    public String toString () {
        return "Environment:" + bindings;
    }
}
