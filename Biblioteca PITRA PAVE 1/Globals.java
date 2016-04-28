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
class Globals {
    //un-assigned general
    public static final int UNAS = 0;               //un-assigned
    public static final int GENE = 1;               //general (load, point conf, analysis, etc)
    //languages
    public static final int ESPA = 1;               //spanish
    public static final int ENGL = 2;               //english
    // unit systems
    public static final int SIST_INTE = 1;          //international system
    public static final int ENGL_SYST = 2;          //english system
    // helpOnStart
    public static final int ON = 1;                 //on
    public static final int OFF = 0;                //off
    // material type
    public static final int ELAS = 1;               //elastic (linear)
    public static final int VISC = 2;               //viscoelastic
    public static final int BULK_STRE_SENS = 3;     //bulk stress sensitivy modulus
    public static final int DEVI_STRE_SENS = 4;     //deviatoric stress sensitivy modulus
    public static final int NLEL = 4;               //non linear elastic
    // layer coupling
    public static final int COMP_ATTA = 1;          //completely attached
    public static final int NON_ATTA = 2;           //non attached
    public static final int PART_ATTA = 3;          //partially attached
    // load type
    public static final int VERT = 1;
    public static final int HORI = 2;
    public static final int VEHO = 3;               //vertical + horizontal
    public static final int GRAV = 4;               //gravity
    // load configuration*  (or predefined axle configuration)
    //GENE = 1;                                     //general load (user defined)
    public static final int SING = 2;               //single
    public static final int DUSI = 3;               //dual single
    public static final int TAND = 4;               //tandem
    public static final int TRID = 5;               //tridem
    // load geometry type
    public static final int CIRC = 1;               //circular (1 parameter)
    public static final int RECT = 2;               //rectangular (2 parameters)
    public static final int COMP = 3;               //rectangular + 2 semicircular (2 parameters)
    public static final int ELIP = 4;               //elipsoidal (2 parameters)
    // load pressure distribution
    public static final int UNIF = 1;               //uniform
    public static final int NOUN = 2;               //non-uniform
    // analysis type*
    //GENE = 1;                                     //general analysis
    public static final int CRAN = 2;               //critical responses analysis
    // linearity analysis type
    public static final int LINE = 1;               //linear analysis
    public static final int NOLI = 2;               //non-linear analysis
    // time analysis type
    public static final int STAT = 1;               //static analysis
    public static final int DYNA = 2;               //dynamic analysis
    // point configuration
    //GENE = 1;                                     //general point configuration (user defined)
    public static final int GRID_POIN = 2;          //grid point configuration
    public static final int SING_POIN = 3;          //single axle grid point configuration
    public static final int DUSI_POIN = 4;          //dual-single axle grid point configuration
    public static final int TAND_POIN = 5;          //tandem axle grid point configuration
    public static final int TRID_POIN = 6;          //tridem axle grid point configuration
}
