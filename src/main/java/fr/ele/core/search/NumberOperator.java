package fr.ele.core.search;

public enum NumberOperator implements SearchOperator {
    EQ {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEQ();
        }
    },
    GT {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitGT();
        }
    },
    LT {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitLT();
        }
    },
    EGT {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEGT();
        }
    },
    ELT {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitELT();
        }
    },
    NULL {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitNULL();
        }
    };

    public static interface Visitor<T> {
        void visitEQ();

        void visitGT();

        void visitLT();

        void visitEGT();

        void visitELT();

        void visitNULL();
    }

    public abstract void accept(Visitor visitor);
}
