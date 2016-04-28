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
class ElasticLayer {
    String ID;
    public static final int UNAS = 0;
    public static final int FIRS = 1;
    public static final int INTE = 2;
    public static final int LAST = 3;

    double E, v;
    double A, B, C, D;
    double dept, thic;
    int type, inde;
    Matrix Qa, Qb;
    
    double[][] gausWeig= {
	{2.,0.,0.,0.,0.,0.,0.,0.,0.,0.},
	{1.,1.,0.,0.,0.,0.,0.,0.,0.,0.},
	{0.555555555555556,0.888888888888889,0.555555555555556,0.,0.,0.,0.,0.,0.,0.},
	{0.347854845137454,0.652145154862546,0.652145154862546,0.347854845137454,0.,0.,0.,0.,0.,0.},
	{0.236926885056189,0.478628670499366,0.568888888888889,0.478628670499366,0.236926885056189,0.,0.,0.,0.,0.},
	{0.171324492379170,0.360761573048139,0.467913934572691,0.467913934572691,0.360761573048139,0.171324492379170,0.,0.,0.,0.},
	{0.1294849662,0.2797053915,0.3818300505,0.4179591837,0.3818300505,0.2797053915,0.1294849662,0.,0.,0.},
	{0.1012285363,0.2223810345,0.3137066459,0.3626837834,0.3626837834,0.3137066459,0.2223810345,0.1012285363,0.,0.},
	{0.0812743883,0.1806481607,0.2606106964,0.3123470770,0.3302393550,0.3123470770,0.2606106964,0.1806481607,0.0812743883,0.},
	{0.0666713443,0.1494513492,0.2190863625,0.2692667193,0.2955242247,0.2955242247,0.2692667193,0.2190863625,0.1494513492,0.0666713443}
    };
    double[][] legeRoot= {
	{0., 0., 0., 0., 0., 0., 0., 0., 0., 0.},
	{-0.577350269189626, 0.577350269189626, 0., 0., 0., 0., 0., 0., 0., 0.},
	{-0.774596669241483, 0., 0.774596669241483, 0. ,0. , 0., 0., 0., 0., 0.},
	{-0.861136311594053,-0.339981043584856, 0.339981043584856, 0.861136311594053, 0., 0., 0., 0., 0., 0.},
	{-0.906179845938664,-0.538469310105683,	0.,	0.538469310105683, 0.906179845938664, 0., 0., 0., 0., 0.},
	{-0.932469514203152,-0.661209386466265,-0.238619186083197, 0.238619186083197, 0.661209386466265, 0.932469514203152, 0., 0., 0., 0.},
	{-0.9491079123, -0.7415311856, -0.4058451514,0.,0.4058451514,0.7415311856,0.9491079123,0.,0.,0.},
	{-0.9602898565, -0.7966664774, -0.5255324099, -0.1834346425, 0.1834346425, 0.5255324099, 0.7966664774, 0.9602898565,0.,0.},
	{-0.9681602395, -0.8360311073, -0.6133714327, -0.3242534234, 0., 0.3242534234, 0.6133714327, 0.8360311073, 0.9681602395,0.},
	{-0.9739065285, -0.8650633667, -0.6794095683, -0.4333953941, -0.1488743390, 0.1488743390, 0.4333953941, 0.6794095683, 0.8650633667, 0.9739065285}
    };
    double[] ns= {0.,3.8375,7.0165,10.1775,13.3245,18.071064,//16.47,				//limite para 40 capas (a=5, z=196)(p=05)
	19.619,22.761,25.9025,29.0485,33.77582,//32.192,					//limite para 19 capas (a=5, z=105)(p=10)
	35.3315,38.474,41.6195,44.76,47.9,51.0435,54.1855,57.3275,60.47,62.04847,//63.61,	//limite para 08 capas (a=5, z=057)(p=20)
	66.753,69.895,73.037,76.1785,79.3205,82.40,85.75,88.75,91.90,96.605268,//95.25		//limite para 04 capas (a=5, z=036)(p=30)
	98.15,101.25,104.25,107.55,110.90,113.75,117.05,120.25,123.20,128.020878,//126.55	//limite para 03 capas (a=5, z=027)(p=40)
	129.3,132.75,136.05,139.05,142.10,145.30,148.25,151.6,154.55,159.43661,//158.05		//limite para 03 capas (a=5, z=022)(p=50)
	161.10,164.05,167.25,170.55,173.30,176.55,179.80,183.05,186.05,190.85241,//189.20	//limite para 03 capas (a=5, z=018)(p=60)
	192.40,195.65,198.55,201.55,204.95,208.20,211.2,214.15,217.55,222.268243,//220.75	//limite para 02 capas (a=5, z=015)(p=70)
	223.9,227.55,230.15,233.25,236.35,239.60,242.65,245.9,248.85,250.5425135};//252.1	//limite para 02 capas (a=5, z=014)(p=80)
    
