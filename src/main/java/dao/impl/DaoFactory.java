package dao.impl;

import dao.PacientesDaoJDBC;
import dao.ProfissionalDaoJDBC;
import db.DB;

public class DaoFactory {
    public static ProfissionalDao createProfissionalDao() {
        return new ProfissionalDaoJDBC(DB.getConnection());
    }
    public static PacientesDao createPacienteDao() {
        return new PacientesDaoJDBC(DB.getConnection());
    }
}
