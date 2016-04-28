/*
 * UNIVERSIDAD DE COSTA RICA (UCR)
 * LABORATORIO NACIONAL DE MATERIALES Y MODELOS ESTRUCTURALES (LANAMME)
 * PROGRAMA DE INFRAESTRUCTURA DEL TRANSPORTE (PITRA)
 * UNIDAD DE MATERIALES Y PAVIMENTOS (UMP)
 * 
 * PITRA PAVE - PROGRAMA PARA ANALISIS DE PAVIMENTOS FLEXIBLES BASADO EN LA TEORIA DE MULTICAPA ELASTICA
 * DESARROLLADO POR: FRANCISCO ROJAS PEREZ
 * VERSION 1.1.0
 */
package my.PITRA_PAVE;

/**
 *
 * @author FR
 */

class MultiElasticLayerManager {
    String ID;
    int elasLayeNumb, loadNumb, measPoinNumb;
    double q, a;            // se reemplazara luego con el arreglo de objetos Load
    double r, z;            // se reemplazara luego con el arregle de objetos MeasurePoint
    MeasurePoint[] poin;    // tendra los resultados finales de la superposicion de esfuerzos
    Load[] load;            // cada una tendra los esfuerzos asociadas a su carga
    ElasticLayer[] elasLaye;
    Matrix[] Qs, Qinvs;
    
