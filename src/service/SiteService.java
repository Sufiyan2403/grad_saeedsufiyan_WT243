package service;

import java.util.List;

import dao.SiteDAO;
import dao.impl.SiteDAOImpl;
import model.Site;

public class SiteService {
    private SiteDAO siteDAO;

    public SiteService() {
        this.siteDAO = new SiteDAOImpl();
    }

    public boolean assignSiteToOwner(int siteNumber, int ownerId, String siteType, String status) {
        if (siteDAO.assignSiteToOwner(siteNumber, ownerId, siteType, status)) {
            System.out.println("Site assigned successfully!");
            return true;
        }
        return false;
    }

    public boolean updateSiteStatus(int siteNumber, String status) {
        Site site = siteDAO.findBySiteNumber(siteNumber);
        if (site == null) {
            System.out.println("Site not found!");
            return false;
        }

        site.setStatus(status);
        if (siteDAO.updateSite(site)) {
            System.out.println("Site status updated successfully!");
            return true;
        }
        return false;
    }

    public List<Site> getAllSites() {
        return siteDAO.findAll();
    }

    public List<Site> getSitesByOwner(int ownerId) {
        return siteDAO.findByOwnerId(ownerId);
    }

    public Site getSiteBySiteNumber(int siteNumber) {
        return siteDAO.findBySiteNumber(siteNumber);
    }

    public Site getSiteByNumberAndOwner(int siteNumber, int ownerId) {
        return siteDAO.findBySiteNumberAndOwner(siteNumber, ownerId);
    }
}