    //implementacion especial j0, diferencias importantes en los ceros de la funcion, inicia divergencia despues de x=1000000000
    public double j_0(double x) {
	double ax;
	if( (ax=Math.abs(x)) < 8.0 ) {
            double y=x*x;
            double ans1=57568490574.0+y*(-13362590354.0+y*(651619640.7+y*(-11214424.18+y*(77392.33017+y*(-184.9052456)))));
            double ans2=57568490411.0+y*(1029532985.0+y*(9494680.718+y*(59272.64853+y*(267.8532712+y*1.0))));
            return ans1/ans2;	
	}
	else {
            double z=8.0/ax;
            double y=z*z;
            double xx=ax-0.785398164;
            double ans1=1.0+y*(-0.1098628627e-2+y*(0.2734510407e-4+y*(-0.2073370639e-5+y*0.2093887211e-6)));
            double ans2 = -0.1562499995e-1+y*(0.1430488765e-3+y*(-0.6911147651e-5+y*(0.7621095161e-6-y*0.934935152e-7)));
            return Math.sqrt(0.636619772/ax)*(Math.cos(xx)*ans1-z*Math.sin(xx)*ans2);
	}
    }
    //implementacion basica j0 en serie de Taylor alrededor de x=0, diferencias en la tercer cifra apartir de x=30, inaceptable despues de x=37, inicia divergencia despues de x=51
    public double j_0_b(double x) {
	int k, k_max= 60;
	double f1, f2, f3=1., j0x=0.;
	for (k=0;k<=k_max;k++) {
            f1= Math.pow(-1., k);
            f2= Math.pow(x/2., 2*k);
            j0x+=f1*f2/(f3*f3);
            f3*= (double)(k+1);
	}
	return j0x;
    }
    //implementacion especial j1, diferencias importantes en los ceros de la funcion, inicia divergencia despues de x=1000000000
    public double j_1(double x) {
	double ax;
	double y;
	double ans1, ans2;
	if ((ax=Math.abs(x)) < 8.0) {
            y=x*x;
            ans1=x*(72362614232.0+y*(-7895059235.0+y*(242396853.1+y*(-2972611.439+y*(15704.48260+y*(-30.16036606))))));
            ans2=144725228442.0+y*(2300535178.0+y*(18583304.74+y*(99447.43394+y*(376.9991397+y*1.0))));
            return ans1/ans2;
	}
	else {
            double z=8.0/ax;
            double xx=ax-2.356194491;
            y=z*z;
            ans1=1.0+y*(0.183105e-2+y*(-0.3516396496e-4+y*(0.2457520174e-5+y*(-0.240337019e-6))));
            ans2=0.04687499995+y*(-0.2002690873e-3+y*(0.8449199096e-5+y*(-0.88228987e-6+y*0.105787412e-6)));
            double ans=Math.sqrt(0.636619772/ax)*(Math.cos(xx)*ans1-z*Math.sin(xx)*ans2);
            if (x < 0.0)
                ans = -ans;
            return ans;
	}
    }
    //implementacion basica j1 en serie de Taylor alrededor de x=0, diferencias en la tercer cifra apartir de x=30, inaceptable despues de x=37,  inicia divergencia despues de x=51
    public double j_1_b(double x) {
	int k, k_max= 60;
	double f1, f2, f3=1., f4=1., j1x=0.;
	for (k=0;k<=k_max;k++) {
            f1= Math.pow(-1., k);
            f2= Math.pow(x/2., 1+(2*k));
            j1x+=f1*f2/(f3*f4);
            f3*= (double)(k+1);
            f4= (double)f3*(k+2);
	}
	return j1x;
    }

    public void init(double newYoun, double newPois, double newDept, double newThic, int newType, int newInde) {
        E= newYoun;
	v= newPois;
	dept= newDept;
	thic= newThic;
	type= newType;
	inde= newInde;
	if (type==FIRS) {
		Qa= new Matrix(); Qa.init(2, 4);
		Qb= new Matrix(); Qb.init(4, 4);
	}
	else if (type==INTE) {
		Qa= new Matrix(); Qa.init(4, 4);
		Qb= new Matrix(); Qb.init(4, 4);
	}
	else if (type==LAST) {
		Qa= new Matrix(); Qa.init(4, 2);
		A= 0.;
		C= 0.;
		//thic= 10000.;
	}
	else {
            //...
	}
    }
    
