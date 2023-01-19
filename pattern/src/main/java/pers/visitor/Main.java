package pers.visitor;

import pers.visitor.visitor.CEOVisitor;
import pers.visitor.visitor.CTOVisitor;

public class Main {

    public static void main(String[] args) {

        BusinessReport report = new BusinessReport();

        CEOVisitor ceoVisitor = new CEOVisitor();
        CTOVisitor ctoVisitor = new CTOVisitor();

        report.showReport(ceoVisitor);
        System.out.println();
        report.showReport(ctoVisitor);
    }

}
