package dao.impl;

import dao.ProfissionalDaoJDBC;
import db.DB;

public class DaoFactory {
    public static ProfissionalDao createDeparmentDao() {
        return new ProfissionalDaoJDBC(DB.getConnection());
    }
}
