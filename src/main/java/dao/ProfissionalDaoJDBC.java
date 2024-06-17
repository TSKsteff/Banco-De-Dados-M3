package dao;

import dao.impl.ProfissionalDao;
import db.DB;
import db.DbExeption;
import entities.Profissional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalDaoJDBC implements ProfissionalDao {
        private Connection conn;

        public ProfissionalDaoJDBC(Connection conn) {
            this.conn = conn;
        }

        @Override
        public void insert(Profissional obj) {
            PreparedStatement st = null;

            try {
                st = conn.prepareStatement("Insert Into profissional "
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
        public void update(Profissional obj) {
            PreparedStatement st = null;

            try {
                st = conn.prepareStatement("Update profissional "
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
                st = conn.prepareStatement("DELETE  FROM profissional WHERE Id = ?");

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
    public void deleteByAtivo(Profissional obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("Update profissional "
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
        public Profissional findById(Integer id) {

            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement("Select * From profissional Where Id = ?");

                st.setInt(1, id);

                rs = st.executeQuery();

                if(rs.next()){
                    Profissional depp = instantieProfissional(rs);
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
        public List<Profissional> finall() {
            PreparedStatement st = null;
            ResultSet rs = null;
            List<Profissional> list = new ArrayList<>();
            try {
                st = conn.prepareStatement("SELECT * FROM profissional ORDER BY Name");
                rs = st.executeQuery();

                while (rs.next()) {
                    list.add(instantieProfissional(rs));
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


        private Profissional instantieProfissional(ResultSet rs) throws SQLException {
            Profissional dep = new Profissional();
            dep.setId(rs.getInt("ProfissionalId"));
            dep.setNome(rs.getString("DepName"));
            return dep;
        }
}
