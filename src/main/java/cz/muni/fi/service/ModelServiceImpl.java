package cz.muni.fi.service;


import cz.muni.fi.dto.SiteDto;
import cz.muni.fi.model.ModelDao;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.structural.dual.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelServiceImpl implements ModelService {

    private ModelDao modelDao;

    @Autowired
    public ModelServiceImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
        this.modelDao.updateTrafficModel(new TrafficModel());
    }

    public List<SiteDto> getAllSites() {
        DualGraph dualGraph = modelDao.getStructuralModel().getDualGraph();
        List<SiteDto> resultSites = new ArrayList<>();
        for(Site site : dualGraph.getAllSites()) {
            SiteDto siteDto = new SiteDto();
            siteDto.setCoordinates(site.getCoordinates());
            resultSites.add(siteDto);
        }
        return resultSites;
    }
}
