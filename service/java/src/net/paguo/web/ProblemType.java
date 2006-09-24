package net.paguo.web;

/**
 * @version $Id $
 */
public enum ProblemType {
    CLIENT (1),
    NETWORK (2);

    private final Integer type;

    private ProblemType(Integer type){
        this.type = type;
    }
    
}
