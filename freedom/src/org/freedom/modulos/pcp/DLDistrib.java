/**
 * @version 14/07/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 *  
 * Pacote: org.freedom.modulos.std <BR>
 * Classe: @(#)DLDistrib.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Coment�rios sobre a classe...
 */

package org.freedom.modulos.pcp;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;

import org.freedom.componentes.JPanelPad;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.componentes.StringDireita;
import org.freedom.componentes.Tabela;
import org.freedom.funcoes.Funcoes;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.FFDialogo;

public class DLDistrib extends FFDialogo implements MouseListener{

	private int casasDec = Aplicativo.casasDec;
	private JTextFieldPad txtSeqEF = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodFase = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtCodProd = new JTextFieldPad(JTextFieldPad.TP_INTEGER,8,0);
	private JTextFieldPad txtSeqEstDE = new JTextFieldPad(JTextFieldPad.TP_INTEGER,5,0);
	private JTextFieldPad txtQuant = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,15,casasDec);
	private JPanelPad pnDistrib = new JPanelPad(JPanelPad.TP_JPANEL,new BorderLayout());
	private JPanelPad pinCab = new JPanelPad(400,60);
	private Tabela tabDistrib = new Tabela();
	private int iCodop = 0;
	private int iSeqop = 0;
	private int iSeqDist = 0;
	private int iCodProd = 0;
	private String sDescProd = "";
	//private ListaCampos lcDistrib = new ListaCampos(this);
  
  public DLDistrib(Connection cn,Component cOrig) {
  	super(cOrig);
    setConexao(cn);
    setTitulo("Distribui��o");
    setAtribos(780,400);
    
    //lcDistrib.setTabela(tabDistrib);
   
    pinCab.setPreferredSize(new Dimension(400,60));
    pnDistrib.add(pinCab,BorderLayout.NORTH);
    JScrollPane spnTabRec = new JScrollPane(tabDistrib);
    pnDistrib.add(spnTabRec,BorderLayout.CENTER);

    c.add(pnDistrib, BorderLayout.CENTER);
    tabDistrib.adicColuna("Seq.fase");
    tabDistrib.adicColuna("C�d.fase");
    tabDistrib.adicColuna("Descri��o da fase");
    tabDistrib.adicColuna("Seq.dist.");
    tabDistrib.adicColuna("C�d.prod.");
    tabDistrib.adicColuna("Descri��o da estrutura");
    tabDistrib.adicColuna("Seq.est.dist.");
    tabDistrib.adicColuna("Quant.");
    
    tabDistrib.setTamColuna(50,0);
    tabDistrib.setTamColuna(50,1);
    tabDistrib.setTamColuna(160,2);
    tabDistrib.setTamColuna(60,3);
    tabDistrib.setTamColuna(70,4);
    tabDistrib.setTamColuna(200,5);
    tabDistrib.setTamColuna(70,6);
    tabDistrib.setTamColuna(80,7);
       
    tabDistrib.addMouseListener(this);
    
  }
  public Object[] getValores() {
    Object[] oRetorno = new Object[3];

    return oRetorno;
  }
  
  public void mouseClicked(MouseEvent mevt) {
    if (mevt.getClickCount() == 2) {
    	if (mevt.getSource() == tabDistrib && tabDistrib.getLinhaSel() >= 0)
    		iSeqDist = Integer.parseInt(""+tabDistrib.getValor(tabDistrib.getLinhaSel(),3));
    		iCodProd = Integer.parseInt(""+tabDistrib.getValor(tabDistrib.getLinhaSel(),4));
	    	sDescProd = ""+tabDistrib.getValor(tabDistrib.getLinhaSel(),5);
    		alteraDistrib();
    }
  }
  
  public void alteraDistrib(){
	  	DLFechaDistrib dl = new DLFechaDistrib(DLDistrib.this,iCodProd,sDescProd,iSeqDist);
		dl.setVisible(true);
		if (dl.OK) {
			tabDistrib.setValor(dl.getValor(),tabDistrib.getLinhaSel(),7);
			dl.dispose();
		} else {
			dl.dispose();
		}
  }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  
  
  public void carregaTabela(int iCodop, int iSeqop) {
  	  PreparedStatement ps = null;
  	  ResultSet rs = null;
  	  String sql = null;
  	  Vector vLinha = null;
  	  this.iCodop = iCodop;
  	  this.iSeqop = iSeqop;
  	  try {
  	  	sql = "SELECT O.CODPROD, ED.DESCEST, D.SEQEST, D.SEQEF, " +
  	  			"D.CODFASE, F.DESCFASE, D.SEQDE, D.CODEMPDE, " +
  	  			"D.CODFILIALDE, D.CODPRODDE, D.SEQESTDE " +
  	  			"FROM PPDISTRIB D, PPOP O, PPESTRUTURA ED, PPFASE F " +
  	  			"WHERE O.CODEMP=? AND O.CODFILIAL=? AND O.CODOP=? AND O.SEQOP=? AND " +
  	  			"D.CODEMP=O.CODEMPPD AND D.CODFILIAL=O.CODFILIALPD AND " +
  	  			"D.CODPROD=O.CODPROD AND D.SEQEST=O.SEQEST AND " +
  	  			"ED.CODEMP=D.CODEMPDE AND " +
  	  			"ED.CODFILIAL=D.CODFILIALDE AND ED.CODPROD=D.CODPRODDE AND " +
  	  			"ED.SEQEST=D.SEQESTDE AND F.CODEMP=D.CODEMPFS AND " +
  	  			"F.CODFILIAL=D.CODFILIALFS AND F.CODFASE=D.CODFASE";
  	  	ps = con.prepareStatement(sql);
  	  	ps.setInt(1,Aplicativo.iCodEmp);
  	  	ps.setInt(2,ListaCampos.getMasterFilial("PPOP"));
  	  	ps.setInt(3,iCodop);
  	  	ps.setInt(4,iSeqop);
  	  	rs = ps.executeQuery();
  	  	while (rs.next()) {
  	  		
  	  		vLinha = new Vector();
  	  		vLinha.addElement(new Integer(rs.getInt("SEQEF")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODFASE")));
  	  		vLinha.addElement(rs.getString("DESCFASE"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQDE")));
  	  		vLinha.addElement(new Integer(rs.getInt("CODPROD")));
  	  		vLinha.addElement(rs.getString("DESCEST"));
  	  		vLinha.addElement(new Integer(rs.getInt("SEQESTDE")));
  	  		vLinha.addElement(new StringDireita( "0" ));
  	  		tabDistrib.adicLinha(vLinha);
  	  		
  	  	}
  	  	rs.close();
  	  	ps.close();
  	  	if (!con.getAutoCommit())
  	  		con.commit();
  	  	
  	  }
  	  catch (SQLException e) {
  	  	Funcoes.mensagemErro(this,"Erro carregando distribui��o!\n"+e.getMessage());
  	  }
  	  finally {
  	  	rs = null;
  	  	ps = null;
  	  	sql = null;
  	  	vLinha = null;
  	  }
  	  
  }
  
}
