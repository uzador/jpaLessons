package ru.roseurobank.persistentcontext;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by MuratovaO on 18.01.2015.
 */
public class AuditInterceptor extends EmptyInterceptor {

    private Session session;
//    private Long userId;

    private Set<Category> inserts = new HashSet<>();
    private Set<Category> updates = new HashSet<>();

    public void setSession(Session session) {
        this.session = session;
    }

//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable) {
            inserts.add((Category) entity);
        }
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof Auditable) {
            updates.add((Category) entity);
        }
        return false;
    }

    @Override
    public void postFlush(Iterator entities) {
        for (Iterator it = inserts.iterator(); it.hasNext();) {
            Auditable entity = (Auditable) it.next();
            System.out.println((Category) entity);
        }
    }

    public Set<Category> getInserts() {
        return inserts;
    }

    public Set<Category> getUpdates() {
        return updates;
    }
}
