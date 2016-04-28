/*
 * UNIVERSIDAD DE COSTA RICA (UCR)
 * LABORATORIO NACIONAL DE MATERIALES Y MODELOS ESTRUCTURALES (LANAMME)
 * PROGRAMA DE INFRAESTRUCTURA DEL TRANSPORTE (PITRA)
 * UNIDAD DE MATERIALES Y PAVIMENTOS (UMP)
 * 
 * PITRA PAVE - PROGRAMA PARA ANALISIS DE PAVIMENTOS FLEXIBLES BASADO EN LA TEORIA DE MULTICAPA ELASTICA
 * DESARROLLADO POR: FRANCISCO ROJAS PEREZ
 * VERSION 1.0.0
 */
package my.PITRA_PAVE;

/**
 *
 * @author FR
 */
class Matrix {
    String ID;
    int rows;
    int cols;
    double[][] matr;
    
    public void init(int newRows, int newCols) {
        rows = newRows;
        cols = newCols;
        matr = new double[rows][cols];
    }
    public void addValue(int rowsInde, int colsInde, double valu) {
        if (rowsInde<rows && colsInde<cols) {
            matr[rowsInde][colsInde]+=valu;
        }
        else {
            System.out.println("ERROR_1: Matrix indices exceeds matrix dimensions...");
        }
    }
    public void setValue(int rowsInde, int colsInde, double valu) {
        if (rowsInde<rows && colsInde<cols) {
            matr[rowsInde][colsInde]=valu;
        }
        else {
            System.out.println("ERROR_1: Matrix indices exceeds matrix dimensions...");
        }
    }
    public double value(int rowsInde, int colsInde){
        if (rowsInde<rows && colsInde<cols) {
            return matr[rowsInde][colsInde];
        }
        else {
            System.out.println("ERROR_2: Matrix indices exceeds matrix dimensions...");
            return -1;
        }
    }
    public void printMatrix(String stri){
        int i, j;
        System.out.print("Matrix ID: "+stri);
        for (i=0;i<rows;i++) {
            System.out.println("");
            for (j=0;j<cols;j++) {
                if (matr[i][j]>=0)
                    System.out.printf(" %9.3e  ", matr[i][j]);
                else
                    System.out.printf("%9.3e  ", matr[i][j]);
            }
        }
        System.out.println("\nMatrix ID: "+stri+"\n");
    }
    public int colums() {
        return cols;
    }
    public int rows() {
        return rows;
    }
    public Matrix inverse() {
        Matrix inve = new Matrix();
        double a11, a12, a13, a14, a21, a22, a23, a24, a31, a32, a33, a34, a41, a42, a43, a44, dete;
        if (rows==cols) {
            inve = new Matrix();    inve.init(rows, cols);
            if (rows==2) {
                a11 = matr[0][0];
		a12 = matr[0][1];
		a21 = matr[1][0];
		a22 = matr[1][1];
		dete = a11*a22 - a12*a21;	
		inve.setValue(0, 0, 1./dete* (a22));
                inve.setValue(0, 1,-1./dete* (a12));
                inve.setValue(1, 0,-1./dete* (a21));
                inve.setValue(1, 1, 1./dete* (a11));
            }
            else if (rows==3) {
                a11= matr[0][0];
		a12= matr[0][1];
		a13= matr[0][2];
		a21= matr[1][0];
		a22= matr[1][1];
		a23= matr[1][2];
		a31= matr[2][0];
		a32= matr[2][1];
		a33= matr[2][2];
		dete= a11*a22*a33 - a11*a23*a32 - a12*a21*a33
                        + a12*a23*a31 + a13*a21*a32 - a13*a22*a31;	
		inve.setValue(0, 0, 1./dete* (a22*a33 - a23*a32));
                inve.setValue(0, 1, 1./dete* (a13*a32 - a12*a33));
                inve.setValue(0, 2, 1./dete* (a12*a23 - a13*a22));
                inve.setValue(1, 0, 1./dete* (a23*a31 - a21*a33));
                inve.setValue(1, 1, 1./dete* (a11*a33 - a13*a31));
                inve.setValue(1, 2, 1./dete* (a13*a21 - a11*a23));
                inve.setValue(2, 0, 1./dete* (a21*a32 - a22*a31));
                inve.setValue(2, 1, 1./dete* (a12*a31 - a11*a32));
                inve.setValue(2, 2, 1./dete* (a11*a22 - a12*a21));
            }
            else if (rows==4) {
                a11= matr[0][0];
		a12= matr[0][1];
		a13= matr[0][2];
		a14= matr[0][3];
		a21= matr[1][0];
		a22= matr[1][1];
		a23= matr[1][2];
		a24= matr[1][3];
		a31= matr[2][0];
		a32= matr[2][1];
		a33= matr[2][2];
		a34= matr[2][3];
		a41= matr[3][0];
		a42= matr[3][1];
		a43= matr[3][2];
		a44= matr[3][3];
		dete= a11*a22*a33*a44 - a11*a22*a34*a43 - a11*a23*a32*a44 + a11*a23*a34*a42 
			+ a11*a24*a32*a43 - a11*a24*a33*a42 - a12*a21*a33*a44 + a12*a21*a34*a43 
			+ a12*a23*a31*a44 - a12*a23*a34*a41 - a12*a24*a31*a43 + a12*a24*a33*a41 
			+ a13*a21*a32*a44 - a13*a21*a34*a42 - a13*a22*a31*a44 + a13*a22*a34*a41 
			+ a13*a24*a31*a42 - a13*a24*a32*a41 - a14*a21*a32*a43 + a14*a21*a33*a42 
			+ a14*a22*a31*a43 - a14*a22*a33*a41 - a14*a23*a31*a42 + a14*a23*a32*a41;
                inve.setValue(0, 0, 1./dete* (a22*a33*a44 - a22*a34*a43 - a23*a32*a44 + a23*a34*a42 + a24*a32*a43 - a24*a33*a42));
                inve.setValue(0, 1, 1./dete* (a12*a34*a43 - a12*a33*a44 + a13*a32*a44 - a13*a34*a42 - a14*a32*a43 + a14*a33*a42));
                inve.setValue(0, 2, 1./dete* (a12*a23*a44 - a12*a24*a43 - a13*a22*a44 + a13*a24*a42 + a14*a22*a43 - a14*a23*a42));
                inve.setValue(0, 3, 1./dete* (a12*a24*a33 - a12*a23*a34 + a13*a22*a34 - a13*a24*a32 - a14*a22*a33 + a14*a23*a32));
                inve.setValue(1, 0, 1./dete* (a21*a34*a43 - a21*a33*a44 + a23*a31*a44 - a23*a34*a41 - a24*a31*a43 + a24*a33*a41));
                inve.setValue(1, 1, 1./dete* (a11*a33*a44 - a11*a34*a43 - a13*a31*a44 + a13*a34*a41 + a14*a31*a43 - a14*a33*a41));
                inve.setValue(1, 2, 1./dete* (a11*a24*a43 - a11*a23*a44 + a13*a21*a44 - a13*a24*a41 - a14*a21*a43 + a14*a23*a41));
                inve.setValue(1, 3, 1./dete* (a11*a23*a34 - a11*a24*a33 - a13*a21*a34 + a13*a24*a31 + a14*a21*a33 - a14*a23*a31));
                inve.setValue(2, 0, 1./dete* (a21*a32*a44 - a21*a34*a42 - a22*a31*a44 + a22*a34*a41 + a24*a31*a42 - a24*a32*a41));
                inve.setValue(2, 1, 1./dete* (a11*a34*a42 - a11*a32*a44 + a12*a31*a44 - a12*a34*a41 - a14*a31*a42 + a14*a32*a41));
                inve.setValue(2, 2, 1./dete* (a11*a22*a44 - a11*a24*a42 - a12*a21*a44 + a12*a24*a41 + a14*a21*a42 - a14*a22*a41));
                inve.setValue(2, 3, 1./dete* (a11*a24*a32 - a11*a22*a34 + a12*a21*a34 - a12*a24*a31 - a14*a21*a32 + a14*a22*a31));
                inve.setValue(3, 0, 1./dete* (a21*a33*a42 - a21*a32*a43 + a22*a31*a43 - a22*a33*a41 - a23*a31*a42 + a23*a32*a41));
                inve.setValue(3, 1, 1./dete* (a11*a32*a43 - a11*a33*a42 - a12*a31*a43 + a12*a33*a41 + a13*a31*a42 - a13*a32*a41));
                inve.setValue(3, 2, 1./dete* (a11*a23*a42 - a11*a22*a43 + a12*a21*a43 - a12*a23*a41 - a13*a21*a42 + a13*a22*a41));
                inve.setValue(3, 3, 1./dete* (a11*a22*a33 - a11*a23*a32 - a12*a21*a33 + a12*a23*a31 + a13*a21*a32 - a13*a22*a31));
            }
            else {
                System.out.println("ERROR_4: Matrix is greater than 4x4, explicit formula is not available.");
            }
        }
        else {
            System.out.println("ERROR_3: Matrix is not square, thus is not invertible.");
        }
        return inve;
    }
    public Matrix product(Matrix newMatr) {
        Matrix prod = new Matrix();
        int i, j, k;
        if (cols == newMatr.rows) {
            prod = new Matrix();    prod.init(rows, newMatr.cols);
            for (i=0;i<prod.rows;i++) {
                for (j=0;j<prod.cols;j++) {
                    for (k=0; k<cols; k++) {
			prod.addValue(i, j, matr[i][k]*newMatr.value(k, j));
                    }
                }
            }
        }
        else {
            System.out.println("ERROR_5: Matrix dimension does not match for product operation.");
        }
        return prod;
    }
    public void checkMatrixClass() {
        //pruebas de inversion de matrices
        int i, j, rowsD = 4, colsD = 4;
        Matrix matrD = new Matrix();
        matrD.init(rowsD, colsD);
        //double[] arra = {1,2,2,2,4,0,2,0,1,0,1,0,0,1,0,2};
        double[] arra = {1,2,7,2,4,7,2,3,1,5,1,9,3,1,9,2};
        double valu;     
        for (i=0;i<rowsD;i++) {
            for (j=0;j<colsD;j++) {
                valu = arra[i*colsD+j];
                matrD.setValue(i, j, valu);
            }
        }
        matrD.printMatrix("prueba_0");
        Matrix inve = matrD.inverse();
        inve.printMatrix("inversa de prueba_0");
    }
}
