package org.openlmis;

public class RightQuery {
    private Right right;
    private Program program;
    private SupervisoryNode supervisoryNode;

    public RightQuery(Right right) {
        this.right = right;
    }

    public RightQuery(Right right, Program program) {
        this.right = right;
        this.program = program;
    }

    public RightQuery(Right right, Program program, SupervisoryNode supervisoryNode) {
        this.right = right;
        this.program = program;
        this.supervisoryNode = supervisoryNode;
    }

    public Right getRight() {
        return right;
    }

    public Program getProgram() {
        return program;
    }

    public SupervisoryNode getSupervisoryNode() {
        return supervisoryNode;
    }
}