    public void calculateMatricesAtParameter(double m) {
        double mh= m*thic, mhn= -1.*m*thic, m2= m*m, a= (1.+v)/E, v2=2.*v, v4=4.*v;
	
	if (type==FIRS) {
            // Q1 matrix
            Qa.setValue(0, 0, m2);
            Qa.setValue(0, 1, m2);
            Qa.setValue(0, 2, -m*(1.-v2));
            Qa.setValue(0, 3, m*(1.-v2));
            
            Qa.setValue(1, 0, m2);
            Qa.setValue(1, 1, -m2);
            Qa.setValue(1, 2, m*v2);
            Qa.setValue(1, 3, m*v2);
		
            // Q matrix (at depth)
            Qb.setValue(0, 0, a*m*Math.exp(mh));
            Qb.setValue(0, 1, -a*m*Math.exp(mhn));
            Qb.setValue(0, 2, -a*(2.-v4-mh)*Math.exp(mh));
            Qb.setValue(0, 3, -a*(2.-v4+mh)*Math.exp(mhn));
	
            Qb.setValue(1, 0, a*m*Math.exp(mh));
            Qb.setValue(1, 1, a*m*Math.exp(mhn));
            Qb.setValue(1, 2, a*(1.+mh)*Math.exp(mh));
            Qb.setValue(1, 3, -a*(1.-mh)*Math.exp(mhn));
	
            Qb.setValue(2, 0, m*Math.exp(mh));
            Qb.setValue(2, 1, m*Math.exp(mhn));
            Qb.setValue(2, 2, -(1.-v2-mh)*Math.exp(mh));
            Qb.setValue(2, 3, (1.-v2+mh)*Math.exp(mhn));
		
            Qb.setValue(3, 0, m*Math.exp(mh));
            Qb.setValue(3, 1, -m*Math.exp(mhn));
            Qb.setValue(3, 2, (v2+mh)*Math.exp(mh));
            Qb.setValue(3, 3, (v2-mh)*Math.exp(mhn));
	}
	else if (type==INTE) {
            // Q matrix (at depth)
            mh=m*dept; mhn=-1.*mh;
		
            Qa.setValue(0, 0, a*m*Math.exp(mh));
            Qa.setValue(0, 1, -a*m*Math.exp(mhn));
            Qa.setValue(0, 2, -a*(2.-v4-mh)*Math.exp(mh));
            Qa.setValue(0, 3, -a*(2.-v4+mh)*Math.exp(mhn));
            
            Qa.setValue(1, 0, a*m*Math.exp(mh));
            Qa.setValue(1, 1, a*m*Math.exp(mhn));
            Qa.setValue(1, 2, a*(1.+mh)*Math.exp(mh));
            Qa.setValue(1, 3, -a*(1.-mh)*Math.exp(mhn));
            
            Qa.setValue(2, 0, m*Math.exp(mh));
            Qa.setValue(2, 1, m*Math.exp(mhn));
            Qa.setValue(2, 2, -(1.-v2-mh)*Math.exp(mh));
            Qa.setValue(2, 3, (1.-v2+mh)*Math.exp(mhn));
		
            Qa.setValue(3, 0, m*Math.exp(mh));
            Qa.setValue(3, 1, -m*Math.exp(mhn));
            Qa.setValue(3, 2, (v2+mh)*Math.exp(mh));
            Qa.setValue(3, 3, (v2-mh)*Math.exp(mhn));
            
            // Q matrix (at depth+thickness)
            mh=m*(dept+thic); mhn=-1.*mh;
		
            Qb.setValue(0, 0, a*m*Math.exp(mh));
            Qb.setValue(0, 1, -a*m*Math.exp(mhn));
            Qb.setValue(0, 2, -a*(2.-v4-mh)*Math.exp(mh));
            Qb.setValue(0, 3, -a*(2.-v4+mh)*Math.exp(mhn));
		
            Qb.setValue(1, 0, a*m*Math.exp(mh));
            Qb.setValue(1, 1, a*m*Math.exp(mhn));
            Qb.setValue(1, 2, a*(1.+mh)*Math.exp(mh));
            Qb.setValue(1, 3, -a*(1.-mh)*Math.exp(mhn));
		
            Qb.setValue(2, 0, m*Math.exp(mh));
            Qb.setValue(2, 1, m*Math.exp(mhn));
            Qb.setValue(2, 2, -(1.-v2-mh)*Math.exp(mh));
            Qb.setValue(2, 3, (1.-v2+mh)*Math.exp(mhn));
		
            Qb.setValue(3, 0, m*Math.exp(mh));
            Qb.setValue(3, 1, -m*Math.exp(mhn));
            Qb.setValue(3, 2, (v2+mh)*Math.exp(mh));
            Qb.setValue(3, 3, (v2-mh)*Math.exp(mhn));
	}
	else if (type==LAST) {
            // Q matrix (at depth) (Q 2n-1)
            mh=m*dept; mhn=-1.*mh;
		
            Qa.setValue(0, 0, -a*m*Math.exp(mhn));
            Qa.setValue(0, 1, -a*(2.-v4+mh)*Math.exp(mhn));
		
            Qa.setValue(1, 0, a*m*Math.exp(mhn));
            Qa.setValue(1, 1, -a*(1.-mh)*Math.exp(mhn));
		
            Qa.setValue(2, 0, m*Math.exp(mhn));
            Qa.setValue(2, 1, (1.-v2+mh)*Math.exp(mhn));
		
            Qa.setValue(3, 0, -m*Math.exp(mhn));
            Qa.setValue(3, 1, (v2-mh)*Math.exp(mhn));
	}
    }
    public double gaussQuadratureParameters (double r, double z, double q, double a, MultiElasticLayerManager mlel) {
	int i, j, p= 5, n= 10;	//a partir de n=5 se ven diferencias en la setima cifra significativa del streZZ en todos los puntos (con r=0)
	
	double mMini, mMaxi, jaco, m;
	double streZZ= 0., streRR= 0., streRZ= 0., streTT= 0., u= 0., w= 0.;
	double mr, ma, mz, m2, mzn, j0_mr, j1_mr, j1_mr_mr, j1_ma;
	double streXX, streYY, streXZ, straXX, straYY, straZZ, straXZ;
	
	int subI= (int)r/(int)a, k;
	
	if (subI<2) {
		for (j=0;j<p;j++) {
			mMini= ns[j]/a; mMaxi= ns[j+1]/a; jaco= (mMaxi-mMini)/2.;
			for (i=0;i<n;i++){
				m= (mMaxi-mMini)/2.*legeRoot[n-1][i] + (mMaxi+mMini)/2.;
				mlel.solveForElasticLayerConstantsAtParameterM(m);
				mr= m*r; ma= m*a; mz= m*z; m2= m*m; mzn= -1.*mz;
				//j0_mr= j0(mr); j1_mr= j1(mr); j1_mr_mr= j1(mr)/mr; j1_ma= j1(ma);
				if (subI<1) {
                                    j0_mr= j_0_b(mr); j1_mr= j_1_b(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1_b(ma);
                                }
                                else {
                                    j0_mr= j_0(mr); j1_mr= j_1(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1(ma);
                                }
				if (mr<1.e-10) j1_mr_mr=0.5;
                                
				//
				/*double j0r, j1a, j1r, j0rf, j1af, j1rf;
				j0r=[self j_0:mr];
				j1r=[self j_1:mr];
				j1a=[self j_1:ma];
				j0rf=[self j_0_b:mr];
				j1rf=[self j_1_b:mr];
				j1af=[self j_1_b:ma];*/
				/*printf("\nj0_r(%f)=%.15e \nj0_s(%f)=%.15e \nj0_f(%f)=%.15e",mr,j0_mr,mr,j0r,mr,j0rf);
				printf("\nj1_r(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e",mr,j1_mr,mr,j1r,mr,j1rf);
				printf("\nj1_a(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e\n",ma,j1_ma,ma,j1a,ma,j1af);*/
				//
				//if (subI<1) { j0_mr= j0rf, j1_mr= j1rf, j1_mr_mr= j1rf/mr, j1_ma= j1af; } else { j0_mr= j0r, j1_mr= j1r, j1_mr_mr= j1_mr/mr, j1_ma= j1af; }
				//if (mr<1.e-10) j1_mr_mr=0.5;
				
				streZZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * ( -m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)-C*m*(1.-2.*v-mz)*Math.exp(mz)+D*m*(1.-2.*v+mz)*Math.exp(mzn)) );
				streRZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j1_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)+C*m*(2.*v+mz)*Math.exp(mz)+D*m*(2.*v-mz)*Math.exp(mzn)) );
				streRR+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+2.*v+mz)*Math.exp(mz)-D*m*(1.+2.*v-mz)*Math.exp(mzn))
																   -m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
				streTT+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  2.*v*m2*j0_mr*(C*Math.exp(mz)-D*Math.exp(mzn))
																   +m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
				w+=		 gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  (1.+v)/E * j0_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)-C*m*(2.-4.*v-mz)*Math.exp(mz)-D*m*(2.-4.*v+mz)*Math.exp(mzn)) );
				u+=		 gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  -1.*(1.+v)/E * j1_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
			}
			//printf("\nnA=%e->nB=%e:  SZZ=%e",mMini,mMaxi,streZZ);
		}
	}
	else {
		double mMiniSubI, mMaxiSubI, mInte;
		for (j=0;j<p;j++){
			//printf("\nInterval:%d From nA=%e to nB=%e",j,ns[j],ns[j+1]);
			for	(k=0;k<subI;k++) {
				mMiniSubI= ns[j]/a; mMaxiSubI= ns[j+1]/a; mInte= (mMaxiSubI-mMiniSubI)/(1.*subI);
				mMini= mMiniSubI+k*mInte; mMaxi= mMiniSubI+(k+1)*mInte; jaco= (mMaxi-mMini)/2.;
				for (i=0;i<n;i++){
					m= (mMaxi-mMini)/2.*legeRoot[n-1][i] + (mMaxi+mMini)/2.;
					mlel.solveForElasticLayerConstantsAtParameterM(m);
					mr= m*r; ma= m*a; mz= m*z; m2= m*m; mzn= -1.*mz;
					//j0_mr= j0(mr), j1_mr= j1(mr), j1_mr_mr= j1(mr)/mr, j1_ma= j1(ma);
					j0_mr= j_0(mr); j1_mr= j_1(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1(ma);
                                        if (mr<1.e-10) j1_mr_mr=0.5;
					//
					/*double j0r, j1a, j1r, j0rf, j1af, j1rf;
					j0r=[self j_0:mr];
					j1r=[self j_1:mr];
					j1a=[self j_1:ma];
					j0rf=[self j_0_b:mr];
					j1rf=[self j_1_b:mr];
					j1af=[self j_1_b:ma];*/
					/*printf("\nj0_r(%f)=%.15e \nj0_s(%f)=%.15e \nj0_f(%f)=%.15e",mr,j0_mr,mr,j0r,mr,j0rf);
					printf("\nj1_r(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e",mr,j1_mr,mr,j1r,mr,j1rf);
					printf("\nj1_a(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e\n",ma,j1_ma,ma,j1a,ma,j1af);*/
					//
					//j0_mr= j0r, j1_mr= j1r, j1_mr_mr= j1_mr/mr, j1_ma= j1a;
					//if (mr<1.e-10) j1_mr_mr=0.5;
					
					streZZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * ( -m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)-C*m*(1.-2.*v-mz)*Math.exp(mz)+D*m*(1.-2.*v+mz)*Math.exp(mzn)) );
					streRZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j1_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)+C*m*(2.*v+mz)*Math.exp(mz)+D*m*(2.*v-mz)*Math.exp(mzn)) );
					streRR+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+2.*v+mz)*Math.exp(mz)-D*m*(1.+2.*v-mz)*Math.exp(mzn))
																		-m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
					streTT+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  2.*v*m2*j0_mr*(C*Math.exp(mz)-D*Math.exp(mzn))
																		+m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
					w+=		 gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  (1.+v)/E * j0_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)-C*m*(2.-4.*v-mz)*Math.exp(mz)-D*m*(2.-4.*v+mz)*Math.exp(mzn)) );
					u+=		 gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  -1.*(1.+v)/E * j1_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
				}
				//printf("\nnA=%e->nB=%e:  SZZ=%e",mMini,mMaxi,streZZ);
			}
		}
	}
	/*
	 printf("\nEvaluation Point: (r,z)=(%e,%e)",r,z);
	 printf("\nSzz= %e",streZZ);
	 printf("\nSrz= %e",streRZ);
	 printf("\nSrr= %e",streRR);
	 printf("\nStt= %e",streTT);
	 printf("\nw=   %e",w);
	 printf("\nu=   %e",u);*/
	
	// transformacion de esfuerzos (cilindricas->cartesiano) para un angulo cita=0
	streXX= streRR;
	streYY= streTT;
	streXZ= streRZ;
	
	// calculo de deformaciones unitarias en sistema cartesiano
	straXX= 1./E*(streXX-v*(streYY+streZZ));
	straYY= 1./E*(streYY-v*(streZZ+streXX));
	straZZ= 1./E*(streZZ-v*(streXX+streYY));
	straXZ= 2*(1.+v)/E*streXZ;
	
	System.out.print("\nEvaluation Point: (r,z)=("+r+","+z+")");
        System.out.printf("\nSxx= %e",streXX);
        System.out.printf("\nSyy= %e",streYY);
        System.out.printf("\nSzz= %e",streZZ);
        System.out.printf("\nSxz= %e",streXZ);
        //System.out.println();
        System.out.printf("\nExx= %e",straXX);
        System.out.printf("\nEyy= %e",straYY);
        System.out.printf("\nEzz= %e",straZZ);
        System.out.printf("\nExz= %e",straXZ);
        //System.out.println();
        System.out.printf("\nu=   %e",u);
        System.out.printf("\nw=   %e",w);
        System.out.println();
        
