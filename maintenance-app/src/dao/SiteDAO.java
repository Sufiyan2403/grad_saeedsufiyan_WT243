package dao;

import java.util.List;

import model.Site;

public interface SiteDAO {
    Site findBySiteNumber(int siteNumber);

    Site findBySiteNumberAndOwner(int siteNumber, int ownerId);

    List<Site> findByOwnerId(int ownerId);

    List<Site> findAll();

    boolean updateSite(Site site);

    boolean assignSiteToOwner(int siteNumber, int ownerId, String siteType, String status);
}
