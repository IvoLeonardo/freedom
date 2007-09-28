package org.freedom.modulos.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import net.sf.jasperreports.engine.JasperPrintManager;

import org.freedom.componentes.GuardaCampo;
import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JRadioGroup;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FPrinterJob;
import org.freedom.telas.FRelatorio;


public class FRFechaDiario extends FRelatorio{

	private static final long serialVersionUID = 1L;
	
	private JTextFieldPad txtCodCaixa = new JTextFieldPad( JTextFieldPad.TP_INTEGER, 8, 0 );
	
	private JTextFieldFK txtDescCaixa = new JTextFieldFK( JTextFieldPad.TP_STRING, 40, 0 );
	
	private JTextFieldPad txtData = new JTextFieldPad( JTextFieldPad.TP_DATE, 10, 0 );
	
	private JTextFieldPad txtIdUsu = new JTextFieldPad( JTextFieldPad.TP_STRING, 8, 0);

	private ListaCampos lcCaixa = new ListaCampos( this );
	
	private JRadioGroup rgTipo = null;
	
	public FRFechaDiario(){
		
		setTitulo( "Fechamento di�rio" );
		setAtribos( 80, 80, 360, 220 );
		
		montaTela();
		montaListaCampos();
	
	}
	
	public void montaTela(){
		
		Vector<String> vLabs = new Vector<String>();
		Vector<String> vVals = new Vector<String>();

		vLabs.addElement( "Resumido" );
		vLabs.addElement( "Detalhado" );
		vVals.addElement( "R" );
		vVals.addElement( "D" );
		
		rgTipo = new JRadioGroup<String, String>( 1, 2, vLabs, vVals );
		rgTipo.setVlrString( "R" );
		
		adic( new JLabelPad( "N� caixa" ), 7, 7, 80, 20 );		
		adic( txtCodCaixa, 7, 27, 70, 20 );
		adic( new JLabelPad( "Descri��o do caixa" ), 80, 7, 200, 20 );
		adic( txtDescCaixa, 80, 27, 223, 20 );

		adic( new JLabelPad("Data"), 7, 47, 50, 20 );
		adic( txtData, 7, 67, 110, 20 );

		adic( new JLabelPad("Usu�rio"), 120, 47, 100, 20);
		adic( txtIdUsu, 120, 67, 100, 20);
		
		adic( rgTipo, 7, 100, 215, 30 );
		
		GregorianCalendar cPeriodo = new GregorianCalendar();
		cPeriodo.set( Calendar.DAY_OF_MONTH, cPeriodo.get( Calendar.DAY_OF_MONTH ) );
		txtData.setVlrDate( cPeriodo.getTime() );
	}
	
	public void montaListaCampos(){
		
		lcCaixa.add( new GuardaCampo( txtCodCaixa, "CodCaixa", "C�d.caixa", ListaCampos.DB_PK, false ) );
		lcCaixa.add( new GuardaCampo( txtDescCaixa, "DescCaixa", "Descri��o do caixa", ListaCampos.DB_SI, false ) );
		lcCaixa.montaSql( false, "CAIXA", "PV" );
		lcCaixa.setReadOnly( true );
		txtCodCaixa.setTabelaExterna( lcCaixa );
		txtCodCaixa.setFK( true );
		txtCodCaixa.setNomeCampo( "CodCaixa" );
		
	}
	
	public void setConexao( Connection cn ){
		
		super.setConexao( cn );
		lcCaixa.setConexao( cn );
		
	}

