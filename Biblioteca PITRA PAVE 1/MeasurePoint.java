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
class MeasurePoint {
    String ID;
    int elasLaye;
    double[] coor, axisCoor;
    double[] stre, stra, disp, axisStre, axisStra, axisDisp, prinStre, prinStra;
    
    public void init (int newElasLaye) {
        ID = String.valueOf(newElasLaye);
        elasLaye = newElasLaye;
        coor = new double[3];
	axisCoor = new double[3];
	//elasLaye = 0;
	disp = new double[3];
	stre = new double[6];
	stra = new double[6];
	axisDisp = new double[3];	
	axisStre = new double[6];
	axisStra = new double[6];
        prinStre = new double[3];
        prinStra = new double[3];
    }
    
    public void loadSuperimpose(MeasurePoint poin) {
        int i;
        for (i=0;i<3;i++){
            disp[i] = disp[i] + poin.displacement()[i];
        }
        for (i=0;i<6;i++){
            stre[i] = stre[i] + poin.stress()[i];
            stra[i] = stra[i] + poin.strain()[i];
        }
    }
    
    public double[] principalStrains() {
        return prinStra;
    }
    
    public double[] principalStresses() {
        return prinStre;
    }
    
    public void principalStressesCalculation(ElasticLayer laye) {
        //... codigo para el calculo de los esfuerzos principales v1.1.0
        double a, b, c, Q, R, D, H, C, S, T;
        a = stre[0]*stre[5]*stre[5] + stre[1]*stre[4]*stre[4] +stre[2]*stre[3]*stre[3] - 2*stre[3]*stre[4]*stre[5] - stre[0]*stre[1]*stre[2];
        b = stre[0]*stre[1] + stre[0]*stre[2] + stre[1]*stre[2] - stre[3]*stre[3] - stre[4]*stre[4] - stre[5]*stre[5];
        c = (stre[0] + stre[1] + stre[2])*-1;
        Q = 1./9. * (3.*b-c*c);
        R = 1./54. * (9.*c*b-27.*a-2.*c*c*c);
        D = Q*Q*Q + R*R;
        if ( D < 0 ) {
            H = R / Math.sqrt(-1*Q*Q*Q);
            C = Math.acos(H);
            prinStre[0] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3)-c/3;
            prinStre[1] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3+4./3*Math.PI)-c/3;
            prinStre[2] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3+2./3*Math.PI)-c/3;
        }
        else if ( D >= 0 ) {
            S = Math.pow(R + Math.sqrt(D), 1./3.);
            T = Math.pow(R - Math.sqrt(D), 1./3.);
            prinStre[0] = S + T -1./3.*c;
            prinStre[1] = -1./2.*(S + T) - 1./3.*c;
            prinStre[2] = -1./2.*(S + T) - 1./3.*c;
            if ( S != S || T !=T ) {
                H = R / Math.sqrt(-1*Q*Q*Q);
                if (H<-1)
                    H = -1;
                else if (H>1)
                    H = 1;
                
                C = Math.acos(H);
                prinStre[0] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3)-c/3;
                prinStre[1] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3+4./3*Math.PI)-c/3;
                prinStre[2] = 2.*Math.sqrt(-1.*Q)*Math.cos(C/3+2./3*Math.PI)-c/3;
            }
            
            if ( prinStre[2] > prinStre[0] ) {
                prinStre[2] = S + T -1./3.*c;
                prinStre[0] = -1./2.*(S + T) - 1./3.*c;
            }
        }
        
        prinStra[0] = 1./laye.E*(prinStre[0]-laye.v*(prinStre[1]+prinStre[2]));
        prinStra[1] = 1./laye.E*(prinStre[1]-laye.v*(prinStre[2]+prinStre[0]));
        prinStra[2] = 1./laye.E*(prinStre[2]-laye.v*(prinStre[0]+prinStre[1]));
        /*else {
            //error, esto no puede ocurrir
            System.out.printf("\nD>0 -> Error: soluciones complejas de los autovalores!");
            System.out.printf("\nD= %e",D);
        }*/
        //... codigo para el calculo de los esfuerzos principales v1.1.0
    }
    
    public double[] coordinates() {
        return coor;
    }
    
    public void coordinatesSet(double newCoorX, double newCoorY, double newCoorZ) {
        coor[0] = newCoorX;
	coor[1] = newCoorY;
	coor[2] = newCoorZ;
	
	axisCoor[0] = Math.sqrt(coor[0]*coor[0]+coor[1]*coor[1]);
	axisCoor[2] = coor[2];
	if ( Math.sqrt(coor[0]*coor[0])<1.e-10 ) {
		if ( Math.sqrt(coor[1]*coor[1])<1.e-10 ) {
                    axisCoor[1] = 0.;
                }
                else if ( coor[1]>0. ) {
                    axisCoor[1] = 1./2.*Math.PI;
		}
		else {
                    axisCoor[1] = 3./2.*Math.PI;
		}
	}
	else if ( coor[0]<0. ) {
		axisCoor[1] = Math.PI + Math.atan(coor[1]/coor[0]);
	}
	else {
		if ( coor[1]>=0.) {
			axisCoor[1] = Math.atan(coor[1]/coor[0]);
		}
		else {
			axisCoor[1] = 2*Math.PI + Math.atan(coor[1]/coor[0]);
		}
	}
    }
    
    public void coordinatesSetRelativeTo(double[] newCoor, double[] loadCoor) {
        coor[0] = newCoor[0]-loadCoor[0];
	coor[1] = newCoor[1]-loadCoor[1];
	coor[2] = newCoor[2];
	
	axisCoor[0] = Math.sqrt(coor[0]*coor[0]+coor[1]*coor[1]);
	axisCoor[2] = coor[2];
	if ( Math.sqrt(coor[0]*coor[0])<1.e-10 ) {
		if ( Math.sqrt(coor[1]*coor[1])<1.e-10 ) {
                    axisCoor[1] = 0.;
                }
                else if ( coor[1]>0. ) {
                    axisCoor[1] = 1./2.*Math.PI;
		}
		else {
                    axisCoor[1] = 3./2.*Math.PI;
		}
	}
	else if ( coor[0]<0. ) {
		axisCoor[1] = Math.PI + Math.atan(coor[1]/coor[0]);
	}
	else {
		if ( coor[1]>=0.) {
			axisCoor[1] = Math.atan(coor[1]/coor[0]);
		}
		else {
			axisCoor[1] = 2*Math.PI + Math.atan(coor[1]/coor[0]);
		}
	}
    }
    
    public int elasticLayerNumber() {
        return elasLaye;
    }
    
    public void elasticLayerNumberSet(int layeNumb) {
        elasLaye= layeNumb;
    }
    
    public double[] displacement() {
        return disp;
    }
    
    public void displacementSet(double dx, double dy, double dz) {
        disp[0] = dx;
	disp[1] = dy;
	disp[2] = dz;
    }
    
    public double[] strain() {
        return stra;
    }
    
    public void strainSet(double straXX, double straYY, double straZZ, double straXY, double straXZ, double straYZ) {
	stra[0] = straXX;
	stra[1] = straYY;
	stra[2] = straZZ;
	stra[3] = straXY;
	stra[4] = straXZ;
	stra[5] = straYZ;
    }
    
    public double[] stress() {
        return stre;
    }
    
    public void stressSet(double streXX, double streYY, double streZZ, double streXY, double streXZ, double streYZ) {
	stre[0] = streXX;
	stre[1] = streYY;
	stre[2] = streZZ;
	stre[3] = streXY;
	stre[4] = streXZ;
	stre[5] = streYZ;
    }
    
    public double aCoordinate() {
	return axisCoor[1];
    }

    public double rCoordinate() {
	return axisCoor[0];
    }

    public double zCoordinate() {
	return axisCoor[2];
    }
    
    public void printResults() {
        System.out.print("\nEvaluation Point: (x,y,z)=("+coor[0]+","+coor[1]+","+coor[2]+")");
        System.out.printf("\nSxx= %e",stre[0]);
        System.out.printf("\nSyy= %e",stre[1]);
        System.out.printf("\nSzz= %e",stre[2]);
        System.out.printf("\nSxy= %e",stre[3]);
        System.out.printf("\nSxz= %e",stre[4]);
        System.out.printf("\nSyz= %e",stre[5]);
        //System.out.println();
        System.out.printf("\nExx= %e",stra[0]);
        System.out.printf("\nEyy= %e",stra[1]);
        System.out.printf("\nEzz= %e",stra[2]);
        System.out.printf("\nExy= %e",stra[3]);
        System.out.printf("\nExz= %e",stra[4]);
        System.out.printf("\nEyz= %e",stra[5]);
        //System.out.println();
        System.out.printf("\nu=   %e",disp[0]);
        System.out.printf("\nv=   %e",disp[1]);
        System.out.printf("\nw=   %e",disp[2]);
        //System.out.println();
        System.out.printf("\nS1= %e",prinStre[0]);
        System.out.printf("\nS2= %e",prinStre[1]);
        System.out.printf("\nS3= %e",prinStre[2]);
        System.out.printf("\nE1= %e",prinStra[0]);
        System.out.printf("\nE2= %e",prinStra[1]);
        System.out.printf("\nE3= %e",prinStra[2]);
        System.out.println();
    }
    public String printInformationXML() {
        String info = new String();
        info = "<MeasurePoint>\n";
        //info = info.concat("\t<ID>" + ID + "</ID>" + "\n");
        info = info + "\t<ID>" + ID + "</ID>" + "\n";
        info = info + "\t<xCoor>" + coor[0] + "</xCoor>" + "\n";
        info = info + "\t<yCoor>" + coor[1] + "</yCoor>" + "\n";
        info = info + "\t<zCoor>" + coor[2] + "</zCoor>" + "\n";
        info = info + "</MeasurePoint>\n";
        //info = info.concat("</MeasurePoint>\n");
        return info;
    }
}
