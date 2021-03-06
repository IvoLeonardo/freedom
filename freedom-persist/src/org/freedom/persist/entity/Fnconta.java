package org.freedom.persist.entity;

// Generated 12/05/2014 09:11:34 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Fnconta generated by hbm2java
 */
@Entity
@Table(name = "FNCONTA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CODEMPPN", "CODFILIALPN", "CODPLAN" }))
public class Fnconta implements java.io.Serializable {

	private FncontaId id;
	private Sgfilial sgfilial;
	private Fnmoeda fnmoeda;
	private Fnhistpad fnhistpad;
	private Fnplanejamento fnplanejamento;
	private Fnbanco fnbanco;
	private String descconta;
	private char tipoconta;
	private Date dataconta;
	private String codcontdeb;
	private String codcontcred;
	private String agenciaconta;
	private String convcobconta;
	private char ativaconta;
	private char tusuconta;
	private char tipocaixa;
	private String postoconta;
	private Date dtins;
	private Date hins;
	private String idusuins;
	private Date dtalt;
	private Date halt;
	private String idusualt;
	private Set fnrecebers = new HashSet(0);
	private Set fnborderosForFnborderofkfnconta = new HashSet(0);
	private Set vdvendedors = new HashSet(0);
	private Set fnmodboletos = new HashSet(0);
	private Set fncontausus = new HashSet(0);
	private Set fnpagtocomis = new HashSet(0);
	private Set fnpagars = new HashSet(0);
	private Set fnborderosForFnborderofkfncontabor = new HashSet(0);
	private Set fnitpagars = new HashSet(0);
	private Set fnfbnclis = new HashSet(0);
	private Set fnitrecebers = new HashSet(0);
	private Set cpcompras = new HashSet(0);
	private Set vdvendas = new HashSet(0);
	private Set fnitmodboletos = new HashSet(0);
	private Set fnplanopags = new HashSet(0);
	private Set sgitprefere6s = new HashSet(0);

	public Fnconta() {
	}

