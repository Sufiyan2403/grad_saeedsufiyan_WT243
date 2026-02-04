package dao;

import java.util.List;

import model.MaintenancePayment;
import model.Site;

public interface MaintenancePaymentDAO {
    boolean createPayment(MaintenancePayment payment);

    List<MaintenancePayment> findBySiteId(int siteId);

    List<Site> findPendingSites(String month);
}
