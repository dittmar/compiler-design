/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolf.interfaces;

import java.util.List;
import wolf.Type;

/**
 *
 * @author perkalab
 */
public abstract class Args {
    List<Arg> arg_list;
    
    public Args(List<Arg> arg_list) {
        this.arg_list = arg_list;
    }
    
    public List<Arg> getArgList() {
        return arg_list;
    }
    
    /**
    * Accept a visitor.
    * @param v a visitor
    */
    public abstract Object accept(Visitor v);
}
