package cz.muni.fi.parser;

import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.structural.dual.GeographicCoordinates;
import cz.muni.fi.model.structural.dual.Site;
import cz.muni.fi.model.structural.primal.Link;
import cz.muni.fi.model.structural.primal.Node;
import cz.muni.fi.model.structural.primal.PrimalGraph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class StructuralModelParserImpl implements StructuralModelParser {
    private JSONObject json;

    public StructuralModelParserImpl(JSONObject json) {
        this.json = json;
    }

    public StructuralModel parseStructuralModel() {
        JSONObject primalGraphJson = json.getJSONObject("graph_primal");
        PrimalGraph primalGraph = parsePrimalGraph(primalGraphJson);
        JSONObject dualGraphJson = json.getJSONObject("graph_dual");
        DualGraph dualGraph = parseDualGraph(dualGraphJson);
        return new StructuralModel(primalGraph, dualGraph);
    }

    private PrimalGraph parsePrimalGraph(JSONObject primalGraphJson) {
        Map<Long, Node> nodes = parseNodesOfPrimalGraph(primalGraphJson);
        Set<Link> links = parseLinksOfPrimalGraph(primalGraphJson, nodes);
        return new PrimalGraph(nodes, links);
    }

    private Map<Long, Node> parseNodesOfPrimalGraph(JSONObject primalGraphJson) {
        Map<Long, Node> resultNodes = new HashMap<>();
        JSONArray vertices = primalGraphJson.getJSONArray("V");
        for (int i = 0; i < vertices.length(); i++) {
            long key = vertices.getLong(i);
            Node node = new Node(key);
            resultNodes.put(key, node);
        }
        return resultNodes;
    }

    private Set<Link> parseLinksOfPrimalGraph(JSONObject primalGraphJson, Map<Long, Node> nodes) {
        Set<Link> resultLinks = new HashSet<>();
        JSONArray edges = primalGraphJson.getJSONArray("E");
        for (int i = 0; i < edges.length(); i++) {
            JSONArray linkJson = edges.getJSONArray(i);
            try {
                Link link = parseLink(linkJson, nodes);
                resultLinks.add(link);
                link.getFirstNode().addLink(link);
                link.getSecondNode().addLink(link);
            } catch (NullPointerException ex) {
//                    ex.printStackTrace();
            }
        }
        return resultLinks;
    }

    private Link parseLink(JSONArray linkJson, Map<Long, Node> nodes) {
        long fromKey = linkJson.getLong(0);
        long toKey = linkJson.getLong(1);
        Node fromNode = nodes.get(fromKey);
        Node toNode = nodes.get(toKey);
        return new Link(fromNode, toNode);
    }

    private DualGraph parseDualGraph(JSONObject dualGraphJson) {
        Map<Long, Site> sites = parseSitesOfDualGraph(dualGraphJson);
        return new DualGraph(sites);
    }

    private Map<Long, Site> parseSitesOfDualGraph(JSONObject dualGraphJson) {
        Map<Long, Site> resultSites = new HashMap<>();
        for (String key : dualGraphJson.keySet()) {
            int siteId = Integer.parseInt(key);
            JSONObject siteJson = dualGraphJson.getJSONObject(key);
            Site site = parseSiteWithoutArms(siteId, siteJson);
            resultSites.put(Long.parseLong(key), site);
        }
        for (Map.Entry<Long, Site> entry : resultSites.entrySet()) {
            String key = entry.getKey().toString();
            JSONArray siteArmsJson = dualGraphJson.getJSONObject(key).getJSONArray("arms");
            List<Arm> arms = parseSiteArms(siteArmsJson, resultSites);
            entry.getValue().setArms(arms);
        }
        return resultSites;
    }

    private Site parseSiteWithoutArms(int siteId, JSONObject siteJson) {
        double latitude = siteJson.getDouble("latitude");
        double longitude = siteJson.getDouble("longitude");
        GeographicCoordinates coordinates = new GeographicCoordinates(latitude, longitude);
        return new Site(siteId, coordinates);
    }

    private List<Arm> parseSiteArms(JSONArray armsArrayJson, Map<Long, Site> sites) {
        List<Arm> resultArms = new ArrayList<>();
        for (int i = 0; i < armsArrayJson.length(); i++) {
            JSONObject armJson = armsArrayJson.getJSONObject(i);
            resultArms.add(parseArm(armJson, sites));
        }
        return resultArms;
    }

    private Arm parseArm(JSONObject armJson, Map<Long, Site> sites) {
        Arm.Builder armBuilder = new Arm.Builder();

        int armAngle = armJson.getInt("arm_angle");
        armBuilder.armAngle(armAngle);

        int armNum = armJson.getInt("arm_num");
        armBuilder.armNum(armNum);

        int armOffset = armJson.getInt("arm_offset");
        armBuilder.armOffset(armOffset);

        int leftLanes = armJson.getInt("left_lanes");
        armBuilder.leftLanes(leftLanes);

        int rightLanes = armJson.getInt("right_lanes");
        armBuilder.rightLanes(rightLanes);

        if (armJson.has("osm_way_id")) {
            long osmWayId = armJson.getLong("osm_way_id");
            armBuilder.osmWayId(osmWayId);
        }

        String streetName = armJson.getString("street_name");
        armBuilder.streetName(streetName);

        long nextSiteId = armJson.getLong("next_site");
        armBuilder.nextSite(sites.get(nextSiteId));

        return armBuilder.build();
    }
}