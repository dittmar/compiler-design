/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolf;

import wolf.interfaces.Visitor;
import wolf.node.TFloatType;
import wolf.node.TIntType;
import wolf.node.TListType;
import wolf.node.TStringType;

/**
 * Enumerated type for different identifier types in WOLF
 * @author William Ezekiel
 * @author Kevin Dittmar
 * @author Joseph Alacqua
 * @version April 3 2016
 */
public enum Type {
    INTEGER(TIntType.class),
    STRING(TStringType.class),
    FLOAT(TFloatType.class),
    LIST(TListType.class);
    // Booleans are represented as integers: 0 - false, rest - true
    
    final Class token_class;
    
    /**
     * Create a fold symbol
     * @param token_class the token class this fold symbol represents
     */
    Type(Class token_class) {
        this.token_class = token_class;
    }
    
        /**
     * Accept a visitor
     * @param v a visitor
     * @return this, which is the type of the parameter
     */
    public Type accept(Visitor v) {
        return this;
    }
}
