/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Projeto;
import Model.Propaganda;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class PropagandaDAO implements IObjectDAO{
    
    private final String BEFORECOND = "select pp.id as pp_id, pp.projeto as pp_projeto, pp.datainicio as pp_dataInicio, pp.datafinal as pp_dataFinal, pp.agencia as pp_agencia, pp.tarifa as pp_tarifa,\n" +
"p.pnumero as p_pnumero, p.pjnome as p_pjnome, p.plocalizacao as p_localizacao, p.dnum as p_dnum";
    
    private final String SQL_POST = "INSERT INTO cia.propaganda(projeto, dataInicio, dataFinal, agencia, tarifa) VALUES(?,?,?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.propaganda SET dataInicio = ?, dataFinal = ?,agencia = ?, tarifa = ? WHERE id = ?;";
    private final String SQL_GET = BEFORECOND + " FROM cia.propaganda as pp, cia.projeto as p WHERE pp.id = ? AND pp.projeto = p.pnumero;";
    private final String SQL_READ = BEFORECOND + " FROM cia.propaganda as pp, cia.projeto as p WHERE pp.projeto = ? AND pp.projeto = p.pnumero;";
    private final String SQL_GETALL = BEFORECOND + " FROM cia.propaganda as pp, cia.projeto as p WHERE pp.projeto = p.pnumero;";
    private final String SQL_DELETE = "DELETE FROM  cia.propaganda as pp WHERE id = ?;";
    private final String SQL_DELETEPROJ = "DELETE FROM cia.propaganda WHERE projeto = ?;";
    private final String SQL_SUMTAXES = "SELECT cia.gera_tarifa(?)";
    private final String SQL_SUMEMPPAY = "SELECT sum(e.salario) FROM cia.empregado AS e, cia.trabalha_em as tt where tt.pjnumero = ? AND tt.essn = e.ssn";

    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object criarObjetoTemplate() throws SQLException{
        Propaganda output = new Propaganda();
        output.setNumero(this.rs.getInt("pp_id"));
        output.setAgencia(this.rs.getString("pp_agencia"));
        output.setDataInicio(this.rs.getDate("pp_dataInicio"));
        output.setDataFinal(this.rs.getDate("pp_dataFinal"));
        output.setTarifa(this.rs.getFloat("pp_tarifa"));

        ProjetoDAO dao = (ProjetoDAO) FactoryDAO.getFactory("Projeto");
        Projeto pro = (Projeto) dao.createObject(this.rs.getInt("p_pnumero"), this.rs.getString("p_pjnome"), this.rs.getString("p_localizacao"), null);

        output.setProjeto(pro);
        System.gc();
        return output;
    }
    
    private ArrayList<Object> buscarVariosObjetosTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
        while(this.rs.next())
            output.add((Propaganda) this.criarObjetoTemplate());
        return output;
    }
    
    @Override
    public void post(Object input) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);

        Propaganda pro = (Propaganda) input;
        this.ps.setInt(1, pro.getProjeto().getNumero());
        this.ps.setDate(2, pro.getDataInicio());
        this.ps.setDate(3, pro.getDataFinal());
        this.ps.setString(4, pro.getAgencia());
        this.ps.setFloat(5, pro.getTarifa());

        this.rs = this.ps.executeQuery();
        if(!this.rs.next())
            throw new Exception("Propaganda nao cadastrada.");
    }

    @Override
    public void update(Object input) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_UPDATE);

        Propaganda pro = (Propaganda) input;
        this.ps.setDate(1, pro.getDataInicio());
        this.ps.setDate(2, pro.getDataFinal());
        this.ps.setString(3, pro.getAgencia());
        this.ps.setFloat(4, pro.getTarifa());
        this.ps.setInt(5, pro.getNumero());

        this.rs = this.ps.executeQuery();
        if(!this.rs.next())
            throw new Exception("Propaganda nao atualizada.");
    }

    // busca a propaganda pelo id
    @Override
    public Object get(Object input) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
        this.ps.setInt(1,(int) input);
        this.rs = this.ps.executeQuery();

        if(!this.rs.next())
            throw new Exception("Nao foi encontrada tarifas");

        return this.criarObjetoTemplate();
    }

    // busca a propaganda pelo projeto
    @Override
    public Object read(Object input) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
        this.ps.setInt(1,(int) input);
        this.rs = this.ps.executeQuery();

        if(this.rs.isLast())
            return this.criarObjetoTemplate();

        return this.buscarVariosObjetosTemplate();
    }

    @Override
    public ArrayList<Object> getAll() throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
        this.rs = this.ps.executeQuery();

        return this.buscarVariosObjetosTemplate();
    }
    
    @Override
    public void delete(Object input) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETE);
        this.ps.setInt(1,(int) input);

        if(this.ps.executeUpdate() == 0)
            throw new Exception("Propaganda nao foi deletada");
    }
    
    // deletar propaganda pelo projeto
    public void deletarProjeto(int projeto) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETEPROJ);
        this.ps.setInt(1,projeto);

        if(this.ps.executeUpdate() == 0)
            throw new Exception("Propaganda nao foi deletada");
    }
    
    public float somarTarifa(int projeto) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_SUMTAXES);
        this.ps.setInt(1,projeto);
        this.rs = this.ps.executeQuery();

        if(!this.rs.next())
            throw new Exception("Nao foi encontrada tarifas");

        return this.rs.getFloat(1);
    }
    
    public float somarDespesa(int projeto) throws SQLException, Exception {
        this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_SUMEMPPAY);
        this.ps.setInt(1,projeto);
        this.rs = this.ps.executeQuery();

        if(!this.rs.next())
            throw new Exception("Nao foi encontrada despesas");

        return this.rs.getFloat(1);
    }
    
}
