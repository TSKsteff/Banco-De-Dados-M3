package dao;

import dao.impl.PacientesDao;
import db.DB;
import db.DbExeption;
import entities.Pacientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesDaoJDBC implements  PacientesDao{
    private Connection conn;

    public PacientesDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Pacientes obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("Insert Into paciente "
                    +"(Name) "
                    +"(CPF)"
                    +("ativo")
                    +"(?) ", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCpf());
            st.setBoolean(1, obj.getAtivo());

            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultset(rs);
            }
            else {
                throw new DbExeption("Unexpected error! No rows affected!");
            }
        }
        catch (Exception e) {
            throw new DbExeption("Error: "+e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }



    @Override
    public void update(Pacientes obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("Update paciente "
                    +"Set name = ? "
                    +"Set cpf = ?"
                    +"Where Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCpf());
            st.setInt(2, obj.getId());

            st.executeUpdate();

        }
        catch (Exception e) {
            throw new DbExeption("Error:"+e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE  FROM paciente WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();

        } catch (Exception e) {
            throw new DbExeption("Error:"+ e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteByAtivo(Pacientes obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("Update paciente "
                    +"Set ativo = ? "
                    +"Where Id = ?");

            st.setBoolean (1, obj.getAtivo());
            st.setInt(2, obj.getId());

            st.executeUpdate();

        }
        catch (Exception e) {
            throw new DbExeption("Error:"+e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Pacientes findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("Select * From paciente Where Id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if(rs.next()){
                Pacientes depp = instantiePacientes(rs);
                return depp;
            }
        }
        catch (SQLException e) {
            throw new DbExeption("Error: "+e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
        return null;
    }

    @Override
    public List<Pacientes> finall() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Pacientes> list = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT * FROM paciente ORDER BY Name");
            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiePacientes(rs));
            }
        }
        catch (SQLException e) {
            throw new DbExeption("Error: "+e.getMessage());
        }
        finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);
        }

        return list;
    }


    private Pacientes instantiePacientes(ResultSet rs) throws SQLException {
        Pacientes dep = new Pacientes();
        dep.setId(rs.getInt("PacientesId"));
        dep.setNome(rs.getString("DepName"));
        return dep;
    }
}