/*      printf("\nEvaluation Point: (r,z)=(%e,%e)",r,z);
	printf("\nSxx= %e",streXX);
	printf("\nSyy= %e",streYY);
	printf("\nSzz= %e",streZZ);
	printf("\nSxz= %e",streXZ);
	//printf("\n");
	printf("\nExx= %e",straXX);
	printf("\nEyy= %e",straYY);
	printf("\nEzz= %e",straZZ);
	printf("\nExz= %e",straXZ);
	//printf("\n");
	printf("\nu=   %e",u);
	printf("\nw=   %e",w);
	printf("\n");*/
	
	return streZZ;
    }
    
    public double gaussQuadratureParameters(int inde, Load load, MultiElasticLayerManager mlel) {
	double r, z, alph, q, a, subI_2;
        MeasurePoint poin = load.measurePoint(inde);
        r = poin.rCoordinate();
        z = poin.zCoordinate();
        alph=poin.aCoordinate();
        q = load.pressure();
        a = load.radius();
        
        int i, j, p= 5, n= 10;	//a partir de n=5 se ven diferencias en la setima cifra significativa del streZZ en todos los puntos (con r=0)
	//codigo de evaluación para mejorar preciscion numerica... debe de evaluarse exahustivamente v1.5.0
        if (mlel.elasLayeNumb>=10) p=5;
        else    p=10;
	//codigo de evaluación para mejorar preciscion numerica... debe de evaluarse exahustivamente v1.5.0
        
	double mMini, mMaxi, jaco, m;
	double streZZ= 0., streRR= 0., streRZ= 0., streTT= 0., u= 0., w= 0.;
	double mr, ma, mz, m2, mzn, j0_mr, j1_mr, j1_mr_mr, j1_ma;
	double streXX= 0., streYY= 0., streXZ= 0., streYZ= 0., streXY= 0., straXX= 0., straYY= 0., straZZ= 0., straXZ= 0., straYZ= 0., straXY= 0.;
	double dx, dy, dz;
	
	int subI, k;
        subI_2 = r/a;
        subI = (int)subI_2;
        
	if (subI<2) {
            for (j=0;j<p;j++) {
                mMini= ns[j]/a; mMaxi= ns[j+1]/a; jaco= (mMaxi-mMini)/2.;
		for (i=0;i<n;i++){
                    m= (mMaxi-mMini)/2.*legeRoot[n-1][i] + (mMaxi+mMini)/2.;
                    mlel.solveForElasticLayerConstantsAtParameterM(m);
                    mr= m*r; ma= m*a; mz= m*z; m2= m*m; mzn= -1.*mz;
                    //j0_mr= j0(mr); j1_mr= j1(mr); j1_mr_mr= j1(mr)/mr; j1_ma= j1(ma);
                    if (subI<1) {
                        j0_mr= j_0_b(mr); j1_mr= j_1_b(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1_b(ma);
                    }
                    else {
                        j0_mr= j_0(mr); j1_mr= j_1(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1(ma);
                    }
                    if (mr<1.e-10) j1_mr_mr=0.5;
                
                    //
                    /*double j0r, j1a, j1r, j0rf, j1af, j1rf;
                    j0r=[self j_0:mr];
                    j1r=[self j_1:mr];
                    j1a=[self j_1:ma];
                    j0rf=[self j_0_b:mr];
                    j1rf=[self j_1_b:mr];
                    j1af=[self j_1_b:ma];*/
                    /*printf("\nj0_r(%f)=%.15e \nj0_s(%f)=%.15e \nj0_f(%f)=%.15e",mr,j0_mr,mr,j0r,mr,j0rf);
                    printf("\nj1_r(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e",mr,j1_mr,mr,j1r,mr,j1rf);
                    printf("\nj1_a(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e\n",ma,j1_ma,ma,j1a,ma,j1af);*/
                    //
                    //if (subI<1) { j0_mr= j0rf, j1_mr= j1rf, j1_mr_mr= j1rf/mr, j1_ma= j1af; } else { j0_mr= j0r, j1_mr= j1r, j1_mr_mr= j1_mr/mr, j1_ma= j1af; }
                    //if (mr<1.e-10) j1_mr_mr=0.5;
		
                    streZZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * ( -m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)-C*m*(1.-2.*v-mz)*Math.exp(mz)+D*m*(1.-2.*v+mz)*Math.exp(mzn)) );
                    streRZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j1_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)+C*m*(2.*v+mz)*Math.exp(mz)+D*m*(2.*v-mz)*Math.exp(mzn)) );
                    streRR+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+2.*v+mz)*Math.exp(mz)-D*m*(1.+2.*v-mz)*Math.exp(mzn))
                						   -m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                    streTT+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  2.*v*m2*j0_mr*(C*Math.exp(mz)-D*Math.exp(mzn))
								   +m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                    w+=      gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  (1.+v)/E * j0_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)-C*m*(2.-4.*v-mz)*Math.exp(mz)-D*m*(2.-4.*v+mz)*Math.exp(mzn)) );
                    u+=	 gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  -1.*(1.+v)/E * j1_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                }
            }
	}
	else {
            double mMiniSubI, mMaxiSubI, mInte;
            for (j=0;j<p;j++){
                //printf("\nInterval:%d From nA=%e to nB=%e",j,ns[j],ns[j+1]);
                //System.out.print("\nInterval:"+j+"/"+p+"\tFrom mA="+ns[j]/a+"\tto mB="+ns[j+1]/a);
		for (k=0;k<subI;k++) {
                    mMiniSubI= ns[j]/a; mMaxiSubI= ns[j+1]/a; mInte= (mMaxiSubI-mMiniSubI)/(1.*subI);
                    mMini= mMiniSubI+k*mInte; mMaxi= mMiniSubI+(k+1)*mInte; jaco= (mMaxi-mMini)/2.;
                    //System.out.print("\n    SubInterval:"+k+"/"+subI+"\t(m="+mMini+" -> m="+mMaxi+")");
                    for (i=0;i<n;i++){
                        /*if (j==p-1 && k==subI-1 && i==n-1){
                            System.out.print("\nPaso Final");
                        }*/
                        
                        m= (mMaxi-mMini)/2.*legeRoot[n-1][i] + (mMaxi+mMini)/2.;
			mlel.solveForElasticLayerConstantsAtParameterM(m);
			mr= m*r; ma= m*a; mz= m*z; m2= m*m; mzn= -1.*mz;
			//j0_mr= j0(mr), j1_mr= j1(mr), j1_mr_mr= j1(mr)/mr, j1_ma= j1(ma);
			j0_mr= j_0(mr); j1_mr= j_1(mr); j1_mr_mr= j1_mr/mr; j1_ma= j_1(ma);
                        if (mr<1.e-10) j1_mr_mr=0.5;
                        
                        /*System.out.print("\n        m="+m+"  mr="+mr+"  ma="+ma);
                        System.out.print("\n        j0(mr)="+j0_mr+"\tj1(mr)="+j1_mr);
                        System.out.print("\n        j1(ma)="+j1_ma+"\tj1(mr)/mr="+j1_mr_mr);*/
                        //
                        /*double j0r, j1a, j1r, j0rf, j1af, j1rf;
                        j0r=[self j_0:mr];
                        j1r=[self j_1:mr];
                        j1a=[self j_1:ma];
                        j0rf=[self j_0_b:mr];
                        j1rf=[self j_1_b:mr];
                        j1af=[self j_1_b:ma];*/
                        /*printf("\nj0_r(%f)=%.15e \nj0_s(%f)=%.15e \nj0_f(%f)=%.15e",mr,j0_mr,mr,j0r,mr,j0rf);
                        printf("\nj1_r(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e",mr,j1_mr,mr,j1r,mr,j1rf);
                        printf("\nj1_a(%f)=%.15e \nj1_s(%f)=%.15e \nj1_f(%f)=%.15e\n",ma,j1_ma,ma,j1a,ma,j1af);*/
                        //
                        //j0_mr= j0r, j1_mr= j1r, j1_mr_mr= j1_mr/mr, j1_ma= j1a;
                        //if (mr<1.e-10) j1_mr_mr=0.5;
					
                        streZZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * ( -m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)-C*m*(1.-2.*v-mz)*Math.exp(mz)+D*m*(1.-2.*v+mz)*Math.exp(mzn)) );
                        streRZ+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j1_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)+C*m*(2.*v+mz)*Math.exp(mz)+D*m*(2.*v-mz)*Math.exp(mzn)) );
                        streRR+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  m*j0_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+2.*v+mz)*Math.exp(mz)-D*m*(1.+2.*v-mz)*Math.exp(mzn))
												-m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                        streTT+= gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  2.*v*m2*j0_mr*(C*Math.exp(mz)-D*Math.exp(mzn))
												+m*j1_mr_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1.+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                        w+=      gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  (1.+v)/E * j0_mr * (A*m2*Math.exp(mz)-B*m2*Math.exp(mzn)-C*m*(2.-4.*v-mz)*Math.exp(mz)-D*m*(2.-4.*v+mz)*Math.exp(mzn)) );
                        u+=      gausWeig[n-1][i] * jaco * -q*a/m*j1_ma * (  -1.*(1.+v)/E * j1_mr * (A*m2*Math.exp(mz)+B*m2*Math.exp(mzn)+C*m*(1+mz)*Math.exp(mz)-D*m*(1.-mz)*Math.exp(mzn)) );
                    
                        /*System.out.printf("\nSzz= %e",streZZ);
                        System.out.printf("\nSrz= %e",streRZ);
                        System.out.printf("\nSrr= %e",streRR);
                        System.out.printf("\nStt= %e",streTT);
                        System.out.printf("\nw= %e",w);
                        System.out.printf("\nu= %e",u);*/
                    }
                    /* System.out.printf("\nSzz= %e",streZZ);
                    System.out.printf("\nSrz= %e",streRZ);
                    System.out.printf("\nSrr= %e",streRR);
                    System.out.printf("\nStt= %e",streTT);
                    System.out.printf("\nw= %e",w);
                    System.out.printf("\nu= %e",u);
                    System.out.print("\n");*/
                }
                /*System.out.printf("\nSzz= %e",streZZ);
                System.out.printf("\nSrz= %e",streRZ);
                System.out.printf("\nSrr= %e",streRR);
                System.out.printf("\nStt= %e",streTT);
                System.out.printf("\nw= %e",w);
                System.out.printf("\nu= %e",u);
                System.out.print("\n");*/
            }
	}
	
	// transformacion de esfuerzos (cilindricas->cartesiano) para un angulo alph
	streXX= streRR*Math.cos(alph)*Math.cos(alph)+streTT*Math.sin(alph)*Math.sin(alph);
	streYY= streRR*Math.sin(alph)*Math.sin(alph)+streTT*Math.cos(alph)*Math.cos(alph);
	streXZ= streRZ*Math.cos(alph);
	streYZ= streRZ*Math.sin(alph);
	streXY= (streRR-streTT)*Math.sin(alph)*Math.cos(alph);
	
	// calculo de deformaciones unitarias en sistema cartesiano
	straXX= 1./E*(streXX-v*(streYY+streZZ));
	straYY= 1./E*(streYY-v*(streZZ+streXX));
	straZZ= 1./E*(streZZ-v*(streXX+streYY));
	straXZ= 2*(1.+v)/E*streXZ;
	straYZ= 2*(1.+v)/E*streYZ;
	straXY= 2*(1.+v)/E*streXY;
        
        // transformación de desplazamientos
	dx= u*Math.cos(alph);
	dy= u*Math.sin(alph);
	dz= w;
        
        poin.stressSet(streXX, streYY, streZZ, streXY, streXZ, streYZ);
        poin.strainSet(straXX, straYY, straZZ, straXY, straXZ, straYZ);
        poin.displacementSet(dx, dy, dz);
        ///*
        double angl = alph * 180 / Math.PI;
        /*System.out.print("\nEvaluation Point: (r,a,z)=("+r+","+angl+","+z+")");
        System.out.printf("\nSxx= %e",streXX);
        System.out.printf("\nSyy= %e",streYY);
        System.out.printf("\nSzz= %e",streZZ);
        System.out.printf("\nSxy= %e",streXY);
        System.out.printf("\nSxz= %e",streXZ);
        System.out.printf("\nSyz= %e",streYZ);
        //System.out.println();
        System.out.printf("\nExx= %e",straXX);
        System.out.printf("\nEyy= %e",straYY);
        System.out.printf("\nEzz= %e",straZZ);
        System.out.printf("\nExy= %e",straXY);
        System.out.printf("\nExz= %e",straXZ);
        System.out.printf("\nEyz= %e",straYZ);
        //System.out.println();
        System.out.printf("\nu=   %e",dx);
        System.out.printf("\nv=   %e",dy);
        System.out.printf("\nw=   %e",dz);
        System.out.println();*/
        //*/
        return streZZ;
    }

    public Matrix firstMatrix() {
        return Qa;
    }
    public Matrix secondMatrix() {
        return Qb;
    }
    public double depth() {
        return dept;
    }
    public double thickness() {
        return thic;
    }
    public boolean pointBelongToLayer(double newDept) {
	boolean belo = false;
	if (type!=LAST){
            if (newDept>=dept && newDept<=(dept+thic)){
                belo = true;
            }
        }
        else{
            if (newDept>=dept){
                belo = true;
            }
        }
	return belo;
    }
    public boolean measurePointBelongToLayer(MeasurePoint poin) {
        double newDept = poin.zCoordinate();
	boolean belo = false;
	if (type!=LAST) {
            if (newDept>=dept && newDept<=(dept+thic)) {
                belo = true;
            }
        }
        else{
            if (newDept>=dept){
                belo = true;
            }
        }
	return belo;
    }
    public void assignLayerParameters(Matrix solu) {
	if (solu.colums()==1) {
		if (solu.rows()==4 && type!=LAST) {
			A = solu.value(0, 0);
			B = solu.value(1, 0);
			C = solu.value(2, 0);
			D = solu.value(3, 0);
		}
		else if (solu.rows()==2 && type==LAST) {
			B = solu.value(0, 0);
			D = solu.value(1, 0);
		}
		else
                    System.out.print("\nElasticLayer - Error in solution process");
	}
	else
            System.out.print("\nElasticLayer - Error in solution process");	
    }
    public void checkBesselFunctions() {
        double[] arre2={0,0.5,1,1.7,2,3,5,10};
        double j0D, j1D;
        int indeD;
        for (indeD=0;indeD<8;indeD++) {
            j0D = j_0(arre2[indeD]);
            System.out.println("j0("+arre2[indeD]+") = "+j0D);
        }
        System.out.println("");
        for (indeD=0;indeD<8;indeD++) {
            j1D = j_1(arre2[indeD]);
            System.out.println("j1("+arre2[indeD]+") = "+j1D);
        }
    }
}