    public void init(int newlayeNumb) {
	elasLayeNumb = newlayeNumb;
	elasLaye = new ElasticLayer[elasLayeNumb];
	Qs = new Matrix[(2*elasLayeNumb+1)];
	Qinvs = new Matrix[(elasLayeNumb+1)];
    }
    public void init(int newlayeNumb, int newLoadNumb, int newMeasPoinNumb) {
	elasLayeNumb = newlayeNumb;
        loadNumb = newLoadNumb;
        measPoinNumb = newMeasPoinNumb;
	elasLaye = new ElasticLayer[elasLayeNumb];
	Qs = new Matrix[(2*elasLayeNumb+1)];
	Qinvs = new Matrix[(elasLayeNumb+1)];
        load = new Load[loadNumb];
        poin = new MeasurePoint[measPoinNumb];
    }
    public Load load(int inde) {
        if (inde<loadNumb)
            return load[inde];
        else
            return null;
    }
    public int loadNumber() {
        return loadNumb;
    }
    public void loadSet(double newLoad) {
        q = newLoad;
    }
    public void loadSet(Load newLoad, int inde) {
        if (inde<loadNumb)
            load[inde] = newLoad;
    }
    public MeasurePoint measurePoint(int inde) {
        if (inde<measPoinNumb)
            return poin[inde];
        else
            return null;
    }
    public int measurePointNumber(){
        return measPoinNumb;
    }
    public void measurePointSet(MeasurePoint newPoin, int inde) {
        if (inde<measPoinNumb)
            poin[inde] = newPoin;
    }
    public void radiusSet(double newRadi) {
	a= newRadi;
    }
    public void radiusMeasurePointSet(double newRadi) {
	r= newRadi;
    }
    public void depthMeasurePointSet(double newDept) {
	z= newDept;
    }
    public ElasticLayer layer(int inde) {
        if (inde<elasLayeNumb)
            return elasLaye[inde];
        else
            return null;
    }
    public int layerNumber() {
        return elasLayeNumb;
    }
    public void layerPropertiesSet(int inde, double E, double v, double thic) {
        int newType, i;
	double newDept=0.;
	
	if (inde<(elasLayeNumb-1) && inde>0) 
            newType= 2;
	else if (inde==0) 
            newType= 1;
	else if (inde==(elasLayeNumb-1)) 
            newType= 3;
	else
            newType= 0;
	
	for (i=0; i<inde;i++)
            newDept += elasLaye[i].thic;

	elasLaye[inde] = new ElasticLayer();
        elasLaye[inde].init(E, v, newDept, thic, newType, inde);
    }
    public void loadPropertiesSet(int inde, double newLoad, double newRadi, double x, double y) { 
        load[inde] = new Load();
        load[inde].init(newLoad, newRadi, measPoinNumb);
        load[inde].coordinatesSet(x, y);
    }
    public void measurePointPropertiesSet(int inde, double x, double y, double z) {
        poin[inde] = new MeasurePoint();
        poin[inde].init(elasLayeNumb);
        poin[inde].coordinatesSet(x, y, z);
    }
    public String[][] results() {
        String[][] resu = null;
        
        if (measPoinNumb>0){
            resu = new String[measPoinNumb][21];
            int i, j;
            for (i=0;i<measPoinNumb;i++){
                if (poin[i].disp[0]>=0) resu[i][0] = String.format(" %1.4e" , poin[i].disp[0]);
                else                    resu[i][0] =  String.format("%1.4e" , poin[i].disp[0]);
                if (poin[i].disp[1]>=0) resu[i][1] = String.format(" %1.4e" , poin[i].disp[1]);
                else                    resu[i][1] =  String.format("%1.4e" , poin[i].disp[1]);
                if (poin[i].disp[2]>=0) resu[i][2] = String.format(" %1.4e" , poin[i].disp[2]);
                else                    resu[i][2] =  String.format("%1.4e" , poin[i].disp[2]);
                
                if (poin[i].stra[0]>=0) resu[i][3] = String.format(" %1.4e" , poin[i].stra[0]);
                else                    resu[i][3] =  String.format("%1.4e" , poin[i].stra[0]);
                if (poin[i].stra[1]>=0) resu[i][4] = String.format(" %1.4e" , poin[i].stra[1]);
                else                    resu[i][4] =  String.format("%1.4e" , poin[i].stra[1]);
                if (poin[i].stra[2]>=0) resu[i][5] = String.format(" %1.4e" , poin[i].stra[2]);
                else                    resu[i][5] =  String.format("%1.4e" , poin[i].stra[2]);
                if (poin[i].stra[3]>=0) resu[i][6] = String.format(" %1.4e" , poin[i].stra[3]);
                else                    resu[i][6] =  String.format("%1.4e" , poin[i].stra[3]);
                if (poin[i].stra[4]>=0) resu[i][7] = String.format(" %1.4e" , poin[i].stra[4]);
                else                    resu[i][7] =  String.format("%1.4e" , poin[i].stra[4]);
                if (poin[i].stra[5]>=0) resu[i][8] = String.format(" %1.4e" , poin[i].stra[5]);
                else                    resu[i][8] =  String.format("%1.4e" , poin[i].stra[5]);
                
                if (poin[i].stre[0]>=0) resu[i][9] = String.format(" %1.4e" , poin[i].stre[0]);
                else                    resu[i][9] =  String.format("%1.4e" , poin[i].stre[0]);
                if (poin[i].stre[1]>=0) resu[i][10]= String.format(" %1.4e" , poin[i].stre[1]);
                else                    resu[i][10]=  String.format("%1.4e" , poin[i].stre[1]);
                if (poin[i].stre[2]>=0) resu[i][11]= String.format(" %1.4e" , poin[i].stre[2]);
                else                    resu[i][11]=  String.format("%1.4e" , poin[i].stre[2]);
                if (poin[i].stre[3]>=0) resu[i][12]= String.format(" %1.4e" , poin[i].stre[3]);
                else                    resu[i][12]=  String.format("%1.4e" , poin[i].stre[3]);
                if (poin[i].stre[4]>=0) resu[i][13]= String.format(" %1.4e" , poin[i].stre[4]);
                else                    resu[i][13]=  String.format("%1.4e" , poin[i].stre[4]);
                if (poin[i].stre[5]>=0) resu[i][14]= String.format(" %1.4e" , poin[i].stre[5]);
                else                    resu[i][14]=  String.format("%1.4e" , poin[i].stre[5]);
                
                if (poin[i].prinStre[0]>=0) resu[i][15] = String.format(" %1.4e" , poin[i].prinStre[0]);
                else                        resu[i][15] =  String.format("%1.4e" , poin[i].prinStre[0]);
                if (poin[i].prinStre[1]>=0) resu[i][16] = String.format(" %1.4e" , poin[i].prinStre[1]);
                else                        resu[i][16] =  String.format("%1.4e" , poin[i].prinStre[1]);
                if (poin[i].prinStre[2]>=0) resu[i][17] = String.format(" %1.4e" , poin[i].prinStre[2]);
                else                        resu[i][17] =  String.format("%1.4e" , poin[i].prinStre[2]);
                if (poin[i].prinStra[0]>=0) resu[i][18] = String.format(" %1.4e" , poin[i].prinStra[0]);
                else                        resu[i][18] =  String.format("%1.4e" , poin[i].prinStra[0]);
                if (poin[i].prinStra[1]>=0) resu[i][19] = String.format(" %1.4e" , poin[i].prinStra[1]);
                else                        resu[i][19] =  String.format("%1.4e" , poin[i].prinStra[1]);
                if (poin[i].prinStra[2]>=0) resu[i][20] = String.format(" %1.4e" , poin[i].prinStra[2]);
                else                        resu[i][20] =  String.format("%1.4e" , poin[i].prinStra[2]);
            }
        }
        
        return resu;
    }
    public void solveAtMeasurePoint(MeasurePoint measPoin) {
	int i, j = 0;
	boolean belo;
	for (i=0;i<elasLayeNumb;i++) {
            belo= elasLaye[i].pointBelongToLayer(z);
            if (belo)
                break;
	}
        measPoin.elasticLayerNumberSet(i);
	double aa = elasLaye[i].gaussQuadratureParameters(j, load[j], this);
        // esto debe de revisarse... compila pero  j,j no tiene sentido
        // usar el indice j=0 es temporal y se deben recorrer todas las j's cargas
    }
    public void solveAtPoint(double newRadi, double newDept) {
        r = newRadi;
        z = newDept;
	int subI= (int)r/(int)a, i;
	boolean belo;
	
	for (i=0;i<elasLayeNumb;i++) {
		belo= elasLaye[i].pointBelongToLayer(z);
		if (belo)
                    break;
	}
        double aa = elasLaye[i].gaussQuadratureParameters(r, z, q, a, this);
    }
    public void solveMultiElasticLayerSystem() {
	int i, j, k;	//i for layers, j for measure points, k for loads
	boolean belo;
	MeasurePoint measPoin;
	
	for (k=0;k<loadNumb;k++)
            load[k].measurePointNumberSet(measPoinNumb);
	
	for (j=0;j<measPoinNumb;j++) {
            for (i=0;i<elasLayeNumb;i++) {
		belo = elasLaye[i].measurePointBelongToLayer(poin[j]);
                    if (belo)
			break;
		}
            poin[j].elasticLayerNumberSet(i);
            for (k=0;k<loadNumb;k++) {
		measPoin = new MeasurePoint(); measPoin.init(i);  //el indice es j? o es i?
		measPoin.coordinatesSetRelativeTo(poin[j].coordinates(), load[k].coordinates());
		load[k].measurePointSet(measPoin, j);
            }
	}
	for (k=0;k<loadNumb;k++) {
            for (j=0;j<measPoinNumb;j++) {
		i= poin[j].elasticLayerNumber();
		elasLaye[i].gaussQuadratureParameters(j, load[k], this);
            }
	}
	for (j=0;j<measPoinNumb;j++) {
            for (k=0;k<loadNumb;k++) {
	        poin[j].loadSuperimpose(load[k].measurePoint(j));
            }
            i= poin[j].elasticLayerNumber();
            poin[j].principalStressesCalculation(elasLaye[i]);
            poin[j].printResults();
	}
    }
    public void solveForElasticLayerConstantsAtParameterM(double m) {
        int i, j=1;
	for (i=0; i<elasLayeNumb; i++)
            elasLaye[i].calculateMatricesAtParameter(m);
	
	for (i=0; i<elasLayeNumb; i++) {
            //System.out.printf("\nlayer: %d\n",i);
            Qs[j] = elasLaye[i].firstMatrix();  //Qs[j].printMatrix(null);
            j++;
            Qs[j] = elasLaye[i].secondMatrix(); //Qs[j].printMatrix(null);
            j++;
	}
	Matrix Qinv, solu, unit;
	unit = new Matrix();
        unit.init(2, 1);
	unit.matr[0][0] = 1.;
	unit.matr[1][0] = 0.;
	
	for (i=1; i<elasLayeNumb; i++) {
            Qinv = Qs[2*i].inverse();
            Qinvs[i] = Qinv.product(Qs[2*i+1]);
	}
	Qinv=Qs[1];
	for (i=1; i<elasLayeNumb; i++) {
            Qinv = Qinv.product(Qinvs[i]);
	}
	Qinvs[elasLayeNumb] = Qinv.inverse();
	
	solu = Qinvs[elasLayeNumb].product(unit);			//printf("\nValor de m=%e\n",m); printf("\nsoluN");	[solu printMatrix:""];
	elasLaye[elasLayeNumb-1].assignLayerParameters(solu);
	for (i=(elasLayeNumb-1); i>0; i--) {
		solu = Qinvs[i].product(solu);						//printf("\nsolu_%d",i);	[solu printMatrix:""];
		elasLaye[i-1].assignLayerParameters(solu);
	}
    }
}
