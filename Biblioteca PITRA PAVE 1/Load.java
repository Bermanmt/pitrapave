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
class Load {
    // shape types (shap)
    public static final int UNAS = 0;
    public static final int CIRC = 1;
    public static final int RECT = 2;
    public static final int ELIP = 3;
    
    // distribution types (dist)
    public static final int UNIF = 1;
    public static final int NOUN = 2;
    
    // types (type)
    public static final int VERT = 1;
    public static final int HORI = 2;
    public static final int VEHO = 3;       //vertical + horizontal
    public static final int GRAV = 4;       //gravity
    
    String ID;
    double load, pres, radi;
    double[] coor;
    int measPoinNumb;
    MeasurePoint[] poin;
    int shap, dist, type;
    
    public void init(double newLoad, double newPres, int newMeasPoinNumb) {
        load = newLoad;
        pres = newPres;
        radi = Math.sqrt(load/(Math.PI*pres));
        measPoinNumb = newMeasPoinNumb;
        coor = new double[2];
        poin = new MeasurePoint[measPoinNumb];
        shap = CIRC;    dist = UNIF;    type = VERT;
    }
    
    public void init(double newLoad, double newPres, double newRadi, int newMeasPoinNumb) {
        load = newLoad;
        pres = newPres;
        radi = newRadi;
        measPoinNumb = newMeasPoinNumb;
        coor = new double[2];
        poin = new MeasurePoint[measPoinNumb];
        shap = CIRC;    dist = UNIF;    type = VERT;
    }
    
    public double[] coordinates(){
        return coor;
    }
    
    public void coordinatesSet(double newCoorX, double newCoorY) {
        coor[0] = newCoorX;
        coor[1] = newCoorY;
    }
    
    public double load() {
        return load;
    }
    
    public void loadSet(double newLoad) {
        load = newLoad;
    }
    
    public MeasurePoint measurePoint(int inde) {
        return poin[inde];
    }
    
    public void measurePointSet(MeasurePoint measPoin, int inde) {
        poin[inde] = measPoin;
    }
    
    public void measurePointNumberSet(int newPoinNumb) {
        measPoinNumb = newPoinNumb;
    }
    
    public double pressure() {
        return pres;
    }
    
    public void pressureSet(double newPres) {
        pres = newPres;
    }
    
    public double radius() {
        return radi;
    }
    
    public void radiusSet(double newRadi) {
        radi = newRadi;
    }
}