	@ Override
	public void imprimir( boolean bVisualizar ) {
		
		ImprimeOS imp = new ImprimeOS( "", con );
		
		StringBuffer sSQL = new StringBuffer();
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuilder sCab = new StringBuilder();
		boolean bComRef = comRef();
		int codcaixa = txtCodCaixa.getVlrInteger().intValue();
		int param = 1;
		String idusu = txtIdUsu.getVlrString().trim().toUpperCase();
		String sRelatorio = "";
		
		try {
		
			sCab.append( "Data: "+txtData.getVlrString() );
			
			ps = con.prepareStatement( montaSql(codcaixa, idusu, sCab) );
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "VDVENDA" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtData.getVlrDate() ));
			if (codcaixa!=0) {
				ps.setInt( param++, Aplicativo.iCodEmp );
				ps.setInt( param++, ListaCampos.getMasterFilial( "PVCAIXA" ) );
				ps.setInt( param++, codcaixa );
			}
			if (!"".equals(idusu)) {
				ps.setString(  param++, idusu );
			}
			ps.setInt( param++, Aplicativo.iCodEmp );
			ps.setInt( param++, ListaCampos.getMasterFilial( "CPCOMPRA" ) );
			ps.setDate( param++, Funcoes.dateToSQLDate( txtData.getVlrDate() ));
			if (!"".equals(idusu)) {
				ps.setString(  param++, idusu );
			}
			rs = ps.executeQuery();
			
			imp.setTitulo( "Fechamento di�rio" );
			imp.addSubTitulo( "FECHAMENTO DI�RIO" );
			
			HashMap< String, Object > hParam = new HashMap< String, Object >();
			
			hParam.put( "CODEMP", Aplicativo.iCodEmp );	
			hParam.put( "CODFILIAL", ListaCampos.getMasterFilial( "VDVENDA" ));
			hParam.put( "COMREF", bComRef ? "S" : "N" );

			if("R".equals( rgTipo.getVlrString())){
				sRelatorio = "relatorios/FechaDiario.jasper";
			}else{
				sRelatorio = "relatorios/FechaDiarioDet.jasper";
			}
			
			FPrinterJob dlGr = new FPrinterJob( sRelatorio, "Fechamento Di�rio", sCab.toString(), rs, hParam, this );

