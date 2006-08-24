package org.freedom.modulos.pdv;

/**
 * @version 30/06/2004 <BR>
 * @author Setpoint Inform�tica Ltda./Fernando Oliveira da Silva <BR>
 * 
 * Projeto: Freedom <BR>
 * 
 * Pacote: org.freedom.modulos.pdv <BR>
 * Classe:
 * @(#)FFechaVenda.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR>
 * <BR>
 * 
 * Coment�rios sobre a classe...
 * 
 */

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.freedom.componentes.JCheckBoxPad;
import org.freedom.componentes.JLabelPad;
import org.freedom.componentes.JTextFieldFK;
import org.freedom.componentes.JTextFieldPad;
import org.freedom.componentes.ListaCampos;
import org.freedom.drivers.ECFDriver;
import org.freedom.funcoes.Funcoes;
import org.freedom.funcoes.Logger;
import org.freedom.telas.Aplicativo;
import org.freedom.telas.AplicativoPDV;
import org.freedom.telas.FFDialogo;

public class DLFechaDia extends FFDialogo {

	private static final long serialVersionUID = 1L;

	private JTextFieldFK txtDataHora = new JTextFieldFK( JTextFieldPad.TP_STRING, 16, 0 );

	private JTextFieldFK txtVlrCaixa = new JTextFieldFK( JTextFieldPad.TP_DECIMAL, 12, 2 );

	private JCheckBoxPad cbReducaoZ = new JCheckBoxPad( "Deseja executar a redu��o Z?", "S", "N" );

	private ECFDriver ecf = new ECFDriver( ! AplicativoPDV.usaEcfDriver() );

	public DLFechaDia() {

		super( Aplicativo.telaPrincipal );
				
		setTitulo( "Fechamento de caixa" );
		setAtribos( 310, 170 );

		adic( new JLabelPad( "Data e Hora: " ), 7, 10, 110, 20 );
		adic( txtDataHora, 7, 30, 110, 20 );
		adic( new JLabelPad( "Saldo do caixa: " ), 120, 10, 120, 20 );
		adic( txtVlrCaixa, 120, 30, 120, 20 );
		adic( cbReducaoZ, 7, 60, 280, 20 );

		txtDataHora.setVlrString( ( new SimpleDateFormat( "dd/MM/yyyy HH:mm" ) ).format( new Date() ) );

	}

	private void buscaSaldoDia() {
	
		try {
			
			String sSQL = "SELECT FIRST 1 VLRSLDMOV FROM PVMOVCAIXA WHERE CODEMP=? AND CODFILIAL=? AND CODCAIXA=? AND DTAMOV=? ORDER BY NROMOV DESC";
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, Aplicativo.iCodFilial );
			ps.setInt( 3, AplicativoPDV.iCodCaixa );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			ResultSet rs = ps.executeQuery();
			
			if ( rs.next() ) {
				txtVlrCaixa.setVlrBigDecimal( new BigDecimal( ( rs.getBigDecimal( "VLRSLDMOV" ) != null ? rs.getDouble( "VLRSLDMOV" ) : 0 ) ) );
			}
			
			rs.close();
			ps.close();
			
			if ( !con.getAutoCommit() )
				con.commit();
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "N�o foi poss�vel buscar o saldo atual.\n" + err.getMessage(), true, con, err );
			err.printStackTrace();
		}
		
	}

	private boolean execFechamento( boolean bReduz ) {

		boolean bRet = false;
		
		try {

			// Fecha o caixa:	

			String sSQL = "EXECUTE PROCEDURE PVFECHACAIXASP(?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setInt( 3, AplicativoPDV.iCodCaixa );
			ps.setDate( 4, Funcoes.dateToSQLDate( new Date() ) );
			ps.setString( 5, bReduz ? "S" : "N" );
			ps.setInt( 6, Aplicativo.iCodFilial );
			ps.setString( 7, Aplicativo.strUsuario );
			ps.execute();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}			
			
			bRet = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao executar fechamento do caixa!\n" + err.getMessage(), true, con, err );
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao executar fechamento do caixa." );
		}
		
		return bRet;
		
	}

	private boolean execSangria() {

		boolean bRet = false;

		// Sangria para o troco:

		try {
			
			String sSQL = "EXECUTE PROCEDURE PVSANGRIASP(?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement( sSQL );
			
			ps.setInt( 1, Aplicativo.iCodEmp );
			ps.setInt( 2, ListaCampos.getMasterFilial( "PVMOVCAIXA" ) );
			ps.setBigDecimal( 3, txtVlrCaixa.getVlrBigDecimal() );
			ps.setInt( 4, AplicativoPDV.iCodCaixa );
			ps.setDate( 5, Funcoes.dateToSQLDate( new Date() ) );
			ps.setString( 6, Aplicativo.strUsuario );
			ps.execute();
			
			ps.close();
			
			if ( !con.getAutoCommit() ) {
				con.commit();
			}
			
			bRet = true;
			
		} catch ( SQLException err ) {
			Funcoes.mensagemErro( null, "Erro ao executar o troco!\n" + err.getMessage(), true, con, err );
			Logger.gravaLogTxt( "", Aplicativo.strUsuario, Logger.LGEB_BD, "Erro ao executar o troco." );
		}
		
		return bRet;
	}

	private void fechaCaixa( boolean bReduz ) {

		if ( txtVlrCaixa.getVlrDouble().doubleValue() > 0 )
			if ( execSangria() && AplicativoPDV.bECFTerm ) {
				if ( ! ecf.sangria( txtVlrCaixa.getVlrBigDecimal() ) ) {
					Funcoes.mensagemErro( null, "Erro ao executar a sangria!" );
					return;
				}
			}
		if ( execFechamento( bReduz ) ) {
			if ( AplicativoPDV.bECFTerm && bReduz ) {
				if ( ! ecf.reducaoZ() ) {
					Funcoes.mensagemErro( null, "Erro ao executar a redu��o Z!" );
					return;
				}
				else if ( bReduz ) {
					FLeFiscal fiscal = new FLeFiscal();
					fiscal.setConexao( con );
					fiscal.gravaReducaoZ( Calendar.getInstance().getTime(), AplicativoPDV.iCodCaixa );
				}
			}
		}
	}

	public void actionPerformed( ActionEvent evt ) {

		if ( evt.getSource() == btOK ) {
			if ( Funcoes.mensagemConfirma( null, "Confirma fechamento?" ) == JOptionPane.YES_OPTION ) {
				if ( cbReducaoZ.getVlrString().equals( "S" ) ) {
					if ( Funcoes.mensagemConfirma( null, "Aten��o!\n" + "Se for executada a 'Redu��o Z''\n" + "o caixa ser� fechado em definitivo!\n" + "Deseja executar assim mesmo?" ) == JOptionPane.YES_OPTION ) {
						fechaCaixa( true );
					}
					else return;
				}
				else {
					fechaCaixa( false );
				}
			}
			else return;
		}
		
		super.actionPerformed( evt );
	}

	public void setConexao( Connection cn ) {

		super.setConexao( cn );
		buscaSaldoDia();
	}
}