	public Fnconta(FncontaId id, Sgfilial sgfilial, Fnmoeda fnmoeda,
			Fnplanejamento fnplanejamento, String descconta, char tipoconta,
			Date dataconta, char ativaconta, char tusuconta, char tipocaixa,
			Date dtins, Date hins, String idusuins) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnmoeda = fnmoeda;
		this.fnplanejamento = fnplanejamento;
		this.descconta = descconta;
		this.tipoconta = tipoconta;
		this.dataconta = dataconta;
		this.ativaconta = ativaconta;
		this.tusuconta = tusuconta;
		this.tipocaixa = tipocaixa;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
	}

	public Fnconta(FncontaId id, Sgfilial sgfilial, Fnmoeda fnmoeda,
			Fnhistpad fnhistpad, Fnplanejamento fnplanejamento,
			Fnbanco fnbanco, String descconta, char tipoconta, Date dataconta,
			String codcontdeb, String codcontcred, String agenciaconta,
			String convcobconta, char ativaconta, char tusuconta,
			char tipocaixa, String postoconta, Date dtins, Date hins,
			String idusuins, Date dtalt, Date halt, String idusualt,
			Set fnrecebers, Set fnborderosForFnborderofkfnconta,
			Set vdvendedors, Set fnmodboletos, Set fncontausus,
			Set fnpagtocomis, Set fnpagars,
			Set fnborderosForFnborderofkfncontabor, Set fnitpagars,
			Set fnfbnclis, Set fnitrecebers, Set cpcompras, Set vdvendas,
			Set fnitmodboletos, Set fnplanopags, Set sgitprefere6s) {
		this.id = id;
		this.sgfilial = sgfilial;
		this.fnmoeda = fnmoeda;
		this.fnhistpad = fnhistpad;
		this.fnplanejamento = fnplanejamento;
		this.fnbanco = fnbanco;
		this.descconta = descconta;
		this.tipoconta = tipoconta;
		this.dataconta = dataconta;
		this.codcontdeb = codcontdeb;
		this.codcontcred = codcontcred;
		this.agenciaconta = agenciaconta;
		this.convcobconta = convcobconta;
		this.ativaconta = ativaconta;
		this.tusuconta = tusuconta;
		this.tipocaixa = tipocaixa;
		this.postoconta = postoconta;
		this.dtins = dtins;
		this.hins = hins;
		this.idusuins = idusuins;
		this.dtalt = dtalt;
		this.halt = halt;
		this.idusualt = idusualt;
		this.fnrecebers = fnrecebers;
		this.fnborderosForFnborderofkfnconta = fnborderosForFnborderofkfnconta;
		this.vdvendedors = vdvendedors;
		this.fnmodboletos = fnmodboletos;
		this.fncontausus = fncontausus;
		this.fnpagtocomis = fnpagtocomis;
		this.fnpagars = fnpagars;
		this.fnborderosForFnborderofkfncontabor = fnborderosForFnborderofkfncontabor;
		this.fnitpagars = fnitpagars;
		this.fnfbnclis = fnfbnclis;
		this.fnitrecebers = fnitrecebers;
		this.cpcompras = cpcompras;
		this.vdvendas = vdvendas;
		this.fnitmodboletos = fnitmodboletos;
		this.fnplanopags = fnplanopags;
		this.sgitprefere6s = sgitprefere6s;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "numconta", column = @Column(name = "NUMCONTA", nullable = false, length = 10)),
			@AttributeOverride(name = "codfilial", column = @Column(name = "CODFILIAL", nullable = false)),
			@AttributeOverride(name = "codemp", column = @Column(name = "CODEMP", nullable = false)) })
	public FncontaId getId() {
		return this.id;
	}

	public void setId(FncontaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODFILIAL", referencedColumnName = "CODFILIAL", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP", nullable = false, insertable = false, updatable = false) })
	public Sgfilial getSgfilial() {
		return this.sgfilial;
	}

	public void setSgfilial(Sgfilial sgfilial) {
		this.sgfilial = sgfilial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODMOEDA", referencedColumnName = "CODMOEDA", nullable = false),
			@JoinColumn(name = "CODFILIALMA", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPMA", referencedColumnName = "CODEMP", nullable = false) })
	public Fnmoeda getFnmoeda() {
		return this.fnmoeda;
	}

	public void setFnmoeda(Fnmoeda fnmoeda) {
		this.fnmoeda = fnmoeda;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODHIST", referencedColumnName = "CODHIST"),
			@JoinColumn(name = "CODFILIALHP", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPHP", referencedColumnName = "CODEMP") })
	public Fnhistpad getFnhistpad() {
		return this.fnhistpad;
	}

	public void setFnhistpad(Fnhistpad fnhistpad) {
		this.fnhistpad = fnhistpad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODPLAN", referencedColumnName = "CODPLAN", nullable = false),
			@JoinColumn(name = "CODFILIALPN", referencedColumnName = "CODFILIAL", nullable = false),
			@JoinColumn(name = "CODEMPPN", referencedColumnName = "CODEMP", nullable = false) })
	public Fnplanejamento getFnplanejamento() {
		return this.fnplanejamento;
	}

	public void setFnplanejamento(Fnplanejamento fnplanejamento) {
		this.fnplanejamento = fnplanejamento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CODBANCO", referencedColumnName = "CODBANCO"),
			@JoinColumn(name = "CODFILIALBO", referencedColumnName = "CODFILIAL"),
			@JoinColumn(name = "CODEMPBO", referencedColumnName = "CODEMP") })
	public Fnbanco getFnbanco() {
		return this.fnbanco;
	}

	public void setFnbanco(Fnbanco fnbanco) {
		this.fnbanco = fnbanco;
	}

	@Column(name = "DESCCONTA", nullable = false, length = 50)
	public String getDescconta() {
		return this.descconta;
	}

	public void setDescconta(String descconta) {
		this.descconta = descconta;
	}

	@Column(name = "TIPOCONTA", nullable = false, length = 1)
	public char getTipoconta() {
		return this.tipoconta;
	}

	public void setTipoconta(char tipoconta) {
		this.tipoconta = tipoconta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATACONTA", nullable = false, length = 10)
	public Date getDataconta() {
		return this.dataconta;
	}

	public void setDataconta(Date dataconta) {
		this.dataconta = dataconta;
	}

	@Column(name = "CODCONTDEB", length = 20)
	public String getCodcontdeb() {
		return this.codcontdeb;
	}

	public void setCodcontdeb(String codcontdeb) {
		this.codcontdeb = codcontdeb;
	}

	@Column(name = "CODCONTCRED", length = 20)
	public String getCodcontcred() {
		return this.codcontcred;
	}

	public void setCodcontcred(String codcontcred) {
		this.codcontcred = codcontcred;
	}

	@Column(name = "AGENCIACONTA", length = 6)
	public String getAgenciaconta() {
		return this.agenciaconta;
	}

	public void setAgenciaconta(String agenciaconta) {
		this.agenciaconta = agenciaconta;
	}

	@Column(name = "CONVCOBCONTA", length = 20)
	public String getConvcobconta() {
		return this.convcobconta;
	}

	public void setConvcobconta(String convcobconta) {
		this.convcobconta = convcobconta;
	}

	@Column(name = "ATIVACONTA", nullable = false, length = 1)
	public char getAtivaconta() {
		return this.ativaconta;
	}

	public void setAtivaconta(char ativaconta) {
		this.ativaconta = ativaconta;
	}

	@Column(name = "TUSUCONTA", nullable = false, length = 1)
	public char getTusuconta() {
		return this.tusuconta;
	}

	public void setTusuconta(char tusuconta) {
		this.tusuconta = tusuconta;
	}

	@Column(name = "TIPOCAIXA", nullable = false, length = 1)
	public char getTipocaixa() {
		return this.tipocaixa;
	}

	public void setTipocaixa(char tipocaixa) {
		this.tipocaixa = tipocaixa;
	}

	@Column(name = "POSTOCONTA", length = 2)
	public String getPostoconta() {
		return this.postoconta;
	}

	public void setPostoconta(String postoconta) {
		this.postoconta = postoconta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTINS", nullable = false, length = 10)
	public Date getDtins() {
		return this.dtins;
	}

	public void setDtins(Date dtins) {
		this.dtins = dtins;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HINS", nullable = false, length = 8)
	public Date getHins() {
		return this.hins;
	}

	public void setHins(Date hins) {
		this.hins = hins;
	}

	@Column(name = "IDUSUINS", nullable = false, length = 8)
	public String getIdusuins() {
		return this.idusuins;
	}

	public void setIdusuins(String idusuins) {
		this.idusuins = idusuins;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DTALT", length = 10)
	public Date getDtalt() {
		return this.dtalt;
	}

	public void setDtalt(Date dtalt) {
		this.dtalt = dtalt;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HALT", length = 8)
	public Date getHalt() {
		return this.halt;
	}

	public void setHalt(Date halt) {
		this.halt = halt;
	}

	@Column(name = "IDUSUALT", length = 8)
	public String getIdusualt() {
		return this.idusualt;
	}

	public void setIdusualt(String idusualt) {
		this.idusualt = idusualt;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnrecebers() {
		return this.fnrecebers;
	}

	public void setFnrecebers(Set fnrecebers) {
		this.fnrecebers = fnrecebers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fncontaByFnborderofkfnconta")
	public Set getFnborderosForFnborderofkfnconta() {
		return this.fnborderosForFnborderofkfnconta;
	}

	public void setFnborderosForFnborderofkfnconta(
			Set fnborderosForFnborderofkfnconta) {
		this.fnborderosForFnborderofkfnconta = fnborderosForFnborderofkfnconta;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getVdvendedors() {
		return this.vdvendedors;
	}

	public void setVdvendedors(Set vdvendedors) {
		this.vdvendedors = vdvendedors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnmodboletos() {
		return this.fnmodboletos;
	}

	public void setFnmodboletos(Set fnmodboletos) {
		this.fnmodboletos = fnmodboletos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFncontausus() {
		return this.fncontausus;
	}

	public void setFncontausus(Set fncontausus) {
		this.fncontausus = fncontausus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnpagtocomis() {
		return this.fnpagtocomis;
	}

	public void setFnpagtocomis(Set fnpagtocomis) {
		this.fnpagtocomis = fnpagtocomis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnpagars() {
		return this.fnpagars;
	}

	public void setFnpagars(Set fnpagars) {
		this.fnpagars = fnpagars;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fncontaByFnborderofkfncontabor")
	public Set getFnborderosForFnborderofkfncontabor() {
		return this.fnborderosForFnborderofkfncontabor;
	}

	public void setFnborderosForFnborderofkfncontabor(
			Set fnborderosForFnborderofkfncontabor) {
		this.fnborderosForFnborderofkfncontabor = fnborderosForFnborderofkfncontabor;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnitpagars() {
		return this.fnitpagars;
	}

	public void setFnitpagars(Set fnitpagars) {
		this.fnitpagars = fnitpagars;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnfbnclis() {
		return this.fnfbnclis;
	}

	public void setFnfbnclis(Set fnfbnclis) {
		this.fnfbnclis = fnfbnclis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnitrecebers() {
		return this.fnitrecebers;
	}

	public void setFnitrecebers(Set fnitrecebers) {
		this.fnitrecebers = fnitrecebers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getCpcompras() {
		return this.cpcompras;
	}

	public void setCpcompras(Set cpcompras) {
		this.cpcompras = cpcompras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getVdvendas() {
		return this.vdvendas;
	}

	public void setVdvendas(Set vdvendas) {
		this.vdvendas = vdvendas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnitmodboletos() {
		return this.fnitmodboletos;
	}

	public void setFnitmodboletos(Set fnitmodboletos) {
		this.fnitmodboletos = fnitmodboletos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getFnplanopags() {
		return this.fnplanopags;
	}

	public void setFnplanopags(Set fnplanopags) {
		this.fnplanopags = fnplanopags;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fnconta")
	public Set getSgitprefere6s() {
		return this.sgitprefere6s;
	}

	public void setSgitprefere6s(Set sgitprefere6s) {
		this.sgitprefere6s = sgitprefere6s;
	}
*/
}