			if ( bVisualizar ) {
				dlGr.setVisible( true );
			}
			else {
				try {
					JasperPrintManager.printReport( dlGr.getRelatorio(), true );
				} catch ( Exception err ) {
					Funcoes.mensagemErro( this, "Erro na impress�o de relat�rio de Fechamento di�rio!" + err.getMessage(), true, con, err );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private String montaSql(int codcaixa, String idusu, StringBuilder sCab) {
		
		StringBuilder sSQL = new StringBuilder();
		
		if("R".equals( rgTipo.getVlrString())){
			
			sCab.append( "Data: "+txtData.getVlrString() );
			sSQL.append( "SELECT CAST('A' AS CHAR(1)) TIPOLANCA, V.DTSAIDAVENDA DATA, ");
			sSQL.append( "V.CODTIPOMOV, M.DESCTIPOMOV, " );
			sSQL.append( "V.CODCAIXA, C.DESCCAIXA, V.IDUSUINS, " );
			sSQL.append( "V.CODPLANOPAG, P.DESCPLANOPAG, SUM(V.VLRLIQVENDA) VALOR " );
			sSQL.append( "FROM VDVENDA V, PVCAIXA C, EQTIPOMOV M, FNPLANOPAG P " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND " );
			sSQL.append( "V.DTEMITVENDA=? AND " );
			if (codcaixa!=0) {
				sSQL.append( "V.CODEMPCX=? AND V.CODFILIALCX=? AND V.CODCAIXA=? AND " );
				sCab.append( " - Caixa: "+codcaixa );
			}
			if (!"".equals(idusu)) {
				sSQL.append( " V.IDUSUINS=? AND ");
				sCab.append( " - Usu�rio: "+idusu );
			}
			sSQL.append( "C.CODEMP=V.CODEMPCX AND C.CODFILIAL=V.CODFILIALCX AND " );
			sSQL.append( "C.CODCAIXA=V.CODCAIXA AND " );
			sSQL.append( "M.CODEMP=V.CODEMPTM AND M.CODFILIAL=V.CODFILIALTM AND " );
			sSQL.append( "M.CODTIPOMOV=V.CODTIPOMOV AND M.SOMAVDTIPOMOV='S' AND " );
			sSQL.append( "P.CODEMP=V.CODEMPPG AND P.CODFILIAL=V.CODFILIALPG AND " );
			sSQL.append( "P.CODPLANOPAG=V.CODPLANOPAG " );
			sSQL.append( "GROUP BY 1, 2, 3, 4, 5, 6, 7, 8, 9 " );
			sSQL.append( "UNION " );
			sSQL.append( "SELECT CAST('B' AS CHAR(1)) TIPOLANCA, CP.DTEMITCOMPRA DATA, " );
			sSQL.append( "CP.CODTIPOMOV, M.DESCTIPOMOV, " );
			sSQL.append( "40 CODCAIXA, CAST( null AS CHAR(40) ) DESCCAIXA, CP.IDUSUINS, " );
			sSQL.append( "CP.CODPLANOPAG, P.DESCPLANOPAG, SUM(CP.VLRLIQCOMPRA*-1) VALOR " );
			sSQL.append( "FROM CPCOMPRA CP, EQTIPOMOV M, FNPLANOPAG P " );
			sSQL.append( "WHERE CP.CODEMP=? AND CP.CODFILIAL=? AND " );
			sSQL.append( "CP.DTEMITCOMPRA=? AND " );
			if (!"".equals(idusu)) {
				sSQL.append( " CP.IDUSUINS=? AND ");
			}
			sSQL.append( "M.CODEMP=CP.CODEMPTM AND M.CODFILIAL=CP.CODFILIALTM AND " );
			sSQL.append( "M.CODTIPOMOV=CP.CODTIPOMOV AND M.TIPOMOV='DV' AND " );
			sSQL.append( "P.CODEMP=CP.CODEMPPG AND P.CODFILIAL=CP.CODFILIALPG AND " );
			sSQL.append( "P.CODPLANOPAG=CP.CODPLANOPAG " );
			sSQL.append( "GROUP BY 1, 2, 3, 4, 5, 6, 7, 8, 9 " );
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5, 6, 7, 8, 9 " );
			
		}else{
			
			sSQL.append( "SELECT CAST('A' AS CHAR(1)) TIPOLANCA, V.DTSAIDAVENDA DATA, ");
			sSQL.append( "V.CODTIPOMOV, M.DESCTIPOMOV, " );
			sSQL.append( "V.CODCAIXA, C.DESCCAIXA, V.IDUSUINS, " );
			sSQL.append( "V.CODPLANOPAG, P.DESCPLANOPAG, V.CODVENDA PEDIDO, V.DOCVENDA DOC," ); 
			sSQL.append( " VO.NOMEVEND, VC.CODORC, VC.CODITORC, ");
			sSQL.append( "SUM(IO.VLRLIQITORC) VALORORC, ");
			sSQL.append( "SUM(IV.VLRLIQITVENDA) VALOR " );
			sSQL.append( "FROM PVCAIXA C, EQTIPOMOV M, FNPLANOPAG P, VDVENDEDOR VO, " );
			sSQL.append( "VDVENDA V, VDITVENDA IV " );
			sSQL.append( "LEFT OUTER JOIN VDVENDAORC VC ON " );
			sSQL.append( "VC.CODEMP=IV.CODEMP AND VC.CODFILIAL=IV.CODFILIAL AND " );
			sSQL.append( "VC.TIPOVENDA=IV.TIPOVENDA AND VC.CODVENDA=IV.CODVENDA AND " );
			sSQL.append( "VC.CODITVENDA=IV.CODITVENDA " );
			sSQL.append( "LEFT OUTER JOIN VDITORCAMENTO IO ON " );
			sSQL.append( "IO.CODEMP=VC.CODEMPOR AND IO.CODFILIAL=VC.CODFILIALOR AND " );
			sSQL.append( "IO.CODORC=VC.CODORC AND IO.CODITORC=VC.CODITORC " );
			sSQL.append( "WHERE V.CODEMP=? AND V.CODFILIAL=? AND " );
			sSQL.append( "V.DTEMITVENDA=? AND " );
			if (codcaixa!=0) {
				sSQL.append( "V.CODEMPCX=? AND V.CODFILIALCX=? AND V.CODCAIXA=? AND " );
				sCab.append( " - Caixa: "+codcaixa );
			}
			if (!"".equals(idusu)) {
				sSQL.append( " V.IDUSUINS=? AND ");
				sCab.append( " - Usu�rio: "+idusu );
			}
			
			sSQL.append( "IV.CODEMP=V.CODEMP AND IV.CODFILIAL=V.CODFILIAL AND ");
			sSQL.append( "IV.TIPOVENDA=V.TIPOVENDA AND IV.CODVENDA=V.CODVENDA AND " );
			sSQL.append( "C.CODEMP=V.CODEMPCX AND C.CODFILIAL=V.CODFILIALCX AND " );
			sSQL.append( "C.CODCAIXA=V.CODCAIXA AND " );
			sSQL.append( "M.CODEMP=V.CODEMPTM AND M.CODFILIAL=V.CODFILIALTM AND " );
			sSQL.append( "M.CODTIPOMOV=V.CODTIPOMOV AND M.SOMAVDTIPOMOV='S' AND " );
			sSQL.append( "P.CODEMP=V.CODEMPPG AND P.CODFILIAL=V.CODFILIALPG AND " );
			sSQL.append( "P.CODPLANOPAG=V.CODPLANOPAG AND VO.CODEMP=V.CODEMPVD AND " );
			sSQL.append( "VO.CODFILIAL=V.CODFILIALVD AND VO.CODVEND=V.CODVEND " );
			sSQL.append( "GROUP BY 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 " );
			sSQL.append( "UNION " );
			sSQL.append( "SELECT CAST('B' AS CHAR(1)) TIPOLANCA, CP.DTEMITCOMPRA DATA, " );
			sSQL.append( "CP.CODTIPOMOV, M.DESCTIPOMOV, " );
			sSQL.append( "0 CODCAIXA, CAST( null AS CHAR(40) ) DESCCAIXA, CP.IDUSUINS, " );
			sSQL.append( "CP.CODPLANOPAG, P.DESCPLANOPAG, CP.CODCOMPRA PEDIDO, CP.DOCCOMPRA DOC," ); 
			sSQL.append( "CAST ( NULL AS CHAR(40)) NOMEVEND, " );
			sSQL.append( "0 CODORC, " );
			sSQL.append( "0 CODITORC, " );
			sSQL.append( "SUM(0) VALORORC, " );
			sSQL.append( "SUM(CP.VLRLIQCOMPRA*-1) VALOR " );
			sSQL.append( "FROM CPCOMPRA CP, EQTIPOMOV M, FNPLANOPAG P " );
			sSQL.append( "WHERE CP.CODEMP=? AND CP.CODFILIAL=? AND " );
			sSQL.append( "CP.DTEMITCOMPRA=? AND " );
			if (!"".equals(idusu)) {
				sSQL.append( " CP.IDUSUINS=? AND ");
			}
			sSQL.append( "M.CODEMP=CP.CODEMPTM AND M.CODFILIAL=CP.CODFILIALTM AND " );
			sSQL.append( "M.CODTIPOMOV=CP.CODTIPOMOV AND M.TIPOMOV='DV' AND " );
			sSQL.append( "P.CODEMP=CP.CODEMPPG AND P.CODFILIAL=CP.CODFILIALPG AND " );
			sSQL.append( "P.CODPLANOPAG=CP.CODPLANOPAG " );
			sSQL.append( "GROUP BY 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 " );
			sSQL.append( "ORDER BY 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 " );
		}
		
		return sSQL.toString();
		
	}
	private boolean comRef() {

		boolean bRetorno = false;
		String sSQL = "SELECT USAREFPROD FROM SGPREFERE1 WHERE CODEMP=? AND CODFILIAL=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "SGPREFERE1" ) );
			rs = ps.executeQuery();
			if ( rs.next() ) {
				if ( rs.getString( "UsaRefProd" ).trim().equals( "S" ) )
					bRetorno = true;
			}
			if ( !con.getAutoCommit() )
				con.commit();
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( this, "Erro ao carregar a tabela PREFERE1!\n" + err.getMessage(), true, con, err );
		} finally {
			ps = null;
			rs = null;
			sSQL = null;
		}
		return bRetorno;
	}
}
