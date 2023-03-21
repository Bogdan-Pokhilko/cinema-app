package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        Role.RoleName role = Role.RoleName.valueOf(roleName);
        try (Session session = factory.openSession()) {
            Query<Role> query = session.createQuery("FROM Role "
                    + "WHERE roleName = :role", Role.class);
            query.setParameter("role", role);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can not find role: " + roleName, e);
        }
    }
}
